package com.monte_carlo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Monte_Carlo {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in); // scanner opened to take user input for monte carlo simulation

            int S;
            int N;

            do {
                System.out.print("Enter a value for S (number of steps) (1-100): ");
                S = scanner.nextInt();
            } while (S < 1 || S > 100); // keep inputs in range specified

            do {
                System.out.print("Enter a value for N (number of walks) (1-100000): ");
                N = scanner.nextInt();
            } while (N < 1 || N > 100000);

            List<Integer> finalPrices = simulateSharePrice(S, N);  //get price list
            scanner.close();

            if(S%2==0) {
                System.out.println("Probability of price equal to £100 after 10 steps = " + calculatePriceProbabilities(finalPrices).get(100));
            } //display probability the price returns to original after S steps

            displayPriceProbabilities(calculatePriceProbabilities(finalPrices)); //calls function to display probability table

    }
    public static List<Integer> simulateSharePrice(int numSteps, int numWalks) { //Method returns List<integer> of final prices
        List<Integer> finalPrices = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numWalks; i++) {  // ij running through each step of each walk
            int price = 100;
            for (int j = 0; j < numSteps; j++) {
                int step = random.nextBoolean() ? 1 : -1;
                price += step;
            }
            finalPrices.add(price);  //at the end of each loop add price to List<Integer> finalPrices
        }

        return finalPrices;
    }
    public static Map<Integer, Double> calculatePriceProbabilities(List<Integer> finalPrices) { //Returns a map <prices: probabilities>
        Map<Integer, Double> priceProbabilities = new HashMap<>();
        int totalWalks = finalPrices.size();

        for (int price : finalPrices) {
            priceProbabilities.put(price, priceProbabilities.getOrDefault(price, 0.0) + 1.0); //Add the number of instances of a price into the probabilities
        }

        // Calculate probabilities and place inside map
        for (Map.Entry<Integer, Double> entry : priceProbabilities.entrySet()) {
            double probability = entry.getValue() / totalWalks;
            priceProbabilities.put(entry.getKey(), probability);
        }

        return priceProbabilities;

    }

    public static void displayPriceProbabilities(Map<Integer, Double> priceProbabilities) { //displays a popup window with a table of prices and probabilities
        DefaultTableModel model = new DefaultTableModel(); //set up a table model
        model.addColumn("Price");
        model.addColumn("Probability"); //add my two columns

        for (Map.Entry<Integer, Double> entry : priceProbabilities.entrySet()) { //loop to add rows to the table from price probabilities map
            model.addRow(new Object[]{"£" + entry.getKey(), entry.getValue()});
        }

        JTable table = new JTable(model); //turns model into JTable

        JFrame frame = new JFrame("Price Probabilities Table"); //create JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);

        frame.add(new JScrollPane(table)); //add table as JScrollPane to JFrame
        frame.setVisible(true);
    }
}
