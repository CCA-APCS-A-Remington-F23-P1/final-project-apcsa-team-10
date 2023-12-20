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
    else if (level == 2){
      level2 lvl2 = new level2();
      ((Component)lvl2).setFocusable(true);

      getContentPane().add(lvl2);
    }
    else if (level == 3){
      level3 lvl3 = new level3();
      ((Component)lvl3).setFocusable(true);

      getContentPane().add(lvl3);
    }
    else if (level == 4){
      level4 lvl4 = new level4();
      ((Component)lvl4).setFocusable(true);

      getContentPane().add(lvl4);
    }
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(int x)
  {
    GameRunner run = new GameRunner(x);
  }
}
