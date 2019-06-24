//File: ballz.java
//Created: 24/06/2019
//Finished: 24/06/2019
//Name: Hisbaan Noorani
//
//This program

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ballz implements ActionListener, WindowListener {
    JFrame mainFrame = new JFrame("Main Menu");
    JPanel mainBottomPanel = new JPanel();
    JButton startButton = new JButton("Start Game");

    JFrame gameFrame = new JFrame("Ballz");


    public static void main(String[] args) {
        new ballz();
    }

    ballz() {
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
        gameFrame.setSize(400, 800);
        gameFrame.setResizable(false);
        gameFrame.setLayout(new BorderLayout());
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);

        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        JFrame temp = new JFrame();

        if (e.getSource() == mainFrame) {
            temp = mainFrame;
        }
        if(e.getSource() == gameFrame) {
            temp = gameFrame;
        }

        JOptionPane.showMessageDialog(temp, "Thanks for playing!\nGoodbye!");
        System.exit(0);
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
