package client_app.view;

import com.github.sarxos.webcam.WebcamPanel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeView {

    private JFrame mainframe;
    private JLabel idLabel;
    private JLabel titleLabel;
    private JTextField idTextField;
    private JButton identButton;
    private WebcamPanel mainPanel;
    private JPanel identAndPicturePanel;
    private JPanel identInputPanel;
    private JPanel picturePanel;
    private JPanel titlePanel;

    public HomeView(JFrame mainFrame) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.mainframe = mainFrame;
        this.identInputPanel = new JPanel();
        this.picturePanel = new JPanel();
        this.identAndPicturePanel = new JPanel();

        Color blueColor = new Color(55,158,193);

        try {
            File file = new File("resources/logo.png");
            System.out.println(file.getPath());
            BufferedImage myPicture = ImageIO.read(file);
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            this.picturePanel.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.idTextField = new JTextField();
        this.idTextField.setPreferredSize(new Dimension(200, 50));
        this.idLabel = new JLabel("Identifiant");
        this.identInputPanel.add(this.idLabel);
        this.identInputPanel.add(this.idTextField);

        this.identButton = new JButton("S'identifier");
        this.identButton.setMaximumSize(new Dimension(50, 100));
        this.identButton.setBackground(MaterialColors.GRAY_300);
        this.identButton.setForeground(blueColor);
        MaterialUIMovement.add(this.identButton, MaterialColors.GRAY_600);
        this.identInputPanel.add(this.identButton);

        this.mainframe.setMinimumSize(new Dimension(1200, 1000));
        this.mainframe.setLayout(new GridLayout(1, 2));
        this.identAndPicturePanel.setLayout(new GridLayout(3,1));
        this.identAndPicturePanel.add(this.picturePanel);
        this.titleLabel = new JLabel("Identification");
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, 50));
        this.titleLabel.setForeground(blueColor);
        this.titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.titlePanel = new JPanel();
        this.titlePanel.add(this.titleLabel);
        this.identAndPicturePanel.add(this.titleLabel);
        this.identAndPicturePanel.add(this.identInputPanel);
        this.mainframe.add(identAndPicturePanel);
    }

    public void createAndShowGUI() {

        this.mainframe.pack();
        this.mainframe.setVisible(true);

        this.mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initMainPanel() {
        this.mainframe.add(this.mainPanel, BorderLayout.CENTER);
    }

    public void closeView() {
        this.mainframe.remove(this.mainPanel);

        this.mainframe.remove(this.identAndPicturePanel);
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
