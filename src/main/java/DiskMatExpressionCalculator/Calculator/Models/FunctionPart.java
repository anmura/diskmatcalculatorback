package DiskMatExpressionCalculator.Calculator.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class FunctionPart implements Cloneable {

    protected boolean inversion;

    public FunctionPart(boolean inversion) {
        this.inversion = inversion;
    }

    @JsonIgnore
    public boolean isInverted() {
        return this.inversion;
    }

    public void invert() {
        this.inversion = !this.inversion;
    }

    public void setInversion(boolean inversion) {
        this.inversion = inversion;
    }

    public abstract String toString(boolean inner);


    public boolean containsFunction() {
        return false;
    }
    @JsonIgnore
    public boolean isFunction() {
        return this.getClass() == Function.class;
    }
    @JsonIgnore
    public boolean isValue() {
        return this.getClass() == Value.class;
    }

    @Override
    public boolean equals(Object part) {


        if (part.getClass() == Value.class && this.isValue()) {
            Value partValue = (Value) part;
            Value selfValue = (Value) this;

            if (partValue.getName() == selfValue.getName() && partValue.isInverted() == selfValue.isInverted() && selfValue.getNumericValue() == partValue.getNumericValue()) {
                return true;
            }
        }
        if (part.getClass() == Function.class && this.isFunction()) {

            if (((Function) part).getOperationName() != ((Function) this).getOperationName() || ((Function) part).isInverted() != this.isInverted()) {
                return false;
            }

            List<FunctionPart> partFunctionParts = ((Function) part).getElements();
            List<FunctionPart> selfFunctionParts = ((Function) this).getElements();

            if (partFunctionParts.size() != selfFunctionParts.size()) {
                return false;
            }

            for (FunctionPart element :
                    partFunctionParts) {
                if (!selfFunctionParts.contains(element)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public FunctionPart clone() {
        try {
            return (FunctionPart) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
