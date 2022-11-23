package DiskMatExpressionCalculator.web.Request;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import lombok.Data;

import java.util.List;

@Data
public class ShannonRequestData {
    private Function function;
    private List<ValueName> expansionValues;
}
