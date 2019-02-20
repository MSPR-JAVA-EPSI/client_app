package client_app.service;

import client_app.controller.Controller;
import client_app.controller.EquipmentController;
import client_app.controller.HomeController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ApplicationService {
    private static ApplicationService applicationService;
    private JFrame mainFrame;
    private Controller currentController;
    private String token;
    private String fullName;
    private String image;
    private static Map<Integer, String> errors;
    private static boolean administrator;

    public ApplicationService() {
        this.errors = new HashMap<>();
        this.errors.put(204, "Nous n'avons pas pu trouver vos identifiants dans la base de donnée");
        this.errors.put(403, "Vous n'etes pas autorisé à accéder a cette partie de l'application");
        this.errors.put(404, "Le serveur n'est pas accessible");
        this.errors.put(500, "Une erreur interne au serveur est survenue");

        this.token = "";
        this.administrator = false;

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
        initCurrentController(new EquipmentController());
    }

    public void auth(String token, String fullName, String image, boolean administrator){
        this.token = token;
        this.fullName = fullName;
        this.image = image;
        System.out.println(token);
        System.out.println(fullName);
        System.out.println(image);
        this.administrator = administrator;
        initEquipementController();
    }

    public boolean isAuthenticated() {
        if (token != null) return true;
        else return false;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return image;
    }

    public static boolean isAdministrator() {
        return administrator;
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
