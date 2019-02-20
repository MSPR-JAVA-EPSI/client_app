package client_app.view;

import client_app.model.EquipmentEditTableModel;
import client_app.model.EquipmentToBorrowTableModel;
import client_app.model.EquipmentToReturnTableModel;
import client_app.model.GuardianEditTableModel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;

public class EquipmentView {
    private JTabbedPane tabbedPane1;
    private JPanel equipementPanel;
    private JTable borrowTable;
    private JButton borrowButton;
    private JButton authButton;
    private JButton returnButton;
    private JScrollPane borrowScrollPane;
    private JScrollPane returnScrollPane;
    private JTabbedPane tabbedPane2;
    private JScrollPane equipmentManagerScrollPane;
    private JScrollPane userManagerScrollPane;
    private JTextField AddEquipmentInput;
    private JButton modifyEquipmentButton;
    private JButton removeEquipmentButton;
    private JButton addItemButton;
    private JTextField addUserInput;
    private JLabel guardianName;
    private JButton addUserButton;
    private JButton updateUserButton;
    private JButton removeUserButton;
    private JFrame mainFrame;

    public EquipmentView(JFrame mainFrame) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainFrame = mainFrame;
        this.mainFrame.setLayout(new GridLayout(1, 1));

        Color blueColor = new Color(55,158,193);
        this.authButton.setForeground(blueColor);
        this.authButton.setBackground(MaterialColors.GRAY_300);
        this.borrowButton.setForeground(blueColor);
        this.borrowButton.setBackground(MaterialColors.GRAY_300);
        this.returnButton.setForeground(blueColor);
        this.returnButton.setBackground(MaterialColors.GRAY_300);

        MaterialUIMovement.add(this.authButton, MaterialColors.GRAY_600);
        MaterialUIMovement.add(this.borrowButton, MaterialColors.GRAY_600);
        MaterialUIMovement.add(this.returnButton, MaterialColors.GRAY_600);

        this.addItemButton.setForeground(blueColor);
        this.addItemButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.addItemButton, MaterialColors.GRAY_600);

        this.addUserButton.setForeground(blueColor);
        this.addUserButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.addUserButton, MaterialColors.GRAY_600);

        this.modifyEquipmentButton.setForeground(blueColor);
        this.modifyEquipmentButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.modifyEquipmentButton, MaterialColors.GRAY_600);

        this.updateUserButton.setForeground(blueColor);
        this.updateUserButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.updateUserButton, MaterialColors.GRAY_600);

        this.removeEquipmentButton.setForeground(blueColor);
        this.removeEquipmentButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.removeEquipmentButton, MaterialColors.GRAY_600);

        this.removeUserButton.setForeground(blueColor);
        this.removeUserButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add(this.removeUserButton, MaterialColors.GRAY_600);
    }

    public void createAndShowGUI() {
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    public void initMainPanel() {
        this.mainFrame.add(this.equipementPanel);
    }

    public void setBorrowTable(EquipmentToBorrowTableModel model) {
        this.borrowScrollPane.setViewportView(new JTable(model));
    }
    public void setReturnTable(EquipmentToReturnTableModel model) {
        this.returnScrollPane.setViewportView(new JTable(model));
    }

    public void closeView() {
        this.mainFrame.remove(this.equipementPanel);
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    public JButton getBorrowButton() {
        return borrowButton;
    }

    public JButton getAuthButton() {
        return authButton;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JButton getModifyEquipmentButton() {
        return modifyEquipmentButton;
    }

    public JButton getRemoveEquipmentButton() {
        return removeEquipmentButton;
    }

    public JButton getAddItemButton() {
        return addItemButton;
    }

    public JButton getAddUserButton() {
        return addUserButton;
    }

    public JButton getUpdateUserButton() {
        return updateUserButton;
    }

    public JButton getRemoveUserButton() {
        return removeUserButton;
    }

    public JLabel getGuardianName() {
        return guardianName;
    }

    public JScrollPane getEquipmentManagerScrollPane() {
        return equipmentManagerScrollPane;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public void setUserManagerTable(GuardianEditTableModel model) {
        this.userManagerScrollPane.setViewportView(new JTable(model));
    }

    public void setEquipmentManagerScrollPane(EquipmentEditTableModel model) {
        this.equipmentManagerScrollPane.setViewportView(new JTable(model));
    }
}
