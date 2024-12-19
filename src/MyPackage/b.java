package MyPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class b extends JFrame {
    private final JTextField display = new JTextField();

    public b() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Настройка дисплея
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Создание панели кнопок
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttons = {"7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new CalculatorActionListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class CalculatorActionListener implements ActionListener {
        private double operand1 = 0;
        private String operator = "";
        private boolean isNewInput = true;
        private boolean equalsPressedLast = false; // Для отслеживания нажатия "="

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                if ("0123456789".contains(command)) {
                    if (isNewInput || equalsPressedLast) {
                        display.setText(command);
                        isNewInput = false;
                        equalsPressedLast = false;
                    } else {
                        display.setText(display.getText() + command);
                    }
                } else if ("/*-+".contains(command)) {
                    if (!isNewInput && !operator.isEmpty()) { // Выполняем операцию перед новой операцией
                        performCalculation();
                    }
                    operator = command;
                    operand1 = Double.parseDouble(display.getText());
                    isNewInput = true;
                    equalsPressedLast = false;
                } else if ("=".equals(command)) {
                    if (!operator.isEmpty()) {
                        performCalculation();
                        operator = ""; // Сброс оператора после вычисления
                        equalsPressedLast = true;
                    }
                } else if ("C".equals(command)) {
                    clear();
                }
            } catch (NumberFormatException ex) {
                display.setText("Error");
                clear();
            }
        }

        private void performCalculation() {
            double operand2 = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        display.setText("Error");
                        clear();
                        return;
                    }
                    break;
                default:
                    return;
            }

            display.setText(String.valueOf(result));
            operand1 = result; // Сохраняем результат для последующих операций
        }

        private void clear() {
            display.setText("");
            operand1 = 0;
            operator = "";
            isNewInput = true;
            equalsPressedLast = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            b calculator = new b();
            calculator.setVisible(true);
        });
    }
}