import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public List<Item> items = new ArrayList<>();
    public int binSize;
    public int itemCount;

    public DataSet(String filename) throws IOException {
        File file = new File("data/" + filename);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        int index = 0;
        while((data = br.readLine()) != null) {
            if(index == 0) {
                String[] firstData = data.split("\\s+");
                if(firstData.length > 1) {
                    binSize = Integer.parseInt(firstData[0]);
                    itemCount = Integer.parseInt(firstData[1]);
                }
            }
            else {
                items.add(new Item(Integer.parseInt(data)));
            }

            index++;
        }

        System.out.println("File parsed : " + filename);
    }

    public DataSet(List<Item> items, int binSize) {
        this.items = items;
        this.binSize = binSize;
    }

    public int getLowerBound() {
        int size = 0;
        for(Item item : items) {
            size += item.getValue();
        }

        if(size % binSize == 0) {
            return size / binSize;
        }

        return (size / binSize) + 1;
    }

    public int getUpperBound() {
        return items.size();
    }
}
