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
    private ExpandedFunctionResponse expandedFunctionResponseX2Zero;
    private ExpandedFunctionResponse expandedFunctionResponseX4One;
    private ExpandedFunctionResponse expandedFunctionResponseX1One;
    private ExpandedFunctionResponse expandedFunctionResponseX3Zero;

}
