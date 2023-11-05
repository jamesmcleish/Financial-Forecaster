package com.monte_carlo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monte_Carlo_AdvancedTest {

//    private Monte_Carlo_Advanced monteCarlo;
//
//    @BeforeEach
//    public void setUp() {
//        monteCarlo = new Monte_Carlo_Advanced();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        monteCarlo = null;
//    }
//
//    @Test
//    public void testSimulateGBM() {
//        double initialPrice = 100.0;
//        double meanReturn = 0.0967;
//        double volatility = 0.015;
//        int numDays = 10;
//        int numWalks = 1000;
//
//        List<Double> priceList = monteCarlo.simulateGBM(initialPrice, meanReturn, volatility, numDays, new Random(), numWalks);
//
//        assertNotNull(priceList);
//        assertEquals(numWalks, priceList.size());
//
//    }
//
//    @Test
//    public void testCalculateMean() {
//        List<Double> priceList = new ArrayList<>();
//
//        priceList.add(100.0);
//        priceList.add(110.0);
//        priceList.add(120.0);
//        priceList.add(130.0);
//        priceList.add(140.0);
//        int numWalks = priceList.size();
//
//        double mean = monteCarlo.calculateMean(priceList, numWalks);
//
//        assertEquals(120.0, mean, 0.01);
//    }
//
//    @Test
//    public void testGetPriceByTicker() {
//        String ticker = "AAPL";
//        Double price = monteCarlo.getPriceByTicker(ticker);
//
//        assertNotNull(price);
//        assertTrue(price > 0.0);
//    }
//    @Test
//    public void testGetPriceByInvalidTicker() {
//        String invalidTicker = "INVALID";
//        Double price = monteCarlo.getPriceByTicker(invalidTicker);
//
//        assertEquals(price,0);
//    }

}
