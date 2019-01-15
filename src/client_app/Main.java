package client_app;

import client_app.view.HomeView;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomeView.createAndShowGUI();
            }
        });
    }
}
