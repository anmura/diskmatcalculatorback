package DiskMatExpressionCalculator.web.Response;


import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;

public class ExpandedFunctionResponse {
    TruthTableResponse truthTableResponse;
    Function startFunction;
    FunctionPart endFunction;

    public ExpandedFunctionResponse(TruthTable truthTable, Function startFunction, FunctionPart endFunction) {
        this.truthTableResponse = new TruthTableResponse(truthTable);
        this.startFunction = startFunction;
        this.endFunction = endFunction;
    }
}
