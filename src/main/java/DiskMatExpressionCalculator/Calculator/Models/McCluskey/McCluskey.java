package DiskMatExpressionCalculator.Calculator.Models.McCluskey;

import DiskMatExpressionCalculator.Calculator.Models.Area;
import DiskMatExpressionCalculator.Calculator.Models.Implicant;
import DiskMatExpressionCalculator.Calculator.Models.TruthTable;
import DiskMatExpressionCalculator.Calculator.Models.FunctionFormData;

import java.util.List;
import java.util.Map;

public class McCluskey {

    private Tabulation disjunctiveTabulation;
    private Tabulation conjunctiveTabulation;

    private CoverageTable disjunctiveCoverageTable;
    private CoverageTable conjunctiveCoverageTable;


    public McCluskey(TruthTable truthTable) {
        Area oneArea = truthTable.getOneArea();
        Area zeroArea = truthTable.getZeroArea();

        disjunctiveTabulation = new Tabulation(oneArea);
        conjunctiveTabulation = new Tabulation(zeroArea);

        disjunctiveCoverageTable = new CoverageTable(disjunctiveTabulation.getPrimaryImplicants(), disjunctiveTabulation.getSimpleImplicants(), oneArea);
        conjunctiveCoverageTable = new CoverageTable(conjunctiveTabulation.getPrimaryImplicants(), conjunctiveTabulation.getSimpleImplicants(), zeroArea);

    }

    public CoverageTable getDisjunctiveCoverageTable() {
        return disjunctiveCoverageTable;
    }

    public CoverageTable getConjunctiveCoverageTable() {
        return conjunctiveCoverageTable;
    }

    public Map<Integer, Map<Integer, List<Implicant>>> getDisjunctiveTabulationData() {
        return disjunctiveTabulation.getTabulationData();
    }

    public Map<Integer, Map<Integer, List<Implicant>>> getConjunctiveTabulationData() {
        return disjunctiveTabulation.getTabulationData();
    }

    public FunctionFormData getDisjunctiveFullNormalFormData() {
        return disjunctiveCoverageTable.getFullNormalFormData();
    }

    public FunctionFormData getConjunctiveFullNormalFormData() {
        return conjunctiveCoverageTable.getFullNormalFormData();
    }

    public FunctionFormData getConjunctiveExpandedNormalFormData() {
        return conjunctiveCoverageTable.getExpandedNormalFormData();
    }

    public FunctionFormData getDisjunctiveExpandedNormalFormData() {
        return disjunctiveCoverageTable.getExpandedNormalFormData();
    }

    public FunctionFormData getConjunctiveMinimalNormalFormData() {
        return conjunctiveCoverageTable.getMinimalNormalFormData();
    }

    public FunctionFormData getDisjunctiveMinimalNormalFormData() {
        return disjunctiveCoverageTable.getMinimalNormalFormData();
    }
}
