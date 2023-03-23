package DiskMatExpressionCalculator.Helpers;

import java.util.regex.Pattern;

public class StudentNumberValidator {

    public static void checkStudentNumber(String studentNumber){
        if (!studentNumberIsValid(studentNumber) || !studentNumberIsSafe(studentNumber)){
            throw new IllegalArgumentException("Invalid or unsafe student number: " + studentNumber);
        }
    }

    private static boolean studentNumberIsValid(String studentNumber){
        String regex = "^(?!00000)\\d{5}$";
        return Pattern.matches(regex, studentNumber);
    }

    private static boolean studentNumberIsSafe(String studentNumber){
        String regex = "[;\"'`|*<>$()#&!]";
        return !studentNumber.matches(".*" + regex + ".*");
    }
}
