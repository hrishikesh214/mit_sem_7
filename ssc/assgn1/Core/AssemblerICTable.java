package Core;

import Core.ICElement;
import java.util.*;

public class AssemblerICTable {
    List<ICElement> thisTable;

    public AssemblerICTable() {
        thisTable = new ArrayList<ICElement>();
    }

    public void add(String __name, Integer __address, String __instruction, String __operand1, String __operand2) {
        // System.out.println("add(" + __name + " " + __address + " " + __instruction +
        // " " + __operand1 + " "
        // + __operand2 + ")");
        thisTable.add(new ICElement(__address, __instruction, __operand1, __operand2));
    }

    public String toString(String __delimiter, AssemblerTable symbolTable, AssemblerTable literalTable) {
        String x = new String();
        for (int i = 0; i < thisTable.size(); i++) {
            x += thisTable.get(i).getString(__delimiter, symbolTable, literalTable) + "\n";
        }
        return x;
    }

    public List<ICElement> getTable() {
        return thisTable;
    }
}