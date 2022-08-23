package Core;

import java.io.Serializable;
import java.util.List;

public class MacroPass1Result implements Serializable {
    public MacroTable mnt, mdt;
    public List<String> outputFileContent;

    @Override
    public String toString() {
        String x = "MNT:\n";
        x += "Index\tName\tMDT_REF\n";
        x += mnt.toString() + "\n";
        x += "ALAs:\n" + mnt.ala_toString() + "\n";
        x += "MDT:\n" + mdt.toString() + "\n";
        // x += "Output:\n";
        // for (String line : outputFileContent) {
        // x += line + "\n";
        // }
        return x;
    }

    public String getOutputFileContent() {
        String x = "";
        for (String line : outputFileContent) {
            x += line + "\n";
        }
        return x;
    }
}
