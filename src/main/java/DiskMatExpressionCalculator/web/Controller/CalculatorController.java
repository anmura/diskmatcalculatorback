package DiskMatExpressionCalculator.web.Controller;

import DiskMatExpressionCalculator.web.Request.ExpandedFunctionRequestData;
import DiskMatExpressionCalculator.web.Request.McCluskeyRequestData;
import DiskMatExpressionCalculator.web.Request.ShannonRequestData;
import DiskMatExpressionCalculator.web.Response.ExpandedFunctionResponse;
import DiskMatExpressionCalculator.web.Response.McCluskeyResponse;
import DiskMatExpressionCalculator.web.Response.ShannonResponse;
import DiskMatExpressionCalculator.web.Response.TruthTableResponse;
import org.springframework.web.bind.annotation.*;

import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.ExpandedFunctionCalculator.calculateExpandedFunction;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.McCluskeyCalculator.calculateMcCluskey;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.ShannonFunctionCalculator.calculateDisjunctiveShannon;
import static DiskMatExpressionCalculator.Calculator.Helpers.ResponseCalculators.TruthTableCalculator.calculateTruthTable;

@RestController
@RequestMapping(value = "/calculator/")
public class CalculatorController {

    @GetMapping("truth-table")
    public TruthTableResponse getTruthTableData(@RequestParam String studentNumber) {
        //TODO: validateStudentNumber

        System.out.println("studentNumber");
        System.out.println(studentNumber);

        return calculateTruthTable(studentNumber);
    }

    @GetMapping("mccluskey")
    public McCluskeyResponse getMcCluskeyData(@RequestBody McCluskeyRequestData requestData) {
        return calculateMcCluskey(requestData.getZeroPositions(), requestData.getOnePositions());
    }

    @GetMapping("shannon")
    public ShannonResponse getShannonDisjunctiveExpansion(@RequestParam ShannonRequestData requestData) {
        return calculateDisjunctiveShannon(requestData.getFunction(), requestData.getExpansionValues());
    }

    @GetMapping("expanded-function")
    public ExpandedFunctionResponse getExpandedFunction(@RequestParam ExpandedFunctionRequestData requestData) {
        return calculateExpandedFunction(requestData.getFunction(),requestData.getValueName(),requestData.getConstant());
    }

}
