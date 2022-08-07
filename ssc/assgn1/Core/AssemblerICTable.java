package Core;

// import Core.AssemblerTableElement;
import java.util.*;

class ICElement {
    Integer address;
    String instruction;
    String operand1;
    String operand2;

    public ICElement(Integer __address, String __instruction, String __operand1, String __operand2) {
        this.address = __address == null ? -1 : __address;
        this.instruction = __instruction == null ? "" : __instruction;
        this.operand1 = __operand1 == null ? "" : __operand1;
        this.operand2 = __operand2 == null ? "" : __operand2;
    }

    public String getString(String __delimiter, AssemblerTable symbolTable, AssemblerTable literalTable) {
        if (symbolTable.containsKey(operand1)) {
            operand1 = symbolTable.get(operand1).opcode;
            operand1 = String.format("(S, %s)", operand1);
        } else if (literalTable.containsKey(operand1)) {
            operand1 = literalTable.get(operand1).opcode;
            operand1 = String.format("(L, %s)", operand1);
        }
        if (symbolTable.containsKey(operand2)) {
            operand2 = symbolTable.get(operand2).opcode;
            operand2 = String.format("(S, %s)", operand2);
        } else if (literalTable.containsKey(operand2)) {
            operand2 = literalTable.get(operand2).opcode;
            operand2 = String.format("(L, %s)", operand2);
        }
        return (this.address == -1 ? ""
                : this.address) + __delimiter + this.instruction + __delimiter + this.operand1 + __delimiter
                + this.operand2;
    }
}

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
}