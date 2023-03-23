package DiskMatExpressionCalculator.Helpers;

import java.util.ArrayList;
import java.util.List;

public class DecHexBinaryCalculator {

    public static String decToBinaryWithLength(int dec, int length) {
        String binary = Integer.toBinaryString(dec);
        if (length != 0) {
            binary = "0".repeat(length).substring(0, length - binary.length()) + binary;
        }
        return binary;
    }

    public static List<Integer> hexArrayToDecList(char[] hexPositions) {
        List<Integer> decPositions = new ArrayList<>();

        for (char hexPosition : hexPositions) {
            int decPosition = Integer.parseInt(String.valueOf(hexPosition), 16);
            decPositions.add(decPosition);
        }

        return decPositions;
    }

}
