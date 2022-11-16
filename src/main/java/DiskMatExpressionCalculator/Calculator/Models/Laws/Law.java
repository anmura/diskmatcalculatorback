package DiskMatExpressionCalculator.Calculator.Models.Laws;

import DiskMatExpressionCalculator.Calculator.Enums.OperationName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Calculator.Models.Operations.Operation;

import java.util.ArrayList;
import java.util.List;

public abstract class Law {

    public static class Absorption extends Law {

        public static FunctionPart apply(Function inputFunction) {

            Function function = inputFunction.clone();

            if (function.getOperationName() == OperationName.AND) {
                return function;
            }

            function.sortElements();
            List<FunctionPart> elements = function.getElements();
            List<FunctionPart> newElements = new ArrayList<>();

            while (elements.size() > 0) {

                if (elements.size() == 1) {
                    newElements.add(elements.get(0));
                    elements.remove(0);
                    break;
                }

                FunctionPart checkElement1 = elements.get(0);

                for (int j = 1; j < elements.size(); j++) {
                    FunctionPart checkElement2 = elements.get(j);

                    FunctionPart afterNeeldumine = getNeeldumineResult(checkElement1, checkElement2);

                    if (afterNeeldumine.isValue()) {
                        newElements.add(afterNeeldumine);

                        elements.remove(checkElement2);
                        elements.remove(checkElement1);
                        break;
                    }

                    if (afterNeeldumine.isFunction()) {
                        List<FunctionPart> checkParts = ((Function) afterNeeldumine).getElements();

                        if (checkParts.contains(checkElement1) && checkParts.contains(checkElement2)) {
                            if (j == elements.size() - 1) {
                                newElements.add(checkElement1);
                                elements.remove(checkElement1);
                            }
                        } else {
                            newElements.add(afterNeeldumine);
                            elements.remove(checkElement1);
                            elements.remove(checkElement2);
                            break;
                        }
                    }
                }
            }

            // remove all  inner functions



            while (newElements.size() == 1) {
                if (newElements.get(0).isValue()) {
                    return newElements.get(0);
                } else {
                    function.setOperation(((Function) newElements.get(0)).getOperation());
                    function.setInversion(newElements.get(0).isInverted());
                    newElements = ((Function) newElements.get(0)).getElements();
                }
            }


            function.setElements(newElements);
            return function;
        }

        private static FunctionPart getNeeldumineResult(FunctionPart element1, FunctionPart element2) {

            if (element1.isValue() && element2.isValue()) {
                return createFunctionFromParts(element1, element2);
            }

            Function result = (Function) element2.clone();

            result.removePartIfContains(element1);
            if (!result.equals(element2) && result.getElements().size() > 0) {
                return element1;
            }

            FunctionPart invertedElement = element1.clone();
            invertedElement.invert();

            result.removePartIfContains(invertedElement);
            if (!result.equals(element2) && result.getElements().size() > 0) {
                return createFunctionFromParts(result, element1);
            }

            return createFunctionFromParts(element1, element2);

        }

        private static Function createFunctionFromParts(FunctionPart element1, FunctionPart element2) {
            Function result = new Function(new Operation.Or());

            result.addElement(element1);
            result.addElement(element2);

            return result;

        }

    }


    public class Idempotent extends Law {

        public static FunctionPart apply(Function function) {

            List<FunctionPart> elements = function.getElements();
            function.setElements(new ArrayList<>());

            for (FunctionPart element : elements) {
                if (!function.containsElement(element)) {
                    function.addElement(element);
                }
            }

            if (function.getElements().size() == 1 && !function.containsFunction()) {
                return function.getElements().get(0);
            }

            return function;

        }
    }

    public static class DeMorgan extends Law {

        public static FunctionPart apply(Function function) {
            function.invert();
            function.reverseOperation();

            for (FunctionPart element : function.getElements()) {
                element.invert();
            }

            return function;
        }
    }

}
