package Core;

import java.io.Serializable;
import java.util.*;
// import Core.AssemblerICTable;
// import Core.AssemblerTable;

public class AssemblerIntermediateResult implements Serializable {
    public AssemblerICTable ic;
    public AssemblerTable symbolTable;
    public AssemblerTable registerTable;
    public List<Integer> poolTable;
    public AssemblerTable literalTable;

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String x = "";
        x += "ADDRESS\tINSTRUCTION\tOPERAND1\tOPERAND2\n";
        x += ic.toString("\t", symbolTable, literalTable) + "\n";
        x += "SYMBOL TABLE: \n" + symbolTable.toString() + "\n";
        x += "LITERAL TABLE: \n" + literalTable.toString() + "\n";
        x += "POOL TABLE\n";
        for (int i = 0; i < poolTable.size(); i++) {
            x += i + "\t" + poolTable.get(i) + "\n";
        }
        return x;
    }
}
