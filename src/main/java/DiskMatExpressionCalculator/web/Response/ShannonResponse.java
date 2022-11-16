package DiskMatExpressionCalculator.web.Response;

import DiskMatExpressionCalculator.Calculator.Models.Function;
import lombok.Getter;

@Getter
public class ShannonResponse {
    Function startFunction;
    Function endFunction;

    public ShannonResponse(Function startFunction, Function endFunction) {
        this.startFunction = startFunction;
        this.endFunction = endFunction;
    }
}
