package client_app.controller;

import client_app.view.HomeView;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
                takeAPicture();
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

    private void takeAPicture() {
        try {
            ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
