package Core;

public class AssemblerTableElement {
    public String name;
    public String opcode;
    public String type;
    public Integer address;
    public Integer value;

    public AssemblerTableElement(String name, String __type, String opcode) {
        this.name = name;
        this.type = __type;
        this.opcode = opcode;
    }

    public AssemblerTableElement(String name, String __type, String opcode, Integer address, Integer value) {
        // for symbols opcode will work as index of symbols and address will be
        // address of declaration

        this.name = name;
        this.type = __type;
        this.opcode = opcode;
        this.address = address;
        this.value = value;
    }

    public void print() {
        System.out.print(this.toString());
    }

    public String toString() {
        return String.format("(%s, %s)", this.type, this.opcode);
    }

    public String getNameOpCode() {
        return this.opcode + "\t" + this.name + "\t" + this.address;
    }

    public String getLiteralValueString() {
        return String.format("(C, %d)", Integer.parseInt(this.name.substring(2, this.name.length() - 1)));
    }
    // public String
}