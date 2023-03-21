package DiskMatExpressionCalculator.Calculator.Helpers;

import DiskMatExpressionCalculator.Enums.OperationName;
import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.Laws.Law;
import DiskMatExpressionCalculator.Models.McCluskey.McCluskey;
import DiskMatExpressionCalculator.Models.TruthTable;
import DiskMatExpressionCalculator.Models.Value;


import java.util.ArrayList;
import java.util.List;

public class FunctionHelper {
    public static FunctionPart calculateWithConstants(Function inputFunction) {

        Function function = inputFunction.clone();

        List<FunctionPart> result = new ArrayList<>();

        for (FunctionPart part : function.getElements()) {
            if (part.isFunction() && part.containsFunction()) {
                result.add(FunctionHelper.calculateWithConstants((Function) part));
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

    public static FunctionPart simplifyDisjunctiveFunctionToMinimal(Function function){
        TruthTable truthTable = new TruthTable(function);
        McCluskey mcCluskey = new McCluskey(truthTable);
        return mcCluskey.getDisjunctiveMinimalNormalFormData().getFunction();
    }


}
