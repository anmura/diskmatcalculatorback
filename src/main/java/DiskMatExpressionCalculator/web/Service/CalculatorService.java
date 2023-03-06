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
    private ExpandedFunctionService expandedFunctionService;
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
        Function functionForShannonExpansion = mcCluskeyFunctionResponse.getDisjunctiveFullNormalFormData().getFunction();
        ShannonResponse shannonResponseX1X3 = shannonFunctionService.calculateDisjunctiveShannon(functionForShannonExpansion, List.of(ValueName.X1, ValueName.X3));
        ShannonResponse shannonResponseX2X4 = shannonFunctionService.calculateDisjunctiveShannon(functionForShannonExpansion, List.of(ValueName.X2, ValueName.X4));

        //Expansion
        Function functionForExpansion = mcCluskeyFunctionResponse.getConjunctiveMinimalNormalFormData().getFunction();
        ExpandedFunctionResponse expandedFunctionResponseX2Zero = expandedFunctionService.calculateExpandedFunction(functionForExpansion, ValueName.X2, 0);
        ExpandedFunctionResponse expandedFunctionResponseX4One = expandedFunctionService.calculateExpandedFunction(functionForExpansion, ValueName.X4, 1);
        ExpandedFunctionResponse expandedFunctionResponseX1One = expandedFunctionService.calculateExpandedFunction(functionForExpansion, ValueName.X1, 1);
        ExpandedFunctionResponse expandedFunctionResponseX3Zero = expandedFunctionService.calculateExpandedFunction(functionForExpansion, ValueName.X3, 0);

        //Set response values
        CalculatorResponse calculatorResponse = new CalculatorResponse();

        calculatorResponse.setTruthTableResponse(truthTableResponse);
        calculatorResponse.setMcCluskeyFunctionResponse(mcCluskeyFunctionResponse);

        calculatorResponse.setShannonResponseX1X3(shannonResponseX1X3);
        calculatorResponse.setShannonResponseX2X4(shannonResponseX2X4);

        calculatorResponse.setExpandedFunctionResponseX2Zero(expandedFunctionResponseX2Zero);
        calculatorResponse.setExpandedFunctionResponseX3Zero(expandedFunctionResponseX3Zero);
        calculatorResponse.setExpandedFunctionResponseX1One(expandedFunctionResponseX1One);
        calculatorResponse.setExpandedFunctionResponseX4One(expandedFunctionResponseX4One);

        return calculatorResponse;
    }
}
