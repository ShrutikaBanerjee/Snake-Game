import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;
        
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    int tileSize = 25;
    Tile snakeHead;
    Tile food;
    Random random;
    Timer gameLoop;
    int velocityX;
    int velocityY;
    int score=0;
    
    ArrayList<Tile> snakeBody;
    
    SnakeGame(int boardheight, int boardwidth) {
        setBackground(new Color(185,223,225));
        setBounds(0, 0, boardwidth, boardheight);

        snakeHead = new Tile(15, 15);
        food = new Tile(25, 15);
        random = new Random();
        snakeBody= new ArrayList<Tile>();
        
        
        velocityX = 0;
        velocityY = 0;
        
        gameLoop = new Timer(100, this);
        gameLoop.start();
        
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (food.x == -1 && food.y == -1) {
            placeFood();
   }
        
        draw(g);
    }

    private void draw(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        // Snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        
        // Food
        g.setColor(Color.yellow);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        
        //snakeBody
        for(int i=0 ; i<snakeBody.size();i++) {
        	Tile snakePart= snakeBody.get(i);
        	g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        }
        
        
    }
    
    private void placeFood() {
        food.x = random.nextInt(getWidth() / tileSize);
        food.y = random.nextInt(getHeight() / tileSize);
    }
    public boolean collision(Tile tile1, Tile tile2) {
    	
    	return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    private void move() {
        // Move snake body
        if (!snakeBody.isEmpty()) {
            // Move each segment except the head
            for (int i = snakeBody.size() - 1; i > 0; i--) {
                snakeBody.get(i).x = snakeBody.get(i - 1).x;
                snakeBody.get(i).y = snakeBody.get(i - 1).y;
            }
            // Move the first segment to where the head was
            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }

        // Move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Check if snake head collides with food
        if (collision(snakeHead, food)) {
            // Add new segment to snake body (at head position)
            snakeBody.add(0, new Tile(snakeHead.x, snakeHead.y));
            score++;
            placeFood(); // Place new food
        }

        // Handle snake going out of bounds
        if (snakeHead.x < 0 || snakeHead.x >= getWidth() / tileSize ||
            snakeHead.y < 0 || snakeHead.y >= getHeight() / tileSize) {
            gameOver();
        }

        // Check if the snake has eaten the food
        if (snakeHead.x == food.x && snakeHead.y == food.y) {
            placeFood(); // Reposition the food
        }

        // Check if the snake has collided with its own body
        for (int i = 1; i < snakeBody.size(); i++) {
            if (collision(snakeHead, snakeBody.get(i))) {
                gameOver();
            }
        }

        repaint(); // Repaint the panel to show updates
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }
    private void gameOver() {
        gameLoop.stop(); // Assuming gameLoop is your game loop instance
        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        
        JLabel label = new JLabel("GAME OVER  , Your Score is "+ score);
        label.setForeground(Color.RED);
        label.setFont(new Font("Arial", Font.BOLD, 18)); // Increase font size
        
        
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);
        
        frame.add(panel);
        
        frame.setSize(400, 200); // Adjust size as needed
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Adjust close operation as needed
        frame.setVisible(true);
       
    }


}
