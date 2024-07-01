import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Home extends JFrame implements Runnable {
    static Thread thread;
    static JLabel heading;

    Home() {
        setBounds(40, 100, 1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures the application closes properly

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1200, 800); // Adjust the bounds to fit the frame
        panel.setBackground(new Color(252,203,255));
        panel.setLayout(null); // Use null layout to set absolute positions
        add(panel);

        heading = new JLabel("WELCOME TO THE SNAKE GAME");
        //heading.setOpaque(true); // Make the background color visible
        heading.setForeground(Color.black);
        heading.setFont(new Font("Serif.", Font.PLAIN, 30));
        heading.setBounds(100, 100, 100, 100); // Initial position and size
        panel.add(heading);
        
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("snake.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(10, 40, 1200, 600);
        panel.add(gifLabel);

        setVisible(true);
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        new Home();
        int x = 1;
        for (int i = 1; i <= 90; x += 6, i++) {
            heading.setSize(x+1, i);
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
            new App();
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}