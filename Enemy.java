import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Enemy extends MovingThing
{
  private int speed;
  private Image image;
  private boolean movingRight;

  public Enemy()
  {
    this(0,0,30,30,0);
    movingRight = true;
  }

  public Enemy(int x, int y)
  {
    super(x, y);
    movingRight = true;
  }

  public Enemy(int x, int y, int s)
  {
    super(x, y);
    speed = s;
    movingRight = true;
  }

  // all ctors call this ctor
  public Enemy(int x, int y, int w, int h, int s)
  {
    super(x, y, w, h);
    speed=s;
    movingRight = true;
    try
    {
      URL url = getClass().getResource("/gameImages/Ogre.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
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

  public boolean isMovingRight() {
    return movingRight;
  }

  public void move(String direction)
  {
    if (direction.equals("UP")) {
      setY(getY() - speed);
    }
    if (direction.equals("DOWN")) {
      setY(getY() + speed);
    }
    if (direction.equals("LEFT")) {
      setX(getX() - speed);
    }
    if (direction.equals("RIGHT")) {
      setX(getX() + speed);
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

  public void draw( Graphics window )
  {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public String toString()
  {
    return super.toString() + " " + getSpeed();
  }
}
