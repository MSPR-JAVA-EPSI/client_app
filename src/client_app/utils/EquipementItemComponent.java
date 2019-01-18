package client_app.utils;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class EquipementItemComponent {
    private JLabel label;
    private JTextField textField;
    private PlainDocument document;
    private int max;
    private String name;
    private int id;

    public EquipementItemComponent(int max, String name, int id) {
        this.textField = new JTextField("0");
        this.label = new JLabel(name +" ( max "+max+" )");
        this.max = max;
        this.name = name;
        this.id = id;
        this.document = (PlainDocument) textField.getDocument();
        this.document.setDocumentFilter(new NumberDocumentFilter(max));
        this.textField.setPreferredSize(new Dimension(200,50));
    }

    public JLabel getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JTextField getTextField() {
        return textField;
    }

    public int getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setName(String name) {
        this.name = name;
        this.textField.setText(this.name);
    }
}
