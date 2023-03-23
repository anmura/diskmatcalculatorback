package DiskMatExpressionCalculator.web.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorControllerService {
    @Autowired
    CalculatorController calculatorController;

    @Test
    public void resultIsCorrect(){
        calculatorController.getCalculations("12345");
    }
}
