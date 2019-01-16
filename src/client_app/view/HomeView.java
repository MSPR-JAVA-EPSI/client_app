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

    public HomeView(JFrame mainFrame){
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }
        this.mainframe = mainFrame;
        this.identButton = new JButton ("S'identifier");
        this.mainframe.setMinimumSize (new Dimension(1200, 1000));

        this.identButton.setMaximumSize (new Dimension (50, 100));
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

    public void closeView() {
        this.mainframe.remove(this.getMainPanel());
        this.mainframe.validate();
        this.mainframe.repaint();
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
