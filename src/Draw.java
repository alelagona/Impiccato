import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Draw extends JPanel {
    private Game game;

    public Draw(Game game) {
        this.game = game;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));

        switch(game.getAttempts()) {
            case 6:
                g2d.drawLine(391, 235, 430, 275); // leg2
            case 5:
                g2d.drawLine(389, 235, 350, 275); // leg1
            case 4:
                g2d.drawLine(391, 170, 430, 190); // arm2
            case 3:
                g2d.drawLine(389, 170, 350, 190); // arm1
            case 2:
                g2d.drawLine(390, 135, 390, 235); // body
            case 1:
                g2d.draw(new Ellipse2D.Double(360, 75, 60, 60)); // head
            case 0:
                g2d.drawLine(90, 360, 210, 360);
                g2d.drawLine(130, 50, 130, 360);
                g2d.drawLine(131, 120, 200, 51);
                g2d.drawLine(130, 50, 390, 50);
                g2d.drawLine(390, 50, 390, 75);
        }
    }
}
