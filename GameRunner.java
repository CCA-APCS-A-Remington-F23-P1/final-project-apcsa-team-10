import javax.swing.JFrame;
import java.awt.Component;

public class GameRunner extends JFrame
{
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public GameRunner()
  {
    super("Heroic Quest");
    setSize(WIDTH,HEIGHT);

    level1 lvl1 = new level1();
    ((Component)lvl1).setFocusable(true);

    getContentPane().add(lvl1);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main( String args[] )
  {
    GameRunner run = new GameRunner();
  }
}
