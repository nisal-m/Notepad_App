import javax.swing.*;
import java.awt.*;
import java.io.*;

public class NotepadApp extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    public NotepadApp() {
        setTitle("Notepad App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        fileChooser = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openFile());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveFile());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> textArea.cut());

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e -> textArea.copy());

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> textArea.paste());

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                    "Notepad Application\nName: DBWNM Wickramasinghe\nID: 16272",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE));

        helpMenu.add(aboutItem);

        JMenu formatMenu = new JMenu("Format");

        JMenuItem fontItem = new JMenuItem("Change Font");
        fontItem.addActionListener(e -> changeFont());

        JMenuItem colorItem = new JMenuItem("Change Text Color");
        colorItem.addActionListener(e -> changeColor());

        formatMenu.add(fontItem);
        formatMenu.add(colorItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(this,
                "Choose Font:", "Font Chooser",
                JOptionPane.PLAIN_MESSAGE, null, fonts, textArea.getFont().getFamily());

        if (selectedFont != null) {
            textArea.setFont(new Font(selectedFont, Font.PLAIN, 16));
        }
    }

    private void changeColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
        if (newColor != null) {
            textArea.setForeground(newColor);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NotepadApp().setVisible(true);
        });
    }
}
