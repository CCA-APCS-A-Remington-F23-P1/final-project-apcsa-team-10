import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Stone extends MovingThing
{
  private int speed;
  private Color color;
  private Boolean movingRight;

  public Stone()
  {
    this(0,0,0);
    movingRight = null;
  }

  public Stone(int x, int y)
  {
    this(x, y, 0);
    movingRight = null;
  }

  public Stone(int x, int y, int s)
  {
    super(x, y);
    speed = s;
    movingRight = null;
  }

  public Stone(int x, int y, int s, Color color)
  {
    super(x, y);
    speed = s;
    this.color = color;
    movingRight = null;
  }

  public void setSpeed(int s)
  {
    speed = s;
  }

  public int getSpeed()
  {
    return speed;
  }

  public void setDirection(Boolean d) {
    movingRight = d;
  }

  public Boolean getDirection() {
    return movingRight;
  }

  public void draw(Graphics window)
  {
    window.setColor(Color.GRAY);
    window.fillRect(getX(), getY(), 5, 5);
  }

  public void draw(Graphics window, Color color)
  {
      //add code to draw the ammo
      window.setColor(color);
      window.fillRect(super.getX(), super.getY(), 5, 5);
  }


  public void move(String direction)
  {
    if (movingRight) {
      setX(getX() + speed);
    }
    else {
      setX(getX() - speed);
    }
  }

  public String toString()
  {
    return getX() + " " + getY() + " " + getSpeed();
  }
}
