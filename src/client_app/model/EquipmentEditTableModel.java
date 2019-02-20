package client_app.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EquipmentEditTableModel extends AbstractTableModel {

    private List<EquipmentEdit> equipments;
    private final String[] headers = {"Identifiant", "Nom", "Quantité disponible"};

    public EquipmentEditTableModel(List<EquipmentEdit> equipments) {
        this.equipments = equipments;
    }


    @Override
    public int getRowCount() {
        return this.equipments.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return equipments.get(rowIndex).getName();
            case 1:
                return equipments.get(rowIndex).getQuantity();
            case 2:
                return equipments.get(rowIndex).getQuantity();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        EquipmentEdit EquipmentEdit = this.equipments.get(rowIndex);
        switch (columnIndex) {
            case 2:
                EquipmentEdit.setQuantity((Integer) value);
                break;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        switch (col) {
            case 2:
                return true;
            default:
                return false;
        }
    }


    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }
}
