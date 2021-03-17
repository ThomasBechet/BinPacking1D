import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private List<Item> items;
    private int binCapacity;

    public int getBinCapacity() {
        return this.binCapacity;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public DataSet(String filename) throws IOException {
        File file = new File("data/" + filename);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        int index = 0;
        while((data = br.readLine()) != null) {
            if(index == 0) {
                String[] firstData = data.split("\\s+");
                if(firstData.length > 1) {
                    this.binCapacity = Integer.parseInt(firstData[0]);
                    this.items = new ArrayList<>(Integer.parseInt(firstData[1]));
                }
            } else {
                this.items.add(new Item(Integer.parseInt(data)));
            }

            index++;
        }

        System.out.println("File parsed : " + filename);
    }

    public DataSet(List<Item> items, int binCapacity) {
        this.items = items;
        this.binCapacity = binCapacity;
    }

    public int getLowerBound() {
        int size = 0;
        for(Item item : items) {
            size += item.getValue();
        }

        if(size % this.binCapacity == 0) {
            return size / this.binCapacity;
        }

        return (size / this.binCapacity) + 1;
    }

    public int getUpperBound() {
        return items.size();
    }
}
