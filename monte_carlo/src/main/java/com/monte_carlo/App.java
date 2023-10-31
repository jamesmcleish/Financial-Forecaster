package monte_carlo.src.main.java.com.monte_carlo;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number of steps: ");

        int steps = scanner.nextInt();

        System.out.println("Enter a number of walks: ");

        int walks = scanner.nextInt();

        List<Integer> finalPrices = simulateSharePrice(steps, walks);
        scanner.close();

        System.out.println(finalPrices);
    }

    public static List<Integer> simulateSharePrice(int numSteps, int numWalks) {
        List<Integer> finalPrices = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numWalks; i++) {
            int price = 100; 
            for (int j = 0; j < numSteps; j++) {
                int step = random.nextBoolean() ? 1 : -1;
                price += step;
            }
            finalPrices.add(price);
        }

        return finalPrices;
    }
}
