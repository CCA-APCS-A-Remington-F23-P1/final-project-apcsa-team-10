import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.net.URL;
import java.io.File;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;

public class level2 extends Canvas implements KeyListener, Runnable {

  private boolean[] keys;
  private BufferedImage back;
  private Player player;
  private Block testBlock; 
  private ArrayList<Block> platforms; 
  private ArrayList<Block> walls;
  private ArrayList<Enemy> enemies;
  private boolean isOnPlatform; 
  private boolean gravityDir; 
  private long jumpTimer; 
  private Block coin;
  private Block healthBar;
  private int lives;

  public level2() {
    setBackground(Color.black);
    keys = new boolean[7];

    player = new Player(50, 100, 30, 30, 2);
    coin = new Block(540, 540, 30, 30);
    healthBar = new Block(20, 20, 235, 59);
    gravityDir = true; 
    jumpTimer = 51; 
    lives = 171;

    enemies = new ArrayList<Enemy>();
    enemies.add(new Enemy(200, 100, 30, 30));
    enemies.add(new Enemy(450, 100, 30, 30));
    enemies.add(new Enemy(550, 260, 30, 30));
    enemies.add(new Enemy(150, 540, 30, 30));

    platforms = new ArrayList<Block>();
    platforms.add(new Block(120, 200, 100, 10));
    platforms.add(new Block(30, 300, 100, 10));
    platforms.add(new Block(120, 400, 100, 10));
    platforms.add(new Block(30, 500, 100, 10));
    platforms.add(new Block(430, 200, 100, 10));
    platforms.add(new Block(520, 300, 100, 10));
    platforms.add(new Block(430, 400, 100, 10));
    platforms.add(new Block(520, 500, 100, 10));

    walls = new ArrayList<Block>();
    walls.add(new Block(300, 200, 50, 600));


    this.addKeyListener(this);
    new Thread(this).start();
    setVisible(true);

  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D) window;

    //take a snap shop of the current screen and same it as an image
    //that is the exact same width and height as the current screen
    if (back == null)
      back = (BufferedImage)(createImage(getWidth(), getHeight()));

    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();
    // graphToBack.setColor(Color.WHITE);
    // graphToBack.fillRect(0,0,800,600);
    try {
      URL url = getClass().getResource("/gameImages/medievalbackground2.png");
      Image image = ImageIO.read(url);
      graphToBack.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
    catch (Exception e) {
      System.out.println("failed to load background");
    }
    

    jumpTimer++; 
    if(jumpTimer >= 30) {
      gravityDir = true; 
    }
    if(jumpTimer < 30) {
      gravityDir = false; 
    }


    player.draw(graphToBack);
    // enemy.draw(graphToBack);
    coin.drawCoin(graphToBack);
    // enemy.backAndForth(10, 100);
    healthBar.drawHealthBar(graphToBack);
    graphToBack.setColor(Color.RED);
    graphToBack.fillRect(79,40,lives,19);

    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).draw(graphToBack);
      if (i == 0) {
        enemies.get(i).backAndForth(130, 200);
      }
      if (i == 1) {
        enemies.get(i).backAndForth(420, 500);
      }
      if (i == 2) {
        enemies.get(i).backAndForth(500, 590);
      }
      if (i == 3) {
        enemies.get(i).setDirection(false);
        enemies.get(i).backAndForth(70, 270);
      }
      if (player.collidesEnemy(enemies.get(i)))  {
        lives-=57;
        enemies.remove(i);
        i--;
      }
    }

    for(Block b : platforms){
      b.drawPlatform(graphToBack);
    }
    for (Block b : walls) {
      b.drawWall(graphToBack);
    }

    if (player.collides(coin)) {
      player.setX(120);
      player.setY(200);
      GameRunner run = new GameRunner(3);
    }

    isOnPlatform = false;

    //make it so that the player cant go off the screen 
    if(player.getX() < 0) {
      player.setX(0);
    }
    if(player.getX()+player.getWidth() > 790) {
      player.setX(790-player.getWidth());
    }
    if(player.getY() < 0) {
      player.setY(0);
    }
    if(player.getY()+player.getHeight() > 570) {
      player.setY(570-player.getHeight());
    }

    //check if the player is on any platforms


    //checks for collision on bottom of platofrms 
    for(Block b : platforms) {
      if(player.didCollideTop(b)) {
        System.out.println("CTOP");
        isOnPlatform = true;
      }
      if(player.didCollideBottom(b)){
        System.out.println("CBOTTOM");
        player.setY(player.getY()+player.getSpeed());
        gravityDir=true;
        jumpTimer = 100000;
      }
      if(player.didCollideRight(b)) {
        System.out.println("CRIGHT");
        player.setX(player.getX()+player.getSpeed());
      }
      if(player.didCollideLeft(b)) {
        System.out.println("CLEFT");
        player.setX(player.getX()-player.getSpeed());
      }
    }

    // player is on floor
    if (player.getY() + player.getHeight() == 570) {
      isOnPlatform = true;
    }

    //check if the player is touching any walls
    for (Block b: walls) {
      if (player.didCollideWallLeft(b)) {
        player.setX(player.getX()-player.getSpeed());
      }
      if (player.didCollideWallRight(b)) {
        player.setX(player.getX()+player.getSpeed());
      }
      if (player.collides(b)) {
        isOnPlatform = true;
      }
    }

    //gravity  
    if(!(isOnPlatform)){
      if(gravityDir==true){
      player.move("DOWN");  
      }
      if(gravityDir==false){
        player.move("UP");  
      }
    }


    //checks for keypresses 
    if (keys[0]) {
      player.move("LEFT");
      //System.out.println("LEFT");
    }
    if (keys[1]) {
      player.move("RIGHT");
      //System.out.println("RIGHT");
    }
    if (keys[2] && isOnPlatform) {
      jumpTimer = 0; 
      player.move("UP");
      //System.out.println("UP");
    }
    if (keys[3] && !isOnPlatform) {
      player.move("DOWN");
      //System.out.println("DOWN");
    }
    if (keys[4]) {

    }


    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
      keys[5] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_P) {
      keys[6] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
      keys[5] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_P) {
      keys[6] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e) {
    //no code needed here
  }

  public void run() {
    try {
      while (true) {
        Thread.currentThread().sleep(5);
        repaint();
      }
    } catch (Exception e) {}
  }
}
