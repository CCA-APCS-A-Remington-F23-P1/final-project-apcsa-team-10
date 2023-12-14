import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Player extends MovingThing implements Collideable
{
  private int speed;
  private Image image;

  public Player()
  {
    this(10,10,10,10,10);
  }

  public Player(int x, int y)
  {
    //add code here
    this(x,y,10,30,10);
  }

  public Player(int x, int y, int s)
  {
    //add code here
    this(x,y,10,30,s);
  }

  // all ctors call this ctor
  public Player(int x, int y, int w, int h, int s)
  {
    super(x, y, w, h);
    speed=s;
    try
    {
      URL url = getClass().getResource("/gameImages/Knight.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
  }

  public void setSpeed(int s)
  {
    //add more code
    speed = s;
  }

  public int getSpeed()
  {
    //add more code
    return speed;
  }

  public void move(String direction)
  {
    //add code here
    if(direction.equals("LEFT")){
      setX(getX()-speed);
    }
    if(direction.equals("RIGHT")){
      setX(getX()+speed);
    }
    if(direction.equals("UP")){
      setY(getY()-speed*2);
    }
    if(direction.equals("DOWN")){
      setY(getY()+speed);
    }
  }

  public boolean collides(Block platform){
    if(platform.getX()+platform.getWidth()>=getX() && platform.getX()<=getX()+getWidth() && platform.getY()+platform.getHeight()>=getY() && platform.getY()<=getY()+getHeight()){
      return true; 
    }
    return false; 
  }

  // PLATFORM COLLISIONS

  public boolean didCollideLeft(Object obj) {
    Block other = (Block) obj;
    return other.getY()>getY() && other.getY()<getY()+getHeight() && other.getX()<=getX()+getWidth() && 
        other.getX()>= getX()+getWidth();
  }

  public boolean didCollideRight(Object obj) {
    Block other = (Block) obj;
    return other.getY()>getY() && other.getY()<getY()+getHeight() && other.getX()+other.getWidth()>=getX() && 
      other.getX()+other.getWidth() <= getX()+getWidth();

    // return (getX() == other.getX() + other.getWidth())
    // && (getY() < other.getY() + other.getHeight())
    // && (getY() + getHeight() > other.getY());
  }

  public boolean didCollideTop(Object obj) {
      Block other = (Block) obj;

      if(getX()>other.getX() && getX()<other.getX()+other.getWidth() && getY()+getHeight()>=other.getY() && 
        getY()+getHeight() <= other.getY()+other.getHeight()/2){return true;} 

    if(getX()+getWidth()>other.getX() && getX()+getWidth()<other.getX()+other.getWidth() && getY()+getHeight()>=other.getY() && 
      getY()+getHeight() <= other.getY()+other.getHeight()/2){return true;} 

    return false; 

    // return (getY() + getHeight() == other.getY())
    // && (getX() < other.getX() + other.getWidth())
    // && (getX() + getWidth() > other.getX());
  }

  public boolean didCollideBottom(Object obj) {
      Block other = (Block) obj;

    if(getX()>other.getX() && getX()<other.getX()+other.getWidth() && getY()<=other.getY()+other.getHeight() && 
      getY()>= other.getY()+other.getHeight()/2){return true;} 

    if(getX()+getWidth()>other.getX() && getX()+getWidth()<other.getX()+other.getWidth() && getY()<=other.getY()+other.getHeight() && 
    getY()>= other.getY()+other.getHeight()/2){return true;} 
    return false; 
  }

  // WALL COLLISIONS

  public boolean didCollideWallRight(Object obj) {
      Block other = (Block) obj;
      return getX() == other.getX() + other.getWidth() && getY() >= other.getY() && getY() <= other.getY() + other.getHeight();
  }

  public boolean didCollideWallLeft(Object obj) {
      Block other = (Block) obj;
      return getX() + getWidth() == other.getX() && getY() >= other.getY() && getY() <= other.getY() + other.getHeight();
  }


  public void draw( Graphics window )
  {    //window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    window.setColor(Color.BLUE); 
    window.fillRect(getX(),getY(),getWidth(),getHeight());
  }

  public String toString()
  {
    return super.toString() + getSpeed();
  }
}
