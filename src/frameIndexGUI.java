import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class frameIndexGUI {
    private JButton generateButton;
    private JPanel panelMain;
    private JTextArea output;
    private JLabel startingNumberLabel;
    private JLabel spriteNumberLabel;
    private JLabel prefixLabel;
    private JLabel endingFrameLabel;
    private JTextField prefixField;
    private JTextField startingNameField;
    private JTextField startingField;
    private JTextField endingField;
    private JTextField partsField;
    private JTextField betweenField;
    private JButton copyDataButton;
    private JScrollPane outputScroll;
    int nameNumber = 0;
    int startingFrame = 0;
    int endingFrame = 1;
    int amountOfParts = 1;
    int framesBetween = 1;
    String startingPrefix = "MD";
    String frameData = "";

    public frameIndexGUI() {
        startingNameField.setTransferHandler(null);
        prefixField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(prefixField.getText().length() > 0)
                    startingPrefix = prefixField.getText();
                else
                    startingPrefix = "MD";
            }
        });
        startingNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(startingNameField.getText().length() > 0)
                    nameNumber = Integer.parseInt(startingNameField.getText());
            }
        });
        startingNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        startingField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(startingField.getText().length() > 0)
                    startingFrame = Integer.parseInt(startingField.getText());
            }
        });
        startingField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        endingField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(endingField.getText().length() > 0)
                    endingFrame = Integer.parseInt(endingField.getText());
            }
        });
        endingField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        partsField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(partsField.getText().length() > 0)
                    amountOfParts= Integer.parseInt(partsField.getText());
            }
        });
        partsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        betweenField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(betweenField.getText().length() > 0)
                    framesBetween = Integer.parseInt(betweenField.getText());
            }
        });
        betweenField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(amountOfParts < 1)
                {
                    JOptionPane.showMessageDialog(null,"Amount of parts must be greater than zero.");
                }
                else if(framesBetween < 1)
                {
                    JOptionPane.showMessageDialog(null,"Amount of frames between must be at least 1.");
                }
                else if(startingFrame > endingFrame)
                {
                    JOptionPane.showMessageDialog(null,"Starting frame must be less than ending frame.");
                }
                else
                {
                    frameData = "";
                    for (int a = startingFrame; a <= endingFrame; a+=framesBetween) {
                        if ((a / 29 >= 256))
                            break;

                        String hexNumber = String.format("%02x", (nameNumber + ((a - startingFrame) / 29))).toUpperCase();
                        for(int b = 0; b < amountOfParts; b++)
                            frameData += ("FrameIndex " + startingPrefix + hexNumber + " " + (char) (((a - startingFrame) % 29) + 65) + " " + b + " " + a + "\n");
                    }
                    output.setText(frameData);
                }
            }
        });
        copyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection (output.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
                clpbrd.setContents (stringSelection, null);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shiny Metagross's Frame Index Generator");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        frameIndexGUI fig = new frameIndexGUI();
        frame.setContentPane(fig.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
