/**
 * The Algorithm class contains recursive methods for several algorithmic problems.
 * <p>
 * It includes methods to compute factorial, count bunny ears, calculate Fibonacci numbers,
 * sum the digits of a number, count occurrences of the digit 8 (with special counting rules),
 * and compute exponentiation recursively.
 * </p>
 * 
 * How to compile and run the application:
 * 1. Open a terminal or command prompt.
 * 2. Compile: 
 *      ```
 *      javac std01-jayden-algo/AlgorithmAPXXtra.java
 *      ```
 * 3. Run & Clean: 
 *      ```
 *      java -cp std01-jayden-algo AlgorithmAPXXtra; rm -rf std01-jayden-algo/*.class
 *      ```
 */
import java.util.*;

public class AlgorithmAPXXtra {
    /**
     * < Q00 >
     * This is a placeholder for the main method. It can be used to test the methods in this class.
     * You can add your own test cases here.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        int[] arr = {99, 820, 23, 43};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        String str = "here is a string!";
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
        }

        ArrayList<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }



    /**
     * < Q38 >
     * Return true if the given string contains an appearance of "xyz" where the "xyz"
     * is not directly preceded by a period ('.'). For example, "xxyz" counts but "x.xyz" does not.
     *
     * @param s The input string to check.
     * @return true if "xyz" appears not preceded by '.', otherwise false.
     * <p>
     * <ul>
     *   <li>xyzThere("abcxyz") returns true</li>
     *   <li>xyzThere("abc.xyz") returns false</li>
     *   <li>xyzThere("xyz.abc") returns true</li>
     * </ul>
     * </p>
     */
    public static boolean xyzThere(String s) {
        s = s.toLowerCase();
        int check = 0;
        for (int i = 0; i < s.length()-2; i++) {
            if (s.charAt(i) == 'x') {
                check = 1;
            }
            else {
                check = 0;
            }


            if (check == 1) {
                if (s.charAt(i+1) == 'y') {
                    check = 1;
                }
                else {
                    check = 0;
                }
            }

            if (check == 1) {
                if (s.charAt(i+2) == 'z') {
                    check = 1;
                }
                else {
                    check = 0;
                }
            }
            if (check == 1) {
                if (i != 0) {
                    if (s.charAt(i-1) != '.') {
                    return true;
                    }
                }
                else {
                    return true;
                }
            }   


        }
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return false;
    }


    /**
     * < Q39 >
     * Given a list of integers, return true if the list contains no 1's and no 3's.
     *
     * @param nums The list of integers to check.
     * @return true if there are no 1's and no 3's in the list, otherwise false.
     * <p>
     * <ul>
     *   <li>lucky13(new int[]{0, 2, 4}) returns true</li>
     *   <li>lucky13(new int[]{1, 2, 3}) returns false</li>
     *   <li>lucky13(new int[]{1, 2, 4}) returns false</li>
     * </ul>
     * </p>
     */
    public static boolean lucky13(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (!(nums[i] != 1 && nums[i] != 3)) {
                return false;
            }


        }
        
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return true;
    }

    

    /**
     * < Q40 >
     * Given a list of integers, return true if the sum of all the 2's in the list is exactly 8.
     *
     * @param nums The list of integers to examine.
     * @return true if the sum of all 2's is exactly 8, otherwise false.
     * <p>
     * <ul>
     *   <li>sum28(new int[]{2,3,2,2,4,2}) returns true</li>
     *   <li>sum28(new int[]{2,3,2,2,4,2,2}) returns false</li>
     *   <li>sum28(new int[]{1,2,3,4}) returns false</li>
     *   <li>sum28(new int[]{2,2,2,2}) returns true</li>
     *   <li>sum28(new int[]{1,2,2,2,2,4}) returns true</li>
     * </ul>
     * </p>
     */
    public static boolean sum28(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 2) {
                count += 2;
                
            }
        }
        if (count == 8) {
            return true;
        }
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return false;
    }
    
    /**
     * < Q41 >
     * Given a list of integers, return true if the number of 1's in the list is greater than
     * the number of 4's.
     *
     * @param nums The list of integers to examine.
     * @return true if count of 1's is greater than count of 4's, otherwise false.
     * <p>
     * <ul>
     *   <li>more14(new int[]{1,4,1}) returns true</li>
     *   <li>more14(new int[]{1,4,1,4}) returns false</li>
     *   <li>more14(new int[]{1,1}) returns true</li>
     *   <li>more14(new int[]{1,6,6}) returns true</li>
     * </ul>
     * </p>
     */
    public static boolean more14(int[] nums) {
        int count1 = 0;
        int count4 = 0;
    
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 1) {

                count1++;


            } 
            else if (nums[i] == 4) {


                count4++;
            }
        }


    
        return count1 > count4;
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
    }

    
    /**
     * < Q42 >
     * Given 3 integer values, a, b, c, return their sum. However, if one of the values
     * is 13 then it does not count toward the sum and values to its right do not count.
     * So for example, if b is 13, then both b and c do not count.
     *
     * @param a First integer.
     * @param b Second integer.
     * @param c Third integer.
     * @return The sum as described, skipping 13 and any values to its right.
     * <p>
     * <ul>
     *   <li>luckySum(1, 2, 3) returns 6</li>
     *   <li>luckySum(1, 2, 13) returns 3</li>
     *   <li>luckySum(1, 13, 3) returns 1</li>
     * </ul>
     * </p>
     */
    public static int luckySum(int a, int b, int c) {
        int[] arr = {a, b, c};
        int sum = 0;
    
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 13) {
                break;
            }
            sum += arr[i];
        }
    
        return sum;
        
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.

    }


    
    /**
     * < Q43 >
     * Given a string, return true if the number of appearances of "is" anywhere in the string
     * is equal to the number of appearances of "not" anywhere in the string. The match is case-sensitive.
     *
     * @param s The input string to examine.
     * @return true if count of "is" equals count of "not", otherwise false.
     * <p>
     * <ul>
     *   <li>equalIsNot("This is not") returns false</li>
     *   <li>equalIsNot("This is notnot") returns true</li>
     *   <li>equalIsNot("noisxxnotyynotxisi") returns true</li>
     * </ul>
     * </p>
     */
    public static boolean equalIsNot(String s) {
        // [Your Implementation Here]
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return false;
    }

    /**
     * < Q44 >
     * Return a list containing exactly the same numbers as the given array, but rearranged so that every 4
     * is immediately followed by a 5. Do not move the 4's themselves, but every other number may move.
     * You may assume:
     *   - The array contains the same number of 4's and 5's.
     *   - Every 4 has a non-4 immediately after it.
     *   - 5's may appear anywhere in the original array.
     *
     * @param nums The input array of integers containing matching numbers of 4's and 5's.
     * @return A new array where each 4 is immediately followed by a 5.
     * <p>
     * <ul>
     *   <li>fix45(new int[]{5,4,9,4,9,5}) returns [9,4,5,4,5,9]</li>
     *   <li>fix45(new int[]{1,4,1,5}) returns [1,4,5,1]</li>
     *   <li>fix45(new int[]{1,4,1,5,5,4,1}) returns [1,4,5,1,1,4,5]</li>
     *   <li>fix45(new int[]{4,2,2,5}) returns [4,5,2,2]</li>
     * </ul>
     * </p>
     */
    public static int[] fix45(int[] nums) {
        // [Your Implementation Here]
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return null;
    }

    /**
     * < Q45 >
     * Return a big string made of {@code count} occurrences of the given {@code word},
     * separated by the given separator string {@code sep}.
     *
     * @param word  The word to repeat.
     * @param sep   The separator to place between words.
     * @param count The number of times to repeat {@code word}.
     * @return The resulting string of repeated words separated by {@code sep}.
     * <p>
     * <ul>
     *   <li>repeatSeparator("Word", "X", 3) returns "WordXWordXWord"</li>
     *   <li>repeatSeparator("This", "And", 2) returns "ThisAndThis"</li>
     *   <li>repeatSeparator("This", "And", 1) returns "This"</li>
     * </ul>
     * </p>
     */
    public static String repeatSeparator(String word, String sep, int count) {
        // [Your Implementation Here]
    
        // Case-1. If the question can be solved with 'iteration (for/while)', 
        //         design the most efficient algorithm.
    
        // Case-2. If the question can be solved with 'recursion', design a 
        //         correct algorithm. Since the recursion can be inefficient, 
        //         use either 'tabulation' or 'memorization' to break it down 
        //         into 'iteration'.
        return null;
    }
}