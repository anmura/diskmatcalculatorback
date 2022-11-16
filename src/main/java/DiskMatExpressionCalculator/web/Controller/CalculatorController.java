package DiskMatExpressionCalculator.web.Controller;

import DiskMatExpressionCalculator.Calculator.Enums.AreaName;
import DiskMatExpressionCalculator.Calculator.Enums.ValueName;
import DiskMatExpressionCalculator.Calculator.Helpers.AreasCalculator;
import DiskMatExpressionCalculator.Calculator.Models.Area;
import DiskMatExpressionCalculator.Calculator.Models.Function;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.ExpandedFunctionResponse;
import DiskMatExpressionCalculator.web.Response.McCluskeyResponse;
import DiskMatExpressionCalculator.web.Response.ShannonResponse;
import DiskMatExpressionCalculator.web.Response.TruthTableResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static DiskMatExpressionCalculator.Calculator.Helpers.Calculators.ExpandedFunctionCalculator.calculateExpandedFunction;
import static DiskMatExpressionCalculator.Calculator.Helpers.Calculators.McCluskeyCalculator.calculateMcCluskey;
import static DiskMatExpressionCalculator.Calculator.Helpers.Calculators.ShannonFunctionCalculator.calculateDisjunctiveShannon;
import static DiskMatExpressionCalculator.Calculator.Helpers.Calculators.TruthTableCalculator.calculateTruthTable;

@Controller
@RequestMapping(value = "/calculator")
public class CalculatorController {

    @GetMapping("truth-table")
    public TruthTableResponse getTruthTableData(@RequestParam String studentNumber) {
        //TODO: validateStudentNumber

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
