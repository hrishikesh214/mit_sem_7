package Core;

import java.io.Serializable;
import java.util.*;

public class AssemblerTable implements Serializable {
    Hashtable<String, AssemblerTableElement> thisTable;
    String thisTableType;

    public AssemblerTable(String __type) {
        this.thisTableType = __type;
        thisTable = new Hashtable<>();
    }

    public void set(String __name, String __optype, String __opcode) {
        // System.out.println("Setting " + __name + " of " + __optype + " to " +
        // __opcode);
        thisTable.put(__name, new AssemblerTableElement(__name, __optype, __opcode));
    }

    public void update(String __name, String __opcode, Integer __address, Integer value) {
        AssemblerTableElement x = thisTable.get(__name);
        // System.out.println("updating " + __name + " of " + x.type + " to " +
        // __opcode);
        x.opcode = __opcode == null ? x.opcode : __opcode;
        x.address = __address == null ? x.address : __address;
        x.value = value == null ? x.value : value;
        // thisTable.remove(__name);
        // thisTable.put(__name, x);
    }

    public AssemblerTableElement get(String __name) {
        return thisTable.get(__name);
    }

    public void printElement(String __name) {
        thisTable.get(__name).print();
    }

    public boolean containsKey(String key) {
        return this.thisTable.containsKey(key);
    }

    public int getSize() {
        return this.thisTable.size();
    }

    @Override
    public String toString() {
        String x = new String();
        for (String key : this.thisTable.keySet()) {
            x += this.thisTable.get(key).getNameOpCode() + "\n";
        }

        return x;
    }

    public Integer giveAddressFrom(int __addr, AssemblerICTable ic, AssemblerTableElement inst1,
            AssemblerTableElement inst2, AssemblerTable literalTable) {
        for (String key : this.thisTable.keySet()) {
            AssemblerTableElement x = this.thisTable.get(key);
            x.address = __addr;
            ic.add(Integer.toString(__addr), __addr, inst1.toString(), inst2.toString(),
                    x.getLiteralValueString());
            literalTable.update(x.name, null, __addr, __addr);
            __addr++;
        }
        return __addr - 1;
    }

    public AssemblerTableElement getElementFromIndex(int i) {
        AssemblerTableElement x = null;
        for (String key : this.thisTable.keySet()) {
            x = this.thisTable.get(key);
            if (Integer.parseInt(x.opcode) == i)
                break;
        }
        return x;
    }

}