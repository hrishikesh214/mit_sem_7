package Core;

import java.io.Serializable;
import java.util.*;
import Core.MacroTableElement;

public class MacroTable implements Serializable {
    Hashtable<String, MacroTableElement> thisTable;
    String thisName;

    public MacroTable(String __name) {
        thisTable = new Hashtable<>();
        thisName = __name;
    }

    public void set(String __index, String col1, String col2) {
        thisTable.put(__index, new MacroTableElement(__index, col1, col2));
    }

    public MacroTableElement get(String __index) {
        return thisTable.get(__index);
    }

    public MacroTableElement getArg(String x) {
        MacroTableElement ele = null;
        for (String key : this.thisTable.keySet()) {
            if (thisTable.get(key).col1.equals(x)) {
                ele = thisTable.get(key);
                break;
            }
        }

        return ele;
    }

    public String toString() {
        String x = new String();
        List<String> keys = new ArrayList<>(thisTable.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            x += this.thisTable.get(key).toString() + "\n";
            // System.out.println(key);
        }

        return x;
    }

    public String ala_toString() {
        String x = new String();
        List<String> keys = new ArrayList<>(thisTable.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            x += this.thisTable.get(key).ala.toString() + "\n";
            // System.out.println(key);
        }

        return x;
    }

    public MacroTableElement findAndGetCol1(String x) {
        MacroTableElement ele = null;
        for (String key : this.thisTable.keySet()) {
            if (thisTable.get(key).col1.equals(x)) {
                ele = thisTable.get(key);
                break;
            }
        }

        return ele;
    }

}