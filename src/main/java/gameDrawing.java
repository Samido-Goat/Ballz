//File: gameDrawing.java
//Created: 24/06/2019
//Finished: 24/06/2019
//Name: Hisbaan Noorani
//
//This program 

import javax.swing.*;
import java.awt.*;

public class gameDrawing extends JPanel {

    gameDrawing() {
        repaint();
    }

    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, 400, 800);

        g.setColor(Color.white);
        g.fillOval(ballz.x, ballz.y, 10, 10);

        g.drawLine(0, 400, 400, 400);
        g.drawLine(200, 0, 200, 800);
    }
}
