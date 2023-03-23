package DiskMatExpressionCalculator.web.Controller;

import DiskMatExpressionCalculator.web.Response.CalculatorResponse;
import DiskMatExpressionCalculator.web.Service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/calculator/")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("calculate/{studentNumber}")
    public CalculatorResponse getCalculations(@PathVariable String studentNumber){
        return calculatorService.getCalculations(studentNumber);
    }

}
