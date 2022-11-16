import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame frame;
    SnakeGame(){
        frame= new JFrame("Snake Game");
        GamePanel panel= new GamePanel();
        panel.setBackground(Color.gray);
        panel.setBounds(10,10,905,700);
        frame.add(panel);


        frame.setBounds(10,10,905, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }
    public static void main (String arg[]){
        SnakeGame object= new SnakeGame();

    }
}
