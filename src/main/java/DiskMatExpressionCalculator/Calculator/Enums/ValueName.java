package DiskMatExpressionCalculator.Calculator.Enums;

public enum ValueName {
    X1, X2, X3, X4, X5;

    public static ValueName getNameFromPosition(int position) {
        return switch (position) {
            case 0 -> X1;
            case 1 -> X2;
            case 2 -> X3;
            case 3 -> X4;
            case 4 -> X5;
            default -> null;
        };

    }
}
