package DiskMatExpressionCalculator.web.Response;

import DiskMatExpressionCalculator.Models.Function;
import lombok.Getter;

@Getter
public class ShannonResponse {
    String startFunctionString;
    String endFunctionString;

    public ShannonResponse(Function startFunctionString, Function endFunctionString) {
        this.startFunctionString = startFunctionString.toString();
        this.endFunctionString = endFunctionString.toString();
    }
}
