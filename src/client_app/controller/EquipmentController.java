package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInEquipmentList;
import client_app.dto.in.DtoInReturnList;
import client_app.dto.out.DtoOutBorrowItem;
import client_app.dto.out.DtoOutBorrowItems;
import client_app.dto.out.DtoOutReturnItem;
import client_app.dto.out.DtoOutReturnItems;
import client_app.model.EquipmentToBorrow;
import client_app.model.EquipmentToBorrowTableModel;
import client_app.model.EquipmentToReturn;
import client_app.model.EquipmentToReturnTableModel;
import client_app.service.ApplicationService;
import client_app.service.HttpService;
import client_app.view.EquipmentView;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentController implements Controller {

    private EquipmentView equipmentView;
    private List<EquipmentToBorrow> equipmentToBorrowModelList;
    private List<EquipmentToReturn> equipmentsToReturnModelList;

    public void initController(JFrame mainFrame) {
        this.equipmentView = new EquipmentView(mainFrame);
        this.equipmentView.initMainPanel();
        //this.equipmentView.getTabbedPane1().getTabComponentAt(2).setEnabled(ApplicationService.isAdministrator(););
        this.equipmentView.getAuthButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipmentView.closeView();
                ApplicationService.getInstance().initHomeController();
            }
        });
        this.equipmentView.getBorrowButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBorrowButtonPressed();
            }
        });
        this.equipmentView.getReturnButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReturnButtonPressed();
            }
        });
        this.equipmentView.createAndShowGUI();
        fetchBorrowList();
        fetchReturnList();
    }

    public void onBorrowButtonPressed() {
        if (equipmentToBorrowModelList == null) {
            showError("Le tableau est vide ou n'est pas initialisé");
        }
        List<DtoOutBorrowItem> borrowedItems = new ArrayList<>();
        for (EquipmentToBorrow equipmentToBorrow : equipmentToBorrowModelList
        ) {
            if (equipmentToBorrow.getBorrowedQuantity() > equipmentToBorrow.getQuantity()) {
                showError("Vous empruntez plus de " + equipmentToBorrow.getName() + " qu'il n'y en a de disponible");
                return;
            } else if (equipmentToBorrow.getBorrowedQuantity() > 0) {
                borrowedItems.add(new DtoOutBorrowItem(equipmentToBorrow.getId(), equipmentToBorrow.getBorrowedQuantity()));
            }
        }
        if (borrowedItems.size() <= 0) {
            showError("Vous n'empruntez rien");
            return;
        }
        DtoOutBorrowItems dtoOutBorrowItems = new DtoOutBorrowItems(borrowedItems);
        sendBorrowedItem(dtoOutBorrowItems);
    }

    public void onReturnButtonPressed() {
        if (equipmentsToReturnModelList == null) {
            showError("Le tableau est vide ou n'est pas initialisé");
        }
        List<DtoOutReturnItem> returnedItems = new ArrayList<>();
        for (EquipmentToReturn equipmentToReturn : equipmentsToReturnModelList) {
            if (equipmentToReturn.getReturnedQuantity() > equipmentToReturn.getQuantity()) {
                showError("Vous essayez de retourner plus d'objets que vous n'en avez emprunté");
                return;
            } else if (equipmentToReturn.getReturnedQuantity() > 0) {
                returnedItems.add(new DtoOutReturnItem(equipmentToReturn.getId(), equipmentToReturn.getQuantity()));
            }
        }
        if (returnedItems.size() <= 0) {
            showError("Vous ne retournez rien");
            return;
        }
        DtoOutReturnItems dtoOutReturnItems = new DtoOutReturnItems(returnedItems);
        sendReturnedItems(dtoOutReturnItems);
    }

    public void fetchBorrowList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        try {
            Response response = HttpService.getInstance().request("/item/getAll", headers, null);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getStatus());
            } else {
                System.out.println(response.getBody());
                DtoInEquipmentList dtoInEquipementList = gson.fromJson(response.getBody(), DtoInEquipmentList.class);

                displayEquipementsToBorrow(dtoInEquipementList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchReturnList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        try {
            Response response = HttpService.getInstance().request("/item/getBorrows", headers, null);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getStatus());
            } else {
                System.out.println(response.getBody());
                DtoInReturnList dtoInReturnList = gson.fromJson(response.getBody(), DtoInReturnList.class);

                displayEquipementsToReturn(dtoInReturnList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEquipementsToBorrow(DtoInEquipmentList list) {
        List<EquipmentToBorrow> equipmentToBorrowModelList = new ArrayList<>();
        if (list.getEquipements() != null && !list.getEquipements().isEmpty()) {
            list.getEquipements().forEach(equipement -> {
                equipmentToBorrowModelList.add(new EquipmentToBorrow(equipement.getId(), equipement.getName(), equipement.getQuantity()));
            });
        }
        this.equipmentToBorrowModelList = equipmentToBorrowModelList;
        this.equipmentView.setBorrowTable(new EquipmentToBorrowTableModel(equipmentToBorrowModelList));
    }

    public void displayEquipementsToReturn(DtoInReturnList list) {
        List<EquipmentToReturn> equipmentsToReturnModelList = new ArrayList<>();
        if (list.getReturnList() != null && !list.getReturnList().isEmpty()) {
            list.getReturnList().forEach(equipement -> {
                equipmentsToReturnModelList.add(new EquipmentToReturn(equipement.getId(), equipement.getName(), equipement.getQuantity()));
            });
        }
        this.equipmentsToReturnModelList = equipmentsToReturnModelList;
        this.equipmentView.setReturnTable(new EquipmentToReturnTableModel(equipmentsToReturnModelList));
    }

    private void sendBorrowedItem(DtoOutBorrowItems dtoOutBorrowItems) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(dtoOutBorrowItems);
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/item/borrow", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getStatus());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Votre emprunt a bien été saisi", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendReturnedItems(DtoOutReturnItems dtoOutReturnItems) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(dtoOutReturnItems);
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/item/returnBorrows", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getStatus());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Votre retour a bien été saisi", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showError(int code) {
        String message = ApplicationService.getErrorFromCode(code);
        JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showError(String message) {
        if (message == null) {
            message = "Une erreur inconnue est survenue";
        }
        JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
