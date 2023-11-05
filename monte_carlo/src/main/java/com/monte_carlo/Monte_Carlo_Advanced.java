package com.monte_carlo;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
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

        initialPrice = getPriceByTicker(ticker);

        List<Double> priceList = simulateGBM(initialPrice, meanReturn, volatility, numDays, random, numWalks);
        double meanPrice = calculateMean(priceList, numWalks);
        System.out.println("Initial price: " + initialPrice);
        System.out.println("Mean of prices: " + meanPrice);
        int numBins = 10; // You can adjust the number of bins as needed
        createPriceHistogram(priceList, numBins);
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

    public static void createPriceHistogram(List<Double> priceList, int numBins) {
        if (priceList.isEmpty()) {
            System.out.println("Price list is empty, cannot create a histogram.");
            return;
        }

        double minPrice = priceList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        double maxPrice = priceList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
        double priceRange = (maxPrice - minPrice) / numBins;

        int[] histogram = new int[numBins];
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Double price : priceList) {
            int bin = (int) ((price - minPrice) / priceRange);
            if (bin >= numBins) {
                bin = numBins - 1;
            }
            histogram[bin]++;
        }

        for (int i = 0; i < numBins; i++) {
            double binMin = minPrice + i * priceRange;
            double binMax = binMin + priceRange;
            String binLabel = String.format("%.0f - %.0f", binMin, binMax); // Round to 0 decimal places
            dataset.addValue(histogram[i], "Occurrences", binLabel);
        }

        JFreeChart histogramChart = ChartFactory.createBarChart(
                "Price Histogram",
                "Price Range",
                "Occurrences",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        ApplicationFrame frame = new ApplicationFrame("Price Histogram");
        ChartPanel chartPanel = new ChartPanel(histogramChart);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setSize(1200, 600);
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

}
