package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInEquipementList;
import client_app.dto.in.DtoInError;
import client_app.service.HttpService;
import client_app.view.EquipementView;
import com.google.gson.Gson;
import javafx.scene.control.CheckBox;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EquipementController implements Controller {
    private EquipementView equipementView;

    public EquipementController() {
    }

    public void initController(JFrame mainFrame) {
        this.equipementView = new EquipementView(mainFrame);
        this.equipementView.initMainPanel();
        this.equipementView.createAndShowGUI();
        fetchEquipementList();
    }

    public void fetchEquipementList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Gson gson = new Gson();
        try {
            Response response = HttpService.getInstance().request("/equipement/list", headers, null);
            if (response.getStatus() != 200) {
                DtoInError dtoInError = gson.fromJson(response.getBody(), DtoInError.class);
                System.out.println("ERROR: " + response.getStatus() + " " + dtoInError.toString());
                showError();
            } else {
                System.out.println(response.getBody());
                DtoInEquipementList dtoInEquipementList = gson.fromJson(response.getBody(), DtoInEquipementList.class);
                System.out.println(dtoInEquipementList);
                displayEquipement(dtoInEquipementList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEquipement(DtoInEquipementList dtoInEquipementList){
        DefaultListModel<JCheckBox> model = new DefaultListModel<>();
        dtoInEquipementList.getEquipements().forEach(equipement -> {
            JCheckBox checkbox = new  JCheckBox(equipement.getName());
            model.addElement(checkbox);
        });
        this.equipementView.getEquipementList().setModel(model);
    }

    private void showError() {
        JOptionPane.showMessageDialog(new JFrame(), "Une erreur innatendue s'est produite, veuillez rééssayer", "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
