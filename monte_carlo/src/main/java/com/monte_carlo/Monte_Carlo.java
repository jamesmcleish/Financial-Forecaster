package com.monte_carlo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Monte_Carlo {
    private JFrame frame;
    private JTextField stepsField;
    private JTextField walksField;
    private DefaultTableModel model;
    private JTable table;

    public Monte_Carlo() {
        frame = new JFrame("Monte Carlo Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton menuButton = new JButton("Main Menu");
        frame.add(menuButton);

        JLabel stepsLabel = new JLabel("Enter the number of steps (1-100):");
        frame.add(stepsLabel);

        stepsField = new JTextField(5);
        frame.add(stepsField);

        JLabel walksLabel = new JLabel("Enter the number of walks (1-100000):");
        frame.add(walksLabel);

        walksField = new JTextField(10);
        frame.add(walksField);

        JButton runButton = new JButton("Run Simulation");
        frame.add(runButton);

        model = new DefaultTableModel();
        model.addColumn("Price");
        model.addColumn("Probability");
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numDaysText = stepsField.getText();
                String numWalksText = walksField.getText();

                int numDays;
                int numWalks;

                try {
                    numDays = Integer.parseInt(numDaysText);
                    numWalks = Integer.parseInt(numWalksText);

                    if (numDays>0 && numDays<253 && numWalks>0 && numWalks<100001) {

                        runMonteCarloSimulation(numDays, numWalks);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Days must be between 1 and 252, Walks between 1 and 100000.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
                }
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.main(new String[0]);
                frame.dispose();
            }
        });


        frame.add(tablePanel);

        frame.setSize(1000, 500);
        frame.setVisible(true);
    }

    public void runMonteCarloSimulation(int numSteps, int numWalks) {
        List<Integer> finalPrices = simulateSharePrice(numSteps, numWalks);
        Map<Integer, Double> priceProbabilities = calculatePriceProbabilities(finalPrices);

        // Clear existing rows in the table
        model.setRowCount(0);

        for (Map.Entry<Integer, Double> entry : priceProbabilities.entrySet()) {
            model.addRow(new Object[]{"£" + entry.getKey(), entry.getValue()});
        }
    }

    public List<Integer> simulateSharePrice(int numSteps, int numWalks) {
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

    public Map<Integer, Double> calculatePriceProbabilities(List<Integer> finalPrices) {
        Map<Integer, Double> priceProbabilities = new HashMap<>();
        int totalWalks = finalPrices.size();

        for (int price : finalPrices) {
            priceProbabilities.put(price, priceProbabilities.getOrDefault(price, 0.0) + 1.0);
        }

        for (Map.Entry<Integer, Double> entry : priceProbabilities.entrySet()) {
            double probability = entry.getValue() / totalWalks;
            priceProbabilities.put(entry.getKey(), probability);
        }

        return priceProbabilities;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Monte_Carlo());
    }
}
