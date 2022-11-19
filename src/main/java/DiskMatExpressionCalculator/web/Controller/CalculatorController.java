package DiskMatExpressionCalculator.web.Controller;

import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.web.Response.ExpandedFunctionResponse;
import DiskMatExpressionCalculator.web.Response.McCluskeyResponse;
import DiskMatExpressionCalculator.web.Response.ShannonResponse;
import DiskMatExpressionCalculator.web.Response.TruthTableResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.ExpandedFunctionCalculator.calculateExpandedFunction;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.McCluskeyCalculator.calculateMcCluskey;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.ShannonFunctionCalculator.calculateDisjunctiveShannon;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.TruthTableCalculator.calculateTruthTable;

@Controller
@RequestMapping(value = "/calculator/")
public class CalculatorController {

    @GetMapping("test")
    public String getTest(@RequestParam String test) {
        return "test " + test;
    }

    @GetMapping("truth-table")
    public TruthTableResponse getTruthTableData(@RequestParam String studentNumber) {
        //TODO: validateStudentNumber

        System.out.println("studentNumber");
        System.out.println(studentNumber);

        return calculateTruthTable(studentNumber);
    }

    @GetMapping("mccluskey")
    public McCluskeyResponse getMcCluskeyData(@RequestParam List<Integer> zeroArea, @RequestParam List<Integer> oneArea) {
        return calculateMcCluskey(zeroArea, oneArea);
    }

    @GetMapping("shannon")
    public ShannonResponse getShannonDisjunctiveExpansion(@RequestParam Function function, @RequestParam List<ValueName> expansionValues) {
        return calculateDisjunctiveShannon(function, expansionValues);
    }

    @GetMapping("expanded-function")
    public ExpandedFunctionResponse getExpandedFunction(@RequestParam Function function, @RequestParam ValueName valueName, @RequestParam int constant) {
        return calculateExpandedFunction(function, valueName, constant);
    }

}
