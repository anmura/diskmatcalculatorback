package Calculations;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.Operations.Operation;
import DiskMatExpressionCalculator.Models.Value;
import org.junit.jupiter.api.Test;

import java.util.List;

import static DiskMatExpressionCalculator.Helpers.FunctionHelper.calculateWithConstants;

public class FunctionWithConstantsTests {

    Value one = new Value(ValueName.X1, 1);
    Value zero = new Value(ValueName.X1, 0);
    Value value = new Value(ValueName.X1);
    Operation or = new Operation.Or();
    Operation and = new Operation.And();

    // OR
    @Test
    public void Or_ZeroZero_Zero(){
        Function function = new Function(or, List.of(zero,zero));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(zero));
    }

    @Test
    public void Or_ZeroOne_One(){
        Function function = new Function(or, List.of(zero,one));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(one));
    }

    @Test
    public void Or_OneOne_One(){
        Function function = new Function(or, List.of(one,one));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(one));
    }

    @Test
    public void Or_OneValue_One(){
        Function function = new Function(or, List.of(one,value));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(one));
    }

    @Test
    public void Or_ZeroValue_Value(){
        Function function = new Function(or, List.of(zero,value));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(value));
    }


    // AND

    @Test
    public void And_ZeroZero_Zero(){
        Function function = new Function(and, List.of(zero,zero));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(zero));
    }

    @Test
    public void And_ZeroOne_Zero(){
        Function function = new Function(and, List.of(zero,one));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(zero));
    }

    @Test
    public void And_OneOne_One(){
        Function function = new Function(and, List.of(one,one));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(one));
    }

    @Test
    public void And_OneValue_Value(){
        Function function = new Function(and, List.of(one,value));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(value));
    }

    @Test
    public void And_ZeroValue_Zero(){
        Function function = new Function(and, List.of(zero,value));
        FunctionPart result = calculateWithConstants(function);

        assert(result.equals(zero));
    }

}
