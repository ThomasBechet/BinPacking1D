import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public List<Item> items = new ArrayList<>();
    public int binSize;

    public DataSet(String file) {
        //parser
    }

    public DataSet(List<Item> items, int binSize) {
        this.items = items;
        this.binSize = binSize;
    }
}
