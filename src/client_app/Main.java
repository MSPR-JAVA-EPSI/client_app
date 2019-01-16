package client_app;

import client_app.controller.HomeController;
import client_app.service.ApplicationService;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationService.getInstance();
            }
        });
    }
}
