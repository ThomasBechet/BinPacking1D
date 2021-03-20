import java.util.ArrayList;
import java.util.List;

public class Bin {
    private final List<Item> items;
    private final int capacity;
    private int totalLength;
    private int minLength;

    public Bin(int capacity) {
        this.items = new ArrayList<>(capacity);
        this.capacity = capacity;
        this.totalLength = 0;
        this.minLength = Integer.MAX_VALUE;
    }

    public Bin(Bin bin) {
        this.items = new ArrayList<>(bin.items);
        this.capacity = bin.capacity;
        this.totalLength = bin.totalLength;
        this.minLength = bin.minLength;
    }

    public void add(Item item) {
        this.items.add(item);
        this.totalLength += item.getValue();
        this.minLength = item.getValue() < this.minLength ? item.getValue() : this.minLength;
    }
    public void remove(Item item) {
        this.items.remove(item);
        this.totalLength -= item.getValue();

        // Update max length
        this.minLength = Integer.MAX_VALUE;
        for (Item i : this.items) {
            this.minLength = i.getValue() < this.minLength ? i.getValue() : this.minLength;
        }
    }
    public int size() {
        return items.size();
    }

    public int getMinLength() {
        return this.minLength;
    }
    public int getTotalLength() {
        return this.totalLength;
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
