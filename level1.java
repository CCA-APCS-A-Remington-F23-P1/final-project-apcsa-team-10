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

import java.util.Scanner;
import java.io.FileWriter;

import javax.swing.JComponent;
import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import java.net.URL;
import java.io.File;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;

public class level1 extends Canvas implements KeyListener, Runnable {

  private boolean[] keys;
  private BufferedImage back;
  private Player player;
  private ArrayList < Enemy > enemies;
  private ArrayList < Block > platforms;
  private ArrayList < Block > walls;
  private boolean isOnPlatform;
  private boolean gravityDir;
  private long jumpTimer;
  private long shotTimer;
  private Block coin;
  private Block healthBar;
  private boolean pause;
  private boolean won;
  private boolean lost;
  private int score;
  FileWriter myWriter;

  public level1() {
    setBackground(Color.WHITE);

    keys = new boolean[7];

    player = new Player(50, 570, 30, 30, 2);
    coin = new Block(540, 540, 30, 30);
    healthBar = new Block(20, 20, 235, 59);

    enemies = new ArrayList < Enemy > ();
    enemies.add(new Enemy(10, 150, 30, 30));
    enemies.add(new Enemy(400, 150, 30, 30));
    enemies.add(new Enemy(400, 540, 30, 30));
    enemies.add(new Enemy(470, 270, 30, 30));

    gravityDir = true;
    jumpTimer = 51;
    shotTimer = 0;

    platforms = new ArrayList < Block > ();
    platforms.add(new Block(120, 200, 100, 20));
    platforms.add(new Block(0, 300, 150, 20));
    platforms.add(new Block(120, 400, 100, 20));
    platforms.add(new Block(0, 500, 150, 20));
    platforms.add(new Block(430, 200, 100, 20));
    platforms.add(new Block(350, 300, 250, 20));
    platforms.add(new Block(430, 400, 250, 20));
    platforms.add(new Block(500, 500, 120, 20));

    walls = new ArrayList < Block > ();
    walls.add(new Block(300, 200, 50, 600));
    // walls.add(new Block(300, 200, 50, 50));
    // walls.add(new Block(300, 250, 50, 50));
    // walls.add(new Block(300, 300, 50, 50));
    // walls.add(new Block(300, 350, 50, 50));
    // walls.add(new Block(300, 400, 50, 50));
    // walls.add(new Block(300, 450, 50, 50));
    // walls.add(new Block(300, 500, 50, 50));
    // walls.add(new Block(300, 550, 50, 50));

    try {
      File file = new File("level1.txt");
      Scanner scan = new Scanner(file);
      score = scan.nextInt();
      System.out.println(score);

    } catch (Exception e) {
      System.out.println("cannot fetch resource!");
    }

    this.addKeyListener(this);
    new Thread(this).start();
    setVisible(true);

  }

  public void update(Graphics window) {
    Graphics2D twoDGraph = (Graphics2D) window;

    if (back == null)
      back = (BufferedImage)(createImage(getWidth(), getHeight()));
    Graphics graphToBack = back.createGraphics();
    if (lost) {
      graphToBack.setColor(Color.BLACK);
      graphToBack.fillRect(0, 0, 800, 600);
      graphToBack.setColor(Color.RED);
      graphToBack.drawString("Game Over", 250, 230);
      twoDGraph.drawImage(back, null, 0, 0);
    } else if (won) {
      graphToBack.setColor(Color.BLACK);
      graphToBack.fillRect(0, 0, 800, 600);
      graphToBack.setColor(Color.GREEN);
      graphToBack.drawString("You are victorious!", 250, 230);
      twoDGraph.drawImage(back, null, 0, 0);
    } else {
      paint(window);
    }
  }

  public void paint(Graphics window) {
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D) window;

    // press p (pause)
    if (keys[5]) {
      pause = true;
    }

    // press r (resume)
    if (keys[6]) {
      pause = false;
    }

    if (!pause) {
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
        URL url = getClass().getResource("/gameImages/medievalbackground.png");
        Image image = ImageIO.read(url);
        graphToBack.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
      } catch (Exception e) {
        System.out.println("failed to load background");
      }

      Font myFont = new Font("Courier New", 1, 17);
      graphToBack.setFont(myFont);
      graphToBack.setColor(Color.WHITE);
      graphToBack.fillRect(300, 32, 150, 32);
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Score: " + score, 315, 54);

      jumpTimer++;
      if (jumpTimer >= 30) {
        gravityDir = true;
      }
      if (jumpTimer < 30) {
        gravityDir = false;
      }

      shotTimer++;

      graphToBack.setColor(Color.WHITE);
      player.draw(graphToBack);
      coin.drawCoin(graphToBack);
      healthBar.drawHealthBar(graphToBack);
      graphToBack.setColor(Color.RED);
      graphToBack.fillRect(79, 40, player.getLives(), 19);

      for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).draw(graphToBack);
        if (i == 0) {
          enemies.get(i).backAndForth(10, 150);
        }
        if (i == 1) {
          enemies.get(i).backAndForth(400, 540);
        }
        if (i == 2) {
          enemies.get(i).backAndForth(400, 560);
        }
        if (i == 3) {
          enemies.get(i).setDirection(false);
          enemies.get(i).backAndForth(370, 500);
        }

        if (player.didCollideTop(enemies.get(i))) {
          score += 20;
          enemies.remove(i);
          i--;
        }

        if (player.collidesEnemy(enemies.get(i))) {
          player.setLives(player.getLives() - 57);
          enemies.remove(i);
          i--;
          if (player.getLives() <= 0) {
            lost = true;
          }
        }
        if (shotTimer == 50) {
          enemies.get(i).pickUpStone();
          shotTimer = 0;
        }
        enemies.get(i).throwStones(graphToBack, player);
        if (player.getLives() <= 0) {
          lost = true;
        }
      }

      try {
        myWriter = new FileWriter("level1.txt");
        myWriter.write(Integer.toString(score));
        myWriter.close(); // Don't forget to close the FileWriter when you're done writing.
      } catch (Exception e) {
        System.out.println("Cannot write to file!");
      }

      for (Block b: platforms) {
        //b.drawPlatform(graphToBack);
        b.draw(graphToBack, Color.BLACK);
      }
      for (Block b: walls) {
        //b.drawWall(graphToBack);
        b.draw(graphToBack, Color.BLACK);
      }

      if (player.collides(coin)) {
        player.setX(120);
        player.setY(200);
        try {
          myWriter = new FileWriter("level1.txt");
          myWriter.write(Integer.toString(100));
          myWriter.close(); // Don't forget to close the FileWriter when you're done writing.
        } catch (Exception e) {
          System.out.println("Cannot write to file!");
        }
        GameRunner run = new GameRunner(2);
      }

      isOnPlatform = false;

      //make it so that the player cant go off the screen 
      if (player.getX() < 0) {
        player.setX(0);
      }
      if (player.getX() + player.getWidth() > 650) {
        player.setX(650 - player.getWidth());
      }
      if (player.getY() < 0) {
        player.setY(0);
      }
      if (player.getY() + player.getHeight() > 570) {
        player.setY(570 - player.getHeight());
      }

      //checks for collision on bottom of platofrms 
      for (Block b: platforms) {
        if (player.didCollideTop(b)) {
          isOnPlatform = true;
        }
        if (player.didCollideBottom(b)) {
          player.setY(player.getY() + player.getSpeed());
          gravityDir = true;
          jumpTimer = 30;
        }
        if (player.didCollideRight(b)) {
          player.setX(player.getX() + player.getSpeed());
        }
        if (player.didCollideLeft(b)) {
          player.setX(player.getX() - player.getSpeed());
        }
      }

      // player is on floor
      if (player.getY() + player.getHeight() == 570) {
        isOnPlatform = true;
      }

      //check if the player is touching any walls
      for (Block b: walls) {
        if (player.didCollideWallLeft(b)) {
          player.setX(player.getX() - player.getSpeed());
        }
        if (player.didCollideWallRight(b)) {
          player.setX(player.getX() + player.getSpeed());
        }
        if (player.collides(b)) {
          isOnPlatform = true;
        }
      }

      //gravity  
      if (!(isOnPlatform)) {
        if (gravityDir == true) {
          player.move("DOWN");
        }
        if (gravityDir == false) {
          player.move("UP");
        }
      }

      //checks for keypresses 
      if (keys[0]) {
        player.move("LEFT");
      }
      if (keys[1]) {
        player.move("RIGHT");
      }
      if (keys[2] && isOnPlatform) {
        jumpTimer = 0;
        player.move("UP");
      }
      if (keys[3] && !isOnPlatform) {
        player.move("DOWN");
      }
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
    if (e.getKeyCode() == KeyEvent.VK_P) {
      keys[5] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
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
    if (e.getKeyCode() == KeyEvent.VK_P) {
      keys[5] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
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