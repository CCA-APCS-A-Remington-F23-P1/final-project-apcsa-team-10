import javax.swing.JFrame;
import java.awt.Component;

public class GameRunner extends JFrame
{
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public GameRunner(int level)
  {
    super("Heroic Quest");
    setSize(WIDTH,HEIGHT);
    if (level == 1) {
      level1 lvl1 = new level1();
      ((Component)lvl1).setFocusable(true);
  
      getContentPane().add(lvl1);
    }
    else {
      level2 lvl2 = new level2();
      ((Component)lvl2).setFocusable(true);

      getContentPane().add(lvl2);
    }
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main( String args[] )
  {
    GameRunner run = new GameRunner(1);
  }
}
