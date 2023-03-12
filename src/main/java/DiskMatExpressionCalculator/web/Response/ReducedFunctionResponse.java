package DiskMatExpressionCalculator.web.Response;


import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.TruthTable;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReducedFunctionResponse {
    TruthTableResponse truthTableResponse;
    String startFunctionString;
    String endFunctionString;

    public ReducedFunctionResponse(TruthTable truthTable, Function startFunctionString, FunctionPart endFunctionString) {
        this.truthTableResponse = new TruthTableResponse(truthTable);
        this.startFunctionString = startFunctionString.toString();
        this.endFunctionString = endFunctionString.toString();
    }
}
