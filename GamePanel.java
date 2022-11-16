import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpeg"));
    ImageIcon faceup = new ImageIcon(getClass().getResource("faceup.png"));
    ImageIcon facedown = new ImageIcon(getClass().getResource("facedown.png"));
    ImageIcon faceright = new ImageIcon(getClass().getResource("faceright.png"));
    ImageIcon faceleft = new ImageIcon(getClass().getResource("faceleft.png"));
    ImageIcon body = new ImageIcon(getClass().getResource("body.png"));
    ImageIcon enemy = new ImageIcon(getClass().getResource("food.png"));

    int xpos[]= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};

    int ypos[]={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    Random random= new Random();
    int enemyx=150, enemyy=200;

    int move=0;
    int score=0;
    boolean left=false;
    boolean right=true;
    boolean up=false;
    boolean down=false;
    boolean gameover= false;
    int lengthofsnake=3;
    int snakexlength[]= new int[750];
    int snakeylength[]= new int[750];

    Timer time;
    int delay=100;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        time= new Timer(delay,this);
        time.start();
    }
    public void newEnemy(){
        enemyx=xpos[random.nextInt(34)];
        enemyy=ypos[random.nextInt(23)];
        for(int i=lengthofsnake-1;i>=0; i--)
        {
            if(snakexlength[i]==enemyx && snakeylength[i]==enemyy)
            {
                newEnemy();
            }
        }
    }

    public void collide_enemy(){
        if(snakexlength[0]==enemyx && snakeylength[0]==enemyy)
        {
            newEnemy();
            lengthofsnake++;
            score++;
            snakexlength[lengthofsnake-1]=snakexlength[lengthofsnake-2];
            snakeylength[lengthofsnake-1]=snakeylength[lengthofsnake-2];
        }
    }

    public void collide_body(){
        for(int i=lengthofsnake-1; i>0; i--)
        {
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0])
            {
                time.stop();
                gameover=true;
            }
        }
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,851,576);
        if(move==0)
        {
            snakexlength[0]= 100;
            snakexlength[1]= 75;
            snakexlength[2]= 50;

            snakeylength[0]= 100;
            snakeylength[1]= 100;
            snakeylength[2]= 100;
            move++;
        }
        if(left)
        {
            faceleft.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        if(right)
        {
            faceright.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        if(up)
        {
            faceup.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        if(down)
        {
            facedown.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        for(int i=1; i<lengthofsnake; i++)
        {
            body.paintIcon(this,g,snakexlength[i], snakeylength[i]);
        }
        enemy.paintIcon(this,g, enemyx,enemyy);
        if(gameover)
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,30));
            g.drawString("GAME OVER",300,300);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,10));
            g.drawString("PRESS SPACE TO RESTART",330,360);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN, 15));
        g.drawString("SCORE :" +score, 750,30);
        g.drawString("LENGTH : "+lengthofsnake,750,50);
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthofsnake-1; i>0; i--)
        {
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if(left)
        {
            snakexlength[0] =snakexlength[0]-25;
        }
        if(right)
        {
            snakexlength[0]=snakexlength[0]+25;
        }
        if(down)
        {
            snakeylength[0]=snakeylength[0]+25;
        }
        if(up)
        {
            snakeylength[0]=snakeylength[0]-25;
        }

        if(snakexlength[0]>850) snakexlength[0]=25;

        if(snakexlength[0]<25) snakexlength[0]=850;

        if(snakeylength[0]>625) snakeylength[0]=75;

        if(snakeylength[0]<75) snakeylength[0]=625;
        collide_enemy();
        collide_body();
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE &&gameover)
        {
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right))
        {
            left=true;
            right=false;
            up=false;
            down= false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left))
        {
            left=false;
            right=true;
            up=false;
            down= false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down))
        {
            left=false;
            right=false;
            up=true;
            down= false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up))
        {
            left=false;
            right=false;
            up=false;
            down= true;
            move++;
        }
    }

    private void restart() {
        gameover=false;
        move=0;
        lengthofsnake=3;
        score=0;
        left=false;
        right=true;
        down=false;
        up=false;
        time.start();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
