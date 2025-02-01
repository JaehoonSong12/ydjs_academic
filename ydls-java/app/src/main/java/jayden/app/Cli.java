package jayden.app;

import jayden.unit.a.Exercise;

import javax.swing.JOptionPane; // Needed for JOptionPane

public class Cli {
    public static void main(String[] args) {
        System.out.println("Hello, Cli!");
        System.out.println("This is just for testing!");

        Exercise.main(args); // test homework! Unit A



        // test();

        jayden.usaco.Exercise01.main(args); /// This is for `./script/java02-build.sh`, not needed.
    }

    public static void test() {
        double score1; // To hold score #1
        double score2; // To hold score #2
        double score3; // To hold score #3
        double average; // To hold the average score
        String input; // To hold the user's input

        // Get the first test score.
        input = JOptionPane.showInputDialog("Enter score #1:");
        score1 = Double.parseDouble(input);

        // Get the second score.
        input = JOptionPane.showInputDialog("Enter score #2:");
        score2 = Double.parseDouble(input);

        // Get the third test score.
        input = JOptionPane.showInputDialog("Enter score #3:");
        score3 = Double.parseDouble(input);

        // Calculate the average score.
        average = (score1 + score2 + score3) / 3.0;

        // Display the average score.
        JOptionPane.showMessageDialog(null, "The average is " + average);

        // If the average was greater than 95, let the user know that's a great score.
        if (average > 95) {
            JOptionPane.showMessageDialog(null, "That's a great score!");
        }

        System.exit(0);
    }
}