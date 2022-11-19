package DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Helpers.FunctionCalculator;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.ExpandedFunctionResponse;

import java.util.ArrayList;
import java.util.List;

public class ExpandedFunctionCalculator {

    public static ExpandedFunctionResponse calculateExpandedFunction(Function function, ValueName valueName, int constant) {

        function.setNumberToValue(constant, valueName);
        Function startFunction = function.clone();

        FunctionPart endFunction = calculateEndFunction(function);

        TruthTable truthTable = new TruthTable(endFunction);

        return new ExpandedFunctionResponse(truthTable, startFunction, endFunction);
    }

    private static FunctionPart calculateEndFunction(Function input) {

        FunctionPart endFunction = FunctionCalculator.calculateWithConstants(input);

        if (endFunction.isFunction()) {
            List<FunctionPart> newElements = new ArrayList<>();
            ((Function) endFunction).getElements().forEach(e -> newElements.add(FunctionCalculator.simplifyFunction(e)));
            ((Function) endFunction).setElements(newElements);
            endFunction = FunctionCalculator.simplifyFunction(endFunction);
        }

        return endFunction;

    }
}
