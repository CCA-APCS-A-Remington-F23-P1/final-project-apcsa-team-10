import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.*;

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
  private String selection;
  private String[] gameArgs;
  private File file;

  public MainMenu(String[] args) {
    super("Heroic Quest - Main Menu");
    try
    {
      file = new File("MainMenu.txt");
      Scanner scan = new Scanner(file);
      selection = scan.nextLine();
    }
    catch(Exception e)
    {
      System.out.println("cannot fetch resource!");
    }
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
    JButton endButton = new JButton(" Exit Game ");
    JButton menuSelectButton = new JButton("Menu Select");

    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    menuSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          BufferedWriter writer = new BufferedWriter(new FileWriter(file));
          writer.write("Start Game");
          writer.flush();
          dispose();
          GameRunner.main(1);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    menuSelectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          BufferedWriter writer = new BufferedWriter(new FileWriter(file));
          writer.write("Menu Select");
          writer.flush();
          menuSelect.main(args); 
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    endButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          File file = new File("MainMenu.txt");
          BufferedWriter writer = new BufferedWriter(new FileWriter(file));
          writer.write("Exit Game");
          writer.flush();
          System.exit(0);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
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
