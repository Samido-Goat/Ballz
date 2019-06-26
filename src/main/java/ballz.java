//File: ballz.java
//Created: 24/06/2019
//Finished: 24/06/2019
//Name: Hisbaan Noorani

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.*;
import org.dyn4j.collision.*;
import org.dyn4j.geometry.Vector2;


public class ballz implements ActionListener, MouseListener, WindowListener {
    public final boolean DEBUG = true;

    World world = new World();

    JFrame mainFrame = new JFrame("Main Menu");
    JPanel mainBottomPanel = new JPanel();
    JButton startButton = new JButton("Start Game");

    JFrame gameFrame = new JFrame("Ballz");
    gameDrawing board = new gameDrawing();

    public static int x = 195;
    public static int y = 395;

    public int initialX = 0;
    public int initialY = 0;

    public int finalX = 0;
    public int finalY = 0;

    int differenceX = 0;
    int differenceY = 0;

    public double angle = 0;
    public double ballMovementAngle = 0;

    public Timer movement;

    public static void main(String[] args) {
        new ballz();
    }

    ballz() {
        movement = new Timer(100, e -> {
            move();
        });

//        this.world.setGravity(World.ZERO_GRAVITY);

        mainMenu();
        bounceCheck();
    }

    public void mainMenu() {
        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        if (mainFrame.getWindowListeners().length < 1) mainFrame.addWindowListener(this);

        mainFrame.add(mainBottomPanel, BorderLayout.SOUTH);

        mainBottomPanel.setLayout(new GridLayout(1, 2));
        mainBottomPanel.add(startButton);

        if (startButton.getActionListeners().length < 1) startButton.addActionListener(this);

        mainFrame.setVisible(true);
        gameFrame.setVisible(false);
    }

    public void startGame() {
        gameFrame.setSize(400, 800);
        gameFrame.setResizable(false);
        gameFrame.setLayout(new BorderLayout());
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);
        if (gameFrame.getMouseListeners().length < 1) gameFrame.addMouseListener(this);

        gameFrame.add(board, BorderLayout.CENTER);

        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
    }


    public void getStartingAngle() {
        differenceX = finalX - initialX;
        differenceY = finalY - initialY;

        double relatedAcuteAngle = Math.abs(Math.toDegrees(Math.atan((double) differenceY / (double) differenceX)));

        if (differenceX > 0 && differenceY > 0) {
            angle = relatedAcuteAngle;
            System.out.println("quadrant 1");
        }
        if (differenceX < 0 && differenceY > 0) {
            angle = 180 - relatedAcuteAngle;
            System.out.println("quadrant 2");
        }
        if (differenceX < 0 && differenceY < 0) {
            angle = 180 + relatedAcuteAngle;
            System.out.println("quadrant 3");
        }
        if (differenceX > 0 && differenceY < 0) {
            angle = 360 - relatedAcuteAngle;
            System.out.println("quadrant 4");
        }

        if (differenceX == 0) {
            if (differenceY > 0) {
                angle = 90;
            }
            if (differenceY < 0) {
                angle = 270;
            }
            if (differenceY == 0) {
                angle = 0;
            }
        }

        if (differenceY == 0) {
            if (differenceX > 0) {
                angle = 360;
            }
            if (differenceX < 0) {
                angle = 180;
            }
            if (differenceX == 0) {
                angle = 0;
            }
        }
        if (DEBUG) System.out.println("X: " + differenceX + " | Y: " + differenceY + " | 0r: " + relatedAcuteAngle);
        if (DEBUG) System.out.println(angle);

        if (angle < 180) {
            ballMovementAngle = angle + 180;
        } else if (angle > 180) {
            ballMovementAngle = angle - 180;
        }

        movement.start();
    }

    public int closestToZero(int x, int y) {
        if (Math.min(Math.abs(x), Math.abs(y)) == Math.abs(x)) {
            return x;
        } else if (Math.min(Math.abs(x), Math.abs(y)) == Math.abs(y)) {
            return y;
        } else {
            return 0;
        }
    }

    public void move() {

        double changeInX = 0;
        double changeInY = 0;

        //x + y should always = 5

        //use a basic physics engine -- > maybe not.. seems confusing
        //processing instead of javafx

        if (differenceX == 0 || differenceY == 0) {
        } else {


            //ball only -y and if you drag up adn to the right
            double reducedX = differenceX / closestToZero(differenceX, differenceY);
            double reducedY = differenceY / closestToZero(differenceX, differenceY);

            changeInX = -1 * Math.round(reducedX * (5 / (reducedX + reducedY)));
            changeInY = -1 * Math.round(reducedY * (5 / (reducedX + reducedY)));
        }
//        if(ballMovementAngle > 0 && ballMovementAngle < 90) {
//            changeInX = 5;
//        }
//
//        if(ballMovementAngle > 90 && ballMovementAngle < 180) {
//            changeInX = -5;
//        }
//
//        if(ballMovementAngle > 180 && ballMovementAngle < 270) {
//            changeInX = -5;
//        }
//
//        if(ballMovementAngle > 270 && ballMovementAngle < 360) {
//            changeInX = 5;
//        }
//
//        int changeInY = (int) ((double) changeInX * Math.tan(ballMovementAngle));

//        int changeInY = (int) Math.round((5.0 * Math.tan(ballMovementAngle)) / (1.0 + Math.tan(ballMovementAngle)));
//        changeInX = 5 - Math.abs(changeInY);

        x += changeInX;
        y += changeInY;

        if (DEBUG)
            System.out.println("change in X: " + changeInX + " | change in Y: " + changeInY + " | angle: " + angle + " | Ball angle: " + ballMovementAngle);

        board.repaint();
    }

    public void bounceCheck() {
        /*
         * for the bouncing, when it hits the east wall, change the angle to the same as the incident from the normal so that is follows the laws of reflection
         * do the same for the west, north and south walls.
         * for example, if the angle is 45 (moving up and to the right at a perfect diagonal) and the ball hits the east wall then the reflection should be 90 + 45 = 135 (or 180 - 45, not sure which to use yet but I think that this is it because you want to use the related angle to find the actual angle, no the actual angle to find the actual angle.)
         * */

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        }
    }

    /*
     * by the amount that the x and y values change, what is the angle that the user is pulling and what is the angle 180 degrees from that
     * aka what angle should the ball move in
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gameFrame) { //and mouse is on the ball (use the x and y coordinates of the ball and how big the ball is to generate an area where the ball is.
            initialX = e.getX();
            initialY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == gameFrame) {
            finalX = e.getX();
            finalY = e.getY();
        }

        getStartingAngle();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        JFrame temp = new JFrame();

        if (e.getSource() == mainFrame) {
            temp = mainFrame;
        }
        if (e.getSource() == gameFrame) {
            temp = gameFrame;
        }

        JOptionPane.showMessageDialog(temp, "Thanks for playing!\nGoodbye!");
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
