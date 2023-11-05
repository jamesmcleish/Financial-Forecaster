package com.monte_carlo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;


public class Monte_Carlo_Advanced {
    private JFrame frame;
    private JTextField numDaysField;
    private JTextField numWalksField;
    private JTextField tickerField;
    private JPanel histogramPanel;
    private JLabel averagePriceLabel;
    private JLabel initialPriceLabel;

    public Monte_Carlo_Advanced() {
        frame = new JFrame("Monte Carlo Simulation (Advanced)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton menuButton = new JButton("Main Menu");
        frame.add(menuButton);

        JLabel numDaysLabel = new JLabel("Enter the number of days (1-252):");
        frame.add(numDaysLabel);

        numDaysField = new JTextField(5);
        frame.add(numDaysField);

        JLabel numWalksLabel = new JLabel("Enter the number of walks (1-100000):");
        frame.add(numWalksLabel);

        numWalksField = new JTextField(10);
        frame.add(numWalksField);

        JLabel tickerLabel = new JLabel("Enter a valid stock ticker symbol from the S&P500 to generate initial price:");
        frame.add(tickerLabel);

        tickerField = new JTextField(10);
        frame.add(tickerField);

        histogramPanel = new JPanel();
        histogramPanel.setLayout(new BorderLayout());
        histogramPanel.setPreferredSize(new Dimension(1200, 600));


        JButton runButton = new JButton("Run Simulation");
        frame.add(runButton);

        initialPriceLabel = new JLabel("Initial Price: ");
        initialPriceLabel.setVisible(false);
        frame.add(initialPriceLabel);

        averagePriceLabel = new JLabel("Average Price: ");
        averagePriceLabel.setVisible(false);
        frame.add(averagePriceLabel);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.main(new String[0]);
                frame.dispose();
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numDays = Integer.parseInt(numDaysField.getText());
                int numWalks = Integer.parseInt(numWalksField.getText());
                String ticker = tickerField.getText();
                runMonteCarloSimulation(numDays, numWalks, ticker);
            }
        });

        frame.add(histogramPanel);  // Add the histogram panel to the frame
        frame.pack();
        frame.setSize(1450,1200);
        frame.setVisible(true);
    }

    public void runMonteCarloSimulation(int numDays, int numWalks, String ticker) {
        Random random = new Random();
        double initialPrice = getPriceByTicker(ticker);
        List<Double> priceList = simulateGBM(initialPrice, 0.0967, 0.015, numDays, random, numWalks);

        // Create and display the price histogram in the histogram panel
        createPriceHistogram(priceList, 13);

        // Display the average price next to the histogram
        displayAveragePrice(priceList);
    }

    public List<Double> simulateGBM(double initialPrice, double meanReturn, double volatility, int numDays, Random random, int numWalks) {
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

    public Double getPriceByTicker(String ticker) {
        double initialPrice = FetchPrices.priceQuote(ticker);
        initialPriceLabel.setText("Initial Price: $" + initialPrice);
        initialPriceLabel.setVisible(true);
        return initialPrice;
    }

    public void createPriceHistogram(List<Double> priceList, int numBins) {
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
            String binLabel = String.format("%.0f-%.0f", binMin, binMax); // Format as an integer, 0 decimal places
            String occurrences = String.format("%.0f", (double) histogram[i]); // Format as an integer

            dataset.addValue(histogram[i], "Occurrences", binLabel);
        }

        JFreeChart histogramChart = ChartFactory.createBarChart(
                "Price Histogram",
                "Price Range ($'s)",
                "Occurrences",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = histogramChart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10)); // Adjust font size
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10)); // Adjust font size
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Set tick units to integers

        ChartPanel chartPanel = new ChartPanel(histogramChart);
        histogramPanel.removeAll();
        histogramPanel.add(chartPanel, BorderLayout.CENTER);
        histogramPanel.revalidate();
        histogramPanel.repaint();
    }

    // Method to display the average price
    public void displayAveragePrice(List<Double> priceList) {
        if (priceList.isEmpty()) {
            System.out.println("Price list is empty, cannot calculate the average price.");
            return;
        }

        double sum = 0.0;
        for (Double price : priceList) {
            sum += price;
        }
        double averagePrice = sum / priceList.size();

        averagePriceLabel.setText("Average Price: $" + String.format("%.2f", averagePrice));
        averagePriceLabel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Monte_Carlo_Advanced());
    }
}
