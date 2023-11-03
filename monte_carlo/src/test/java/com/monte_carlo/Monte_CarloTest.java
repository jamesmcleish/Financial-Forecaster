package monte_carlo.src.test.java.com.monte_carlo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import monte_carlo.src.main.java.com.monte_carlo.Monte_Carlo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Monte_CarloTest {

    @Test
    public void testCalculatePriceProbabilities() {
        List<Integer> finalPrices = new ArrayList<>();
        finalPrices.add(100);
        finalPrices.add(101);
        finalPrices.add(99);
        finalPrices.add(102);
        finalPrices.add(98);

        Map<Integer, Double> priceProbabilities = Monte_Carlo.calculatePriceProbabilities(finalPrices);

        assertEquals(0.2, priceProbabilities.get(100), 0.00000000000001);
        assertEquals(0.2, priceProbabilities.get(101), 0.00000000000001);
        assertEquals(0.2, priceProbabilities.get(99), 0.00000000000001);
        assertEquals(0.2, priceProbabilities.get(102), 0.00000000000001);
        assertEquals(0.2, priceProbabilities.get(98), 0.00000000000001);
    }

    @Test
    public void testSimulateSharePrice1() {
        // Test if the simulateSharePrice method generates the correct number of final prices
        int numSteps = 1;
        int numWalks = 1;
        List<Integer> finalPrices = Monte_Carlo.simulateSharePrice(numSteps, numWalks);

        assertEquals(numWalks, finalPrices.size());
    }
    @Test
    public void testSimulateSharePrice2() {
        // Test if the simulateSharePrice method generates the correct number of final prices
        int numSteps = 100;
        int numWalks = 10000;
        List<Integer> finalPrices = Monte_Carlo.simulateSharePrice(numSteps, numWalks);

        assertEquals(numWalks, finalPrices.size());
    }

    @Test
    public void testSimulateSharePricePriceRange() {
        // Test if the simulated prices fall within the expected range
        int numSteps = 10;
        int numWalks = 10000;
        List<Integer> finalPrices = Monte_Carlo.simulateSharePrice(numSteps, numWalks);

        for (int price : finalPrices) {
            // Assuming each step is either +1 or -1, the price should be within [100 - numSteps, 100 + numSteps]
            assertTrue(price >= 100 - numSteps && price <= 100 + numSteps);
        }
    }

    @Test
    public void testProbabilityDistributionSumsToOne() {
        // Test if multiple runs of simulateSharePrice produce consistent results
        int numSteps = 10;
        int numWalks = 10000;

        List<Integer> finalPrices = Monte_Carlo.simulateSharePrice(numSteps, numWalks);
        Map<Integer, Double> probabilityTable = Monte_Carlo.calculatePriceProbabilities(finalPrices);

        double sum = 0;

        for(Double value: probabilityTable.values()){
            sum += value;
        }


        assertEquals(sum, 1, 0.000000000000001);
    }

    @Test
    public void testCalculatePriceProbabilitiesExecutionTime() {
        int numSteps = 100;
        int numWalks = 100000;

        long startTime = System.currentTimeMillis();

        List<Integer> finalPrices = Monte_Carlo.simulateSharePrice(numSteps, numWalks);
        Map<Integer, Double> priceProbabilities = Monte_Carlo.calculatePriceProbabilities(finalPrices);
        Monte_Carlo.displayPriceProbabilities(priceProbabilities);


        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Check if the execution time is less than 5 seconds (5000 milliseconds)
        assertTrue("Execution time should be less than 5 seconds", executionTime < 5000);
    }
}
