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

//        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, ballz.canvasXSize, ballz.canvasYSize);

        g.setColor(Color.white);
        g.fillOval(ballz.xPos - ballz.ballRadius, ballz.yPos - ballz.ballRadius, ballz.ballDiametre, ballz.ballDiametre);

        g.drawLine(0, ballz.canvasYSize / 2, ballz.canvasXSize, ballz.canvasYSize / 2);
        g.drawLine(ballz.canvasXSize / 2, 0, ballz.canvasXSize / 2, ballz.canvasYSize);
    }
}
