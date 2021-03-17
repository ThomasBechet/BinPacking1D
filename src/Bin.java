import java.util.ArrayList;
import java.util.List;

public class Bin {
    private final List<Item> items;

    public Bin(int capacity) {
        this.items = new ArrayList<>(capacity);
    }

    public void add(Item item) {
        this.items.add(item);
    }

    public int size() {
        return items.size();
    }
}
