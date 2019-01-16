package client_app.view;

import client_app.utils.CheckBoxList;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class EquipementView {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton identButton;
    private CheckBoxList equipementList;

    public EquipementView(JFrame mainFrame) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainFrame = mainFrame;
        this.mainPanel = new JPanel();
        this.identButton = new JButton("S'identifier");
        this.equipementList= new CheckBoxList();
    }

    public void createAndShowGUI() {
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    public void initMainPanel() {
        this.mainFrame.add(this.mainPanel, BorderLayout.CENTER);
        this.mainPanel.add(identButton);
        this.mainPanel.add(equipementList);
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

    public CheckBoxList getEquipementList() {
        return equipementList;
    }
}
