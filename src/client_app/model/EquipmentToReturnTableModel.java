package client_app.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EquipmentToReturnTableModel extends AbstractTableModel {

    private List<EquipmentToReturn> equipmentsToReturn;
    private final String[] headers = {"Nom", "Quantité empruntée", "Quantité retournée"};

    public EquipmentToReturnTableModel(List<EquipmentToReturn> equipmentsToReturn) {
        this.equipmentsToReturn = equipmentsToReturn;
    }


    @Override
    public int getRowCount() {
        return this.equipmentsToReturn.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return equipmentsToReturn.get(rowIndex).getName();
            case 1:
                return equipmentsToReturn.get(rowIndex).getQuantity();
            case 2:
                return equipmentsToReturn.get(rowIndex).getReturnedQuantity();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        EquipmentToReturn equipmentToReturn = this.equipmentsToReturn.get(rowIndex);
        switch (columnIndex) {
            case 2:
                equipmentToReturn.setReturnedQuantity((Integer) value);
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