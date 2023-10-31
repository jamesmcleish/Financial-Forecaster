package monte_carlo.src.main.java.com.monte_carlo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number of steps: ");

        int steps = scanner.nextInt();

        System.out.println("Enter a number of walks: ");

        int walks = scanner.nextInt();

        List<BigDecimal> finalPrices = simulateSharePrice(steps, walks);
        scanner.close();

        System.out.println(calculatePriceProbabilities(finalPrices));

    }

    public static List<BigDecimal> simulateSharePrice(int numSteps, int numWalks) {
        List<BigDecimal> finalPrices = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numWalks; i++) {
            BigDecimal price = BigDecimal.valueOf(100); // Initial price as a BigDecimal
            for (int j = 0; j < numSteps; j++) {
                int step = random.nextBoolean() ? 1 : -1;
                price = price.add(BigDecimal.valueOf(step)); // Update price using BigDecimal
            }
            finalPrices.add(price);
        }

        return finalPrices;
    }

    public static Map<BigDecimal, Double> calculatePriceProbabilities(List<BigDecimal> finalPrices) {
        Map<BigDecimal, Double> priceProbabilities = new HashMap<>();
        int totalWalks = finalPrices.size();

        for (BigDecimal price : finalPrices) {
            priceProbabilities.put(price, priceProbabilities.getOrDefault(price, 0.0) + 1.0);
        }

        // Calculate probabilities
        for (Map.Entry<BigDecimal, Double> entry : priceProbabilities.entrySet()) {
            double probability = entry.getValue() / totalWalks;
            priceProbabilities.put(entry.getKey(), probability);
        }

        
        
        return priceProbabilities;
    }
}