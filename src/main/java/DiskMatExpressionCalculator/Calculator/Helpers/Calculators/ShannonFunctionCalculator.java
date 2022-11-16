package DiskMatExpressionCalculator.Calculator.Helpers.Calculators;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Calculator.Models.Operations.Operation;
import DiskMatExpressionCalculator.Calculator.Models.Value;
import DiskMatExpressionCalculator.web.Response.ShannonResponse;

import java.util.List;

import static DiskMatExpressionCalculator.Calculator.Helpers.DecHexBinaryCalculator.decToBinaryWithLength;
import static DiskMatExpressionCalculator.Calculator.Helpers.FunctionCalculator.simplifyFunction;

public class ShannonFunctionCalculator {

    public static ShannonResponse calculateDisjunctiveShannon(Function function, List<ValueName> expansionValues) {
        Function startFunction = new Function(new Operation.Or());
        Function endFunction = new Function(new Operation.Or());

        int amountOfValues = expansionValues.size();
        int settingNumber = (int) Math.pow(2, amountOfValues);


        for (int i = 0; i < settingNumber; i++) {
            char[] settingNumbers = decToBinaryWithLength(i, amountOfValues).toCharArray();

            Function shannonStartPart = new Function(new Operation.And());
            Function shannonEndPart = new Function(new Operation.And());

            Function functionClone = function.clone();

            for (int bin = 0; bin < settingNumbers.length; bin++) {
                ValueName valueName = expansionValues.get(bin);
                Value value = new Value(valueName);

                Integer number = Integer.parseInt(String.valueOf(settingNumbers[bin]));

                if (number.equals(0)) {
                    value.invert();
                }
                shannonStartPart.addElement(value);
                shannonEndPart.addElement(value);

                functionClone.setNumberToValue(number, valueName);

            }
            shannonStartPart.addElement(functionClone);

            // Because of design
            FunctionPart endElement = simplifyFunction(functionClone);
            if (endElement.isValue()) {
                endElement = new Function(new Operation.And(), List.of(endElement));
            }

            shannonEndPart.addElement(endElement);

            startFunction.addElement(shannonStartPart);
            endFunction.addElement(shannonEndPart);
        }

        return new ShannonResponse(startFunction, endFunction);

    }

}
