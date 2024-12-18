import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
    private String word;
    private int attempts;
    private final int numberOfWords = 150;
    
    Game() {
        try {
            FileReader fIn = new FileReader("res/words.txt");
            BufferedReader fRead = new BufferedReader(fIn);
            Random r = new Random();

            int n = r.nextInt(numberOfWords);
            for(int i = 0; i != n; i ++)
                fRead.readLine();
            word = fRead.readLine();

            fIn.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "words.txt not found", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e1) {
            JOptionPane.showMessageDialog(null, "Error while reading words.txt", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getWord() {
        return word;
    }

    public int getWordLength() {
        return word.length();
    }

    public void incrementAttempts() {
        attempts ++;
    }

    public void decrementAttempts() {
        attempts --;
    }

    public int getAttempts() {
        return attempts;
    }
}
