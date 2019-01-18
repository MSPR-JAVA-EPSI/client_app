package client_app.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

class NumberDocumentFilter extends DocumentFilter {
    private int max;

    public NumberDocumentFilter(int max) {
        super();
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            // warn the user and don't allow the insert
            System.out.println("ERROR: insert number under max quantity please please");
        }
    }

    private boolean test(String text) {
        try {
            Integer quantity = Integer.parseInt(text);
            if(quantity > max) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            // warn the user and don't allow the insert
            System.out.println("ERROR: insert number under max quantity please please");
        }

    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);

        if (test(sb.toString())) {
            super.remove(fb, offset, length);
        } else {
            // warn the user and don't allow the insert
            System.out.println("ERROR: insert number under max quantity please please");
        }

    }
}