package Pass;

import Core.*;
import java.util.*;

public class Two {
    public AssemblerICTable ic;
    public AssemblerTable symbolTable;
    public AssemblerTable registerTable;
    public List<Integer> poolTable;
    public AssemblerTable literalTable;

    public Two(AssemblerIntermediateResult intermediate_result) {
        ic = intermediate_result.ic;
        symbolTable = intermediate_result.symbolTable;
        registerTable = intermediate_result.registerTable;
        poolTable = intermediate_result.poolTable;
        literalTable = intermediate_result.literalTable;
    }

}