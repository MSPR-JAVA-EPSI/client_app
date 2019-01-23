package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInEquipement;
import client_app.dto.in.DtoInEquipementList;
import client_app.dto.in.DtoInError;
import client_app.dto.out.DtoOutBorrowItem;
import client_app.dto.out.DtoOutBorrowItems;
import client_app.dto.out.DtoOutEquipement;
import client_app.service.ApplicationService;
import client_app.service.HttpService;
import client_app.utils.EquipementItemComponent;
import client_app.view.EquipementView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipementController implements Controller {

    private EquipementView equipementView;
    private ArrayList<EquipementItemComponent> equipements;

    public EquipementController() {
        this.equipements = new ArrayList<>();
    }

    public void initController(JFrame mainFrame) {
        this.equipementView = new EquipementView(mainFrame);
        this.equipementView.initMainPanel();
        this.equipementView.getIdentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipementView.closeView();
                ApplicationService.getInstance().initHomeController();
            }
        });
        this.equipementView.createAndShowGUI();
        fetchEquipementList();
    }

    public void fetchEquipementList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        DtoOutEquipement dtoOutEquipement = new DtoOutEquipement(ApplicationService.getInstance().getToken());
        String body = gson.toJson(dtoOutEquipement);
        try {
            Response response = HttpService.getInstance().request("/item/getAll", headers, body);
            if (response.getStatus() != 200) {
                DtoInError dtoInError = gson.fromJson(response.getBody(), DtoInError.class);
                System.out.println("ERROR: " + response.getStatus() + " " + dtoInError.toString());
                showError("Une erreur s'est produite en récupérant la liste des equipements disponibles, veuillez rééssayer");
            } else {
                System.out.println(response.getBody());
                DtoInEquipementList dtoInEquipementList = gson.fromJson(response.getBody(), DtoInEquipementList.class);

                displayEquipement(dtoInEquipementList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEquipement(DtoInEquipementList dtoInEquipementList) {
        this.equipementView.setGridLayoutRowNumber(dtoInEquipementList.getEquipements().size() + 3);
        dtoInEquipementList.getEquipements().forEach(equipement -> {
            EquipementItemComponent equipementItemComponent = new EquipementItemComponent(equipement.getQuantity(), equipement.getName(), equipement.getId());
            this.equipementView.addComponentToPanelList(equipementItemComponent);
            this.equipements.add(equipementItemComponent);
        });
        JLabel errorMessage = new JLabel("");
        errorMessage.setForeground(MaterialColors.RED_400);
        this.equipementView.setErrorMessage(errorMessage);
        this.equipementView.addComponentToPanelList(errorMessage);
        JButton sendButton = new JButton("Confirmer la saisie");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<DtoOutBorrowItem> dtoOutBorrowItems = new ArrayList<>();
                equipements.forEach(equipement -> {
                    dtoOutBorrowItems.add(new DtoOutBorrowItem(Integer.parseInt(equipement.getTextField().getText()), equipement.getId()));
                });
                sendBorrowedItem(new DtoOutBorrowItems(dtoOutBorrowItems));
            }
        });
        sendButton.setBackground(MaterialColors.GRAY_300);
        MaterialUIMovement.add (sendButton, MaterialColors.GRAY_600);
        this.equipementView.setSendButton(sendButton);
        this.equipementView.addComponentToPanelList(sendButton);
    }

    private void showError(String error) {
        if (error == null) error = "Une erreur innatendue s'est produite, veuillez réésayer";
        this.equipementView.getErrorMessage().setText(error);
    }

    private void sendBorrowedItem(DtoOutBorrowItems dtoOutBorrowItems){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(dtoOutBorrowItems);
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/item/borrow", headers, body);
            if (response.getStatus() != 200) {
                DtoInError dtoInError = gson.fromJson(response.getBody(), DtoInError.class);
                System.out.println("ERROR: " + response.getStatus() + " " + dtoInError.toString());
                showError("Une erreur s'est produite en récupérant la liste des equipements disponibles, veuillez rééssayer");
            } else {
               System.out.println("Things went well");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
