package by.vision.rlv;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ReentrantLockValidator {

    private static int numToDelete;
    private static char deletionChar;
    private static String currentStr;

    /**
     * Delete minimal number of
     *
     * @param input
     * @return
     */
    public static Set<String> validate(String input) {

        Set<String> set = new TreeSet<>();

        if (!edgeCasesCheck(input)) return set;

        numToDelete = unnecessaryBraces(currentStr);

        fillSet(currentStr, 0, set);

        return set;

    }

    /**
     * Get rid of all closing braces '}'  at the beginning
     * and all opening braces '{' at the end of string(sb)
     *
     * @param str string to validate for edge cases
     * @return true if string meets all conditions; false otherwise.
     */
    private static boolean edgeCasesCheck(String str) {
        if (str == null) return false;

        final StringBuilder sb = new StringBuilder(str);

        while (sb.indexOf("}") > -1 && sb.indexOf("}") < sb.indexOf("{")) {
            final int index = sb.indexOf("}");
            sb.deleteCharAt(index);
        }
        while (sb.lastIndexOf("}") > -1 && sb.lastIndexOf("{") > sb.lastIndexOf("}")) {
            final int index = sb.lastIndexOf("{");
            sb.deleteCharAt(index);
        }

        //check if current string can be converted to "correct"
        //i.e. string should have at least one opening and closing braces
        if (sb.indexOf("{") == -1 || sb.indexOf("}") == -1) return false;

        currentStr = sb.toString();

        return true;
    }

    /**
     * Count bare minimum number of braces that needed to be deleted
     * from string(str) to make it "correct"
     *
     * @param str
     * @return number of braces needed to delete
     */
    private static int unnecessaryBraces(String str) {
        int counter;

        int openingBracesCount = 0;
        int closingBracesCount= 0;

        for (char c :
                str.toCharArray()) {
            if (c == '{') openingBracesCount++;
            if (c == '}') closingBracesCount++;
        }

        counter = openingBracesCount - closingBracesCount;

        if (counter > 0) {
            deletionChar = '{';
        } else if (counter < 0) {
            counter = Math.abs(counter);
            deletionChar = '}';
        }

        return counter;

    }

    /**
     * Fill set with all possible combinations of opening/closing braces
     *
     * @param inputStr
     * @param index
     * @param set
     */
    private static void fillSet(String inputStr, int index, Set<String> set) {

        final int maxStrSize = currentStr.length() - numToDelete;
        final boolean minSize = (inputStr.length() == maxStrSize);

        if (minSize && bracesBalanced(inputStr)) set.add(inputStr);

        for (int i = index; i < inputStr.length(); i++) {
            final char c = inputStr.charAt(i);
            if (deletionChar == c) {
                final String newStr = inputStr.substring(0, i) + inputStr.substring(i + 1);
                fillSet(newStr, i, set);
            }
        }

    }

    /**
     * Check if all braces in string(str) are balanced
     * i.e. each opening brace has it's own closing brace.
     *
     * @param str string to be checked
     * @return true if string is balanced; false otherwise.
     */
    private static boolean bracesBalanced(String str) {

        Stack<Character> bracesStack = new Stack<>();

        for (char c :
                str.toCharArray()) {
            if (c != '{' && c != '}') continue;
            if (c == '{') {
                bracesStack.push(c);
                continue;
            }
            if (bracesStack.empty()) {
                return false;
            } else bracesStack.pop();
        }

        return bracesStack.empty();
    }

}
