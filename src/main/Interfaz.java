package main;

import clases.Unidad;
import clases.Utilidades;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import javax.swing.*;

public class Interfaz extends JFrame {
    private final Utilidades utilidades = new Utilidades();
    private final JTextField nombreField = new JTextField(20);
    private final JTextArea outputArea = new JTextArea(20, 80);

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
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        controls.add(new JLabel("Nombre opcional:"), gbc);

        gbc.gridx = 1;
        controls.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton crearButton = new JButton("Crear unidad");
        JButton verPasivasButton = new JButton("Ver pasivas");
        JButton verUnidadesButton = new JButton("Ver unidades");
        JButton limpiarButton = new JButton("Limpiar salida");
        buttonPanel.add(crearButton);
        buttonPanel.add(verPasivasButton);
        buttonPanel.add(verUnidadesButton);
        buttonPanel.add(limpiarButton);
        controls.add(buttonPanel, gbc);

        add(controls, BorderLayout.NORTH);

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        crearButton.addActionListener(e -> createUnit());
        verPasivasButton.addActionListener(e -> showFile("Pasivas.txt"));
        verUnidadesButton.addActionListener(e -> showFile("Unidades.txt"));
        limpiarButton.addActionListener(e -> outputArea.setText(""));

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
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
            Path archivo = Paths.get(fileName);
            if (!Files.exists(archivo)) {
                outputArea.setText("El archivo '" + fileName + "' no existe en el directorio del proyecto.");
                return;
            }
            String contenido = Files.readString(archivo, StandardCharsets.UTF_8);
            outputArea.setText("Contenido de " + fileName + ":\n\n" + contenido);
        } catch (IOException ex) {
            showError("No se pudo leer el archivo '" + fileName + "'.", ex);
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
