package DiskMatExpressionCalculator.Models;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Operations.Operation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static DiskMatExpressionCalculator.Helpers.DecHexBinaryCalculator.decToBinaryWithLength;


@Data
public class Implicant {
    private String binaryValue;
    private boolean isPrimary;
    private boolean wasGlued = false;
    private List<Integer> coverage;

    public Implicant(String binaryValue) {
        this.binaryValue = binaryValue;
        isPrimary = false;

        setCoverage();
    }

    public int getIndex(int value) {
        int count = 0;

        char[] chars = binaryValue.toCharArray();
        for (char character :
                chars) {
            if (Objects.equals(String.valueOf(character), String.valueOf(value))) {
                count++;
            }
        }

        return count;
    }

    public Implicant getGluedImplicant(Implicant compareImplicant) {
        char[] controlBytes = this.getBinaryValue().toCharArray();
        char[] compareBytes = compareImplicant.getBinaryValue().toCharArray();

        StringBuilder newImplicantBytes = new StringBuilder();

        int difference = 0;

        for (int i = 0; i < controlBytes.length; i++) {
            if (controlBytes[i] != compareBytes[i]) {
                difference++;
                if (difference > 1) {
                    return this;
                }
                newImplicantBytes.append('-');
            } else {
                newImplicantBytes.append(compareBytes[i]);
            }
        }

        wasGlued = true;
        return new Implicant(newImplicantBytes.toString());

    }

    public void setCoverage() {
        List<Integer> positions = new ArrayList<>();

        int emptyPlacesAmount = (int) String.copyValueOf(binaryValue.toCharArray()).chars().filter(c -> c == '-').count();
        int variationsAmount = (int) Math.pow(2, emptyPlacesAmount);

        for (int pos = 0; pos < variationsAmount; pos++) {
            char[] insertValues = decToBinaryWithLength(pos, emptyPlacesAmount).toCharArray();
            String binary = this.binaryValue;

            for (int i = 0; i < emptyPlacesAmount; i++) {
                binary = binary.replaceFirst("-", String.valueOf(insertValues[i]));
            }
            positions.add(Integer.parseInt(binary, 2));

        }

        this.coverage = positions;

    }

    public FunctionPart getFunctionPart(Operation operation) {

        char[] binaryElements = binaryValue.toCharArray();

        Function result = new Function(operation);

        for (int position = 0; position < binaryElements.length; position++) {
            String element = String.valueOf(binaryElements[position]);

            if (!element.equals("-")) {
                result.addElement(new Value(ValueName.getNameFromPosition(position),
                        operation.getPrimaryNumber() == Integer.parseInt(element)));
            }
        }

        if (result.getElements().size() == 1) {
            return result.getElements().get(0);
        }

        return result;
    }

    @Override
    public String toString() {
        return binaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Implicant implicant = (Implicant) o;
        return binaryValue.equals(implicant.binaryValue);
    }

}
