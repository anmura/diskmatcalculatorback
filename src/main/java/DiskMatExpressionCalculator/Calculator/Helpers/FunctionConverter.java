package DiskMatExpressionCalculator.Calculator.Helpers;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;

public class FunctionConverter {
    Function function;

    public FunctionConverter(Function function) {
        this.function = function;
    }

    public FunctionPart getResidualFunctionByValueNameAndConstant(ValueName valueName, int constant) {
        function.setNumberToValue(constant, valueName);
        return FunctionHelper.calculateWithConstants(function);

    }

}
