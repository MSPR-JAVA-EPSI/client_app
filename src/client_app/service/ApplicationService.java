package client_app.service;

import client_app.controller.Controller;
import client_app.controller.EquipementController;
import client_app.controller.HomeController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ApplicationService {
    private static ApplicationService applicationService;
    private JFrame mainFrame;
    private Controller currentController;
    private String token;
    private static Map<Integer, String> errors;

    public ApplicationService() {
        this.errors = new HashMap<>();
        this.errors.put(204, "Nous n'avons pas pu trouver vos identifiants dans la base de donnée");
        this.errors.put(403, "Vous n'etes pas autorisé à accéder a cette partie de l'application");
        this.errors.put(404, "Le serveur n'est pas accessible");
        this.errors.put(500, "Une erreur interne au serveur est survenue");

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

    public static String getErrorFromCode(Integer code) {
        try {
            return errors.get(code);
        }catch(Exception e){
            return "Une erreur inconnue est survenue";
        }
    }
}
