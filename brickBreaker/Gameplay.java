package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;

    private static final int PLAY_AREA_WIDTH = 692;
    private static final int PLAY_AREA_HEIGHT = 592;

    private static final int PADDLE_Y = 550;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 8;
    private static final int PADDLE_MOVE = 20;

    private static final int BALL_SIZE = 20;
    private static final int INITIAL_BALL_X = 120;
    private static final int INITIAL_BALL_Y = 350;
    private static final int INITIAL_BALL_X_DIR = -1;
    private static final int INITIAL_BALL_Y_DIR = -2;

    private static final int INITIAL_PLAYER_X = 310;
    private static final int RIGHT_BORDER_LIMIT = 670;
    private static final int LEFT_BORDER_LIMIT = 0;
    private static final int BALL_FALL_LIMIT = 570;

    private boolean play = false;
    private int score = 0;
    private int lives = 3;

    private static final int BRICK_ROWS = 3;
    private static final int BRICK_COLUMNS = 7;
    private static final int TOTAL_BRICKS = BRICK_ROWS * BRICK_COLUMNS;

    private int totalBricks = TOTAL_BRICKS;

    private Timer timer;
    private int delay = 8;

    private int playerX = INITIAL_PLAYER_X;

    private int ballPosX = INITIAL_BALL_X;
    private int ballPosY = INITIAL_BALL_Y;
    private int ballXdir = INITIAL_BALL_X_DIR;
    private int ballYdir = INITIAL_BALL_Y_DIR;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(BRICK_ROWS, BRICK_COLUMNS);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);

        // Draw map
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.green);
        g.fillRect(0, 0, 3, PLAY_AREA_HEIGHT);
        g.fillRect(0, 0, PLAY_AREA_WIDTH, 3);
        g.fillRect(PLAY_AREA_WIDTH - 1, 0, 3, PLAY_AREA_HEIGHT);

        // Score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 540, 30);

        // Lives
        g.drawString("Lives: " + lives, 20, 30);

        // Paddle
        g.setColor(Color.red);
        g.fillRect(playerX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Ball
        g.setColor(Color.blue);
        g.fillOval(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE);

        // Win condition
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won! Score: " + score, 220, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        // Game over condition
        if (!play && lives <= 0) {
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over! Score: " + score, 200, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            Rectangle ballRect = new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE);
            Rectangle paddleRect = new Rectangle(playerX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

            // Paddle collision
            if (ballRect.intersects(paddleRect)) {
                ballYdir = -ballYdir;
            }

            // Brick collision
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + BALL_SIZE - 1 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            // Ball movement
            ballPosX += ballXdir;
            ballPosY += ballYdir;

            // Wall collision
            if (ballPosX < LEFT_BORDER_LIMIT) {
                ballXdir = -ballXdir;
            }

            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }

            if (ballPosX > RIGHT_BORDER_LIMIT) {
                ballXdir = -ballXdir;
            }

            // Ball falls below paddle
            if (ballPosY > BALL_FALL_LIMIT) {
                lives--;

                if (lives <= 0) {
                    play = false;
                    ballXdir = 0;
                    ballYdir = 0;
                } else {
                    resetBallAndPaddle();
                }
            }
        }

        repaint();
    }

    private void resetBallAndPaddle() {
        play = false;
        ballPosX = INITIAL_BALL_X;
        ballPosY = INITIAL_BALL_Y;
        ballXdir = INITIAL_BALL_X_DIR;
        ballYdir = INITIAL_BALL_Y_DIR;
        playerX = INITIAL_PLAYER_X;
    }

    private void resetGame() {
        play = false;
        score = 0;
        lives = 3;
        totalBricks = TOTAL_BRICKS;
        playerX = INITIAL_PLAYER_X;
        ballPosX = INITIAL_BALL_X;
        ballPosY = INITIAL_BALL_Y;
        ballXdir = INITIAL_BALL_X_DIR;
        ballYdir = INITIAL_BALL_Y_DIR;
        map = new MapGenerator(BRICK_ROWS, BRICK_COLUMNS);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                resetGame();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += PADDLE_MOVE;
    }

    public void moveLeft() {
        play = true;
        playerX -= PADDLE_MOVE;
    }
}
