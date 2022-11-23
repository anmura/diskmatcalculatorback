package DiskMatExpressionCalculator.web.Request;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import lombok.Data;

@Data
public class ExpandedFunctionRequestData {
    private Function function;
    private ValueName valueName;
    private int constant;
}
