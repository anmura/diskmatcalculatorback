package DiskMatExpressionCalculator.web.Response;

import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TruthTableResponse {

    private List<Integer> zeroPositions;
    private List<Integer> onePositions;

    public TruthTableResponse(TruthTable truthTable) {
        this.zeroPositions = truthTable.getZeroArea().getPositions();
        this.onePositions = truthTable.getOneArea().getPositions();
    }
}
