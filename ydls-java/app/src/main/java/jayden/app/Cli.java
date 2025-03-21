package jayden.app;

import jayden.usaco.Exercise02;
import jayden.app.javatest.MainJ;
import jayden.app.IqTest;

import java.util.HashMap;
import java.util.Map;

import java.text.DecimalFormat;


import jayden.unit.a.Exercise;

import javax.swing.JOptionPane; // Needed for JOptionPane

import java.util.Scanner; // Needed for the Scanner class
import java.io.IOException; // Needed for IOException

public class Cli {
    public static void main(String[] args) { // executable main method 

        MainJ.main(args);
        // System.out.println("Hello, Cli!");
        // System.out.println("This is just for testing!");
        // String text = "Hello, world!";
        // String subText = text.substring(7,9); // Extracts "Hello"
        // System.out.println(subText);
        // System.out.println("Running Exercise02 from CLI...");
        // Exercise02.main(args);
        // int quantity = 9;
        // double walletAmount = 3254.323;
        // String item = "Shirt";
        // Map<String, Double> itemPrice = new HashMap<>();

        // itemPrice.put("Shirt", 15.50);
        // itemPrice.put("Lanyard", 4.25);
        // itemPrice.put("Sweatshirt", 25.00);
        // itemPrice.put("Mug", 10.50);

        // Double Price = itemPrice.get(item);
        // Double realPrice = Price * quantity;
        // Double okPrice = walletAmount - realPrice;

        // if (okPrice < 0) {
        //     System.out.println("Not enough money!");
        // } else {
        //     System.out.printf("%.2f%n", okPrice);
        // }






        



        // Exercise.main(args); // test homework! Unit A

        // try {
        //     if (System.in.available() <= 0) {  // This checks if input is available
        //         System.out.println("No input detected. Are you running this in an environment that supports standard input?");
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // Scanner sc = new Scanner(System.in); // Initialize scanner once
        
        // System.out.println("Welcome to IQ test");
        
        // System.out.print("name: ");
        // String name = sc.hasNextLine() ? sc.nextLine() : ""; // Check before reading

        // System.out.print("age: ");
        // int age = sc.hasNextInt() ? sc.nextInt() : 0;
        // sc.nextLine(); // Consume newline

        // System.out.println("Hello " + name + ", you are " + age + " years old.");
        
        // sc.close(); // Close only at the end



        // IqTest.main(args); // test homework! Unit B

        // test();

        // jayden.usaco.Exercise01.main(args); /// This is for `./script/java02-build.sh`, not needed.
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