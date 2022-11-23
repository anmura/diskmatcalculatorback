package DiskMatExpressionCalculator.web.Request;

import lombok.Data;

import java.util.List;

@Data
public class McCluskeyRequestData {
    private List<Integer> zeroPositions;
    private List<Integer> onePositions;
}
