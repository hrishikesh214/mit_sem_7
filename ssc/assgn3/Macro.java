import java.io.*;
import java.util.*;

import Core.MacroPass1Result;
import Pass.One;
import Pass.Two;

public class Macro {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length < 2) {
            System.out.println("Please pass all arguments!");
            return;
        }

        String inputFile = args[1];
        System.out.println("Input file: " + inputFile + "\n");

        if (args[0].equals("1")) {
            // pass 1
            String outputFile = inputFile.substring(0, inputFile.length() - 4);
            One pass1 = new One(inputFile);
            MacroPass1Result result = pass1.run();
            // System.out.println(result.toString());

            // store output to file
            // write to class file
            FileOutputStream f = new FileOutputStream(new File(outputFile + "_macropass1.class"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(result);
            o.close();
            f.close();
            System.out.println("Class file generated for pass2 " + outputFile + "_macropass1.class");

            // write to text file for reading
            FileOutputStream fs = new FileOutputStream(new File(outputFile + "_macropass1.txt"));
            fs.write(result.toString().getBytes());
            fs.close();
            System.out.println("\nReadable Output generated in " + outputFile + "_macropass1.txt");

            // generate output file
            FileOutputStream fs_o = new FileOutputStream(new File(outputFile + ".output.txt"));
            fs_o.write(result.getOutputFileContent().getBytes());
            fs_o.close();
            System.out.println("\nOutput File generated in " + outputFile + ".output.txt");

        } else {

            if (args.length < 3) {
                System.out.println("Please pass all arguments!");
                return;
            }

            // pass2
            String outputFile = inputFile.replace(".output.txt", "");
            FileInputStream fi = new FileInputStream(new File(args[2]));
            ObjectInputStream oi = new ObjectInputStream(fi);
            MacroPass1Result intermediate_result = (MacroPass1Result) oi.readObject();

            // System.out.println(intermediate_result.toString());
            Two pass2 = new Two(intermediate_result, inputFile);

            String result = pass2.run();

            // write string to file
            FileOutputStream fs = new FileOutputStream(new File(outputFile + "_processed.txt"));
            fs.write(result.getBytes());
            fs.close();

            System.out.println("\nProcessed Output generated in " + outputFile + "_process.txt");
        }
    }
}

// javac --class-path .\bin\ --source-path . .\Macro.java
// java Macro 1 .\input1.txt
// java Macro 2 .\input1.output.txt .\input1_macropass1.class