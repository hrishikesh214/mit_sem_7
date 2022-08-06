import Pass.One;

public class Assembler{
    public static void main(String[] args){
        if(args.length <= 0) {
            System.out.println("No input file specified");
            return;
        }
        String inputFile = args[0];
        System.out.println("Input file: " + inputFile + "\n\n");

        One pass1 = new One(inputFile);
        pass1.run();

    }
}