import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      MainMenu menu = new MainMenu(args);
      menu.setVisible(true);
    });
  }
}

class MainMenu extends JFrame {

  private static final int MENU_WIDTH = 800;
  private static final int MENU_HEIGHT = 600;
  private String[] gameArgs;

  public MainMenu(String[] args) {
    super("Heroic Quest - Main Menu");
    setSize(MENU_WIDTH, MENU_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    URL imageURL = getClass().getResource("/gameImages/mainbackground.jpg");
    Image backgroundImage = new ImageIcon(imageURL).getImage();
    setContentPane(new JPanel() {
      @Override
      protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
      }
    });
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    JButton startButton = new JButton("Start Game");
    JButton endButton = new JButton("Exit Game");
    JButton menuSelectButton = new JButton("Menu Select");

    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    menuSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        GameRunner.main(1);
      }
    });

    menuSelectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        menuSelect.main(args); 
      }
    });

    endButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    add(Box.createVerticalGlue());
    add(startButton);
    add(Box.createVerticalStrut(15));
    add(endButton);
    add(Box.createVerticalGlue());
    add(menuSelectButton);
    add(Box.createVerticalStrut(15));
    add(Box.createVerticalGlue());

    setLocationRelativeTo(null);
    this.gameArgs = args;
  }
}