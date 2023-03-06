package DiskMatExpressionCalculator.Models.Operations;

import DiskMatExpressionCalculator.Enums.OperationName;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Operation {

    private final String Symbol;
    private final OperationName Name;

    public String getSymbol() {
        return Symbol;
    }

    public OperationName getName() {
        return Name;
    }

    @JsonIgnore
    public Integer getPrimaryNumber() {
        return null;
    }

    public Operation(OperationName operationName) {
        Name = operationName;
        Symbol = operationName.OperationSymbol;


    }

    public static class And extends Operation {
        public And() {
            super(OperationName.AND);
        }

        public Integer getPrimaryNumber() {
            return 0;
        }
    }

    public static class Or extends Operation {
        public Or() {
            super(OperationName.OR);
        }

        public Integer getPrimaryNumber() {
            return 1;
        }
    }

    public class Xor extends Operation {
        public Xor() {
            super(OperationName.XOR);
        }

    }


}
