package client_app.view;

import com.github.sarxos.webcam.WebcamPanel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;

public class HomeView {

    private JFrame mainframe;
    private JButton identButton;
    private WebcamPanel mainPanel;

    public HomeView(){
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }
        this.mainframe = new JFrame ("MSPR CLIENT APP");
        this.identButton = new JButton ("S'identifier");
        this.mainframe.setMinimumSize (new Dimension(800, 600));

        this.identButton.setMaximumSize (new Dimension (50, 100));

        // on hover, button will change to a light gray
        MaterialUIMovement.add (this.identButton, MaterialColors.GRAY_100);
    }
    public void createAndShowGUI() {

        this.mainframe.pack ();
        this.mainframe.setVisible (true);

        this.mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initMainPanel() {
        this.mainPanel.add (this.identButton);
        this.mainframe.add (this.mainPanel, BorderLayout.CENTER);
    }
    public JFrame getMainframe() {
        return mainframe;
    }

    public JButton getIdentButton() {
        return identButton;
    }

    public void setMainframe(JFrame mainframe) {
        this.mainframe = mainframe;
    }

    public void setIdentButton(JButton identButton) {
        this.identButton = identButton;
    }

    public WebcamPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(WebcamPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
