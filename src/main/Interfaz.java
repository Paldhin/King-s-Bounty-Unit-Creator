package main;

import clases.Unidad;
import clases.Utilidades;
import java.awt.*;
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
    }

    private void createUnit() {
        Unidad unidad = new Unidad();
        String nombre = nombreField.getText().trim();
        unidad.setNombre(nombre.isEmpty() ? getDefaultName() : nombre);
        utilidades.CrearUnidad(unidad);

        String unidadTexto = unidad.toFile().trim();
        try {
            Path archivo = Paths.get("Unidades.txt");
            Files.writeString(archivo, unidadTexto + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            outputArea.setText("Unidad generada y guardada en Unidades.txt:\n\n" + unidad.toString());
        } catch (IOException ex) {
            showError("No se pudo guardar la unidad en Unidades.txt.", ex);
        }
    }

    private void showFile(String fileName) {
        try {
            String contenido = readFileOrResource(fileName);
            if ("Unidades.txt".equals(fileName)) {
                editingFile = fileName;
                outputArea.setEditable(true);
                setEditingMode(true);
                outputArea.setText(contenido);
                outputArea.setCaretPosition(0);
            } else {
                editingFile = null;
                outputArea.setEditable(false);
                setEditingMode(false);
                outputArea.setText("Contenido de " + fileName + ":\n\n" + contenido);
                outputArea.setCaretPosition(0);
            }
        } catch (IOException ex) {
            outputArea.setText("El archivo '" + fileName + "' no existe en el directorio del proyecto ni en los recursos del JAR.");
            setEditingMode(false);
        }
    }

    private String readFileOrResource(String fileName) throws IOException {
        Path archivo = Paths.get(fileName);
        if (Files.exists(archivo)) {
            try {
                return Files.readString(archivo, StandardCharsets.UTF_8);
            } catch (IOException utf8Ex) {
                byte[] bytes = Files.readAllBytes(archivo);
                return new String(bytes, Charset.defaultCharset());
            }
        }
        try (InputStream resourceStream = getClass().getResourceAsStream("/" + fileName)) {
            if (resourceStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    private void saveEdits() {
        if (!"Unidades.txt".equals(editingFile)) {
            outputArea.setText("No hay ninguna edición activa de 'Unidades.txt'.");
            return;
        }

        try {
            Files.writeString(Paths.get(editingFile), outputArea.getText(), StandardCharsets.UTF_8);
            editingFile = null;
            outputArea.setEditable(false);
            setEditingMode(false);
            outputArea.setText("Cambios guardados en Unidades.txt.\n\n" + outputArea.getText());
        } catch (IOException ex) {
            showError("No se pudo guardar 'Unidades.txt'.", ex);
        }
    }

    private void cancelEdit() {
        if (editingFile != null) {
            editingFile = null;
            outputArea.setEditable(false);
            setEditingMode(false);
            outputArea.setText("Edición cancelada. Use 'Ver unidades' para volver a cargar el archivo.");
            outputArea.setCaretPosition(0);
        }
    }

    private String getDefaultName() {
        String[] nombres = {
                "Alaric", "Briana", "Caelum", "Darian", "Elara",
                "Fenris", "Galen", "Helena", "Ishar", "Jora"
        };
        return nombres[new Random().nextInt(nombres.length)];
    }

    private void showError(String message, Exception ex) {
        outputArea.setText(message + "\n" + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new Interfaz().setVisible(true));
    }
}
