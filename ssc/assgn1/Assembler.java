import Pass.One;
import Pass.Two;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Core.AssemblerIntermediateResult;

public class Assembler {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length <= 1) {
            System.out.println("Please pass all arguments");
            return;
        }

        String inputFile = args[1];
        System.out.println("Input file: " + inputFile + "\n\n");

        if (args[0].equals("1")) {
            // run pass 1
            String outputFile = inputFile.substring(0, inputFile.length() - 3) + "class";
            String outputFileReadable = inputFile.substring(0, inputFile.length() - 3) + "class.txt";
            One pass1 = new One(inputFile);
            AssemblerIntermediateResult intermediate_result = pass1.run();
            intermediate_result.print();
            FileOutputStream f = new FileOutputStream(new File(outputFile));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(intermediate_result);
            o.close();
            f.close();

            FileOutputStream fs = new FileOutputStream(new File(outputFileReadable));
            fs.write(intermediate_result.toString().getBytes());
            fs.close();
            System.out.println("\n\nReadable Output generated in " + outputFileReadable);
            System.out.println("\nClass File generated in " + outputFile);
        }

        else {
            String outputFile = inputFile.replace(".class", ".output");
            FileInputStream fi = new FileInputStream(new File(inputFile));
            ObjectInputStream oi = new ObjectInputStream(fi);
            AssemblerIntermediateResult intermediate_result = (AssemblerIntermediateResult) oi.readObject();
            // System.out.println(i.toString());
            Two pass2 = new Two(intermediate_result);
            String machine_code = pass2.run();

            System.out.println("Machine code: \n" + machine_code);
            // store to file
            FileOutputStream f = new FileOutputStream(new File(outputFile));
            f.write(machine_code.getBytes());
            f.close();
            System.out.println("\n\nOutput generated in " + outputFile);
        }

    }
}