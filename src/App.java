import java.awt.Color;
import javax.swing.JFrame;

public class App extends JFrame {
    App() {
        setLocation(10, 10);
        setSize(1300, 700);
        setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SnakeGame snake = new SnakeGame(1200, 600);
        add(snake);
        
        setVisible(true);
    }

    public static void main(String[] args) {
    	new Home();
        new App();
    }
}
