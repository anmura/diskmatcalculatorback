package DiskMatExpressionCalculator.web.Service;

import DiskMatExpressionCalculator.Enums.ValueName;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.web.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    @Autowired
    private TruthTableService truthTableService;
    @Autowired
    private ReducedFunctionService reducedFunctionService;
    @Autowired
    private McCluskeyFunctionService mcCluskeyFunctionService;
    @Autowired
    private ShannonFunctionService shannonFunctionService;

    public CalculatorResponse getCalculations(String studentNumber) {
        //Truth table
        TruthTableResponse truthTableResponse = truthTableService.calculateTruthTable(studentNumber);

        //McCluskey + all function responses
        McCluskeyFunctionResponse mcCluskeyFunctionResponse = mcCluskeyFunctionService.calculateMcCluskeyFunction(truthTableResponse.getZeroPositions(), truthTableResponse.getOnePositions());

        //Shannon
        Function functionForShannonExpansion = mcCluskeyFunctionResponse.getDisjunctiveMinimalNormalFormData().getFunction();
        ShannonResponse shannonResponseX1X3X5 = shannonFunctionService.calculateDisjunctiveShannon(functionForShannonExpansion, List.of(ValueName.X1, ValueName.X3, ValueName.X5));
        ShannonResponse shannonResponseX2X4 = shannonFunctionService.calculateDisjunctiveShannon(functionForShannonExpansion, List.of(ValueName.X2, ValueName.X4));

        //Expansion
        Function functionForExpansion = mcCluskeyFunctionResponse.getDisjunctiveMinimalNormalFormData().getFunction();
        ReducedFunctionResponse reducedFunctionResponseX2Zero = reducedFunctionService.calculateReducedFunction(functionForExpansion, ValueName.X2, 0);
        ReducedFunctionResponse reducedFunctionResponseX4One = reducedFunctionService.calculateReducedFunction(functionForExpansion, ValueName.X4, 1);
        ReducedFunctionResponse reducedFunctionResponseX1One = reducedFunctionService.calculateReducedFunction(functionForExpansion, ValueName.X1, 1);
        ReducedFunctionResponse reducedFunctionResponseX3Zero = reducedFunctionService.calculateReducedFunction(functionForExpansion, ValueName.X3, 0);

        //Set response values
        CalculatorResponse calculatorResponse = new CalculatorResponse();

        calculatorResponse.setTruthTableResponse(truthTableResponse);
        calculatorResponse.setMcCluskeyFunctionResponse(mcCluskeyFunctionResponse);

        calculatorResponse.setShannonResponseX1X3X5(shannonResponseX1X3X5);
        calculatorResponse.setShannonResponseX2X4(shannonResponseX2X4);

        calculatorResponse.setReducedFunctionResponseX2Zero(reducedFunctionResponseX2Zero);
        calculatorResponse.setReducedFunctionResponseX3Zero(reducedFunctionResponseX3Zero);
        calculatorResponse.setReducedFunctionResponseX1One(reducedFunctionResponseX1One);
        calculatorResponse.setReducedFunctionResponseX4One(reducedFunctionResponseX4One);

        return calculatorResponse;
    }
}
