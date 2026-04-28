package brickBreaker;

import javax.swing.JFrame;

public class Main {
    private JFrame frame = new JFrame();

    public Main() {
        Gameplay gamePlay = new Gameplay();

        frame.setTitle("Brick Breaker");
        frame.setBounds(10, 10, 700, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePlay);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}