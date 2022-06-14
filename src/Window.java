import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

     private static final long serialVersionUID = -240840600533728354L;

     public Window(int width, int height, String title, FaceGenerator faceGenerator){
         JFrame frame = new JFrame(title);

         frame.setPreferredSize (new Dimension(width, height));
         frame.setMaximumSize   (new Dimension(width, height));
         frame.setMinimumSize   (new Dimension(width, height));

         frame.setResizable(true);
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         frame.add(faceGenerator);
         frame.setVisible(true);
         faceGenerator.start();
     }

}