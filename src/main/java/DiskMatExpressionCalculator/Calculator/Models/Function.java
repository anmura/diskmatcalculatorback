package DiskMatExpressionCalculator.Calculator.Models;


import DiskMatExpressionCalculator.Calculator.Enums.OperationName;
import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Operations.Operation;

import java.util.*;

public class Function extends FunctionPart implements Cloneable {

    private Operation operation;
    private List<FunctionPart> elements;

    public Function(Operation operation, List<FunctionPart> elements) {
        super(false);
        this.operation = operation;
        setElements(elements);
    }

    public Function(Function function) {
        super(function.inversion);
        this.operation = function.operation;
        setElements(function.elements);

    }

    public Function(Operation operation) {
        super(false);
        this.operation = operation;
        this.elements = new ArrayList<>();
    }

    public Function(Operation operation, List<FunctionPart> elements, boolean inversion) {
        super(inversion);
        this.operation = operation;
        setElements(elements);

    }

    private String getOperationSymbol() {
        return operation.getSymbol();
    }

    private boolean containsNumericValue() {
        for (FunctionPart part : elements) {
            if (part.isValue()) {
                if (((Value) part).isNumeric()) return true;
            }
        }
        return false;
    }

    private String constructFunctionString(String prefix, String suffix) {

        String delimiter = containsNumericValue() && operation.getName() == OperationName.AND ? OperationName.AND.CUSTOM_AND_SYMBOL : getOperationSymbol();

        StringJoiner rawResult = new StringJoiner(delimiter, prefix, suffix);
        elements.forEach(el -> rawResult.add(el.toString(true)));

        String result = rawResult.toString();

        if (isInverted()) {
            result = " ¬" + result;
        }

        return result;
    }

    @Override
    public String toString(final boolean inner) {
        return constructFunctionString("(", ")");
    }


    @Override
    public String toString() {
        if (isInverted()) return toString(true);

        return constructFunctionString("", "");
    }

    public OperationName getOperationName() {
        return operation.getName();
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<FunctionPart> getElements() {
        return elements;
    }

    public void setElements(List<FunctionPart> elements) {
        this.elements = new ArrayList<>();
        elements.forEach(e -> this.elements.add(e.clone()));
    }

    public void addElement(FunctionPart element) {
        this.elements.add(element.clone());
    }

    public void removeElement(FunctionPart element) {
        this.elements.remove(element);
    }

    public void removePartIfContains(FunctionPart functionPart) {

        if (functionPart.isFunction()) {
            List<FunctionPart> rollBackElements = new ArrayList<>(this.elements);

            Function function = (Function) functionPart;

            if (this.elements.contains(function)) {
                this.elements.remove(function);
                return;
            }

            for (FunctionPart part : function.elements) {
                if (!this.elements.contains(part)) {
                    this.elements = rollBackElements;
                    break;
                }
                this.elements.remove(part);
            }
        } else {
            this.elements.remove(functionPart);
        }


    }

    public void sortElements() {
        List<FunctionPart> newElements = new ArrayList<>();

        for (FunctionPart part : this.elements) {
            if (part.isValue()) {
                newElements.add(part);
            }
        }

        int elementSize = 2;
        while (this.elements.size() != newElements.size()) {
            for (FunctionPart part : this.elements) {
                if (part.isFunction() && ((Function) part).elements.size() == elementSize) {
                    newElements.add(part);
                }
            }
            elementSize++;
        }

        this.elements = newElements;
    }

    @Override
    public boolean containsFunction() {
        for (FunctionPart element :
                elements) {
            if (element.isFunction()) {
                return true;
            }
        }
        return false;
    }


    // change AND to OR and OR to AND
    public void reverseOperation() {
        if (getOperationName() == OperationName.AND) {
            setOperation(new Operation.Or());
        } else if (getOperationName() == OperationName.OR) {
            setOperation(new Operation.And());
        }
    }

    public boolean containsValueWithNumericValue(Integer numericValue) {
        for (FunctionPart part :
                elements) {
            if (part.isValue()) {
                if (Objects.equals(((Value) part).getNumericValue(), numericValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    public FunctionPart getValueWithNumericValue(Integer numericValue) {
        for (FunctionPart part :
                elements) {
            if (part.isValue()) {
                if (Objects.equals(((Value) part).getNumericValue(), numericValue)) {
                    return part;
                }
            }
        }
        return null;
    }

    public void removeValuesWithNumericValue(Integer numericValue) {
        List<FunctionPart> result = new ArrayList<>();

        for (FunctionPart part :
                elements) {
            if (part.isValue()) {
                if (Objects.equals(((Value) part).getNumericValue(), numericValue)) {
                    continue;
                }
            }
            result.add(part);
        }

        elements = result;
    }

    public void setNumberToValue(Integer number, ValueName valueName) {
        for (FunctionPart element :
                elements) {
            if (element.isFunction()) {
                ((Function) element).setNumberToValue(number, valueName);
            } else {
                if (((Value) element).getName() == valueName) {
                    ((Value) element).setNumericValue(number);
                }
            }
        }
    }

    public List<ValueName> getContainedValueNames() {
        List<ValueName> result = new ArrayList<>();
        for (FunctionPart element :
                elements) {
            if (element.isValue()) {
                result.add(((Value) element).getName());
            } else {
                result.addAll(((Function) element).getContainedValueNames());
            }
        }

        result = new ArrayList<>(new HashSet<>(result));
        result = result.stream().sorted().toList();
        return result;
    }

    public Operation getOperation() {
        return this.operation;
    }

    @Override
    public Function clone() {
        Function clone = (Function) super.clone();
        List<FunctionPart> elements = clone.getElements();
        List<FunctionPart> cloneElements = new ArrayList<>();

        elements.forEach(e -> cloneElements.add(e.clone()));

        clone.setElements(cloneElements);
        return clone;
    }

    public boolean containsElement(FunctionPart checkElement) {
        for (FunctionPart element : elements) {
            if (element.equals(checkElement)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSubFunction(Function subFunction) {
        for (FunctionPart element : elements) {
            if (new HashSet<>(elements).containsAll(subFunction.getElements())) {
                return true;
            }
        }
        return false;
    }
}
