package DiskMatExpressionCalculator.web.Controller;

import DiskMatExpressionCalculator.web.Response.CalculatorResponse;
import DiskMatExpressionCalculator.web.Service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculator/")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("calculate/{studentNumber}")
    public CalculatorResponse getCalculations(@PathVariable String studentNumber){
        //TODO: validateStudentNumber
        return calculatorService.getCalculations(studentNumber);
    }

}
