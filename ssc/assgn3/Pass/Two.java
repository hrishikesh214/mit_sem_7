package Pass;

import java.io.*;
import java.util.*;

import Core.MacroPass1Result;
import Core.MacroTable;
import Core.MacroTableElement;

public class Two {
    public MacroTable mnt;
    public MacroTable mdt;
    public String inputFile;
    public List<String> outputFileContent;

    public Two(MacroPass1Result x, String inputFile) throws IOException {
        mnt = x.mnt;
        mdt = x.mdt;
        this.inputFile = inputFile;
        outputFileContent = new ArrayList<String>();
    }

    public String run() throws IOException {
        FileInputStream fi = new FileInputStream(new File(inputFile));
        BufferedReader br = new BufferedReader(new InputStreamReader(fi));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            StringTokenizer tokens = new StringTokenizer(inputLine);
            String nextToken = tokens.nextToken();
            MacroTableElement ele = mnt.findAndGetCol1(nextToken);
            if (ele == null) {
                // not a call copy as it is
                outputFileContent.add(inputLine);
            } else {
                // its a call, process it
                int argCounter = 1;
                while (tokens.hasMoreTokens()) {
                    // copy args to ala of ele
                    String arg = tokens.nextToken();
                    ele.ala.get("#" + Integer.toString(argCounter)).col2 = arg;
                    argCounter++;
                }
                // System.out.println(ele.ala.toString());

                // copy mdt to outputFileContent
                int mdtIndex = Integer.parseInt(ele.col2) + 1;

                while (true) {
                    MacroTableElement mdtEle = mdt.get(Integer.toString(mdtIndex));
                    String currLine = mdtEle.col1;
                    StringTokenizer lineTokens = new StringTokenizer(currLine);
                    String newCurrLine = "+";
                    if (mdtEle.col1.equals("MEND")) {
                        break;
                    }
                    // System.out.println(currLine);
                    while (lineTokens.hasMoreTokens()) {
                        nextToken = lineTokens.nextToken();
                        // System.out.println("\t"+nextToken);
                        if (nextToken.startsWith("#")) {
                            newCurrLine += " " + ele.ala.get(nextToken).col2;
                            // System.out.println("\t\t"+currLine);
                        } else {
                            newCurrLine += " " + nextToken;
                        }
                    }

                    outputFileContent.add(newCurrLine);

                    mdtIndex++;
                }
            }
        }
        br.close();
        fi.close();
        return String.join("\n", outputFileContent);
    }
}
