package Core;

import java.util.*;
// import Core.AssemblerICTable;
// import Core.AssemblerTable;

public class AssemblerIntermediateResult {
    public AssemblerICTable ic;
    public AssemblerTable symbolTable;
    public AssemblerTable registerTable;
    public List<Integer> poolTable;
    public AssemblerTable literalTable;

    public void print() {
        System.out.println("ADDRESS\tINSTRUCTION\tOPERAND1\tOPERAND2\n");
        System.out.println(ic.toString("\t", symbolTable,
                literalTable));
        System.out.println("SYMBOL TABLE: \n" + symbolTable.toString());
        System.out.println("LITERAL TABLE: \n" + literalTable.toString());

        System.out.println("POOL TABLE");
        for (int i = 0; i < poolTable.size(); i++) {
            System.out.println(i + "\t" + poolTable.get(i));
        }
    }
}
