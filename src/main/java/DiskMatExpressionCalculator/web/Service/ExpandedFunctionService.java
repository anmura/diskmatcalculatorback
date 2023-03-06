package DiskMatExpressionCalculator.web.Service;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.ExpandedFunctionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static DiskMatExpressionCalculator.Calculator.Helpers.FunctionHelper.calculateWithConstants;
import static DiskMatExpressionCalculator.Calculator.Helpers.FunctionHelper.simplifyFunction;

@Service
public class ExpandedFunctionService {
    public ExpandedFunctionResponse calculateExpandedFunction(Function function, ValueName valueName, int constant) {

        function.setNumberToValue(constant, valueName);
        Function startFunction = function.clone();

        FunctionPart endFunction = calculateEndFunction(function);

        TruthTable truthTable = new TruthTable(endFunction);

        return new ExpandedFunctionResponse(truthTable, startFunction, endFunction);
    }

    private FunctionPart calculateEndFunction(Function input) {

        FunctionPart endFunction = calculateWithConstants(input);

        if (endFunction.isFunction()) {
            List<FunctionPart> newElements = new ArrayList<>();
            ((Function) endFunction).getElements().forEach(e -> newElements.add(simplifyFunction(e)));
            ((Function) endFunction).setElements(newElements);
            endFunction = simplifyFunction(endFunction);
        }

        return endFunction;
    }
}
