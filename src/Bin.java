import java.util.ArrayList;
import java.util.List;

public class Bin {
    private final List<Item> items;
    private final int capacity;

    public Bin(int capacity) {
        this.items = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    public void add(Item item) {
        this.items.add(item);
    }
    public void remove(Item item) {this.items.remove(item);}
    public int size() {
        return items.size();
    }

    public int getTotalLength() {
        int length = 0;
        for (Item item : this.items) {
            length += item.getValue();
        }
        return length;
    }
    public int getRemainingLength() {
        return this.capacity - this.getTotalLength();
    }
    public int getCapacity() {
        return this.capacity;
    }
    public List<Item> getItems() {
        return this.items;
    }
}
