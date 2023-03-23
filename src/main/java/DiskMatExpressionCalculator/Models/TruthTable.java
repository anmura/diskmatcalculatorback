package DiskMatExpressionCalculator.Models;


import DiskMatExpressionCalculator.Enums.AreaName;
import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Operations.Operation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DiskMatExpressionCalculator.Helpers.DecHexBinaryCalculator.decToBinaryWithLength;
import static DiskMatExpressionCalculator.Helpers.FunctionHelper.calculateWithConstants;


@Getter
public class TruthTable {
    Area zeroArea;
    Area oneArea;
    Map<Integer, Integer> truthTable;

    public TruthTable(Area zeroArea, Area oneArea) {
        this.zeroArea = zeroArea;
        this.oneArea = oneArea;

        truthTable = new HashMap<>();
        setTruthTable();
    }

    public TruthTable(FunctionPart input) {
        oneArea = new Area(AreaName.ONE, new ArrayList<>());
        zeroArea = new Area(AreaName.ZERO, new ArrayList<>());

        if (input.isValue()) {
            Value value = (Value) input;
            if (!value.isNumeric()) {
                input = new Function(new Operation.And(), List.of(input));
            } else {
                return;
            }
        }

        setPositionsFromFunction((Function) input);
    }

    private void setPositionsFromFunction(Function function) {
        List<ValueName> containedValueNames = function.getContainedValueNames();

        int binaryLength = containedValueNames.size();
        int tableSize = (int) Math.pow(2, binaryLength);

        for (int i = 0; i < tableSize; i++) {
            char[] binaryValues = decToBinaryWithLength(i, binaryLength).toCharArray();
            Function calculatingFunction = function.clone();

            for (int j = 0; j < binaryLength; j++) {
                calculatingFunction.setNumberToValue(Integer.parseInt(String.valueOf(binaryValues[j])), containedValueNames.get(j));
            }

            Value res = (Value) calculateWithConstants(calculatingFunction);
                int num = res.getNumericValue();

                if (num == 1) {
                    oneArea.addPosition(i);
                } else if (num == 0) {
                    zeroArea.addPosition(i);
                }

        }
    }

    private void setTruthTable() {
        setValuePositions(0, zeroArea.getPositions());
        setValuePositions(1, oneArea.getPositions());
    }

    private void setValuePositions(int value, List<Integer> positions) {
        positions.forEach(position -> truthTable.put(position, value));
    }
}
