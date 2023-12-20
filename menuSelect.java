import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

class menuSelect {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      lvlSelectMenu menu = new lvlSelectMenu(args);
      menu.setVisible(true);
    });
  }
}

class lvlSelectMenu extends JFrame {

  private static final int MENU_WIDTH = 800;
  private static final int MENU_HEIGHT = 600;
  private String[] gameArgs;

  public lvlSelectMenu(String[] args) {
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

    JButton level1button = new JButton("Level 1");
    JButton level2button = new JButton("Level 2");
    JButton level3button = new JButton("Level 3");
    JButton level4button = new JButton("Level 4");
    JButton level5button = new JButton("Level 5");
    JButton backbutton = new JButton("Back");



    level1button.setAlignmentX(Component.CENTER_ALIGNMENT);
    level2button.setAlignmentX(Component.CENTER_ALIGNMENT);
    level3button.setAlignmentX(Component.CENTER_ALIGNMENT);
    level4button.setAlignmentX(Component.CENTER_ALIGNMENT);
    level5button.setAlignmentX(Component.CENTER_ALIGNMENT);
    backbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
   

    level1button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameRunner.main(1);
      }
    });
    level2button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameRunner.main(2);
      }
    });
    level3button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameRunner.main(3);
      }
    });
    level4button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameRunner.main(4);
      }
    });
    level5button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameRunner.main(5);
      }
    });

    backbutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.main(args); 
      }
    });


    add(Box.createVerticalGlue());
    add(level1button);
    add(Box.createVerticalStrut(15));

    add(Box.createVerticalGlue());
    add(level2button);
    add(Box.createVerticalStrut(15));

    add(Box.createVerticalGlue());
    add(level3button);
    add(Box.createVerticalStrut(15));

    add(Box.createVerticalGlue());
    add(level4button);
    add(Box.createVerticalStrut(15));

    add(Box.createVerticalGlue());
    add(level5button);
    add(Box.createVerticalStrut(15));

    add(Box.createVerticalGlue());
    add(backbutton);
    add(Box.createVerticalStrut(15));


    setLocationRelativeTo(null);
    this.gameArgs = args;
  }
}