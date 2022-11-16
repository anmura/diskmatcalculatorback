package DiskMatExpressionCalculator.Calculator.Helpers;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
public class FunctionConverter {
    Function function;

    public FunctionConverter(Function function) {
        this.function = function;
    }

    public FunctionPart getResidualFunctionByValueNameAndConstant(ValueName valueName, int constant) {
        function.setNumberToValue(constant, valueName);
        return FunctionCalculator.calculateWithConstants(function);

    }

}
