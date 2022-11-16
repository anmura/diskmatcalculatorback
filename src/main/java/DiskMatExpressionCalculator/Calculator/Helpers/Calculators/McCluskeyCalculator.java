package DiskMatExpressionCalculator.Calculator.Helpers.Calculators;

import DiskMatExpressionCalculator.Calculator.Enums.AreaName;
import DiskMatExpressionCalculator.Calculator.Models.Area;
import DiskMatExpressionCalculator.Calculator.Models.McCluskey.McCluskey;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.McCluskeyResponse;

import java.util.List;

public class McCluskeyCalculator {

    public static McCluskeyResponse calculateMcCluskey(List<Integer> zeroArea, List<Integer> oneArea) {
        TruthTable truthTable = new TruthTable(new Area(AreaName.ZERO, zeroArea), new Area(AreaName.ONE, oneArea));
        McCluskey mcCluskey = new McCluskey(truthTable);

        return new McCluskeyResponse(mcCluskey);
    }
}
