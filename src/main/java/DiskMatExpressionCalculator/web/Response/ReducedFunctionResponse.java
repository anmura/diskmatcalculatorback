package DiskMatExpressionCalculator.web.Response;


import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.TruthTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ReducedFunctionResponse {
    TruthTableResponse truthTableResponse;
    String startFunctionString;
    String endFunctionString;
    public ReducedFunctionResponse(TruthTable truthTable, Function startFunction, FunctionPart endFunction) {
        this.truthTableResponse = new TruthTableResponse(truthTable);
        this.startFunctionString = startFunction.toString();
        this.endFunctionString = endFunction.toString();
    }
}
