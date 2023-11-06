package com.monte_carlo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Monte_Carlo_AdvancedTest {
    private Monte_Carlo_Advanced monteCarlo;

    @BeforeEach
    public void setUp() {
        monteCarlo = new Monte_Carlo_Advanced();
    }

    @Test
    public void testSimulateGBM() {
        Random random = new Random();
        double initialPrice = 100.0;
        double meanReturn = 0.0967;
        double volatility = 0.15;
        int numDays = 10;
        int numWalks = 100;
        List<Double> priceList = monteCarlo.simulateGBM(initialPrice, meanReturn, volatility, numDays, random, numWalks);

        assertEquals(numWalks, priceList.size());

        assertTrue(priceList.stream().allMatch(price -> price >= 0));
    }

    @Test
    public void testingJFrameButtons() {
        JFrame frame = Monte_Carlo_Advanced.Monte_Carlo_Frame();
        assertNotNull(frame);
        Component[] components = frame.getContentPane().getComponents();
        JPanel menuRow = null;
        JPanel outputPanel = null;

        for (Component component : components) {
            if (component instanceof JPanel && "menuRow".equals(component.getName())) {
                menuRow = (JPanel) component;
            } else if (component instanceof JPanel && "outputPanel".equals(component.getName())) {
                outputPanel = (JPanel) component;
            }
        }
        assertNotNull(menuRow);
        assertNotNull(outputPanel);

        boolean mainMenuButtonExists = false;

        Component[] menuRowComponents = menuRow.getComponents();

        for (Component component : menuRowComponents) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("Main Menu".equals(button.getText())) {
                    mainMenuButtonExists = true;
                }
            }
        }
        assertTrue(mainMenuButtonExists);

        boolean runSimulationButtonExists = false;

        Component[] outputPanelComponents = outputPanel.getComponents();

        for (Component component : outputPanelComponents) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("Run Simulation".equals(button.getText())) {
                    runSimulationButtonExists = true;
                }
            }
        }
        assertTrue(runSimulationButtonExists);

    }

    @Test
    public void testingJFrameInputFields() {
        JFrame frame = Monte_Carlo_Advanced.Monte_Carlo_Frame();
        assertNotNull(frame);
        Component[] components = frame.getContentPane().getComponents();
        JPanel inputPanel = null;

        for (Component component : components) {
            if (component instanceof JPanel && "inputPanel".equals(component.getName())) {
                inputPanel = (JPanel) component;
            }
        }
        assertNotNull(inputPanel);
        boolean numDaysFieldExists = false;
        boolean numWalksFieldExists = false;
        boolean tickerFieldExists = false;

        Component[] inputPanelComponents = inputPanel.getComponents();

        for (Component component : inputPanelComponents) {
            if (component instanceof JTextField) {
                JTextField numDaysField = (JTextField) component;
                JTextField numWalksField = (JTextField) component;
                JTextField tickerField = (JTextField) component;
                if ("numDaysField".equals(numDaysField.getName())) {
                    numDaysFieldExists = true;
                }
                else if ("numWalksField".equals(numDaysField.getName())) {
                    numWalksFieldExists = true;
                }
                else if ("tickerField".equals(numDaysField.getName())) {
                    tickerFieldExists = true;
                }
            }
        }
        assertTrue(numDaysFieldExists);
    }
}