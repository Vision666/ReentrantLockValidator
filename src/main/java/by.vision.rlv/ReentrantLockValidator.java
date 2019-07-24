package by.vision.rlv;

import java.util.*;

public class ReentrantLockValidator {

    private static int finalStrSize;
    private static char deletionChar;
    private static String currentStr;

    public static Set<String> validate(String input) {

        final Set<String> set = new TreeSet<>();

        if (!edgeCasesCheck(input)) return set;

        finalStrSize = unnecessaryBraces(currentStr);

        fillSet(new StringBuilder(currentStr), 0, set);

        return set;

    }

    /**
     * To proceed calculations input string should meet next conditions:
     * 1. it's not null;
     * 2. it can't start with closing brace and end with opening brace;
     * 3. if at least one opening brace is present in the string, there must be at least one closing brace and vice versa.
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

        currentStr = sb.toString();

        final boolean hasOpeningBrace = sb.indexOf("{") != -1;
        final boolean hasClosingBrace = sb.indexOf("}") != -1;

        return hasOpeningBrace == hasClosingBrace;

    }

    /**
     * Counts bare minimum number of the braces that needed to be deleted
     * from the string to make it "valid"
     *
     * @param str input string
     * @return number of braces needed to be deleted
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

        return str.length() - counter;

    }

    /**
     * Fills set with all possible options of the "valid" strings
     *
     * @param inputStr input string
     * @param index point in input string, from which the search of
     * @param set contains all possible variants of opening/closing braces
     */
    private static void fillSet(StringBuilder inputStr, int index, Set<String> set) {

        final int length = inputStr.length();
        final boolean meetSize = (length == finalStrSize);

        if (meetSize && bracesBalanced(inputStr)) set.add(inputStr.toString());

        if (length > finalStrSize) {
            for (int i = index; i < length; i++) {
                final char c = inputStr.charAt(i);
                if (deletionChar == c) {
                    final StringBuilder newStr = new StringBuilder(inputStr);
                    newStr.deleteCharAt(i);
                    fillSet(newStr, i, set);
                }
            }
        }

    }

    /**
     * Checks if all braces in the string are balanced
     * i.e. each opening brace has it's own closing brace.
     *
     * @param str string to be checked
     * @return true if string is balanced; false otherwise.
     */
    private static boolean bracesBalanced(StringBuilder str) {

        Deque<Character> bracesDeque = new ArrayDeque<>();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c != '{' && c != '}') continue;
            if (c == '{') {
                bracesDeque.push(c);
                continue;
            }
            if (bracesDeque.isEmpty()) {
                return false;
            } else bracesDeque.removeLast();
        }

        return bracesDeque.isEmpty();
    }

}
