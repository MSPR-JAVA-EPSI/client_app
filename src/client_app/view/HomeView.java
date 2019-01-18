package client_app.view;

import com.github.sarxos.webcam.WebcamPanel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;

public class HomeView {

    private JFrame mainframe;
    private JLabel idLabel;
    private JTextField idTextField;
    private JButton identButton;
    private WebcamPanel mainPanel;
    private JPanel identInputPanel;

    public HomeView(JFrame mainFrame){
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }
        this.mainframe = mainFrame;

        this.idTextField = new JTextField();
        this.idTextField.setPreferredSize(new Dimension(200, 50));
        this.idLabel = new JLabel("Identifiant");
        this.identInputPanel = new JPanel();
        this.identInputPanel.add(this.idLabel);
        this.identInputPanel.add(this.idTextField);

        this.identButton = new JButton ("S'identifier");
        this.identButton.setMaximumSize (new Dimension (50, 100));
        this.identButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add (this.identButton, MaterialColors.GRAY_600);
        this.identInputPanel.add(this.identButton);

        this.mainframe.setMinimumSize (new Dimension(1200, 1000));
        this.mainframe.setLayout(new GridLayout(1, 2));
        this.mainframe.add(this.identInputPanel);
    }
    public void createAndShowGUI() {

        this.mainframe.pack ();
        this.mainframe.setVisible (true);

        this.mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initMainPanel() {
        this.mainframe.add (this.mainPanel, BorderLayout.CENTER);
    }

    public void closeView() {
        this.mainframe.remove(this.mainPanel);
        this.mainframe.remove(this.identInputPanel);
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

    public JTextField getIdTextField() {
        return idTextField;
    }
}
