import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame {
    private Container c;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameItem, exitItem;
    private JLabel titleLabel;
    private JPanel bottomPanel, centerPanel, lettersPanel, guessPanel, wordPanel;
    private Draw draw;
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
    
        gameMenu = new JMenu("...");
        menuBar.add(gameMenu);
    
        newGameItem = new JMenuItem("Nuova partita");
        newGameItem.addActionListener(new ButtonsListener());
        gameMenu.add(newGameItem);

        exitItem = new JMenuItem("Esci");
        exitItem.addActionListener(new ButtonsListener());
        gameMenu.add(exitItem);
    
        titleLabel = new JLabel("GIOCO DELL'IMPICCATO", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
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
        JPanel guessPanel1, guessPanel2;
        guessPanel1 = new JPanel();
        guessPanel.add(guessPanel1);
        guessPanel2 = new JPanel();
        guessPanel.add(guessPanel2);
        bottomPanel.add(guessPanel);

        guessField = new JTextField(20);
        guessField.setFont(new Font("Times New Roman", Font.BOLD, 25));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.addKeyListener(new FieldListener());
        guessPanel1.add(guessField);
    
        guessButton = new JButton("Indovina la parola!");
        guessButton.setFocusable(false);
        guessButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        guessButton.setBackground(Color.WHITE);
        guessPanel2.add(guessButton);
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        c.add(centerPanel);

        draw = new Draw(game);
        centerPanel.add(draw);

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

        this.setVisible(true);
    }    

    private class ButtonsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == newGameItem) {
                dispose();
                new Frame();
            } else if(e.getSource() == exitItem)
                System.exit(0);
            else {
                boolean guess = false;
                
                for(int i = 0; i < 26; i ++)
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

                if(!guess)
                    game.incrementAttempts();

                draw.repaint();

                if(game.getAttempts() == 6) {
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
                }
            }
        }
    }

    private class FieldListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if(guessField.getText().length() >= game.getWordLength())
                e.consume();
        }

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {}
    }
}
