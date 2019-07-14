package by.vision.rlv;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ReentrantLockValidator {

    private static int numToDelete;
    private static char deletionChar;
    private static String currentStr;

    public static Set<String> validate(String input) {

        Set<String> set = new TreeSet<>();

        currentStr = edgeCases(input);

        //check if current string can be converted to "correct"
        //i.e. string should have one or more '{'  '}' pair
        if (!currentStr.contains("{") || !currentStr.contains("}")) return set;

        numToDelete = unnecessaryBrackets(currentStr);

        fillSet(currentStr, 0, set);

        return set;

    }

    /**
     * Get rid of all closing braces '}'  at the beginning
     * and all opening braces '{' at the end of string(sb)
     *
     * @param str
     */
    private static String edgeCases(String str) {
        final StringBuilder sb = new StringBuilder(str);

        while (sb.indexOf("}") > -1 && sb.indexOf("}") < sb.indexOf("{")) {
            final int index = sb.indexOf("}");
            sb.deleteCharAt(index);
        }
        while (sb.lastIndexOf("}") > -1 && sb.lastIndexOf("{") > sb.lastIndexOf("}")) {
            final int index = sb.lastIndexOf("{");
            sb.deleteCharAt(index);
        }

        return sb.toString();
    }

    /**
     * Count bare minimum number of brackets that needed to be deleted
     * from string(str) to make it "correct"
     *
     * @param str
     * @return brackets number to delete
     */
    private static int unnecessaryBrackets(String str) {
        int counter;

        int openBracketsCount = 0;
        int closedBracketsCount = 0;

        for (char c :
                str.toCharArray()) {
            if (c == '{') openBracketsCount++;
            if (c == '}') closedBracketsCount++;
        }

        counter = openBracketsCount - closedBracketsCount;

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
     * @param s
     * @param index
     * @param set
     */
    private static void fillSet(String s, int index, Set<String> set) {

        final boolean minSize = (s.length() == currentStr.length() - numToDelete);
        if (bracesClosed(s) && minSize) set.add(s);

        for (int i = index; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (deletionChar == c) {
                final String s1 = s.substring(0, i) + s.substring(i + 1);
                fillSet(s1, i, set);
            }
        }

    }

    /**
     * Check if all braces in string(str) are balanced
     * i.e. each opening brace has it's own closing brace.
     *
     * @param str
     * @return boolean
     */
    private static boolean bracesClosed(String str) {

        Stack<Character> stack = new Stack<>();

        for (char c :
                str.toCharArray()) {
            if (c != '{' && c != '}') continue;
            if (c == '{') {
                stack.push(c);
                continue;
            }
            if (stack.empty()) {
                return false;
            } else stack.pop();
        }

        return stack.empty();
    }

}
