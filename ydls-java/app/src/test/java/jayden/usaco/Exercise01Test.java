package jayden.usaco;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/// This is your first hw! 
/// for testing, run `./script/java04-test.sh`, 
/// then, you can check up why you got wrong test results here!
/// 
/// Your goal is to get 100% passing!


public class Exercise01Test {

    @Test
    public void testListenCase1() {
        int songs = 15;
        int podcasts = 8;
        String result = Exercise01.listen(songs, podcasts);
        String expected = "By listening to 15 songs and 8 podcasts, you have spent 4 hours and 5 minutes on Spotify.";
        assertEquals(expected, result);
    }

    @Test
    public void testListenCase2() {
        int songs = 9;
        int podcasts = 1;
        String result = Exercise01.listen(songs, podcasts);
        String expected = "By listening to 9 songs and 1 podcasts, you have spent 0 hours and 52 minutes on Spotify.";
        assertEquals(expected, result);
    }

    @Test
    public void testDominosTimeCase1() {
        int pizzas = 3;
        int pastas = 2;
        int wings = 2;
        String result = Exercise01.dominosTime(pizzas, pastas, wings);
        String expected = "By ordering 3 pizzas, 2 orders of pasta, and 2 orders of chicken wings, your order total comes to $64.";
        assertEquals(expected, result);
    }

    @Test
    public void testDominosTimeCase2() {
        int pizzas = 14;
        int pastas = 5;
        int wings = 8;
        String result = Exercise01.dominosTime(pizzas, pastas, wings);
        String expected = "By ordering 14 pizzas, 5 orders of pasta, and 8 orders of chicken wings, your order total comes to $262.";
        assertEquals(expected, result);
    }

    @Test
    public void testTipAndSplitCase1() {
        double total = 150;
        int tipPercentage = 20;
        int people = 3;
        String result = Exercise01.tipAndSplit(total, tipPercentage, people);
        String expected = "The driver got a tip of $30.00. Each person paid $60.00.";
        assertEquals(expected, result);
    }

    @Test
    public void testTipAndSplitCase2() {
        double total = 25;
        int tipPercentage = 18;
        int people = 1;
        String result = Exercise01.tipAndSplit(total, tipPercentage, people);
        String expected = "The driver got a tip of $4.50. Each person paid $29.50.";
        assertEquals(expected, result);
    }

    @Test
    public void testYoutuberCase1() {
        int videos = 4;
        double payPerView = 0.01;
        int views = 100000;
        String result = Exercise01.youtuber(videos, payPerView, views);
        String expected = "You have made $4000.00 by making YouTube videos!";
        assertEquals(expected, result);
    }

    @Test
    public void testYoutuberCase2() {
        int videos = 22;
        double payPerView = 0.01;
        int views = 1500000;
        String result = Exercise01.youtuber(videos, payPerView, views);
        String expected = "You have made $330000.00 by making YouTube videos!";
        assertEquals(expected, result);
    }

    @Test
    public void testBathBombCase1() {
        double radius = 3;
        String result = Exercise01.bathBomb(radius);

        // Extract numerical value from the result
        double volume = Double.parseDouble(result.substring(45, result.length()-1));

        // Check if the volume is within the range 112.00 to 113.99
        assertTrue(volume >= 112.00 && volume <= 113.99,
            "Volume should be between 112.00 and 113.99, but got: " + volume);
    }

    @Test
    public void testBathBombCase2() {
        double radius = 7;
        String result = Exercise01.bathBomb(radius);

        // Extract numerical value from the result
        double volume = Double.parseDouble(result.substring(45, result.length()-1));

        // Check if the volume is within the range 1435.00 to 1436.99
        assertTrue(volume >= 1435.00 && volume <= 1436.99,
            "Volume should be between 1435.00 and 1436.99, but got: " + volume);
    }
}
