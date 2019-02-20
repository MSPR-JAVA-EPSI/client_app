package client_app.controller;

import client_app.dto.Response;
import client_app.dto.in.DtoInEquipmentList;
import client_app.dto.in.DtoInGuardiansList;
import client_app.dto.in.DtoInReturnList;
import client_app.dto.out.*;
import client_app.model.*;
import client_app.service.ApplicationService;
import client_app.service.HttpService;
import client_app.utils.EncodeToString;
import client_app.view.EquipmentView;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/*
 * Cette classe est trop grande et certaines fonctions pourraient etre déplacées dans un service
 * */

public class EquipmentController implements Controller {

    private EquipmentView equipmentView;
    private List<EquipmentToBorrow> equipmentToBorrowModelList;
    private List<EquipmentToReturn> equipmentsToReturnModelList;
    private List<GuardianEdit> guardianEditModelList;
    private List<EquipmentEdit> equipmentEditModelList;
    private Webcam webcam;


    public void initController(JFrame mainFrame) {
        this.equipmentView = new EquipmentView(mainFrame);
        this.equipmentView.initMainPanel();
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
        this.equipmentView.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchBorrowList();
                fetchReturnList();
                if (ApplicationService.getInstance().isAdministrator()) {
                    fetchGuardiansList();
                    fetchEquipmentEditList();
                }
            }
        });

        this.equipmentView.getRemoveUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemoveUserButtonPressed();
            }
        });

        this.equipmentView.getUpdateUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateGuardianPressed();
            }
        });

        this.equipmentView.getAddUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddGardianPressed();
            }
        });

        this.equipmentView.getModifyEquipmentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateEquipmentPressed();
            }
        });

        this.equipmentView.getRemoveEquipmentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemoveEquipmentPressed();
            }
        });

        this.equipmentView.getAddItemButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddEquipmentPressed();
            }
        });
        this.equipmentView.createAndShowGUI();
        this.equipmentView.getGuardianName().setText(ApplicationService.getInstance().getFullName());
        this.webcam = Webcam.getDefault();
        fetchBorrowList();
        fetchReturnList();
        if (ApplicationService.getInstance().isAdministrator()) {
            fetchGuardiansList();
            fetchEquipmentEditList();
        }else {
            this.equipmentView.getTabbedPane1().remove(2);
        }
    }

    private String takeAPicture() {
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setMirrored(true);
        try {
            BufferedImage picture = webcam.getImage();
            String encodedFile = EncodeToString.encodeToString(picture, "png");
            System.out.println(encodedFile);
            return encodedFile;
        } catch (Exception err) {
            err.printStackTrace();
            return "";
        } finally {
            webcam.close();
        }
    }

    public void onBorrowButtonPressed() {
        if (equipmentToBorrowModelList == null) {
            showError("Le tableau est vide ou n'est pas initialisé");
        }
        List<DtoOutBorrowItem> borrowedItems = new ArrayList<>();
        for (EquipmentToBorrow equipmentToBorrow : equipmentToBorrowModelList) {
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
        fetchBorrowList();
    }

    public void onUpdateGuardianPressed() {
        if (guardianEditModelList == null || guardianEditModelList.size() <= 0) {
            showError("Le tableau est vide ou n'est pas initialisé");
            return;
        }
        List<DtoOutEditGuardian> guardiansEdited = new ArrayList<>();
        for (GuardianEdit guardian : guardianEditModelList) {
            guardiansEdited.add(new DtoOutEditGuardian(guardian.getId(), guardian.getIdentifier(), guardian.isAdministrator(), guardian.getFullName()));
        }
        if (guardiansEdited.size() <= 0){
            showError("Vous n'avez rien modifié");
            return;
        }
        DtoOutEditGuardians dtoOutEditGuardians = new DtoOutEditGuardians(guardiansEdited);
        sendEditedGuardians(dtoOutEditGuardians);
        fetchGuardiansList();
    }

    public void onUpdateEquipmentPressed() {
        if(equipmentEditModelList == null || equipmentEditModelList.size() <= 0){
            showError("Le tableau est vide ou n'est pas initialisé");
            return;
        }
        List<DtoOutEditEquipment> equipmentsEdited = new ArrayList<>();
        for(EquipmentEdit equipmentEdit: equipmentEditModelList){
            equipmentsEdited.add(new DtoOutEditEquipment(equipmentEdit.getId(), equipmentEdit.getName(), equipmentEdit.getQuantity()));
        }
        if(equipmentsEdited.size() <= 0){
            showError("Vous n'avez rien modifié");
            return;
        }
        DtoOutEditEquipments dtoOutEditEquipments = new DtoOutEditEquipments(equipmentsEdited);
        sendEditedEquipments(dtoOutEditEquipments);
        fetchEquipmentEditList();
    }

    public void onAddGardianPressed(){
        String name = this.equipmentView.getAddUserInput().getText();
        if(name == null){
            showError("Vous devez retrer un nom d'utilisateur");
            return;
        }
        DtoOutAddGuardian dtoOutAddGuardian = new DtoOutAddGuardian(name,takeAPicture(), name, false);
        sendAddGuardian(dtoOutAddGuardian);
        fetchGuardiansList();
    }

    public void onAddEquipmentPressed(){
        String name = this.equipmentView.getAddEquipmentNameInput().getText();
        int quantity = Integer.parseInt(this.equipmentView.getAddEquipmentQuantityInput().getText());
        if(name == null || quantity < 0){
            showError("Vous devez retrer un nom d'objet et une quantité valide");
            return;
        }
        DtoOutAddEquipment dtoOutAddEquipment = new DtoOutAddEquipment(name,quantity);
        sendAddEquipment(dtoOutAddEquipment);
        fetchEquipmentEditList();
    }

    public void onReturnButtonPressed() {
        if (equipmentsToReturnModelList == null) {
            showError("Le tableau est vide ou n'est pas initialisé");
            return;
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
        fetchReturnList();
    }

    public void onRemoveUserButtonPressed(){
        if(this.guardianEditModelList == null){
            showError("Le tableau de gardiens est vide");
            return;
        }
        int selectedRow = this.equipmentView.getUserManagerTable().getSelectedRow();
        List<DtoOutRemoveGuardian> list = new ArrayList<>();
        list.add(new DtoOutRemoveGuardian(this.guardianEditModelList.get(selectedRow).getId()));
        sendRemoveGuardian(list);
        fetchGuardiansList();
    }

    public void onRemoveEquipmentPressed(){
        if(this.equipmentEditModelList == null){
            showError("Le tableau d'équipements est vide");
            return;
        }
        int selectedRow = this.equipmentView.getEquipmentEditTable().getSelectedRow();
        List<DtoOutRemoveEquipment> list = new ArrayList<>();
        list.add(new DtoOutRemoveEquipment(this.equipmentEditModelList.get(selectedRow).getId()));
        sendRemoveEquipment(list);
        fetchEquipmentEditList();
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
                showError(response.getBody());
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
                showError(response.getBody());
            } else {
                System.out.println(response.getBody());
                DtoInReturnList dtoInReturnList = gson.fromJson(response.getBody(), DtoInReturnList.class);

                displayEquipementsToReturn(dtoInReturnList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchGuardiansList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        try {
            Response response = HttpService.getInstance().request("/admin/user/getAll", headers, null);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                System.out.println(response.getBody());
                DtoInGuardiansList dtoInGuardiansList = gson.fromJson(response.getBody(), DtoInGuardiansList.class);

                displayGuardians(dtoInGuardiansList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchEquipmentEditList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        try {
            Response response = HttpService.getInstance().request("/admin/item/getAll", headers, null);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                System.out.println(response.getBody());
                DtoInEquipmentList dtoInEquipmentList = gson.fromJson(response.getBody(), DtoInEquipmentList.class);

                displayEquipmentEditList(dtoInEquipmentList);
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

    public void displayGuardians(DtoInGuardiansList list) {
        List<GuardianEdit> guardianEditModelList = new ArrayList<>();
        if (list.getGuardians() != null && !list.getGuardians().isEmpty()) {
            list.getGuardians().forEach(guardian -> {
                guardianEditModelList.add(new GuardianEdit(guardian.getId(), guardian.getName(), guardian.getFullName(), guardian.isAdministrator()));
            });
        }
        this.guardianEditModelList = guardianEditModelList;
        this.equipmentView.setUserManagerTable(new GuardianEditTableModel(guardianEditModelList));
    }

    public void displayEquipmentEditList(DtoInEquipmentList list) {
        List<EquipmentEdit> equipmentEditModelList = new ArrayList<>();
        if (list.getEquipements() != null && !list.getEquipements().isEmpty()) {
            list.getEquipements().forEach(equipment -> {
                equipmentEditModelList.add(new EquipmentEdit(equipment.getId(), equipment.getName(), equipment.getQuantity()));
            });
        }
        this.equipmentEditModelList = equipmentEditModelList;
        this.equipmentView.setEquipmentManagerScrollPane(new EquipmentEditTableModel(equipmentEditModelList));
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
                showError(response.getBody());
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
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Votre retour a bien été saisi", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEditedGuardians(DtoOutEditGuardians dtoOutEditGuardians) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(dtoOutEditGuardians);
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/user/edit", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Votre modification a bien été saisie", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEditedEquipments(DtoOutEditEquipments dtoOutEditEquipments){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(dtoOutEditEquipments);
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/item/edit", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Votre modification a bien été saisie", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAddGuardian(DtoOutAddGuardian dtoOutAddGuardian){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        List<DtoOutAddGuardian> guardians = new ArrayList<>();
        guardians.add(dtoOutAddGuardian);
        String body = gson.toJson(new DtoOutAddGuardians(guardians));
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/user/new", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Nouveau guardien ajouté", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  sendAddEquipment(DtoOutAddEquipment equipment){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        List<DtoOutAddEquipment> list = new ArrayList<>();
        list.add(equipment);
        String body = gson.toJson(new DtoOutAddEquipments(list));
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/item/new", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Nouvel objet ajouté", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRemoveGuardian(List<DtoOutRemoveGuardian> list){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(new DtoOutRemoveGuardians(list));
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/user/remove", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Gardien supprimé", "Opération réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRemoveEquipment(List<DtoOutRemoveEquipment> equipments){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApplicationService.getInstance().getToken());
        Gson gson = new Gson();
        String body = gson.toJson(new DtoOutRemoveEquipments(equipments));
        System.out.println(body);
        try {
            Response response = HttpService.getInstance().request("/admin/item/remove", headers, body);
            if (response.getStatus() != 200) {
                System.out.println("ERROR: " + response.getStatus());
                showError(response.getBody());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Equipement supprimé", "Opération réussie",
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
