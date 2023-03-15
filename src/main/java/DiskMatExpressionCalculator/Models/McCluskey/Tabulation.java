package DiskMatExpressionCalculator.Models.McCluskey;


import DiskMatExpressionCalculator.Calculator.Helpers.DecHexBinaryCalculator;
import DiskMatExpressionCalculator.Models.Area;
import DiskMatExpressionCalculator.Models.Implicant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabulation {

    private Map<Integer, Map<Integer, List<Implicant>>> tabulationData;
    private List<Implicant> startImplicantList;
    private final int countingValue;

    private final int MAX_INDEX = 5;

    public Tabulation(Area area) {
        countingValue = Integer.parseInt(area.getName().Value);
        setStartImplicantList(area.getPositions());
        setTabulationData();
    }

    private void setStartImplicantList(List<Integer> positions) {
        startImplicantList = new ArrayList<>();
        for (int position : positions) {
            String binary = DecHexBinaryCalculator.decToBinaryWithLength(position, MAX_INDEX);
            startImplicantList.add(new Implicant(binary));
        }
    }

    public Map<Integer, Map<Integer, List<Implicant>>> getTabulationData() {
        return tabulationData;
    }

    private void setTabulationData() {
        tabulationData = new HashMap<>();

        int interval = 0;
        Map<Integer, List<Implicant>> implicantsByIndex = getStartImplicantsByIndex();
        tabulationData.put((int) Math.pow(2, interval), implicantsByIndex);
        interval++;

        while (true) {
            Map<Integer, List<Implicant>> nextImplicantsByIndex = getNewImplicantsByIndex(implicantsByIndex);

            boolean isEmpty = checkEmpty(nextImplicantsByIndex);
            if (isEmpty) {
                break;
            }

            fillWithEmptyKeys(nextImplicantsByIndex);
            tabulationData.put((int) Math.pow(2, interval), nextImplicantsByIndex);

            interval++;
            implicantsByIndex = nextImplicantsByIndex;

        }
    }

    private Map<Integer, List<Implicant>> getNewImplicantsByIndex(Map<Integer, List<Implicant>> implicantsByIndex) {
        int index = 0;
        Map<Integer, List<Implicant>> nextImplicantsByIndex = new HashMap<>();

        while (index + 1 <= MAX_INDEX) {
            List<Implicant> newImplicants = getNewImplicants(implicantsByIndex, index);
            nextImplicantsByIndex.put(index, newImplicants);
            index++;

        }
        return nextImplicantsByIndex;
    }

    private void fillWithEmptyKeys(Map<Integer, List<Implicant>> nextImplicantsByIndex) {
        for (int i = 0; i <= MAX_INDEX; i++) {
            if (!nextImplicantsByIndex.containsKey(i)) {
                nextImplicantsByIndex.put(i, new ArrayList<>());
            }
        }
    }

    private boolean checkEmpty(Map<Integer, List<Implicant>> nextImplicantsByIndex) {
        boolean isEmpty = true;

        for (List<Implicant> list :
                nextImplicantsByIndex.values()) {
            if (!list.isEmpty()) {
                isEmpty = false;
                break;  // from for
            }
        }

        return isEmpty;
    }

    private List<Implicant> getNewImplicants(Map<Integer, List<Implicant>> implicantsByIndex, int index) {
        List<Implicant> newImplicants = new ArrayList<>();

        List<Implicant> controlList = implicantsByIndex.get(index);
        List<Implicant> compareList = implicantsByIndex.get(index + 1);

        for (Implicant controlImplicant : controlList) {
            for (Implicant compareImplicant : compareList) {
                Implicant newImplicant = controlImplicant.getGluedImplicant(compareImplicant);
                if (newImplicant != controlImplicant) {
                    controlImplicant.setPrimary(false);
                    compareImplicant.setPrimary(false);
                    newImplicant.setPrimary(true);

                    if (!newImplicants.contains(newImplicant)) {
                        newImplicants.add(newImplicant);
                    }

                }else{
                    compareImplicant.setPrimary(true);
                }
            }
        }
        return newImplicants;
    }

    private Map<Integer, List<Implicant>> getStartImplicantsByIndex() {
        Map<Integer, List<Implicant>> implicantsByIndex = new HashMap<>();
        List<Implicant> startImplicants = List.copyOf(startImplicantList);

        for (Implicant implicant : startImplicants) {
            int index = implicant.getIndex(countingValue);

            if (!implicantsByIndex.containsKey(index)) {
                implicantsByIndex.put(index, new ArrayList<>());
            }

            List<Implicant> indexImplicants = implicantsByIndex.get(index);
            indexImplicants.add(implicant);
            implicantsByIndex.put(index, indexImplicants);
        }

        // place empty keys (because all indexes must be in the map)
        fillWithEmptyKeys(implicantsByIndex);

        return implicantsByIndex;
    }

    public List<Implicant> getPrimaryImplicants() {
        List<Implicant> primaryImplicants = new ArrayList<>();

        List<Integer> intervals = tabulationData.keySet().stream().toList();
        for (int interval : intervals) {

            for (int i = 0; i <= MAX_INDEX; i++) {
                List<Implicant> implicants = tabulationData.get(interval).get(i);

                for (Implicant implicant :
                        implicants) {
                    if (implicant.isPrimary()) {
                        primaryImplicants.add(implicant);
                    }
                }
            }
        }

        return primaryImplicants;
    }

    public List<Implicant> getStartImplicants() {
        return startImplicantList;
    }


}
