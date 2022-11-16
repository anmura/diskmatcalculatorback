package DiskMatExpressionCalculator.web.Response;

import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import lombok.Getter;

import java.util.List;

@Getter
public class TruthTableResponse {

    private List<Integer> zeroPositions;
    private List<Integer> onePositions;

    public TruthTableResponse(TruthTable truthTable) {
        this.zeroPositions = truthTable.getZeroArea().getPositions();
        this.onePositions = truthTable.getOneArea().getPositions();
    }
}
