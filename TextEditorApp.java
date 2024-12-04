import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TextEditorApp extends JFrame {
    private JTextPane textPane;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, toolsMenu;
    private TextEditFunctionality textFunc;
    private ShapePanel shapePanel;

    public TextEditorApp() {
        setTitle("Advanced Text Editor");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        textPane = new JTextPane();
        textFunc = new TextEditFunctionality(textPane);
        shapePanel = new ShapePanel();

        // Setup layout
        setLayout(new BorderLayout());
        add(new JScrollPane(textPane), BorderLayout.CENTER);
        add(shapePanel, BorderLayout.EAST);

        // Create menu bar
        createMenuBar();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        fileMenu.add(textFunc.createMenuItem("New", KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        fileMenu.add(textFunc.createMenuItem("Open", KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(textFunc.createMenuItem("Save", KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuBar.add(fileMenu);

        // Edit Menu
        editMenu = new JMenu("Edit");
        editMenu.add(textFunc.createMenuItem("Cut", KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        editMenu.add(textFunc.createMenuItem("Copy", KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        editMenu.add(textFunc.createMenuItem("Paste", KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        editMenu.add(textFunc.createMenuItem("Find", KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        editMenu.add(textFunc.createMenuItem("Replace", KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        menuBar.add(editMenu);

        // Format Menu
        formatMenu = new JMenu("Format");
        formatMenu.add(textFunc.createMenuItem("Font", KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        formatMenu.add(textFunc.createMenuItem("Upper Case", 0, 0));
        formatMenu.add(textFunc.createMenuItem("Lower Case", 0, 0));
        menuBar.add(formatMenu);

        // Tools Menu
        toolsMenu = new JMenu("Tools");
        toolsMenu.add(textFunc.createMenuItem("Word Count", 0, 0));
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            TextEditorApp editor = new TextEditorApp();
            editor.setVisible(true);
        });
    }
}