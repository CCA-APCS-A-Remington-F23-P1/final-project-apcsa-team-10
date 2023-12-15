import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Block{
  private int xPos;
  private int yPos;
  private int width;
  private int height;

  private Color color;

  public Block() {
    xPos = 100;
    yPos = 150;
    width = 100;
    height = 20;
    color = Color.BLACK;
  }

  //add other block constructors - x , y , width, height, color Hint:Look at BlackTestOne to determine which constructors are needed

  public Block(int xp, int yp, int w, int h, Color c) {
    width = w;
    height = h;
    xPos = xp;
    yPos = yp;
    color = c;
  }

  public Block(int xp, int yp, int w, int h) {
    width = w;
    height = h;
    xPos = xp;
    yPos = yp;
    color = Color.BLACK;
  }

  //add the other set methods Hint: check the Locateable interface
  public void setColor(Color col) {
    color = col;
  }

  public void setPos(int x, int y) {
    xPos = x;
    yPos = y;
  }

  public void setX(int x) {
    xPos = x;
  }

  public void setY(int y) {
    yPos = y;
  }

  public void draw(Graphics window) {
    //uncomment after you write the set and get methods
    window.setColor(color);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public void draw(Graphics window, Color col) {
    window.setColor(col);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public void drawCoin( Graphics window )
    {    
      try
      {
        URL url = getClass().getResource("/gameImages/Coin.png");
        Image image = ImageIO.read(url);
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
      }
      catch(Exception e)
      {
        System.out.println("cannot fetch resource!");
      }
    }

  public void drawHealthBar( Graphics window )
  {    
    try
    {
      URL url = getClass().getResource("/gameImages/healthbar.png");
      Image image = ImageIO.read(url);
      window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
  }

  public void drawStartButton( Graphics window )
  {    
    try
    {
      URL url = getClass().getResource("/gameImages/startbutton.png");
      Image image = ImageIO.read(url);
      window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
  }
  

  public boolean equals(Object obj) {
    Block other = (Block) obj;
    if (other.getWidth() == getWidth() && other.getHeight() == getHeight() && other.getX() == getX() && other.getY() == getY()) {
      return true;
    }
    return false;
  }

  //add the other get methods Hint: check the Locateable interface
  public int getX() {
    return xPos;
  }

  public int getY() {
    return yPos;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Color getColor() {
    return color;
  }

  //add a toString() method  - x , y , width, height, color
  public String toString() {
    return "x: " + xPos + " y: " + yPos + " width: " + width + " height: " + height + " color: " + color;
  }
}