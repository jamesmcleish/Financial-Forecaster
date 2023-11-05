package com.monte_carlo;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import com.monte_carlo.FetchPrices;

public class Monte_Carlo_Advanced {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numWalks;
        int numDays;
        double initialPrice;
        double meanReturn = 0.0967; //mean return of the S&P500 over 20 years
        double volatility = 0.015; //average volatility for 2023
        String ticker;
        Random random = new Random();


        do {
            System.out.print("Enter a value for S (number of days) (1-252): ");
            numDays = scanner.nextInt();
        } while (numDays < 1 || numDays > 252); // keep inputs in range specified for number of trading days

        do {
            System.out.print("Enter a value for N (number of walks) (1-100000): ");
            numWalks = scanner.nextInt();
        } while (numWalks < 1 || numWalks > 100000);

        System.out.println("Enter a valid stock ticker symbol from the S&P500 to generate initial price");
        ticker = scanner.next();
        do {
            initialPrice = getPriceByTicker(ticker);
        } while(false);

        List<Double> priceList = simulateGBM(initialPrice, meanReturn, volatility, numDays, random, numWalks);
        double meanPrice = calculateMean(priceList, numWalks);
        System.out.println("Initial price: " + initialPrice);
        System.out.println("Mean of prices: " + meanPrice);
    }

    public static List<Double> simulateGBM(double initialPrice, double meanReturn, double volatility, int numDays, Random random, int numWalks) {
        List<Double> priceList = new ArrayList<>();

        double dailyDrift = (meanReturn - (Math.pow(volatility, 2) / 2)) / 252.0;
        double dailyVolatility = volatility / Math.sqrt(252.0);

        for (int walk = 0; walk < numWalks; walk++) {
            double price = initialPrice;
            for (int day = 0; day < numDays; day++) {

                double dailyReturn = dailyDrift + dailyVolatility * random.nextGaussian();

                price *= Math.exp(dailyReturn);
            }
            priceList.add(price);
        }
        return priceList;
    }

    public static Double getPriceByTicker(String ticker){
        return FetchPrices.priceQuote(ticker);
    }
    public static double calculateMean(List<Double> priceList, java.lang.Integer numWalks) {
        if (priceList.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (double price : priceList) {
            sum += price;
        }

        return sum / numWalks;
    }

}
