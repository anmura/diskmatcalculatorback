package DiskMatExpressionCalculator.web.Service;

import DiskMatExpressionCalculator.Enums.AreaName;
import DiskMatExpressionCalculator.Models.Area;
import DiskMatExpressionCalculator.Models.TruthTable;
import DiskMatExpressionCalculator.web.Response.TruthTableResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DiskMatExpressionCalculator.Calculator.Helpers.DecHexBinaryCalculator.hexArrayToDecList;

@Service
public class TruthTableService {

    private long workingNumber;
    private List<Integer> onePositions = new ArrayList<>();
    private List<Integer> zeroPositions = new ArrayList<>();

    public TruthTableResponse calculateTruthTable(String studentNumber) {
        Map<AreaName, Area> areas = getAreas(studentNumber);
        TruthTable truthTable = new TruthTable(areas.get(AreaName.ZERO), areas.get(AreaName.ONE));

        return new TruthTableResponse(truthTable);
    }

    private Map<AreaName, Area> getAreas(String studentNumber) {
        final int workingNumberLength = 5;

        workingNumber = Integer.parseInt(studentNumber.substring(studentNumber.length() - workingNumberLength));

        String sevenDigitHex = multiplyHexBySevenToLength(7);
        List<Integer> onePositionList = hexArrayToDecList(sevenDigitHex.toCharArray());

        addOnePositions(onePositionList);

        String eihthDigitHex = multiplyHexBySevenToLength(8);
        onePositionList = hexArrayToDecList(eihthDigitHex.toCharArray());
        onePositionList = addNumberToPositions(onePositionList, 8);

        addOnePositions(onePositionList);

        String nineDigitHex = multiplyHexBySevenToLength(9);
        onePositionList = hexArrayToDecList(nineDigitHex.toCharArray());
        onePositionList = addNumberToPositions(onePositionList, 16);

        addOnePositions(onePositionList);

        setZeroArea();

        Map<AreaName, Area> areas = new HashMap<>();
        Area oneArea = new Area(AreaName.ONE, List.copyOf(onePositions));
        Area zeroArea = new Area(AreaName.ZERO, List.copyOf(zeroPositions));

        areas.put(oneArea.getName(), oneArea);
        areas.put(zeroArea.getName(), zeroArea);

        resetAreas();

        return areas;

    }

    private void resetAreas() {
        onePositions.clear();
        zeroPositions.clear();
    }

    private void setZeroArea() {
        for (int i = 0; i < 32; i++) {
            if (!onePositions.contains(i)) {
                zeroPositions.add(i);
            }
        }
    }

    private List<Integer> addNumberToPositions(List<Integer> positions, int number) {
        return positions.stream().map(position -> position + number).toList();
    }


    private String multiplyHexBySevenToLength(int resultLength) {
        String hex = Long.toHexString(workingNumber);

        while (hex.length() != resultLength) {
            hex = Long.toHexString(workingNumber *= 7);
        }

        return hex;
    }

    private void addOnePositions(List<Integer> areaPositions) {
        areaPositions.forEach(position -> addPosition(position, onePositions));
    }

    private void addPosition(int position, List<Integer> positionsList) {
        if (!positionsList.contains(position)) {
            positionsList.add(position);
        }
    }
}
