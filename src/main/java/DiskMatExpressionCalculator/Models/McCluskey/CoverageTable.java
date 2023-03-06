package DiskMatExpressionCalculator.Models.McCluskey;

import DiskMatExpressionCalculator.Enums.AreaName;
import DiskMatExpressionCalculator.Models.Area;
import DiskMatExpressionCalculator.Models.Function;
import DiskMatExpressionCalculator.Models.Implicant;
import DiskMatExpressionCalculator.Models.Operations.Operation;
import DiskMatExpressionCalculator.Models.FunctionFormData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CoverageTable {

    private final List<Integer> activePositions;
    private final List<Implicant> startImplicants;
    private final List<Implicant> primaryImplicants;


    private Operation functionOperation;
    private Operation innerFunctionOperation;

    public CoverageTable(List<Implicant> primaryImplicants, List<Implicant> startImplicants, Area area) {
        this.startImplicants = startImplicants;
        this.primaryImplicants = primaryImplicants;

        this.activePositions = area.getPositions();
        AreaName areaName = area.getName();

        switch (areaName) {
            case ONE -> {
                functionOperation = new Operation.Or();
                innerFunctionOperation = new Operation.And();
            }
            case ZERO -> {
                functionOperation = new Operation.And();
                innerFunctionOperation = new Operation.Or();
            }
        }
    }

    public List<Implicant> getPrimaryImplicants() {
        return primaryImplicants;
    }

    public FunctionFormData getFullNormalFormData() {
        Function fullForm = new Function(functionOperation);

        primaryImplicants.forEach(pi -> fullForm.addElement(pi.getFunctionPart(innerFunctionOperation)));

        return new FunctionFormData(fullForm, primaryImplicants);
    }

    public FunctionFormData getExpandedNormalFormData() {
        Function expandedForm = new Function(functionOperation);

        startImplicants.forEach(i -> expandedForm.addElement(i.getFunctionPart(innerFunctionOperation)));

        return new FunctionFormData(expandedForm, startImplicants);
    }

    public FunctionFormData getMinimalNormalFormData() {
        Function minimalForm = new Function(functionOperation);

        List<Implicant> minimalCoverageImplicants = getMinimalCoverageImplicants();

        minimalCoverageImplicants.forEach(i -> minimalForm.addElement(i.getFunctionPart(innerFunctionOperation)));

        return new FunctionFormData(minimalForm, minimalCoverageImplicants);
    }

    private List<Implicant> getMinimalCoverageImplicants() {
        List<Implicant> minimalCoverageImplicants = new ArrayList<>(primaryImplicants);

        for (Implicant implicant : primaryImplicants) {
            minimalCoverageImplicants.remove(implicant);
            if (notCovered(minimalCoverageImplicants)) {
                minimalCoverageImplicants.add(implicant);
            }
        }

        return minimalCoverageImplicants;
    }

    private boolean notCovered(List<Implicant> implicants) {
        List<Integer> coveredAreas = new ArrayList<>();

        for (Implicant implicant : implicants) {
            coveredAreas.addAll(implicant.getCoverage());
        }

        coveredAreas = new ArrayList<>(new HashSet<>(coveredAreas));

        return coveredAreas.size() != this.activePositions.size();

    }

}
