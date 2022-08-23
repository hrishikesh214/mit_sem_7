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
    MacroTable mnt, mdt;
    List<String> outputFileContent;

    public One(String __fn) {
        FILE_NAME = __fn;
    }

    public MacroPass1Result run() {
        try {
            // reading file
            File inputFile = new File(FILE_NAME);
            outputFileContent = new ArrayList<>();
            mnt = new MacroTable("MNT");
            mdt = new MacroTable("MDT");

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String inputLine;
                int mnt_counter = 1;
                int mdt_counter = 1;

                try {
                    Boolean isMacroDefinationOn = false;
                    Boolean isNameAdded = false;
                    String currentIndexForMNT = "";
                    String currentIndexForMDT = "";

                    while ((inputLine = br.readLine()) != null) {
                        StringTokenizer tokens = new StringTokenizer(inputLine);
                        String nextToken = tokens.nextToken();

                        // System.out.println(nextToken);
                        if (nextToken.equals("MEND")) {
                            // end the macro defination
                            currentIndexForMDT = Integer.toString(mdt_counter);
                            // input mend to mdt also
                            mdt.set(currentIndexForMDT, "MEND", null);
                            mdt_counter++;
                            isNameAdded = false;
                            isMacroDefinationOn = false;
                            continue;
                        } else if (isMacroDefinationOn) {
                            // parse macro defination
                            if (!isNameAdded) {
                                // add name as well as args
                                currentIndexForMNT = Integer.toString(mnt_counter);
                                currentIndexForMDT = Integer.toString(mdt_counter);
                                mnt.set(currentIndexForMNT, nextToken, currentIndexForMDT);
                                mnt_counter++;
                                int argCounter = 1;
                                String currentArgIndex = "";
                                String currLine = nextToken;

                                while (tokens.hasMoreTokens()) {
                                    String expectedArg = tokens.nextToken();
                                    currLine += " " + expectedArg;
                                    if (expectedArg.startsWith("&")) {
                                        // add to ala
                                        currentArgIndex = "#" + Integer.toString(argCounter);
                                        mnt.get(currentIndexForMNT).ala.set(currentArgIndex, expectedArg,
                                                null);
                                        argCounter++;
                                    } else {
                                        System.out
                                                .println("ERROR: ARGUMENT " + expectedArg + " DOES NOT START WITH '&'");
                                    }
                                }

                                mdt.set(currentIndexForMDT, currLine, null);
                                mdt_counter++;
                                isNameAdded = true;
                            } else {
                                // add body
                                currentIndexForMDT = Integer.toString(mdt_counter);
                                String currLine = nextToken;
                                while (tokens.hasMoreTokens()) {
                                    nextToken = tokens.nextToken();
                                    if (nextToken.startsWith("&")) {
                                        MacroTableElement argEle = mnt.get(currentIndexForMNT).ala.getArg(nextToken);
                                        if (argEle.equals(null)) {
                                            System.out.println(String.format("ERROR: ARG '%s' NOTFOUND!", nextToken));
                                        } else
                                            currLine += " " + argEle.index;
                                    } else {
                                        currLine += " " + nextToken;
                                    }
                                }
                                mdt.set(currentIndexForMDT, currLine, null);
                                mdt_counter++;
                            }
                        } else if (nextToken.equals("MACRO")) {
                            // macro definatiomn starts
                            isMacroDefinationOn = true;
                            isNameAdded = false;
                            continue;
                        } else {
                            // System.out.println("skipping "+nextToken);
                            outputFileContent.add(inputLine);
                        }

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
            // return result;

        } catch (

        FileNotFoundException e) {
            System.out.println("INPUT FILE NOT FOUND: " + FILE_NAME);
        }
        MacroPass1Result result = new MacroPass1Result();
        result.mnt = mnt;
        result.mdt = mdt;
        result.outputFileContent = outputFileContent;
        return result;
    }
}