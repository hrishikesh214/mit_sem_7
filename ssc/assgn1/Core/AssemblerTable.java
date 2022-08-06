package Core;

import java.util.*;

public class AssemblerTable {
    Hashtable<String, AssemblerTableElement> thisTable;
    String thisTableType;

    public AssemblerTable(String __type) {
        this.thisTableType = __type;
        thisTable = new Hashtable<>();
    }

    public void set(String __name, String __optype, String __opcode) {
        thisTable.put(__name, new AssemblerTableElement(__name, __optype, __opcode));
    }

    public void update(String __name, String __opcode, Integer __address, Integer value) {
        AssemblerTableElement x = thisTable.get(__name);
        x.opcode = __opcode;
        x.address = __address;
        x.value = value;
        thisTable.put(__name, x);
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

    public String toString() {
        String x = new String();
        for (String key : this.thisTable.keySet()) {
            x += this.thisTable.get(key).getNameOpCode() + "\n";
        }

        return x;
    }

}