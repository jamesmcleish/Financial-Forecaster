package com.monte_carlo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Monte Carlo Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel description = new JLabel("<html>This is a monte carlo simulator:<br>'Basic Monte Carlo Simulation' simulates an initial price of 100 with random steps of +/- £1 <br>'Advanced Monte Carlo Simulation' allows you to input an SNP500 stock ticker and will return a histogram of prices based on geometric brownian motion<br></html>");
        topPanel.add(description);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton basicButton = new JButton("Basic Monte Carlo Simulation");
        JButton advancedButton = new JButton("Advanced Monte Carlo Simulation");

        JLabel label = new JLabel("Choose an option:");
        bottomPanel.add(label);
        bottomPanel.add(basicButton);
        bottomPanel.add(advancedButton);

        frame.add(topPanel);
        frame.add(bottomPanel);

        basicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monte_Carlo.main(new String[0]);
                frame.dispose(); // Close the frame when the button is clicked
            }
        });

        advancedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monte_Carlo_Advanced.main(new String[0]);
                frame.dispose(); // Close the frame when the button is clicked
            }
        });
        frame.pack();
        frame.setSize(1000,200);
        frame.setVisible(true);
    }
}
