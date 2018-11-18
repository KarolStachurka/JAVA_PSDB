import gui.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("PSDB");
        AdminMainWindow gui = new AdminMainWindow();
        frame.setContentPane(gui.getWindowPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
