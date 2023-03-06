package DiskMatExpressionCalculator.Models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FunctionFormData {
    Function function;
    List<List<Integer>> positions;
    String functionString;

    public FunctionFormData(Function function, List<Implicant> implicants) {
        this.function = function;
        this.functionString = function.toString();
        setPositions(implicants);
    }

    private void setPositions(List<Implicant> implicants) {
        this.positions = new ArrayList<>();
        implicants.forEach(implicant -> positions.add(implicant.getCoverage()));
    }
}
