package Pass;

import Core.*;
import java.util.*;

public class Two {
    public AssemblerICTable ic;
    public AssemblerTable symbolTable;
    public AssemblerTable registerTable;
    public List<Integer> poolTable;
    public AssemblerTable literalTable;

    public Two(AssemblerIntermediateResult intermediate_result) {
        ic = intermediate_result.ic;
        symbolTable = intermediate_result.symbolTable;
        registerTable = intermediate_result.registerTable;
        poolTable = intermediate_result.poolTable;
        literalTable = intermediate_result.literalTable;
    }

    public String run() {
        String machineCode = "";
        for (ICElement curr : ic.getTable()) {
            String final_row = "";

            String[] row = curr.getString(";", symbolTable, literalTable).split(";");
            if (row.length < 4)
                continue;
            // System.out.println(curr.getString(";", symbolTable, literalTable));

            // extract instruction
            String[] instruction = row[1].substring(1, row[1].length() - 1).split(",");
            String instruction_type = instruction[0].trim();
            String instruction_opcode = instruction[1].trim();
            if (instruction_type.equals("AD") && instruction_opcode != "05") {
                continue;
            }

            // extract operand1
            String[] operand1 = row[2] == "" ? null
                    : row[2].substring(1, row[2].length()
                            - 1).split(",");
            String operand1_type = operand1 == null ? null : operand1[0].trim();
            String operand1_opcode = operand1 == null ? null : operand1[1].trim();

            // extract operand2
            String[] operand2 = row[3] == "" ? null
                    : row[3].substring(1, row[3].length()
                            - 1).split(",");
            String operand2_type = operand2 == null ? null : operand2[0].trim();
            String operand2_opcode = operand2 == null ? null : operand2[1].trim();

            final_row += instruction_opcode + " ";
            if (operand1_type != null && operand1_type.equals("R")) {
                final_row += operand1_opcode + " ";
            } else {
                final_row += "00" + " ";
            }

            if (operand2_type == null) {
                final_row += "00" + " ";
            } else if (operand2_type.equals("S")) {
                final_row += symbolTable.getElementFromIndex(Integer.parseInt(operand2_opcode)).address + " ";
            } else if (operand2_type.equals("L")) {
                final_row += literalTable.getElementFromIndex(Integer.parseInt(operand2_opcode)).address + " ";
            } else if (operand2_type.equals("C")) {
                final_row += operand2_opcode + " ";
            }

            machineCode += final_row + "\n";
            // System.out.println("\n");
        }

        return machineCode;
    }

}

/*
 * 
 * ADDRESS INSTRUCTION OPERAND1 OPERAND2
 * 
 * (AD, 01) (C, 100)
 * 101 (LS, 09) (S, 3)
 * 102 (LS, 04) (R, 01) (S, 1)
 * 103 (LS, 05) (R, 00) (S, 2)
 * 104 (LS, 05) (R, 03) (L, 0)
 * 105 (LS, 05) (R, 04) (L, 1)
 * 106 (AD, 03) (C, 106)
 * 112 (AD, 05) (DL, 01) (C, 12)
 * 113 (AD, 05) (DL, 01) (C, 1)
 * 114 (LS, 00)
 * 117
 * (AD, 02)
 * 
 * SYMBOL TABLE:
 * 1 ONE 115
 * 0 l1 102
 * 2 TERM 118
 * 3 N 119
 * 
 * LITERAL TABLE:
 * 1 ='12' 112
 * 0 ='1' 113
 * 
 * POOL TABLE
 * 0 0
 * 1 2
 * 
 * 
 * Machine Code
 * 09 00 119
 * 04 01 115
 * 05 00 118
 * 05 03 102
 * ...
 */