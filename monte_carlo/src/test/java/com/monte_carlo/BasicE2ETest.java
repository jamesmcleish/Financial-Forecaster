package com.monte_carlo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicE2ETest {
    private JTable table;

    @Test
    public void mainMenuToMonteCarloBasic() throws InterruptedException {
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
        Component[] bottomPanelComponents = bottomPanel.getComponents();

        JButton basicButton = null;
        for (Component component : bottomPanelComponents) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("Basic Monte Carlo Simulation".equals(button.getText())) {
                    basicButton = (JButton) button;
                }
            }
        }
        assertNotNull(basicButton);
        simulateButtonClick(basicButton);

        JFrame monteCarloFrame = Monte_Carlo.monteCarloFrame();

        assertNotNull(monteCarloFrame);

        Component[] fields = monteCarloFrame.getContentPane().getComponents();
        JTextField walksField = null;
        JTextField stepsField = null;

        for (Component component : fields) {
            if (component instanceof JTextField && "stepsField".equals(component.getName())) {
                walksField = (JTextField) component;
            } else if (component instanceof  JTextField && "walksField".equals(component.getName())){
                stepsField = (JTextField) component;
            }
        }

        assertNotNull(walksField);
        assertNotNull(stepsField);

        table = Monte_Carlo.getTable();
        assertEquals(tableLength(table), 0);

        walksField.setText("100");
        stepsField.setText("1000");

        JButton runSimulationButton = null;

        for (Component component: fields){
            if (component instanceof JButton && "runSimulationButton".equals(component.getName())){
                runSimulationButton = (JButton) component;
            }
        }
        assertNotNull(runSimulationButton);
        simulateButtonClick(runSimulationButton);
        table = Monte_Carlo.getTable();
        assertNotNull(table);
        Assertions.assertNotEquals(tableLength(table), 0);

    }

    private void simulateButtonClick(JButton button) {
        for (ActionListener listener : button.getActionListeners()) {
            ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
            listener.actionPerformed(event);
        }
    }

    public static int tableLength(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        int rowCount = model.getRowCount();
        int sum = 0;
        for (int row = 0; row < rowCount; row++) {
            sum += 1;
        }
        return sum;
    }

}
