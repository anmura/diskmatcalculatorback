package DiskMatExpressionCalculator.Models;

import DiskMatExpressionCalculator.Enums.AreaName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Area {
    private AreaName name;
    private List<Integer> positions;
    public void addPosition(int position){
        positions.add(position);
    }

}
