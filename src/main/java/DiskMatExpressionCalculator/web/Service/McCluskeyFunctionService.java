package DiskMatExpressionCalculator.web.Service;

import DiskMatExpressionCalculator.Enums.AreaName;
import DiskMatExpressionCalculator.Models.Area;
import DiskMatExpressionCalculator.Models.McCluskey.McCluskey;
import DiskMatExpressionCalculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.McCluskeyFunctionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class McCluskeyFunctionService {

    public McCluskeyFunctionResponse calculateMcCluskeyFunction(List<Integer> zeroArea, List<Integer> oneArea) {
        TruthTable truthTable = new TruthTable(new Area(AreaName.ZERO, zeroArea), new Area(AreaName.ONE, oneArea));
        McCluskey mcCluskey = new McCluskey(truthTable);

        return new McCluskeyFunctionResponse(mcCluskey);
    }
}
