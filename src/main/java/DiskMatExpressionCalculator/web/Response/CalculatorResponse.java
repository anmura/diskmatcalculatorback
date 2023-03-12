package DiskMatExpressionCalculator.web.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CalculatorResponse {

    private TruthTableResponse truthTableResponse;
    private McCluskeyFunctionResponse mcCluskeyFunctionResponse;
    private ShannonResponse shannonResponseX1X3;
    private ShannonResponse shannonResponseX2X4;
    private ReducedFunctionResponse reducedFunctionResponseX2Zero;
    private ReducedFunctionResponse reducedFunctionResponseX4One;
    private ReducedFunctionResponse reducedFunctionResponseX1One;
    private ReducedFunctionResponse reducedFunctionResponseX3Zero;

}
