import Pass.One;

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

        System.out.println("ADDRESS\tINSTRUCTION\tOPERAND1\tOPERAND2\n");
        System.out.println(intermediate_result.ic.toString("\t", intermediate_result.symbolTable,
                intermediate_result.literalTable));
        System.out.println("SYMBOL TABLE: \n" + intermediate_result.symbolTable.toString());
        System.out.println("LITERAL TABLE: \n" + intermediate_result.literalTable.toString());

    }
}