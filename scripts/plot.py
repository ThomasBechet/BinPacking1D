import matplotlib
import matplotlib.pyplot as plt
from matplotlib import transforms
import sys
import math

if (len(sys.argv) < 2):
    sys.exit(1)

# Fitness graph
fig, ax = plt.subplots()
ax.set_title('Evolution of fitness with iterations')

for i, record in enumerate(sys.argv[1:]):
    f = open(record)
    first_line  = f.readline()
    second_line = f.readline() 
    lines = f.readlines()

    first_fit_sorted = int(first_line.split(' ')[1])
    first_fit        = int(first_line.split(' ')[2])
    ax.axhline(y=first_fit_sorted, color='r', linestyle='-', label='first fit sorted')
    ax.axhline(y=first_fit, color='g', linestyle='-', label='first fit')

    trans = transforms.blended_transform_factory(
        ax.get_yticklabels()[0].get_transform(), ax.transData)

    ax.axhline(y=61124685, linewidth=0.5, color='red', linestyle='-')
    ax.text(0, 61124685, str(61124685), color="red", transform=trans, 
        ha="right", va="center")

    RS_length = int(second_line.split(' ')[0])
    TS_length = int(second_line.split(' ')[1])

    # Recuit simulé
    x = []
    fitnesses = []
    for i, line in enumerate(lines[:RS_length]):
        fitness = line.split(' ')[0]
        x.append(i)
        fitnesses.append(int(fitness))
    ax.plot(x, fitnesses, linewidth=0.6, label='recuit simulé')

    # Tabu Search
    x = []
    y = []
    for i, line in enumerate(lines[RS_length:(RS_length + TS_length)]):
        x.append(i)
        y.append(int(line))
    ax.plot(x, y, linewidth=0.6, label='tabu search')

# plt.legend()
ax.grid(True)
ax.ticklabel_format(axis="x", style="plain", scilimits=(0,0))
ax.ticklabel_format(axis="y", style="plain", scilimits=(0,0))
ax.set_xlabel('iteration number')
ax.set_ylabel('fitness (higher is better)')
plt.legend(loc="lower right")
plt.savefig('fitness.png', bbox_inches='tight', dpi=300)