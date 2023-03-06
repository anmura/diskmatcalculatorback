package DiskMatExpressionCalculator.web.Response;


import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.TruthTable;

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
