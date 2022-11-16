package DiskMatExpressionCalculator.Calculator.Helpers.Calculators;

import DiskMatExpressionCalculator.Calculator.Enums.AreaName;
import DiskMatExpressionCalculator.Calculator.Helpers.AreasCalculator;
import DiskMatExpressionCalculator.Calculator.Models.Area;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.TruthTableResponse;

import java.util.Map;

public class TruthTableCalculator {

    public static TruthTableResponse calculateTruthTable(String studentNumber){
        Map<AreaName, Area> areas = AreasCalculator.getAreas(studentNumber);
        TruthTable truthTable = new TruthTable(areas.get(AreaName.ZERO), areas.get(AreaName.ONE));

        return new TruthTableResponse(truthTable);
    }
}
