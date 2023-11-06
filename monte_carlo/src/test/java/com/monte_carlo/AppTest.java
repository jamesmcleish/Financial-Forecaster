package com.monte_carlo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {

    @Test
    public void testCreateAndShowGUI() {
        JFrame frame = App.createAndShowGUI();

        assertNotNull(frame);

        Component[] components = frame.getContentPane().getComponents();
        JPanel bottomPanel = null;

        for (Component component : components) {
            if (component instanceof JPanel && "bottomPanel".equals(component.getName())) {
                bottomPanel = (JPanel) component;
                break;
            }
        }

        assertNotNull(bottomPanel);

        boolean basicButtonExists = false;
        boolean advancedButtonExists = false;

        Component[] panelComponents = bottomPanel.getComponents();

        for (Component component : panelComponents) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("Basic Monte Carlo Simulation".equals(button.getText())) {
                    basicButtonExists = true;
                } else if ("Advanced Monte Carlo Simulation".equals(button.getText())) {
                    advancedButtonExists = true;
                }
            }
        }

        Assertions.assertTrue(basicButtonExists);
        Assertions.assertTrue(advancedButtonExists);

        frame.dispose();
    }
}




