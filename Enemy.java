import java.util.Scanner;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Enemy extends MovingThing
{
  private int speed;
  //private Image image;
  private boolean movingRight;
  private ArrayList<Stone> stones;

  public Enemy()
  {
    super(0,0,30,30);
    speed = 0;
    movingRight = true;
    stones = new ArrayList<Stone>();
  }

  public Enemy(int x, int y)
  {
    super(x, y, 30, 30);
    speed = 0;
    movingRight = true;
    stones = new ArrayList<Stone>();
  }

  public Enemy(int x, int y, int s)
  {
    super(x, y, 30, 30);
    speed = s;
    movingRight = true;
    stones = new ArrayList<Stone>();
  }

  // all ctors call this ctor
  public Enemy(int x, int y, int w, int h)
  {
    super(x, y, w, h);
    try {
      Scanner file = new Scanner(new File("Enemy.txt"));
      int s = file.nextInt();
      speed = s;
    }
    catch (Exception e) {
      speed = 0;
      System.out.println("Error reading from Enemy.txt");
    }
    movingRight = true;
    stones = new ArrayList<Stone>();
  }

  public void setSpeed(int s)
  {
    speed = s;
  }

  public int getSpeed()
  {
    return speed;
  }

  public void setDirection(boolean b) {
    movingRight = b;
  }

  public boolean getDirection() {
    return movingRight;
  }

  public boolean isMovingRight() {
    return movingRight;
  }

  public void move(String direction)
  {
    if (direction.equals("LEFT")) {
      setX(getX() - speed);
      setDirection(false);
    }
    if (direction.equals("RIGHT")) {
      setX(getX() + speed);
      setDirection(true);
    }
  }

  public void backAndForth(int leftXPos, int rightXPos) {
    if (getX() < rightXPos && isMovingRight()) {
      this.move("RIGHT");
    }
    if (getX() > leftXPos && !isMovingRight()) {
      this.move("LEFT");
    }
    if (getX() == rightXPos) {
      setDirection(false);
    }
    if (getX() == leftXPos) {
      setDirection(true);
    }
  }

  public void pickUpStone()
  {
    stones.add(new Stone(getX() + getWidth() / 2, getY(), 4));
  }

  public void throwStones(Graphics window, Player player) {
    for (int i = 0; i < stones.size(); i++) {
      if (stones.get(i).getDirection() == null) {
        stones.get(i).setDirection(this.getDirection());
      }
      stones.get(i).draw(window);
      stones.get(i).move(""); 
      if (stones.get(i).didCollide(player)){
        stones.remove(i);
        i--;
        player.setLives(player.getLives() - 19);
      }
      if (clearStone(stones.get(i))) {
        stones.remove(i);
        i--;
      }
    }
  }

  private boolean clearStone(Stone stone) {
    return !stone.getDirection() && stone.getX() < 10 || stone.getDirection() && stone.getX() > 500;
  }

  public void draw( Graphics window )
  {
    // // FOR TESTING
    // window.setColor(Color.red);
    // window.fillRect(getX(), getY(), getWidth(), getHeight());

    // draw the enemy
    try
    {
      Scanner file = new Scanner( new File("Enemy.txt") );
      file.nextLine();
      if (getDirection()) {
        file.nextLine();
      }
      String filePath = file.nextLine();
      URL url = getClass().getResource(filePath);
      Image image = ImageIO.read(url);
      window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
  }

  public String toString()
  {
    return super.toString() + " " + getSpeed();
  }
}
