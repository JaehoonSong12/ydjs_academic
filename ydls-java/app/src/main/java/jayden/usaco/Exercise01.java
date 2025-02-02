package jayden.usaco;

import java.util.Scanner;


/// This is your first hw! 
/// for testing, run `./script/java04-test.sh`.






/**
 * Exercise01 contains methods that help practice:
 * 1. Writing `functions` and evaluating `expressions`.
 * 2. Learning `String formatting` to manipulate strings using variables. 
 * This exercise also involves basic arithmetic operations and formatted string output.
 * 
 * The methods in this class include:
 * - `listen`: Calculates total time spent listening to songs and podcasts.
 * - `dominosTime`: Calculates the total cost of a food order.
 * - `tipAndSplit`: Calculates the tip amount and per-person cost based on a bill.
 * - `youtuber`: Calculates total earnings from YouTube videos based on views.
 * - `bathBomb`: Calculates the volume of a bath bomb given its radius.
 */
public class Exercise01 {

    public static void main(String[] args) {
        System.out.println(listen(10, 5));
        System.out.println(listen(10, 2));


        String formatter = "I ate %d apple and %s grape, and my hight is %.2f .";
        int apple = 3;
        String grape = "6";
        double height = 173.252525;

        String output = String.format(formatter, apple, grape, height);
        System.out.println(output);



        System.out.println(dominosTime(3, 2, 1));
        System.out.println(tipAndSplit(100, 15, 4));
        System.out.println(youtuber(10, 0.5, 1000));
        System.out.println(bathBomb(5));
    }

    /**
     * Calculates the total time spent listening to songs and podcasts in hours and minutes.
     * Assumes all inputs (songs and podcasts) are integers representing the number of songs 
     * and podcasts listened to.
     * The method computes the total time as:
     * - Each song lasts 3 minutes.
     * - Each podcast lasts 25 minutes.
     * The time is then converted to hours and minutes format.
     * 
     * @param songs    Number of songs listened to (must be an integer)
     * @param podcasts Number of podcasts listened to (must be an integer)
     * @return A formatted string showing the total time spent listening in hours and minutes.
     *         The format is: "By listening to X songs and Y podcasts, you have spent Z hours 
     *         and W minutes on Spotify."
     */
    public static String listen(int songs, int podcasts) {
        // given by Admin (Jaehoon)
        String formatter = 
            "By listening to %d songs and %d podcasts, " + 
            "you have spent %d hours and %d minutes on Spotify.";
        /**
         * < Your Implementation Here >
         */
        // Calculate total minutes
        int songMin = songs * 3;
        int podcastMin = podcasts * 25;
        int totalMinutes = songMin + podcastMin;

        // Convert to hours and minutes
        int hours = totalMinutes / 60; // quotient -> hours
        int minutes = totalMinutes % 60; // remainder -> minute
        // integer division
        // e.g. 154 min -> 2 hour 34 min

        // Return the formatted string
        /**
         * 
         * The String.format() method in Java is used to create a formatted string.
         * 
         */
        return String.format(formatter, songs, podcasts, hours, minutes);
    }

    /**
     * Calculates the total cost of an order based on the quantities of pizzas, pastas,
     * and chicken wings ordered. Assumes all inputs (pizzas, pastas, wings) are integers 
     * representing the quantities of each item.
     * The method computes the total cost as:
     * - Each pizza costs $12.
     * - Each pasta costs $6.
     * - Each order of chicken wings costs $8.
     * 
     * @param pizzas  Number of pizzas ordered (must be an integer)
     * @param pastas  Number of pasta orders (must be an integer)
     * @param wings   Number of chicken wing orders (must be an integer)
     * @return A formatted string showing the total cost of the order.
     *         The format is: "By ordering X pizzas, Y orders of pasta, and Z orders of 
     *         chicken wings, your order total comes to $A."
     */
    public static String dominosTime(int pizzas, int pastas, int wings) {
        // given by Admin (Jaehoon)
        String formatter = 
            "By ordering %d pizzas, %d orders of pasta, " + 
            "and %d orders of chicken wings, your order total comes to $%d.";
        /**
         * < Your Implementation Here >
         */
        // Prices of each item
        int pizzaPrice = 12;
        int pastaPrice = 6;
        int wingsPrice = 8;
        
        // Calculate total cost
        int totalCost = (pizzas * pizzaPrice) + (pastas * pastaPrice) + (wings * wingsPrice);
        
        // Return formatted string
        return String.format(formatter,pizzas, pastas, wings, totalCost);
    }

    /**
     * Calculates the tip amount and the per-person cost of a bill.
     * Assumes all inputs are positive integers. The total bill is a double, and the tip 
     * percentage and number of people are integers.
     * The method computes the tip and per-person cost as:
     * - Tip is calculated as the total bill multiplied by the tip percentage divided by 100.
     * - Per-person cost is the total bill plus the tip, divided by the number of people.
     * 
     * @param total         The order total (must be a positive double value)
     * @param tipPercentage The percentage of the total to be given as a tip (must be a positive integer)
     * @param people        The number of people splitting the bill (must be a positive integer)
     * @return A formatted string showing the tip amount and the per-person cost.
     *         The format is: "The driver got a tip of $X.XX. Each person paid $Y.YY."
     *         Both the tip and per-person cost should be rounded to 2 decimal places.
     */
    public static String tipAndSplit(double total, int tipPercentage, int people) {
        // Calculate tip amount
        double tip = (total * tipPercentage) / 100.0;
        
        // Calculate per-person cost
        double perPersonCost = (total + tip) / people;
        
        // Format output to 2 decimal places
        return String.format("The driver got a tip of $%.2f. Each person paid $%.2f.", tip, perPersonCost);
    }

    /**
     * Calculates total earnings from YouTube videos based on the number of videos,
     * views per video, and earnings per view. Assumes all inputs are integers except for 
     * payPerView, which is a double.
     * The method computes the total earnings as:
     * - Earnings are calculated as the number of videos multiplied by the number of 
     *   views per video, multiplied by the earnings per view.
     * 
     * @param videos     Number of videos created (must be a positive integer)
     * @param payPerView Earnings per view (must be a positive double value)
     * @param views      Number of views per video (must be a positive integer)
     * @return A formatted string showing the total earnings.
     *         The format is: "You have made $X.XX by making YouTube videos!"
     *         Earnings should be rounded to 2 decimal places.
     */
    public static String youtuber(int videos, double payPerView, int views) {
        if (videos <= 0 || payPerView <= 0 || views <= 0) {
            return "Invalid input. All values must be positive.";
        }
        
        double totalEarnings = videos * views * payPerView;
        return String.format("You have made $%.2f by making YouTube videos!", totalEarnings);
    }

    /**
     * Calculates the volume of a bath bomb given its radius. The radius is a floating-point number.
     * The method computes the volume using the formula for the volume of a sphere:
     * - Volume = (4/3) * π * radius^3
     * 
     * +
     * 6-
     * @param radius Radius of the bath bomb (must be a positive floating-point value)
     * @return A formatted string showing the volume of the bath bomb.
     *         The format is: "The volume of a bath bomb with radius X.X is Y.YY."
     *         The volume should be rounded to 2 decimal places.
     */
    public static String bathBomb(double radius) {
        // Calculate the volume of the bath bomb using the formula for the volume of a sphere
        double volume = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        
        // Format the result to 2 decimal places
        return String.format("The volume of a bath bomb with radius %.1f is %.2f.", radius, volume);
    }
}
