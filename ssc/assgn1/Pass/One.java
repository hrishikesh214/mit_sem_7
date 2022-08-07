package Pass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import Core.*;

public class One {
    public String FILE_NAME;
    AssemblerTable symbolTable;
    AssemblerICTable ic;
    AssemblerTable optab;
    AssemblerTable registerTable;
    List<Integer> poolTable;
    List<String> literalTable;

    public One(String __fn) {
        FILE_NAME = __fn;
    }

    boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    boolean isSymbol(String str) {
        return !(optab.containsKey(str) || isNumeric(str));
    }

    String getParsedOperandForInstruction(String x) {
        if (registerTable.containsKey(x)) {
            x = registerTable.get(x).toString();
        } else if (isSymbol(x)) {
            symbolTable.set(x, "S", null);
        }

        return x;
    }

    AssemblerTable getOptab() {
        AssemblerTable optab = new AssemblerTable("OPTAB");

        optab.set("STOP", "LS", "00");
        optab.set("ADD", "LS", "01");
        optab.set("SUB", "LS", "02");
        optab.set("MULT", "LS", "03");
        optab.set("MOVER", "LS", "04");
        optab.set("MOVEM", "LS", "05");
        optab.set("COMP", "LS", "06");
        optab.set("BC", "LS", "07");
        optab.set("DIV", "LS", "08");
        optab.set("READ", "LS", "09");
        optab.set("PRINT", "LS", "10");

        optab.set("DC", "DL", "01");
        optab.set("DS", "DL", "02");

        optab.set("START", "AD", "01");
        optab.set("END", "AD", "02");
        optab.set("ORIGIN", "AD", "03");
        optab.set("EQU", "AD", "04");
        optab.set("LTORG", "AD", "05");

        return optab;

    }

    AssemblerTable getRegTable() {
        AssemblerTable regTable = new AssemblerTable("REGISTER");
        String optype = "R";
        regTable.set("AREG", optype, "00");
        regTable.set("BREG", optype, "01");
        regTable.set("CREG", optype, "03");
        regTable.set("DREG", optype, "04");
        return regTable;
    }

    public AssemblerIntermediateResult run() {
        try {
            AssemblerIntermediateResult result = new AssemblerIntermediateResult();
            ic = new AssemblerICTable();
            optab = getOptab();
            registerTable = getRegTable();
            symbolTable = new AssemblerTable("SYMBOL");
            List<Integer> poolTable = new ArrayList<Integer>();
            poolTable.add(0);

            boolean isStop = false;
            int lc = 0;
            int symbolCount = 0;

            // reading file
            File inputFile = new File(FILE_NAME);
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String inputLine;
                try {

                    while ((inputLine = br.readLine()) != null) {
                        StringTokenizer tokens = new StringTokenizer(inputLine);
                        String nextToken = tokens.nextToken();

                        if (nextToken.equals("END")) { // * CHECK FOR END
                            ic.add(nextToken, null, optab.get("END").toString(), null, null);
                        } else if (nextToken.equals("START")) {// * CHECK FOR START
                            if (tokens.hasMoreTokens()) {
                                int startAddr = Integer.parseInt(tokens.nextToken());
                                lc = startAddr - 1;
                                AssemblerTableElement element = new AssemblerTableElement("START", "C",
                                        Integer.toString(lc));
                                ic.add(nextToken, null, optab.get("START").toString(), null, element.toString());
                            } else {
                                System.out.println("ERROR: START directive must have an operand");
                                return null;
                            }
                        } else if (nextToken.equals("ORIGIN")) { // * CHECK FOR ORIGIN
                            if (tokens.hasMoreTokens()) {
                                // eg type: X+3
                                // ! STILL REMAINING TO IMPLEMENT
                            } else {
                                System.out.println("ERROR: ORIGIN directive must have an operand");
                                return null;
                            }
                        } else if (isStop) {
                            // * now only declarative statements should be there
                            if (!tokens.hasMoreTokens()) {
                                System.out.println("ERROR: No more tokens");
                                return null;
                            }
                            String symbolLabel = nextToken;
                            String inst = tokens.nextToken();
                            if (!tokens.hasMoreTokens()) {
                                System.out.println("ERROR: No more tokens");
                                return null;
                            }
                            String operand1 = tokens.nextToken();

                            if (inst.equals("DC")) {
                                symbolTable.update(symbolLabel, String.format("%d", (symbolCount)),
                                        (lc), Integer.parseInt(operand1));
                            } else if (inst.equals("DS")) {
                                int spaceCount = Integer.parseInt(operand1);
                                lc += spaceCount;
                                for (int i = 0; i < spaceCount; i++) {
                                    ic.add("-", lc, null, null, null);
                                    lc++;
                                }
                                symbolTable.update(symbolLabel, Integer.toString(spaceCount), (lc), 0);
                            }
                            symbolCount++;
                        } else {
                            // * ELSE take it as general token and check
                            AssemblerTableElement ate = optab.get(nextToken);
                            if (nextToken.equals("STOP")) {
                                isStop = true;
                            }
                            if (ate == null) {
                                // ic.add(nextToken, lc, null, null, null);
                                // * this means its label

                            } else {

                                // * if it is a directive
                                String operand1, operand2;
                                if (tokens.hasMoreTokens()) {
                                    operand1 = getParsedOperandForInstruction(tokens.nextToken());

                                    if (tokens.hasMoreTokens()) {
                                        operand2 = getParsedOperandForInstruction(tokens.nextToken());
                                        ic.add(nextToken, lc, ate.toString(), operand1, operand2);
                                    } else
                                        ic.add(nextToken, lc, ate.toString(), null, operand1);
                                }
                                ic.add(nextToken, lc, ate.toString(), null, null);
                            }
                        }
                        lc++;
                    }

                    br.close();
                } catch (IOException ioe) {
                    System.out.println(ioe);
                }
            } catch (FileNotFoundException e) {
                throw e;
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }

            // * store in result object
            result.literalTable = literalTable;
            result.ic = ic;
            result.symbolTable = symbolTable;
            result.poolTable = poolTable;
            result.registerTable = registerTable;
            return result;

        } catch (

        FileNotFoundException e) {
            System.out.println("INPUT FILE NOT FOUND: " + FILE_NAME);
        }
        return null;
    }
}

/*
 * 
 * REGISTER opcode
 * AREG 01
 * BREG 02
 * CREG 03
 * 
 * 
 * ADDRESS opcode OP1 OP2
 * --- 0 101
 * 101 (LS, 09) 0 201
 * 102 (LS, 04) 01 202
 * 103 (LS, 05) 02 203
 * 104 (LS, 00)
 * 105
 * 106
 * 107
 * ---
 * 
 * 
 * Symbol table
 * LABEL ADDRESS
 * N 201
 * ONE 202
 * TERM 203
 * 
 */
