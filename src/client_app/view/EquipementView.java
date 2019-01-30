package client_app.view;

import client_app.utils.EquipementItemComponent;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EquipementView {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton identButton;
    private ArrayList<JPanel> textFieldPanels;
    private JButton sendButton;
    private JLabel errorMessage;

    public EquipementView(JFrame mainFrame) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainFrame = mainFrame;
        this.mainFrame.setLayout(new GridLayout(1, 1));
        this.mainPanel = new JPanel();
        this.identButton = new JButton("Retour à l'identification");
        this.textFieldPanels = new ArrayList<>();

        Color blueColor = new Color(55,158,193);

        this.identButton.setBackground(MaterialColors.GRAY_300);
        this.identButton.setForeground(blueColor);
        MaterialUIMovement.add(this.identButton, MaterialColors.GRAY_600);
    }

    public void createAndShowGUI() {
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    public void setGridLayoutRowNumber(int rowNumber) {
        this.mainFrame.setLayout(new GridLayout(rowNumber, 1));
    }

    public void addComponentToPanelList(EquipementItemComponent equipementItemComponent) {
        JPanel panel = new JPanel();
        panel.add(equipementItemComponent.getLabel());
        panel.add(equipementItemComponent.getTextField());
        textFieldPanels.add(panel);
        this.mainFrame.add(panel);
    }

    public void addComponentToPanelList(Component component) {
        JPanel panel = new JPanel();
        panel.add(component);
        textFieldPanels.add(panel);
        this.mainFrame.add(panel);
    }

    public void closeView() {
        this.mainFrame.remove(this.mainPanel);
        this.textFieldPanels.forEach(panel -> {
            mainFrame.remove(panel);
        });
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }


    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(JLabel errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void initMainPanel() {
        this.mainFrame.add(this.mainPanel);
        this.mainPanel.add(identButton);
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getIdentButton() {
        return identButton;
    }

}
