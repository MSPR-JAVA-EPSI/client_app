package client_app.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GuardianEditTableModel extends AbstractTableModel {

    private List<GuardianEdit> guardians;
    private final String[] headers = {"Identifiant", "Nom", "Administrateur"};

    public GuardianEditTableModel(List<GuardianEdit> guardians) {
        this.guardians = guardians;
    }


    @Override
    public int getRowCount() {
        return this.guardians.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return guardians.get(rowIndex).getId();
            case 1:
                return guardians.get(rowIndex).getName();
            case 2:
                return guardians.get(rowIndex).isAdministrator();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        GuardianEdit GuardianEdit = this.guardians.get(rowIndex);
        switch (columnIndex) {
            case 1:
                GuardianEdit.setName((String) value);
                break;
            case 3:
                GuardianEdit.setAdministrator((Boolean) value);
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
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            default:
                return Object.class;
        }
    }
}
