import matplotlib.pyplot as plt
import sys

if (len(sys.argv) < 2):
    sys.exit(1)

fig, ax = plt.subplots()
ax.set_title('Title')

for i, record in enumerate(sys.argv[1:]):
    f = open(record)
    first_line  = f.readline()
    second_line = f.readline() 
    lines = f.readlines()

    first_fit_sorted = int(first_line.split(' ')[1])
    first_fit        = int(first_line.split(' ')[2])
    ax.axhline(y=first_fit_sorted, color='r', linestyle='-')
    ax.axhline(y=first_fit, color='g', linestyle='-')

    RS_length = int(second_line.split(' ')[0])
    TS_length = int(second_line.split(' ')[1])

    x = []
    y = []
    for i, line in enumerate(lines[:RS_length]):
        x.append(i)
        y.append(int(line))
    ax.plot(x, y, label='l1')

    x = []
    y = []
    for i, line in enumerate(lines[RS_length:(RS_length + TS_length)]):
        x.append(i)
        y.append(int(line))
    ax.plot(x, y, label='l2')

# plt.legend()
ax.grid(True)
ax.ticklabel_format(axis="x", style="plain", scilimits=(0,0))
ax.ticklabel_format(axis="y", style="plain", scilimits=(0,0))
ax.set_xlabel('iteration number')
ax.set_ylabel('fitness (higher is better)')
plt.show()