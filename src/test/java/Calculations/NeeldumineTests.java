package Calculations;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.FunctionPart;
import DiskMatExpressionCalculator.Models.Laws.Law;
import DiskMatExpressionCalculator.Models.Operations.Operation;
import DiskMatExpressionCalculator.Models.Value;
import org.junit.jupiter.api.Test;

public class NeeldumineTests {
    Value x1 = new Value(ValueName.X1);
    Value x2 = new Value(ValueName.X2);
    Value x3 = new Value(ValueName.X3);
    Value x4 = new Value(ValueName.X4);
    Value x5 = new Value(ValueName.X5);

    @Test
    public void neeldumine_orWithSameValues_NoChanges() {
        //x1 v x1 = x1 v x1
        Function function = new Function(new Operation.Or());
        function.addElement(x1);
        function.addElement(x1);

        System.out.println(function);
        FunctionPart result = Law.Absorption.apply(function);

        Function expected = new Function(new Operation.Or());
        expected.addElement(x1);
        expected.addElement(x1);


        System.out.println(result);
        System.out.println(expected);

        assert (expected.equals(result));

    }

    @Test
    public void neeldumine_orWithAllDifferentValues_NoChanges() {
        //x1 v x4x3 = x1 v x4x3
        Function function = new Function(new Operation.Or());
        function.addElement(x1);

        Function right = new Function(new Operation.And());
        right.addElement(x4);
        right.addElement(x3);

        function.addElement(right);
        System.out.println(function);

        FunctionPart result = Law.Absorption.apply(function);


        Function expected = new Function(new Operation.Or());
        expected.addElement(x1);

        Function expectedPart = new Function(new Operation.And());
        expectedPart.addElement(new Value(ValueName.X4));
        expectedPart.addElement(new Value(ValueName.X3));

        expected.addElement(expectedPart);

        System.out.println(result);
        System.out.println(expected);

        assert (expected.equals(result));

    }

    @Test
    public void neeldumine_orWithValueAndFunctionWithValue_Value() {
        //X1 v (X4X1X2) = x1
        Function testf = new Function(new Operation.Or());
        testf.addElement(x1);

        Function removeablePart = new Function(new Operation.And());

        removeablePart.addElement(x4);
        removeablePart.addElement(x1);
        removeablePart.addElement(x2);

        testf.addElement(removeablePart);

        System.out.println(testf);
        FunctionPart result = Law.Absorption.apply(testf);
        System.out.println(result);

        Value expected = x1;

        System.out.println(expected);

        assert (expected.equals(result));

    }


    @Test
    public void neeldumine_orWithInvertedValueAndFunctionWithValue_FunctionWithoutValue() {
        //X̅1 v (X4X1X2) = X̅1 v (X4X2)
        Function testf = new Function(new Operation.Or());
        testf.addElement(x1.getInverted());

        Function removeablePart = new Function(new Operation.And());

        removeablePart.addElement(x4);
        removeablePart.addElement(x1);
        removeablePart.addElement(x2);

        testf.addElement(removeablePart);

        System.out.println(testf);
        FunctionPart result = Law.Absorption.apply(testf);


        Function expected = new Function(new Operation.Or());
        expected.addElement(x1.getInverted());

        Function expectedPart = new Function(new Operation.And());
        expectedPart.addElement(x4);
        expectedPart.addElement(x2);

        expected.addElement(expectedPart);

        System.out.println(result);
        System.out.println(expected);

        assert (expected.equals(result));

    }

    @Test
    public void neeldumine_FunctionWithInvertedValue_FunctionWithoutInvertedValue() {
        //X1 v (X4X̅1X2) = X1 v (X4X2)
        Function testf = new Function(new Operation.Or());
        testf.addElement(x1);

        Function removeablePart = new Function(new Operation.And());

        removeablePart.addElement(x4);
        removeablePart.addElement(x1.getInverted());
        removeablePart.addElement(x2);

        testf.addElement(removeablePart);

        System.out.println(testf);
        FunctionPart result = Law.Absorption.apply(testf);


        Function expected = new Function(new Operation.Or());
        expected.addElement(x1);

        Function expectedPart = new Function(new Operation.And());
        expectedPart.addElement(x4);
        expectedPart.addElement(x2);

        expected.addElement(expectedPart);

        System.out.println(result);
        System.out.println(expected);

        assert (expected.equals(result));

    }

    @Test
    public void neeldumine_InvertedValueWithFunction_InvertedValue() {
        // X̅1 v (X4X̅1X2) = X̅1
        Function testf = new Function(new Operation.Or());
        testf.addElement(x1.getInverted());

        Function removeablePart = new Function(new Operation.And());

        removeablePart.addElement(x4);
        removeablePart.addElement(x1.getInverted());
        removeablePart.addElement(x2);

        testf.addElement(removeablePart);

        System.out.println(testf);

        FunctionPart res = Law.Absorption.apply(testf);

        System.out.println(res);
        System.out.println(x1.getInverted());

        assert (x1.getInverted().equals(res));

    }


    @Test
    public void neeldumine_TwoInvertedFunctions_OneInvertedFunction() {
        //  ¬(X1X2) v ( ¬(X1X2)X4X5) = ¬(X1X2)
        Function testf = new Function(new Operation.Or());

        Function left = new Function(new Operation.And());
        left.addElement(x1);
        left.addElement(x2);
        left.invert();

        Function right = new Function(new Operation.And());

        Function rightIn = new Function(new Operation.And());
        rightIn.addElement(x1);
        rightIn.addElement(x2);
        rightIn.invert();
        right.addElement(rightIn);

        right.addElement(x4);
        right.addElement(x5);

        testf.addElement(left);
        testf.addElement(right);

        System.out.println(testf);

        FunctionPart res = Law.Absorption.apply(testf);

        System.out.println(res);


        Function expected = new Function(new Operation.And());
        expected.addElement(x1);
        expected.addElement(x2);

        expected.invert();

        System.out.println(expected);

        assert (res.equals(expected));

    }

    @Test
    public void neeldumine_twoPartFunction_onePartFunction() {
        // (X1X2) v (X1X2X4X5) = X1X2
        Function testf = new Function(new Operation.Or());

        Function left = new Function(new Operation.And());
        left.addElement(x1);
        left.addElement(x2);

        Function right = new Function(new Operation.And());
        right.addElement(x1);
        right.addElement(x2);
        right.addElement(x4);
        right.addElement(x5);

        testf.addElement(left);
        testf.addElement(right);

        System.out.println(testf);

        FunctionPart res = Law.Absorption.apply(testf);

        System.out.println(res);


        Function expected = new Function(new Operation.And());
        expected.addElement(x1);
        expected.addElement(x2);

        System.out.println(expected);

        assert (res.equals(expected));

    }

    @Test
    public void neeldumine_threePartFunction_twoPartFunction() {
        // (X1X2X5) v (X4X5) v (X1X2) = X1X2 v X4X5
        Function testf = new Function(new Operation.Or());

        Function part1 = new Function(new Operation.And());
        part1.addElement(x1);
        part1.addElement(x2);
        part1.addElement(x5);

        Function part2 = new Function(new Operation.And());
        part2.addElement(x4);
        part2.addElement(x5);

        Function part3 = new Function(new Operation.And());
        part3.addElement(x1);
        part3.addElement(x2);

        testf.addElement(part1);
        testf.addElement(part2);
        testf.addElement(part3);

        System.out.println(testf);
        FunctionPart res = Law.Absorption.apply(testf);
        System.out.println(res);


        Function expected = new Function(new Operation.Or());

        Function expectedPart1 = new Function(new Operation.And());
        expectedPart1.addElement(x1);
        expectedPart1.addElement(x2);

        Function expectedPart2 = new Function(new Operation.And());
        expectedPart2.addElement(x4);
        expectedPart2.addElement(x5);

        expected.addElement(expectedPart1);
        expected.addElement(expectedPart2);

        System.out.println(expected);

        assert (res.equals(expected));

    }
}
