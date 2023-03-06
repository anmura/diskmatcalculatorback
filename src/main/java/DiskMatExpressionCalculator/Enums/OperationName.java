package DiskMatExpressionCalculator.Enums;

public enum OperationName {
    AND(""), OR(" v "), XOR(" ⊕ ");

    public final String CUSTOM_AND_SYMBOL = " & ";
    public final String OperationSymbol;
    OperationName(String symbol){
        this.OperationSymbol = symbol;
    }

}
