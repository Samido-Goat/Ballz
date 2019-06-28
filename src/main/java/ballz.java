//File: ballz.java
//Created: 24/06/2019
//Finished: 24/06/2019
//Author: Hisbaan Noorani

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ballz implements ActionListener, MouseListener, WindowListener {
    public final boolean DEBUG = true;

    JFrame mainFrame = new JFrame("Main Menu");
    JPanel mainBottomPanel = new JPanel();
    JButton startButton = new JButton("Start Game");

    JFrame gameFrame = new JFrame("Ballz");
    gameDrawing board = new gameDrawing();

    public static int canvasXSize = 800;
    public static int canvasYSize = 400;
    public static int ballDiametre = 10;
    public static int ballRadius = ballDiametre / 2;

    //TODO position currently represents the top left corner of the ellipse. It needs to represent the centre pixel
    public static int xPos = canvasXSize / 2;
    public static int yPos = canvasYSize / 2;

    public int xDragInitial = 0;
    public int yDragInitial = 0;

    public int xDragFinal = 0;
    public int yDragFinal = 0;

    int xDragDelta = 0;
    int yDragDelta = 0;

    int xVelocity = 0;
    int yVelocity = 0;

    public double dragAngle = 0;
    public double ballMovementAngle = 0;

    public Timer movement;

    Velocity velocity = new Velocity();

    public static void main(String[] args) {
        new ballz();
    }

    ballz() {
        movement = new Timer(10, e -> {
            wallCollision();
            move();
        });

        mainMenu();
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
        gameFrame.setSize(canvasXSize, canvasYSize);
        gameFrame.setResizable(false);
        gameFrame.setLayout(new BorderLayout());
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);
        if (gameFrame.getMouseListeners().length < 1) gameFrame.addMouseListener(this);

        board.setSize(canvasXSize, canvasYSize);
        gameFrame.add(board, BorderLayout.CENTER);

        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
    }


    public void getStartingAngle() {
        xDragDelta = xDragFinal - xDragInitial;
        yDragDelta = yDragFinal - yDragInitial;

        double relatedAcuteAngle = Math.abs(Math.toDegrees(Math.atan((double) yDragDelta / (double) xDragDelta)));

        if (xDragDelta > 0 && yDragDelta > 0) {
            dragAngle = relatedAcuteAngle;
            System.out.println("quadrant 1");
        }
        if (xDragDelta < 0 && yDragDelta > 0) {
            dragAngle = 180 - relatedAcuteAngle;
            System.out.println("quadrant 2");
        }
        if (xDragDelta < 0 && yDragDelta < 0) {
            dragAngle = 180 + relatedAcuteAngle;
            System.out.println("quadrant 3");
        }
        if (xDragDelta > 0 && yDragDelta < 0) {
            dragAngle = 360 - relatedAcuteAngle;
            System.out.println("quadrant 4");
        }

        if (xDragDelta == 0) {
            if (yDragDelta > 0) {
                dragAngle = 90;
            }
            if (yDragDelta < 0) {
                dragAngle = 270;
            }
            if (yDragDelta == 0) {
                dragAngle = 0;
            }
        }

        if (yDragDelta == 0) {
            if (xDragDelta > 0) {
                dragAngle = 360;
            }
            if (xDragDelta < 0) {
                dragAngle = 180;
            }
            if (xDragDelta == 0) {
                dragAngle = 0;
            }
        }
        if (DEBUG) System.out.println("X: " + xDragDelta + " | Y: " + yDragDelta + " | 0r: " + relatedAcuteAngle);
        if (DEBUG) System.out.println(dragAngle);

        if (dragAngle == 0) {
            ballMovementAngle = 180;
        } else if (dragAngle == 180) {
            ballMovementAngle = 0;
        } else if (dragAngle < 180) {
            ballMovementAngle = dragAngle + 180;
        } else if (dragAngle > 180) {
            ballMovementAngle = dragAngle - 180;
        }

        if (ballMovementAngle < 0) {
            ballMovementAngle += 360;
            if (DEBUG) System.out.println("angle < 0");
        } else if (ballMovementAngle > 360) {
            ballMovementAngle -= 360;
            if (DEBUG) System.out.println("angle > 360");
        }

        xVelocity = (int) (5 * Math.cos(Math.toRadians(ballMovementAngle)));
        yVelocity = (int) (5 * Math.sin(Math.toRadians(ballMovementAngle)));

        if (DEBUG)
            System.out.println("xVel: " + xVelocity + " | yVel: " + yVelocity + " | dragAngle: " + dragAngle + " | ballAngle: " + ballMovementAngle);

        movement.start();
    }

    public void move() {
        if (xDragDelta == 0 || yDragDelta == 0) {
        } else {

            xPos += xVelocity;
            yPos += yVelocity;

            board.repaint();
        }
    }

    //TODO the coordinates for this are wrong (due to the position being slightly incorrect). Once that is fixed, come here and fix the coords.
    public void wallCollision() {
        if(xPos >= canvasXSize - ballDiametre) {
            xVelocity *= -1;

            if (DEBUG) System.out.println("Hit east wall:  (" + xPos + ", " + yPos + ")");
        }

        if(xPos <= 0) {
            xVelocity *= -1;

            if (DEBUG) System.out.println("Hit west wall:  (" + xPos + ", " + yPos + ")");
        }

        if(yPos >= canvasYSize - ballDiametre) {
            yVelocity *= -1;

            if (DEBUG) System.out.println("Hit south wall: (" + xPos + ", " + yPos + ")");
        }

        if(yPos <= 0) {
            yVelocity *= -1;

            if (DEBUG) System.out.println("Hit north wall: (" + xPos + ", " + yPos + ")");
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gameFrame) { //and mouse is on the ball (use the xPos and yPos coordinates of the ball and how big the ball is to generate an area where the ball is.
            xDragInitial = e.getX();
            yDragInitial = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == gameFrame) {
            xDragFinal = e.getX();
            yDragFinal = e.getY();
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
