package FinalCodePhase3;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class UI extends JFrame {
    static JFrame frame;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private static int amountA = 0;
    static int amountB = 0;
    static int amountC = 0;
    static int valueA = 0;
    static int valueB = 0;
    static int valueC = 0;
    static int amountT = 0;
    static int amountL = 0;
    static int amountP = 0;
    static int valueT = 0;
    static int valueL = 0;
    static int valueP = 0;

    private JTextField objectAField;
    private JTextField objectBField;
    private JTextField objectCField;
    private JTextField valueAField;
    private JTextField valueBField;
    private JTextField valueCField;
    private JTextField objectTField;
    private JTextField objectLField;
    private JTextField objectPField;
    private JTextField valueTField;
    private JTextField valueLField;
    private JTextField valuePField;

    private JButton ABCButton;
    private JButton TLPButton;
    private JButton PackButton;

    private JPanel ABCorTLPPanel;
    private JPanel AmountAndValueABCPanel;
    private JPanel AmountAndValueTLPPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public static String letters;

    public UI() {

        this.createComponents();
        super.setSize(WIDTH, HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setBackground(Color.lightGray);
    }

    private void createComponents() {

        this.mainPanel = new JPanel(new CardLayout());
        this.ABCorTLPPanel = this.createABCorTLPPanel();
        this.AmountAndValueABCPanel = this.createAmountAndValueABCPanel();
        this.AmountAndValueTLPPanel = this.createAmountAndValueTLPPanel();
        this.mainPanel.add(this.ABCorTLPPanel);
        this.mainPanel.add(this.AmountAndValueABCPanel, "Amount and values for A, B and C");
        this.mainPanel.add(this.AmountAndValueTLPPanel, "Amount and values for T, L and P");
        this.cardLayout = (CardLayout) this.mainPanel.getLayout();
        this.cardLayout.show(this.mainPanel, "A, B, C or T, L, P");
        super.add(this.mainPanel);
    }

    private JPanel createABCorTLPPanel() {

        JPanel panel = new JPanel(new GridLayout(1, 2));
        this.ABCButton = new JButton ("Do you want to use objects A, B and C?");
        ABCButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Parcels.useLPT=false;
                cardLayout.show(mainPanel, "Amount and values for A, B and C");
            }
        });
        this.TLPButton = new JButton("Do you want to use objects T, L and P?");
        TLPButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e1) {
                Parcels.useLPT=true;
                cardLayout.show(mainPanel, "Amount and values for T, L and P");
            }

        });
        panel.add(this.ABCButton);
        panel.add(this.TLPButton);
        return panel;
    }

    private JPanel createAmountAndValueABCPanel() {

        setTitle("Amount and values");
        JPanel panel = new JPanel(new GridLayout(5, 3));
        this.PackButton = new JButton ("Pack");
        PackButton.addActionListener(new ActionListener() {   // If the Pack button is pressed, this method will be executed

            @Override
            public void actionPerformed(ActionEvent e2) {
                try {
                    DancingLinks.pieceOneBound = Integer.parseInt(objectAField.getText());
                    if (amountA < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceTwoBound = Integer.parseInt(objectBField.getText());
                    if (amountB < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceThreeBound = Integer.parseInt(objectCField.getText());
                    if (amountC < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceOneValue = Integer.parseInt(valueAField.getText());
                    if (valueA < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceTwoValue = Integer.parseInt(valueBField.getText());
                    if (valueB < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceThreeValue = Integer.parseInt(valueCField.getText());
                    if (valueC < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                Parcels go = new Parcels();

            }
        });
        final int TEXT_FIELD_SIZE = 20;
        this.objectAField = new JTextField(TEXT_FIELD_SIZE);
        this.objectBField = new JTextField(TEXT_FIELD_SIZE);
        this.objectCField = new JTextField(TEXT_FIELD_SIZE);
        this.valueAField = new JTextField(TEXT_FIELD_SIZE);
        this.valueBField = new JTextField(TEXT_FIELD_SIZE);
        this.valueCField = new JTextField(TEXT_FIELD_SIZE);

        panel.add(new JLabel(""));
        panel.add(new JLabel("Amount of object"));
        panel.add(new JLabel("Value of object"));
        panel.add(new JLabel("Object A"));
        panel.add(this.objectAField);
        panel.add(this.valueAField);
        panel.add(new JLabel("Object B"));
        panel.add(this.objectBField);
        panel.add(this.valueBField);
        panel.add(new JLabel("Object C"));
        panel.add(this.objectCField);
        panel.add(this.valueCField);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(this.PackButton);
        return panel;
    }

    private JPanel createAmountAndValueTLPPanel() {

        setTitle("Amount and values");
        JPanel panel = new JPanel(new GridLayout(5, 3));
        this.PackButton = new JButton ("Pack");
        PackButton.addActionListener(new ActionListener() {   // If the Pack button is pressed, this method will be executed

            @Override
            public void actionPerformed(ActionEvent e2) {

                try {
                    DancingLinks.pieceOneBound  = Integer.parseInt(objectTField.getText());
                    if (amountT < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceTwoBound = Integer.parseInt(objectLField.getText());
                    if (amountL < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceThreeBound = Integer.parseInt(objectPField.getText());
                    if (amountP < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceOneValue = Integer.parseInt(valueTField.getText());
                    if (valueT < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceTwoValue = Integer.parseInt(valueLField.getText());
                    if (valueL < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    DancingLinks.pieceThreeValue = Integer.parseInt(valuePField.getText());
                    if (valueP < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Input must be positive and can only contain numbers!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                Parcels go = new Parcels();

            }
        });
        final int TEXT_FIELD_SIZE = 20;
        this.objectTField = new JTextField(TEXT_FIELD_SIZE);
        this.objectLField = new JTextField(TEXT_FIELD_SIZE);
        this.objectPField = new JTextField(TEXT_FIELD_SIZE);
        this.valueTField = new JTextField(TEXT_FIELD_SIZE);
        this.valueLField = new JTextField(TEXT_FIELD_SIZE);
        this.valuePField = new JTextField(TEXT_FIELD_SIZE);

        panel.add(new JLabel(""));
        panel.add(new JLabel("Amount of object"));
        panel.add(new JLabel("Value of object"));
        panel.add(new JLabel("Object T"));
        panel.add(this.objectTField);
        panel.add(this.valueTField);
        panel.add(new JLabel("Object P"));
        panel.add(this.objectLField);
        panel.add(this.valueLField);
        panel.add(new JLabel("Object L"));
        panel.add(this.objectPField);
        panel.add(this.valuePField);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(this.PackButton);
        return panel;
    }

    public static void main(String[] args) {

        frame = new UI();
    }
}