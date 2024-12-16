package com.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalibrationCorrectionForm extends JFrame {
    private JPanel mainPanel;
    private int numeroPaneles;

    public CalibrationCorrectionForm() {
        initDialog();
        initComponents();
    }

    private void initDialog() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(this,
                        "Ingrese el número de paneles de Corrección:",
                        "Configuración de Paneles",
                        JOptionPane.QUESTION_MESSAGE);

                if (input == null) {
                    // Usuario cancela
                    dispose();
                    System.exit(0);
                }

                numeroPaneles = Integer.parseInt(input);

                if (numeroPaneles > 0 && numeroPaneles <= 10) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Por favor, ingrese un número entre 1 y 10",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese un número válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initComponents() {
        setTitle("Formulario de Certificados de Calibración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Crear paneles de Corrección dinámicamente
        for (int i = 0; i < numeroPaneles; i++) {
            JPanel panelCorreccion = createCorrectionPanel(i + 1);
            panelCorreccion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            mainPanel.add(panelCorreccion);

            // Agregar espacio entre paneles
            if (i < numeroPaneles - 1) {
                mainPanel.add(Box.createVerticalStrut(10));
            }
        }

        // Establecer layout de BorderLayout
        setLayout(new BorderLayout());

        // Agregar scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Tamaño de ventana original
        setSize(837, 751);
        setLocationRelativeTo(null);
    }

    private JPanel createCorrectionPanel(int numero) {
        JPanel Correccion = new JPanel();
        Correccion.setLayout(null); // Usando layout nulo para posicionamiento absoluto
        Correccion.setBackground(Color.WHITE);
        Correccion.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Panel de título
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(204, 204, 204));
        headerPanel.setBounds(0, 0, 770, 40);

        JLabel tituloLabel = new JLabel("CORRECCIÓN");
        tituloLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBounds(0, 0, 770, 40);
        headerPanel.add(tituloLabel);

        Correccion.add(headerPanel);

        // Sección de Escala de Calibración
        JPanel escalaPanel = new JPanel();
        escalaPanel.setLayout(null);
        escalaPanel.setBackground(new Color(0, 204, 51));
        escalaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        escalaPanel.setBounds(0, 40, 190, 70);

        JLabel escalaLabel1 = new JLabel("ESCALA DE CALIBRACIÓN");
        JLabel escalaLabel2 = new JLabel("Unidades del reporte XXX");
        JLabel escalaLabel3 = new JLabel("(Unidades SI XXX)");

        escalaLabel1.setBounds(10, 0, 200, 20);
        escalaLabel2.setBounds(10, 20, 200, 20);
        escalaLabel3.setBounds(30, 40, 200, 20);

        escalaPanel.add(escalaLabel1);
        escalaPanel.add(escalaLabel2);
        escalaPanel.add(escalaLabel3);

        // Sección de Factor de Corrección
        JPanel factorPanel = new JPanel();
        factorPanel.setLayout(null);
        factorPanel.setBackground(new Color(0, 204, 51));
        factorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        factorPanel.setBounds(190, 40, 190, 70);

        JLabel factorLabel1 = new JLabel("FACTOR DE");
        JLabel factorLabel2 = new JLabel("CORRECCIÓN");
        JLabel factorLabel3 = new JLabel("XXX");

        factorLabel1.setBounds(60, 10, 100, 20);
        factorLabel2.setBounds(60, 30, 100, 20);
        factorLabel3.setBounds(80, 50, 100, 20);

        factorPanel.add(factorLabel1);
        factorPanel.add(factorLabel2);
        factorPanel.add(factorLabel3);

        // Campos de texto
        JTextField campoEscala = new JTextField();
        campoEscala.setHorizontalAlignment(JTextField.CENTER);
        campoEscala.setBounds(0, 110, 190, 30);

        JTextField campoFactor = new JTextField();
        campoFactor.setHorizontalAlignment(JTextField.CENTER);
        campoFactor.setBounds(190, 110, 190, 30);

        // Agregar componentes al panel principal
        Correccion.add(escalaPanel);
        Correccion.add(factorPanel);
        Correccion.add(campoEscala);
        Correccion.add(campoFactor);

        // Nota importante
        JPanel notaPanel = new JPanel();
        notaPanel.setLayout(null);
        notaPanel.setBorder(BorderFactory.createEtchedBorder());
        notaPanel.setBounds(380, 40, 390, 100);

        JLabel notaLabel1 = new JLabel("TENER EN CUENTA:");
        JLabel notaLabel2 = new JLabel("VALOR REAL = (LECTURA + FACTOR DE CORRECCIÓN)");

        notaLabel1.setBounds(100, 10, 200, 20);
        notaLabel2.setBounds(10, 30, 370, 20);

        notaPanel.add(notaLabel1);
        notaPanel.add(notaLabel2);

        Correccion.add(notaPanel);

        Correccion.setPreferredSize(new Dimension(770, 200));
        return Correccion;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer look and feel Nimbus
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                // Si Nimbus no está disponible, mantener el look and feel por defecto
                e.printStackTrace();
            }

            new CalibrationCorrectionForm().setVisible(true);
        });
    }
}