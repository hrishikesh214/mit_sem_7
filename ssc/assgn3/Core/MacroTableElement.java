package Core;

import java.io.Serializable;
import java.util.*;

public class MacroTableElement implements Serializable {
    public String index;
    public String col1;
    public String col2;
    public MacroTable ala;

    public MacroTableElement(String _index, String _col1, String _col2) {
        index = _index;
        col1 = _col1;
        col2 = _col2;

        ala = new MacroTable("ALA");
    }

    public String toString() {
        if (col2 == null) {
            return String.format("%s\t%s", index, col1);
        }
        return String.format("%s\t%s\t%s", index, col1, col2);
    }

}