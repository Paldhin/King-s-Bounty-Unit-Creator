package main;

import clases.Unidad;
import clases.Utilidades;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.*;

public class Interfaz extends JFrame {
    private final Utilidades utilidades = new Utilidades();
    private static final String SOURCE_FILES_DIR = "src/Files";
    private static final String RESOURCE_FILES_DIR = "Files";

    private final JTextField nombreField = new JTextField(20);
    private final JTextArea outputArea = new JTextArea(20, 80);
    private final JButton crearButton = new JButton("Crear unidad");
    private final JButton verPasivasButton = new JButton("Ver pasivas");
    private final JButton verTalentosButton = new JButton("Ver talentos");
    private final JButton verUnidadesButton = new JButton("Ver unidades");
    private final JButton guardarButton = new JButton("Guardar cambios");
    private final JButton cancelarButton = new JButton("Cancelar edición");
    private final JButton limpiarButton = new JButton("Limpiar salida");
    private String editingFile = null;
    private String originalFileContent = null;

    public Interfaz() {
        super("KingsBounty Unit Creator");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel controls = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        namePanel.add(new JLabel("Nombre opcional:"));
        namePanel.add(nombreField);
        controls.add(namePanel, gbc);

        gbc.gridy = 1;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        buttonPanel.add(crearButton);
        buttonPanel.add(verPasivasButton);
        buttonPanel.add(verTalentosButton);
        buttonPanel.add(verUnidadesButton);
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);
        buttonPanel.add(limpiarButton);
        controls.add(buttonPanel, gbc);

        add(controls, BorderLayout.NORTH);

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        crearButton.addActionListener(e -> createUnit());
        verPasivasButton.addActionListener(e -> showFile("Pasivas.txt"));
        verTalentosButton.addActionListener(e -> showFile("Talentos.txt"));
        verUnidadesButton.addActionListener(e -> showFile("Unidades.txt"));
        guardarButton.addActionListener(e -> saveEdits());
        cancelarButton.addActionListener(e -> cancelEdit());
        limpiarButton.addActionListener(e -> {
            outputArea.setText("");
            cancelEdit();
        });

        setEditingMode(false);
        setupEnterKey();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
    }

    private void setEditingMode(boolean editing) {
        crearButton.setVisible(!editing);
        verPasivasButton.setVisible(!editing);
        verTalentosButton.setVisible(!editing);
        verUnidadesButton.setVisible(!editing);
        guardarButton.setVisible(editing);
        cancelarButton.setVisible(editing);
        nombreField.setEnabled(!editing);
        limpiarButton.setVisible(!editing);
        getRootPane().setDefaultButton(editing ? guardarButton : crearButton);
    }

    private void createUnit() {
        Unidad unidad = new Unidad();
        String nombre = nombreField.getText().trim();
        unidad.setNombre(nombre.isEmpty() ? getDefaultName() : nombre);
        utilidades.CrearUnidad(unidad);

        String unidadTexto = unidad.toFile().trim();
        try {
            Path archivo = getWritePath("Unidades.txt");
            Files.createDirectories(archivo.getParent());
            Files.writeString(archivo, unidadTexto + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            outputArea.setText("Unidad generada y guardada en " + archivo + ":\n\n" + unidad.toString());
        } catch (IOException ex) {
            showError("No se pudo guardar la unidad.", ex);
        }
    }

    private void showFile(String fileName) {
        try {
            String contenido = readFileOrResource(fileName);
            if ("Unidades.txt".equals(fileName)) {
                editingFile = fileName;
                originalFileContent = contenido;
                outputArea.setEditable(true);
                setEditingMode(true);
                outputArea.setText(contenido);
                outputArea.setCaretPosition(0);
            } else {
                editingFile = null;
                originalFileContent = null;
                outputArea.setEditable(false);
                setEditingMode(false);
                outputArea.setText("Contenido de " + fileName + ":\n\n" + contenido);
                outputArea.setCaretPosition(0);
            }
        } catch (IOException ex) {
            editingFile = null;
            originalFileContent = null;
            outputArea.setText("El archivo '" + fileName + "' no existe en el directorio del proyecto ni en los recursos del JAR.");
            setEditingMode(false);
        }
    }

    private String readFileOrResource(String fileName) throws IOException {
        Path archivo = resolveRelativePath(fileName);
        if (archivo != null && Files.exists(archivo)) {
            try {
                return Files.readString(archivo, StandardCharsets.UTF_8);
            } catch (IOException utf8Ex) {
                byte[] bytes = Files.readAllBytes(archivo);
                return new String(bytes, Charset.defaultCharset());
            }
        }
        try (InputStream resourceStream = getClass().getResourceAsStream("/" + RESOURCE_FILES_DIR + "/" + fileName)) {
            if (resourceStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    private Path getWritePath(String fileName) {
        Path existingPath = resolveRelativePath(fileName);
        if (existingPath != null) {
            return existingPath;
        }
        Path target = Paths.get(SOURCE_FILES_DIR, fileName);
        if (Files.exists(target.getParent()) || !Files.exists(Paths.get(RESOURCE_FILES_DIR))) {
            return target;
        }
        return Paths.get(RESOURCE_FILES_DIR, fileName);
    }

    private Path resolveRelativePath(String fileName) {
        Path candidate = Paths.get(SOURCE_FILES_DIR, fileName);
        if (Files.exists(candidate)) {
            return candidate;
        }
        candidate = Paths.get(RESOURCE_FILES_DIR, fileName);
        if (Files.exists(candidate)) {
            return candidate;
        }
        candidate = Paths.get(fileName);
        if (Files.exists(candidate)) {
            return candidate;
        }
        return null;
    }

    private void saveEdits() {
        if (!"Unidades.txt".equals(editingFile)) {
            outputArea.setText("No hay ninguna edición activa de 'Unidades.txt'.");
            return;
        }

        if (hasUnsavedChanges() && !confirmProceed("guardar los cambios")) {
            return;
        }

        try {
            Path archivo = getWritePath(editingFile);
            Files.createDirectories(archivo.getParent());
            Files.writeString(archivo, outputArea.getText(), StandardCharsets.UTF_8);
            editingFile = null;
            originalFileContent = null;
            outputArea.setEditable(false);
            setEditingMode(false);
            outputArea.setText("Cambios guardados en " + archivo + ".\n\n" + outputArea.getText());
        } catch (IOException ex) {
            showError("No se pudo guardar 'Unidades.txt'.", ex);
        }
    }

    private void cancelEdit() {
        if (editingFile == null) {
            return;
        }

        if (hasUnsavedChanges() && !confirmProceed("descartar los cambios")) {
            return;
        }

        editingFile = null;
        originalFileContent = null;
        outputArea.setEditable(false);
        setEditingMode(false);
        outputArea.setText("Edición cancelada. Use 'Ver unidades' para volver a cargar el archivo.");
        outputArea.setCaretPosition(0);
    }

    private String getDefaultName() {
        String[] nombres = {
                "Alaric", "Briana", "Caelum", "Darian", "Elara",
                "Fenris", "Galen", "Helena", "Ishar", "Jora"
        };
        return nombres[new Random().nextInt(nombres.length)];
    }

    private boolean hasUnsavedChanges() {
        return editingFile != null && originalFileContent != null && !outputArea.getText().equals(originalFileContent);
    }

    private boolean confirmProceed(String actionDescription) {
        int option = JOptionPane.showOptionDialog(
                this,
                "¿Seguro que quieres " + actionDescription + "?",
                "Confirmar acción",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"Sí", "Volver Atrás"},
                "Volver Atrás"
        );
        return option == JOptionPane.YES_OPTION;
    }

    private void setupEnterKey() {
        AbstractAction enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Unidades.txt".equals(editingFile)) {
                    saveEdits();
                } else {
                    createUnit();
                }
            }
        };

        JRootPane rootPane = getRootPane();
        rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        rootPane.getActionMap().put("enterAction", enterAction);

        outputArea.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        outputArea.getActionMap().put("enterAction", enterAction);
    }

    private void showError(String message, Exception ex) {
        outputArea.setText(message + "\n" + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new Interfaz().setVisible(true));
    }
}
