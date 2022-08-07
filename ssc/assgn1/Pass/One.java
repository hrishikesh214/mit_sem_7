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
    AssemblerTable literalTable;
    int literalCount = 0;

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
        } else if (x.startsWith("=")) {
            // then this is a literal
            literalTable.set(x, "L", String.format("%s", literalCount));
            literalCount++;
        } else if (isSymbol(x)) {
            symbolTable.set(x, "S", null);
        }

        return x;
    }

    AssemblerTable getOptab() {
        AssemblerTable optab = new AssemblerTable("OPTAB");

        optab.set("STOP", "IS", "00");
        optab.set("ADD", "IS", "01");
        optab.set("SUB", "IS", "02");
        optab.set("MULT", "IS", "03");
        optab.set("MOVER", "IS", "04");
        optab.set("MOVEM", "IS", "05");
        optab.set("COMP", "IS", "06");
        optab.set("BC", "IS", "07");
        optab.set("DIV", "IS", "08");
        optab.set("READ", "IS", "09");
        optab.set("PRINT", "IS", "10");

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
        regTable.set("AREG", optype, "01");
        regTable.set("BREG", optype, "02");
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
            literalTable = new AssemblerTable("LITERAL");
            List<Integer> poolTable = new ArrayList<Integer>();
            poolTable.add(0);

            // System.out.println(" --- \n\n");

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
                                String operand = tokens.nextToken();
                                String[] operands = operand.split("\\+");
                                if (operands.length == 2) {
                                    String symbolX = operands[0];
                                    String offset = operands[1];
                                    int nxtLc;
                                    if (isNumeric(offset)) {
                                        nxtLc = symbolTable.get(symbolX).address + Integer.parseInt(offset);
                                    } else {
                                        System.out.println("ERROR: ORIGIN directive offset is invalid");
                                        return null;
                                    }

                                    AssemblerTableElement element = new AssemblerTableElement("ORIGIN", "C",
                                            Integer.toString(lc));

                                    ic.add(nextToken, lc, optab.get("ORIGIN").toString(), null, element.toString());
                                    lc = nxtLc - 1;
                                } else {
                                    System.out.println("ERROR: ORIGIN directive invalid operand");
                                    return null;
                                }
                            } else {
                                System.out.println("ERROR: ORIGIN directive must have an operand");
                                return null;
                            }
                        } else if (nextToken.equals("LTORG")) {
                            lc = literalTable.giveAddressFrom(lc, ic, optab.get("LTORG"), optab.get("DC"),
                                    literalTable);

                            poolTable.add(literalTable.getSize());

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
                                symbolTable.update(symbolLabel, String.format("%d", (symbolCount)), (lc), 0);
                            }
                            symbolCount++;
                        } else {
                            // * ELSE take it as general token and check
                            AssemblerTableElement ate = optab.get(nextToken);
                            if (nextToken.equals("STOP")) {
                                isStop = true;
                            }
                            if (ate == null) {
                                // * this means its label
                                symbolTable.set(nextToken, "S", null);
                                symbolTable.update(nextToken, String.format("%d", (symbolCount)),
                                        (lc), 0);
                                symbolCount++;

                                if (!tokens.hasMoreTokens()) {
                                    System.out.println("ERROR: No more tokens");
                                    return null;
                                }
                                nextToken = tokens.nextToken();
                                ate = optab.get(nextToken);
                                if (ate == null) {
                                    System.out.println("ERROR: Invalid instruction near " + nextToken);
                                    return null;
                                }
                            }

                            // * if it is a directive
                            String operand1, operand2;
                            if (tokens.hasMoreTokens()) {
                                operand1 = getParsedOperandForInstruction(tokens.nextToken());

                                if (tokens.hasMoreTokens()) {
                                    operand2 = getParsedOperandForInstruction(tokens.nextToken());
                                    ic.add(nextToken, lc, ate.toString(), operand1, operand2);
                                } else
                                    ic.add(nextToken, lc, ate.toString(), null, operand1);
                            } else
                                ic.add(nextToken, lc, ate.toString(), null, null);

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
