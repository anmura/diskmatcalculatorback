package DiskMatExpressionCalculator.Calculator.Helpers;

import DiskMatExpressionCalculator.Calculator.Enums.OperationName;
import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Calculator.Models.Laws.Law;
import DiskMatExpressionCalculator.Calculator.Models.Value;


import java.util.ArrayList;
import java.util.List;

public class FunctionCalculator {
    public static FunctionPart calculateWithConstants(Function inputFunction) {

        Function function = inputFunction.clone();

        List<FunctionPart> result = new ArrayList<>();

        for (FunctionPart part : function.getElements()
        ) {
            if (part.isFunction() && part.containsFunction()) {
                result.add(FunctionCalculator.calculateWithConstants((Function) part));
            } else if (part.getClass() == Value.class) {
                result.add(part);
            } else {
                result.add(simplifyAndOrFunctionWithConstants((Function) part));
            }

        }
        if (result.size() == 1 && result.get(0).isValue()) {
            return result.get(0);
        }
        function.setElements(result);

        return simplifyAndOrFunctionWithConstants(function);

    }

    public static FunctionPart simplifyFunction(FunctionPart input) {

        if (input.isValue()){
            return input.clone();
        }

        Function functionClone = (Function) input.clone();

        FunctionPart result = calculateWithConstants(functionClone);

        if (result.isFunction()) {
            result = Law.Idempotent.apply((Function) result);
        }

        if (result.isFunction()) {
            result = Law.Absorption.apply((Function) result);
        }

        return result;
    }


    // No situation with the same values (x1 & x1) -- no remove dublicate values
    private static FunctionPart simplifyAndOrFunctionWithConstants(Function function) {

        List<FunctionPart> elements = function.getElements();

        if (elements.size() == 1 && elements.get(0).getClass() == Value.class) {
            return elements.get(0);
        }

        int priorityNumber;
        int minorityNumber;

        if (function.getOperationName() == OperationName.AND) {
            priorityNumber = 0;
            minorityNumber = 1;
        } else {
            priorityNumber = 1;
            minorityNumber = 0;
        }

        if (function.containsValueWithNumericValue(priorityNumber)) {
            return function.getValueWithNumericValue(priorityNumber);
        }
        // no priority
        if (function.containsValueWithNumericValue(minorityNumber)) {
            function.removeValuesWithNumericValue(minorityNumber);

            if (function.getElements().size() == 0) {
                function.addElement(new Value(ValueName.X1, minorityNumber));
            }

            if (function.getElements().size() == 1) {
                return function.getElements().get(0);
            }
            return function;

        }

        return function;

    }


}
