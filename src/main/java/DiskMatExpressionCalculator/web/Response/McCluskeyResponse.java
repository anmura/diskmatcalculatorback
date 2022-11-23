package DiskMatExpressionCalculator.web.Response;

import DiskMatExpressionCalculator.Calculator.Models.FunctionFormData;
import DiskMatExpressionCalculator.Calculator.Models.Implicant;
import DiskMatExpressionCalculator.Calculator.Models.McCluskey.CoverageTable;
import DiskMatExpressionCalculator.Calculator.Models.McCluskey.McCluskey;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class McCluskeyResponse {

    private Map<Integer, Map<Integer, List<String>>> customConjunctiveTabulationData;
    private Map<Integer, Map<Integer, List<String>>> customDisjunctiveTabulationData;

    private Map<String, List<Integer>> conjunctivePrimaryImplicantPositionsByBinaryValues;
    private Map<String, List<Integer>> disjunctivePrimaryImplicantPositionsByBinaryValues;

    private FunctionFormData conjunctiveFullNormalFormData;
    private FunctionFormData disjunctiveFullNormalFormData;
    private FunctionFormData conjunctiveExpandedNormalFormData;
    private FunctionFormData disjunctiveExpandedNormalFormData;
    private FunctionFormData conjunctiveMinimalNormalFormData;
    private FunctionFormData disjunctiveMinimalNormalFormData;

    public McCluskeyResponse(McCluskey mcCluskey) {
        Map<Integer, Map<Integer, List<Implicant>>> conjunctiveTabulationData = mcCluskey.getConjunctiveTabulationData();
        this.customConjunctiveTabulationData = getCustomTabulationData(conjunctiveTabulationData);

        Map<Integer, Map<Integer, List<Implicant>>> disjunctiveTabulationData = mcCluskey.getDisjunctiveTabulationData();
        this.customDisjunctiveTabulationData = getCustomTabulationData(disjunctiveTabulationData);

        CoverageTable conjunctiveCoverageTable = mcCluskey.getConjunctiveCoverageTable();
        CoverageTable disjunctiveCoverageTable = mcCluskey.getDisjunctiveCoverageTable();

        this.conjunctivePrimaryImplicantPositionsByBinaryValues = getPrimaryImplicantsPositions(conjunctiveCoverageTable.getPrimaryImplicants());
        this.disjunctivePrimaryImplicantPositionsByBinaryValues = getPrimaryImplicantsPositions(disjunctiveCoverageTable.getPrimaryImplicants());

        disjunctiveFullNormalFormData = mcCluskey.getDisjunctiveFullNormalFormData();
        conjunctiveFullNormalFormData = mcCluskey.getConjunctiveFullNormalFormData();
        conjunctiveExpandedNormalFormData = mcCluskey.getConjunctiveExpandedNormalFormData();
        disjunctiveExpandedNormalFormData = mcCluskey.getDisjunctiveExpandedNormalFormData();
        conjunctiveMinimalNormalFormData = mcCluskey.getConjunctiveMinimalNormalFormData();
        disjunctiveMinimalNormalFormData = mcCluskey.getDisjunctiveMinimalNormalFormData();

    }

    private Map<String, List<Integer>> getPrimaryImplicantsPositions(List<Implicant> primaryImplicants) {
        Map<String, List<Integer>> result = new HashMap<>();
        primaryImplicants.forEach(p -> result.put(p.getBinaryValue(), p.getCoverage()));
        return result;
    }

    private Map<Integer, Map<Integer, List<String>>> getCustomTabulationData(Map<Integer,
            Map<Integer, List<Implicant>>> tabulationData) {

        Map<Integer, Map<Integer, List<String>>> result = new HashMap<>();

        Set<Integer> intervals = tabulationData.keySet();

        for (Integer interval : intervals) {
            Map<Integer, List<Implicant>> intervalData = tabulationData.get(interval);
            Set<Integer> intervalIndexes = intervalData.keySet();

            Map<Integer, List<String>> pasteMap = new HashMap<>();

            for (Integer index : intervalIndexes) {
                List<Implicant> implicants = intervalData.get(index);
                List<String> binaryImplicants = new ArrayList<>();

                implicants.forEach(i -> binaryImplicants.add(i.getBinaryValue()));

                pasteMap.put(index, binaryImplicants);

            }

            result.put(interval, pasteMap);
        }

        return result;
    }
}























