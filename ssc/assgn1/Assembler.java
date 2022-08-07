import Pass.One;
import Pass.Two;

import Core.AssemblerIntermediateResult;

public class Assembler {
    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("No input file specified");
            return;
        }
        String inputFile = args[0];
        System.out.println("Input file: " + inputFile + "\n\n");

        One pass1 = new One(inputFile);
        AssemblerIntermediateResult intermediate_result = pass1.run();

        intermediate_result.print();

        System.out.println("\n\n");

        Two pass2 = new Two(intermediate_result);
        String machine_code = pass2.run();

        System.out.println("Machine code: \n" + machine_code);

    }
}