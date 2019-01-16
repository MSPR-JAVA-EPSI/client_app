package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInIdentification;
import client_app.dto.out.DtoOutIdentification;
import client_app.service.HttpService;
import client_app.utils.EncodeToString;
import client_app.view.HomeView;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.gson.Gson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

    private HomeView homeView;
    private Webcam webcam;

    public HomeController() {
        this.homeView = new HomeView();
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
                if(picture == null) {
                    System.out.println("ERROR: no picture taken");
                    return;
                }
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                DtoOutIdentification dtoOutIdentification = new DtoOutIdentification(picture);
                Gson gson = new Gson();
                String body = gson.toJson(dtoOutIdentification);
                System.out.println(body);
                try {
                    Response response = HttpService.getInstance().request("/auth", headers, body);
                    System.out.println(response.getBody());
                    DtoInIdentification dtoInIdentification = gson.fromJson(response.getBody(), DtoInIdentification.class);
                    System.out.println(dtoInIdentification.toString());
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
            return encodedFile;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }
}
