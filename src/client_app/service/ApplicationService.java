package client_app.service;

import client_app.controller.Controller;
import client_app.controller.EquipementController;
import client_app.controller.HomeController;

import javax.swing.*;

public class ApplicationService {
    private static ApplicationService applicationService;
    private JFrame mainFrame;
    private Controller currentController;
    private String token;

    public ApplicationService() {
        this.mainFrame = new JFrame("MSPR CLIENT APP");
        initHomeController();
    }

    private void initCurrentController(Controller controller) {
        this.currentController = controller;
        this.currentController.initController(this.mainFrame);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void initHomeController(){
        initCurrentController(new HomeController());
    }
    public void initEquipementController(){
        initCurrentController(new EquipementController());
    }

    public void auth(String token){
        this.token = token;
        initEquipementController();
    }

    public boolean isAuthenticated() {
        if (token != null) return true;
        else return false;
    }

    public static ApplicationService getInstance() {
        if (applicationService == null) {
            applicationService = new ApplicationService();
        }
        return applicationService;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public String getToken() {
        return token;
    }
}
