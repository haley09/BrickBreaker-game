package brickBreaker;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuScreen {

    private JFrame frame = new JFrame("Brick Breaker");
    private JButton startButton = new JButton("Start Game");

    public MenuScreen() {
        JLabel title = new JLabel("Brick Breaker", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusPainted(false);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(title, BorderLayout.NORTH);
        frame.add(startButton, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            frame.dispose();
            new Main();
        });

        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuScreen();
    }
}