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

  private Block startBut = new Block(MENU_WIDTH / 2 - 50, MENU_HEIGHT /2 - 50, 100, 50, Color.GREEN);

  public MainMenu(String[] args) {
    super("Heroic Quest - Main Menu");
    setSize(MENU_WIDTH, MENU_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    URL imageURL = getClass().getResource("/gameImages/mainbackground.jpg");
    Image backgroundImage = new ImageIcon(imageURL).getImage();

    URL imageURL2 = getClass().getResource("/gameImages/startbutton.png");
    Image button1= new ImageIcon(imageURL2).getImage();
    
   
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    JButton startBut = createButton("", "/gameImages/startbutton.png");
    JButton endBut = createButton("Exit", "/gameImages/exitbutton.jpg");

    startBut.setAlignmentX(Component.CENTER_ALIGNMENT);
    endBut.setAlignmentX(Component.CENTER_ALIGNMENT);

    startBut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        GameRunner.main(args);
      }
    });

    endBut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    setContentPane(new JPanel() {
      @Override
      protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        // g.drawImage(button1, 200, 0, 136, 56, this);
     
        

      }
    });

    startBut.setAlignmentX(Component.CENTER_ALIGNMENT);
    endBut.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(Box.createVerticalGlue());
    add(startBut);
    add(Box.createVerticalStrut(10));
    add(endBut);
    add(Box.createVerticalGlue());
  

    setLocationRelativeTo(null);
    this.gameArgs = args;
  }

  private JButton createButton(String text, String iconPath){
    JButton button = new JButton(text);
    URL iconURL = getClass().getResource(iconPath);
    if(iconURL != null){
      ImageIcon icon = new ImageIcon(iconURL);
      button.setIcon(icon);
    }
    return button;
  }
}