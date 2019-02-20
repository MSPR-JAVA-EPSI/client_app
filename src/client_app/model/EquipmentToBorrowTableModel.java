package client_app.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EquipmentToBorrowTableModel extends AbstractTableModel {

    private List<EquipmentToBorrow> equipmentsToBorrow;
    private final String[] headers = {"Nom", "Quantité disponible", "Quantité empruntée"};

    public EquipmentToBorrowTableModel(List<EquipmentToBorrow> equipmentsToBorrow) {
        this.equipmentsToBorrow = equipmentsToBorrow;
    }


    @Override
    public int getRowCount() {
        return this.equipmentsToBorrow.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return equipmentsToBorrow.get(rowIndex).getName();
            case 1:
                return equipmentsToBorrow.get(rowIndex).getQuantity();
            case 2:
                return equipmentsToBorrow.get(rowIndex).getBorrowedQuantity();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        EquipmentToBorrow equipmentToBorrow = this.equipmentsToBorrow.get(rowIndex);
        switch (columnIndex) {
            case 2:
                equipmentToBorrow.setBorrowedQuantity((Integer) value);
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
