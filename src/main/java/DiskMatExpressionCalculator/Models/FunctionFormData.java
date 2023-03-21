package DiskMatExpressionCalculator.Models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FunctionFormData {
    List<List<Integer>> positions;
    List<String> implicants;
    String functionString;

    Function function;

    public FunctionFormData(Function function, List<Implicant> implicants) {
        this.function = function;
        this.functionString = function.toString();
        setImplicantPositions(implicants);
    }

    private void setImplicantPositions(List<Implicant> implicants) {
        this.positions = new ArrayList<>();
        implicants.forEach(implicant -> positions.add(implicant.getCoverage()));

        this.implicants = implicants.stream().map(implicant-> implicant.toString()).toList();
    }
}
