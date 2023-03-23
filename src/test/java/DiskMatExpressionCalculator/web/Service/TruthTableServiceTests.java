package DiskMatExpressionCalculator.web.Service;

import DiskMatExpressionCalculator.web.Response.TruthTableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class TruthTableServiceTests {

    @Autowired
    private TruthTableService truthTableService;
    @Test
    public void generatesTruntTableWithCorrectData(){
        for (int i = 1; i <= 99999; i++) {
            TruthTableResponse truthTable = truthTableService.calculateTruthTable("12345");

            assert(truthTable.getOnePositions().size() + truthTable.getZeroPositions().size() == 32);
            assert(truthTable.getOnePositions().size() != 0);
            assert(truthTable.getZeroPositions().size() != 0);
            assert(containsOnlyUniqueNumbers(truthTable.getOnePositions()));
            assert(containsOnlyUniqueNumbers(truthTable.getZeroPositions()));
            assert(noDublicationsInLists(truthTable.getZeroPositions(), truthTable.getOnePositions()));
        }
    }

    private boolean containsOnlyUniqueNumbers(List<Integer> numberList){
        return new HashSet<>(numberList).size() == numberList.size();
    }

    private boolean noDublicationsInLists(List<Integer> numberList, List<Integer> checkNumberList){
        List<Integer> fullList = new ArrayList<>();
        fullList.addAll(numberList);
        fullList.addAll(checkNumberList);

        return new HashSet<>(fullList).size() == fullList.size();
    }
}
