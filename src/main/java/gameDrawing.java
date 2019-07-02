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

//        g.drawLine(0, ballz.canvasYSize / 2, ballz.canvasXSize, ballz.canvasYSize / 2);
//        g.drawLine(ballz.canvasXSize / 2, 0, ballz.canvasXSize / 2, ballz.canvasYSize);

        for(int y = 0; y < 12; y++) {
            for(int x = 0; x < 8; x++) {
                if(ballz.blocks[x][y] == -1) {
                    g.setColor(Color.white);
                    g.drawOval(x * (ballz.canvasXSize / 8), y * (ballz.canvasXSize / 8), ballz.canvasXSize / 8, ballz.canvasXSize / 8);

                    //don't add collision here
                } else if(ballz.blocks[x][y] > 0) {
                    g.setColor(Color.red);
                    g.fillRect(x * (ballz.canvasXSize / 8), y * (ballz.canvasXSize / 8), ballz.canvasXSize / 8, ballz.canvasXSize / 8);

                    //add collision here --> maybe by making another array (boolean) that tells the user whether or not there is an object in the designated location.
                }
            }
        }
    }
}
