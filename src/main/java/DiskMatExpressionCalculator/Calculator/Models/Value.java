package DiskMatExpressionCalculator.Calculator.Models;


import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Value extends FunctionPart implements Cloneable {
    private final ValueName name;
    private Integer numericValue;

    public Value(ValueName name, boolean inversion) {
        super(inversion);
        this.name = name;
    }

    public Value(ValueName name) {
        super(false);
        this.name = name;
    }

    public Value(ValueName name, Integer numericValue, boolean inversion) {
        super(inversion);
        this.name = name;
        this.numericValue = numericValue;
    }

    public Value(ValueName name, Integer numericValue) {
        super(false);
        this.name = name;
        this.numericValue = numericValue;
    }

    @Override
    public String toString() {
        if (isNumeric()) return numericValue.toString();

        if (!isInverted()) return getName().name();

        char[] nameChars = getName().name().toCharArray();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nameChars.length; i++) {
            result.append(nameChars[i]);
            if (i == 0) result.append("̅");
        }

        return result.toString();

    }

    @Override
    public String toString(final boolean inner) {
        return toString();
    }


    public ValueName getName() {
        return name;
    }

    @JsonIgnore
    public boolean isNumeric() {
        return this.numericValue != null;
    }

    public Integer getNumericValue() {
        return this.numericValue;
    }

    public  Value getInverted(){
        Value result = new Value(this.name, this.numericValue);
        result.invert();
        return result;
    }

    public void invert(){
        this.inversion = !this.inversion;
        this.setNumericValue(numericValue);
    }

    public void setNumericValue(Integer number){

        if (!this.isInverted()){
            this.numericValue = number;
        }
        else {
            if (Objects.equals(number, 1)){
                this.numericValue = 0;
            } else if (Objects.equals(number, 0)) {
                this.numericValue = 1;
            }
        }


    }


    @Override
    public Value clone() {
        return (Value) super.clone();
    }
}
