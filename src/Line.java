import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Line {
    String id;
    Double value;

    public Line(String id, Double value) {
        this.id = id;
        this.value = value;
    }

    public void addValue(Double amount) {
        this.value += amount;
    }

    public static ArrayList<Line> merge(ArrayList<Line> h) {
        HashMap<String, Line> hash = new HashMap<>();

        for(int i = 0; i < h.size(); i++){
            for (int j = i+1; j < h.size(); j++) {
                if(h.get(i).id.equals(h.get(j).id)) {
                    h.get(j).addValue(h.get(i).value);
                    hash.put(h.get(j).id, h.get(j));
                }
            }
        }

        ArrayList<Line> h2 = new ArrayList<>(hash.values());

        h2.sort(new LineSorter());

        return h2;
    }

    @Override
    public String toString() {
        return id + "," + value;
    }
}

class LineSorter implements Comparator<Line> {
    public int compare(Line a, Line b) {
        return a.id.compareTo(b.id);
    }
}