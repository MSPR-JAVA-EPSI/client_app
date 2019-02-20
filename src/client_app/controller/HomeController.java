package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInIdentification;
import client_app.dto.out.DtoOutIdentification;
import client_app.service.ApplicationService;
import client_app.service.HttpService;
import client_app.utils.EncodeToString;
import client_app.view.HomeView;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class HomeController implements Controller {

    private HomeView homeView;
    private Webcam webcam;

    public HomeController() {

    }

    public void initController(JFrame mainFrame) {
        this.homeView = new HomeView(mainFrame);
        this.homeView.createAndShowGUI();
        this.webcam = Webcam.getDefault();
        this.homeView.getIdentButton().addActionListener(onIdentButtonClick());
        displayWebcamImage();
    }

    public ActionListener onIdentButtonClick() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String picture = takeAPicture();
                if (picture == null) {
                    System.out.println("ERROR: no picture taken");
                    showError("Erreur: la photo n'a pas pu etre prise");
                    return;
                }
                String id = homeView.getIdTextField().getText();
                System.out.println(id);
                if (id != null && id.equals("")) {
                    System.out.println("ERROR: No id entered");
                    showError("Veuillez entrer un identifiant");
                    return;
                }
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                DtoOutIdentification dtoOutIdentification = new DtoOutIdentification(picture, id);
                Gson gson = new Gson();
                String body = gson.toJson(dtoOutIdentification);
                try {
                    Response response = HttpService.getInstance().request("/auth", headers, body);
                    if (response.getStatus() != 200) {
                        System.out.println("ERROR: " + response.getStatus());
                        showError(response.getStatus());
                    } else {
                        DtoInIdentification dtoInIdentification = gson.fromJson(response.getBody(), DtoInIdentification.class);
                        authenticate(dtoInIdentification.getToken(), dtoInIdentification.getFullName(), dtoInIdentification.getImage(), dtoInIdentification.isAdministrator());
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        };
    }

    private void displayWebcamImage() {
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setMirrored(true);
        this.homeView.setMainPanel(panel);
        this.homeView.initMainPanel();
    }

    private String takeAPicture() {
        try {
            BufferedImage picture = webcam.getImage();
            String encodedFile = EncodeToString.encodeToString(picture, "png");
            System.out.println(encodedFile);
            return encodedFile;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    private void authenticate(String token, String fullName, String image, boolean isAdministrator) {
        this.homeView.getMainPanel().stop();
        this.homeView.closeView();
        ApplicationService.getInstance().auth(token, fullName, image, isAdministrator);
    }

    private void showError(int code) {
        String message = ApplicationService.getErrorFromCode(code);
        JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showError(String message) {
        if (message == null) {
            message = "Une erreur inconnue est survenue";
        }
        JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
