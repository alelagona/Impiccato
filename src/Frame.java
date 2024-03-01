import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Frame extends JFrame {
    private Container c;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameItem, exitItem, backGroundItem;
    private JLabel titleLabel;
    private JPanel bottomPanel, centerPanel, lettersPanel;
    private JPanel guessPanel, guessPanel1, guessPanel2, wordPanel;
    private DrawPanel drawPanel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton[] lettersButtons;
    private JLabel[] wordLabels;
    private Game game;

    public Frame() {
        this.setSize(1200, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        
        game = new Game();

        c = getContentPane();
        c.setLayout(new BorderLayout());
    
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
    
        gameMenu = new JMenu("⋯");
        menuBar.add(gameMenu);
    
        newGameItem = new JMenuItem("Nuova partita");
        newGameItem.addActionListener(new ButtonsListener());
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        gameMenu.add(newGameItem);
        
        backGroundItem = new JMenuItem("Cambia sfondo");
        backGroundItem.addActionListener(new ButtonsListener());
        backGroundItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        gameMenu.add(backGroundItem);

        exitItem = new JMenuItem("Esci");
        exitItem.addActionListener(new ButtonsListener());
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        gameMenu.add(exitItem);
    
        titleLabel = new JLabel("GIOCO DELL'IMPICCATO", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.BLACK));
        c.add(titleLabel, BorderLayout.NORTH);
    
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        c.add(bottomPanel, BorderLayout.SOUTH);
    
        wordPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(0, (this.getWidth()/game.getWordLength()/2), 30, (this.getWidth()/game.getWordLength()/2));
            }
        };
        wordPanel.setLayout(new GridLayout(1, 5, (this.getWidth()/game.getWordLength()/2), 0));
        bottomPanel.add(wordPanel);

        wordLabels = new JLabel[game.getWordLength()];
        for(int i = 0; i < game.getWordLength(); i ++) {
            wordLabels[i] = new JLabel(" ");
            wordLabels[i].setHorizontalAlignment(JLabel.CENTER);
            wordLabels[i].setVerticalAlignment(JLabel.BOTTOM);
            wordLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 40));
            wordLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            wordPanel.add(wordLabels[i]);
        }
        wordLabels[0].setText(String.valueOf(game.getWord().charAt(0)));
        wordLabels[game.getWordLength()-1].setText(String.valueOf(game.getWord().charAt(game.getWordLength()-1)));

        guessPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(0, 0, 20, 0);
            }
        };
        guessPanel.setLayout(new GridLayout(2, 1));
        guessPanel1 = new JPanel();
        guessPanel2 = new JPanel();
        guessPanel.add(guessPanel1);
        guessPanel.add(guessPanel2);
        bottomPanel.add(guessPanel);

        guessField = new JTextField(20);
        guessField.setFont(new Font("Times New Roman", Font.BOLD, 25));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.addKeyListener(new KeyboardListener());
        guessPanel1.add(guessField);
    
        guessButton = new JButton("Indovina la parola!");
        guessButton.setFocusable(false);
        guessButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        guessButton.setBackground(Color.WHITE);
        guessButton.addActionListener(new ButtonsListener());
        guessButton.addKeyListener(new KeyboardListener());
        guessPanel2.add(guessButton);
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        c.add(centerPanel);

        drawPanel = new DrawPanel(game);
        centerPanel.add(drawPanel);

        lettersPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(120, 40, 120, 80);
            }
        };
        lettersPanel.setLayout(new GridLayout(3, 8, 8, 8));
        centerPanel.add(lettersPanel);
        
        lettersButtons = new JButton[26];
        char letter = 'A';
        for(int i = 0; i < 26; i ++) {
            lettersButtons[i] = new JButton(String.valueOf(letter));
            lettersButtons[i].addActionListener(new ButtonsListener());
            lettersButtons[i].setBackground(Color.WHITE);
            lettersButtons[i].setFocusable(false);
            lettersPanel.add(lettersButtons[i]);
            letter ++;
        }
        
        backGroundItem.doClick();

        this.setVisible(true);
    }    
    
    private class ButtonsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == newGameItem) {
                dispose();
                new Frame();
            } else if(e.getSource() == exitItem) {
                System.exit(0);
            } else if(e.getSource() == backGroundItem) {
                Random r = new Random();
                Color color = new Color((r.nextInt(105) + 150), (r.nextInt(105) + 150), (r.nextInt(105) + 150));

                titleLabel.setBackground(color);
                centerPanel.setBackground(color);
                drawPanel.setBackground(color);
                lettersPanel.setBackground(color);
                guessPanel1.setBackground(color);
                guessPanel2.setBackground(color);
                guessPanel.setBackground(color);
                wordPanel.setBackground(color);
            } else {
                boolean guess = false;

                if(e.getSource() == guessButton) {
                    String enteredWord = guessField.getText().trim().toUpperCase();
                    guessField.setText("");
                    if(game.getWord().equals(enteredWord)) {
                        guess = true;
                        for(int i = 0; i < game.getWordLength(); i ++)
                            wordLabels[i].setText(String.valueOf(enteredWord.charAt(i)));
                    }
                } else {
                    for(int i = 0; i < 26; i ++) {
                        if(lettersButtons[i].getText().equals(e.getActionCommand())) {
                            for(int j = 1; j < (game.getWordLength() - 1); j ++)
                                if(lettersButtons[i].getText().charAt(0) == game.getWord().charAt(j)) {
                                    wordLabels[j].setText(lettersButtons[i].getText());
                                    guess = true;
                                }
                            lettersButtons[i].setEnabled(false);
                            lettersButtons[i].setForeground(Color.LIGHT_GRAY);
                            lettersButtons[i].setBackground(Color.LIGHT_GRAY);
                        }
                    }
                }
        
                if(!guess) {
                    game.incrementAttempts();
                }

                drawPanel.repaint();
                
                int counter = 0;
                for(int i = 0; i < game.getWordLength(); i ++) {
                    if(game.getWord().charAt(i) != wordLabels[i].getText().charAt(0)) {
                        counter ++;
                    }
                }

                if(game.getAttempts() == 6 || counter == 0) {
                    for(int i = 0; i < 26; i ++) {
                        lettersButtons[i].setEnabled(false);
                        lettersButtons[i].setForeground(Color.LIGHT_GRAY);
                        lettersButtons[i].setBackground(Color.LIGHT_GRAY);
                    }
                    guessField.setEditable(false);
                    guessField.setFocusable(false);
                    guessButton.setEnabled(false);
                    guessButton.setForeground(Color.LIGHT_GRAY);
                    guessButton.setBackground(Color.LIGHT_GRAY);

                    if(counter == 0) {
                        ImageIcon icon = new ImageIcon("res/check.png");
                        int choice = JOptionPane.showConfirmDialog(null,
                        "Vuoi iniziare una nuova partita?",
                        "Hai vinto!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                        if(choice == JOptionPane.YES_OPTION) {
                            dispose();
                            new Frame();
                        }
                    } else {
                        ImageIcon icon = new ImageIcon("res/cross.png");
                        int choice = JOptionPane.showConfirmDialog(null, 
                        "La parola da indovinare era: " + game.getWord() + ".\nVuoi iniziare una nuova partita?",
                        "Hai perso!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                        if(choice == JOptionPane.YES_OPTION) {
                            dispose();
                            new Frame();
                        }
                    }
                }
            }
        }
    }

    private class KeyboardListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if(guessField.getText().length() >= game.getWordLength())
                e.consume();
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER && !guessField.getText().trim().equals(""))
                guessButton.doClick();
        }

        public void keyReleased(KeyEvent e) {}
    }
}
