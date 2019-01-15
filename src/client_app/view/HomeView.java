package client_app.view;

import javax.swing.*;

public class HomeView {
    public static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("HelloWorldSwing");

        JLabel label = new JLabel("Hello World");

        frame.pack();

        frame.setVisible(true);

    }
}
