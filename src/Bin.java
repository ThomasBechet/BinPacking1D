import java.util.ArrayList;
import java.util.List;

public class Bin {
    private List<Item> items;

    public Bin(int capacity) {
        this.items = new ArrayList<>(capacity);
    }

    public int add(Item item) {
        this.items.add(item);
    }

    public int size() {
        return items.size();
    }
}
