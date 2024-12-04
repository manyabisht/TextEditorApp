import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditFunctionality {
    private JTextPane textPane;

    public TextEditFunctionality(JTextPane textPane) {
        this.textPane = textPane;
    }

    public JMenuItem createMenuItem(String label, int keyEvent, int modifiers) {
        JMenuItem menuItem = new JMenuItem(label);
        
        if (keyEvent != 0) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, modifiers));
        }

        menuItem.addActionListener(e -> handleMenuAction(label));
        return menuItem;
    }

    private void handleMenuAction(String action) {
        switch (action) {
            case "New":
                newFile();
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Cut":
                textPane.cut();
                break;
            case "Copy":
                textPane.copy();
                break;
            case "Paste":
                textPane.paste();
                break;
            case "Find":
                findText();
                break;
            case "Replace":
                replaceText();
                break;
            case "Font":
                changeFont();
                break;
            case "Upper Case":
                changeCase(true);
                break;
            case "Lower Case":
                changeCase(false);
                break;
            case "Word Count":
                showWordCount();
                break;
        }
    }

    private void newFile() {
        textPane.setText("");
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                textPane.read(reader, null);
                reader.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                textPane.write(writer);
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
    }

    private void findText() {
        String searchTerm = JOptionPane.showInputDialog("Enter text to find:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            String text = textPane.getText();
            int index = text.indexOf(searchTerm);
            if (index != -1) {
                textPane.setCaretPosition(index);
                textPane.select(index, index + searchTerm.length());
            } else {
                JOptionPane.showMessageDialog(null, "Text not found");
            }
        }
    }

    private void replaceText() {
        String searchTerm = JOptionPane.showInputDialog("Enter text to replace:");
        String replaceTerm = JOptionPane.showInputDialog("Enter replacement text:");
        
        if (searchTerm != null && replaceTerm != null) {
            String text = textPane.getText();
            text = text.replace(searchTerm, replaceTerm);
            textPane.setText(text);
        }
    }

    private void changeFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();
        
        Font selectedFont = (Font) JOptionPane.showInputDialog(
            null, 
            "Choose Font", 
            "Font Selection", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            fonts, 
            fonts[0]
        );

        if (selectedFont != null) {
            StyledDocument doc = textPane.getStyledDocument();
            SimpleAttributeSet attributes = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attributes, selectedFont.getFamily());
            doc.setCharacterAttributes(0, doc.getLength(), attributes, false);
        }
    }

    private void changeCase(boolean toUpperCase) {
        String selectedText = textPane.getSelectedText();
        if (selectedText != null) {
            String newText = toUpperCase ? selectedText.toUpperCase() : selectedText.toLowerCase();
            textPane.replaceSelection(newText);
        }
    }

    private void showWordCount() {
        String text = textPane.getText();
        String selectedText = textPane.getSelectedText();

        int totalWords = text.trim().isEmpty() ? 0 : text.split("\\s+").length;
        int selectedWords = selectedText == null || selectedText.trim().isEmpty() ? 0 : selectedText.split("\\s+").length;

        String message = String.format(
            "Total Words: %d\nSelected Words: %d\n\n" +
            "Total Characters: %d\nSelected Characters: %d", 
            totalWords, selectedWords, 
            text.length(), 
            selectedText == null ? 0 : selectedText.length()
        );

        JOptionPane.showMessageDialog(null, message, "Word Count", JOptionPane.INFORMATION_MESSAGE);
    }
}