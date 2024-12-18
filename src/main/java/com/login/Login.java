/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.login;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author sena
 */
public class Login extends javax.swing.JFrame {
    private JPanel mainPanel;
    private int numeroPaneles;
    private int numeroRepeticiones;
    private JPanel correccionContainer;
    private ArrayList<JPanel> correccionPanels;
    private int numeroFilasTabla;

public Login() {
    initDialog();
    initComponents();
    
    // Agregar estas líneas
    JScrollPane scrollPane = new JScrollPane(Main);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    setContentPane(scrollPane);
    
    createCorreccionPanels();
}
    private void initDialog() {
    // Diálogo para número de repeticiones
        while (true) {
        try {
            String input = JOptionPane.showInputDialog(this,
                    "Ingrese el número de repeticiones del panel de Corrección:",
                    "Configuración de Repeticiones",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                dispose();
                System.exit(0);
            }

            numeroRepeticiones = Integer.parseInt(input);

            if (numeroRepeticiones > 0 && numeroRepeticiones <= 10) {
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
            while (true) {
        try {
            String input = JOptionPane.showInputDialog(this,
                    "Ingrese el número de filas para la tabla de Escala de Calibración:",
                    "Configuración de Tabla",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                dispose();
                System.exit(0);
            }

            numeroFilasTabla = Integer.parseInt(input);

            if (numeroFilasTabla > 0 && numeroFilasTabla <= 20) {
                break;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese un número entre 1 y 20",
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
private JPanel createDynamicTable(int rows) {
    // Create a panel to hold the table
    JPanel tablePanel = new JPanel(new GridLayout(rows + 1, 2));
    tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Create header labels
    JLabel headerScale = new JLabel("Escala de Calibración", SwingConstants.CENTER);
    JLabel headerCorrection = new JLabel("Factor de Corrección", SwingConstants.CENTER);
    headerScale.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
    headerCorrection.setFont(new Font("Helvetica Neue", Font.BOLD, 12));

    // Add headers
    tablePanel.add(headerScale);
    tablePanel.add(headerCorrection);

    // Create text fields for each row
    for (int i = 0; i < rows; i++) {
        JTextField scaleTextField = new JTextField();
        JTextField correctionTextField = new JTextField();

        // Customize text fields
        scaleTextField.setHorizontalAlignment(JTextField.CENTER);
        correctionTextField.setHorizontalAlignment(JTextField.CENTER);
        scaleTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        correctionTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tablePanel.add(scaleTextField);
        tablePanel.add(correctionTextField);
    }

    return tablePanel;
}
private void createCorreccionPanels() {
    // Eliminar el panel de Corrección original
    Main.remove(Correccion);

    // Crear un nuevo contenedor para los paneles de corrección
    correccionContainer = new JPanel();
    correccionContainer.setLayout(new BoxLayout(correccionContainer, BoxLayout.Y_AXIS));
    correccionContainer.setBackground(Color.WHITE);

    // Crear y agregar paneles de corrección
    for (int i = 0; i < numeroRepeticiones; i++) {
        JPanel panelCopia = createCorreccionPanelCopy();
        correccionContainer.add(panelCopia);
    }

    // Agregar el contenedor de paneles al Main (sin scroll)
    Main.add(correccionContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 770, -1));

    // Revalidar y repintar el frame
    revalidate();
    repaint();
}
    private JPanel createCorreccionPanelCopy() {
        // Panel principal
        JPanel panelCopia = new JPanel();
        panelCopia.setLayout(new BoxLayout(panelCopia, BoxLayout.Y_AXIS));
        panelCopia.setBackground(Color.WHITE);
        panelCopia.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Header panel (CORRECCIÓN)
        JPanel headerPanel = createPanelHeader();
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Panel contenedor para los campos
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Panel izquierdo (Escala de Calibración)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        // Panel derecho (Factor de Corrección)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        // Crear los paneles de título
        JPanel scalePanel = createScalePanel();
        JPanel correctionPanel = createCorrectionPanel();

        // Agregar los paneles de título
        leftPanel.add(scalePanel);
        rightPanel.add(correctionPanel);

        // Crear y agregar campos de texto
        for (int i = 0; i < numeroFilasTabla; i++) {
            JTextField scaleField = new JTextField();
            JTextField correctionField = new JTextField();
            
            // Configurar los campos
            configureTextField(scaleField);
            configureTextField(correctionField);
            
            // Agregar los campos
            leftPanel.add(scaleField);
            rightPanel.add(correctionField);
        }

        // Panel para la nota
        JPanel notePanel = createPanelNote();

        // Agregar los paneles al contentPanel
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        // Agregar todo al panel principal
        panelCopia.add(headerPanel);
        
        // Panel para organizar el contenido y la nota
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.add(contentPanel);
        horizontalPanel.add(notePanel);
        
        panelCopia.add(horizontalPanel);

        return panelCopia;
    }

    private void configureTextField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setPreferredSize(new Dimension(190, 30));
        field.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Agregar margen
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private JPanel createPanelHeader() {
        JPanel jPanel6 = new JPanel(new BorderLayout());
        jPanel6.setBackground(new Color(204, 204, 204));
        jPanel6.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel jLabel5 = new JLabel("CORRECCIÓN", SwingConstants.CENTER);
        jLabel5.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        jPanel6.add(jLabel5, BorderLayout.CENTER);

        return jPanel6;
    }

private JPanel createPanelCenter() {
    JPanel jPanel4 = new JPanel(new org.netbeans.lib.awtextra.AbsoluteLayout());
    jPanel4.setBackground(Color.WHITE);
    jPanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Crear paneles internos
    JPanel jPanel5 = createScalePanel();
    JPanel jPanel7 = createCorrectionPanel();

    // Crear campos de texto con bordes y configuraciones
    JTextField scaleTextField = new JTextField();
    scaleTextField.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    scaleTextField.setHorizontalAlignment(JTextField.CENTER);
    scaleTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JTextField correctionTextField = new JTextField();
    correctionTextField.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    correctionTextField.setHorizontalAlignment(JTextField.CENTER);
    correctionTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Añadir paneles
    jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 70));
    jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 190, 70));

    // Añadir campos de texto debajo de los paneles en su posición original
    jPanel4.add(scaleTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 190, 30));
    jPanel4.add(correctionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 190, 30));

    return jPanel4;
}

private JPanel createScalePanel() {
    JPanel jPanel5 = new JPanel(new GridLayout(3, 1));
    jPanel5.setBackground(new Color(0, 204, 51));
    jPanel5.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JLabel jLabel8 = new JLabel("ESCALA DE CALIBRACIÓN", SwingConstants.CENTER);
    JLabel jLabel9 = new JLabel("Unidades del reporte XXX", SwingConstants.CENTER);
    JLabel jLabel10 = new JLabel("(Unidades SI XXX)", SwingConstants.CENTER);

    jLabel8.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    jLabel9.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    jLabel10.setFont(new Font("Helvetica Neue", Font.BOLD, 13));

    jPanel5.add(jLabel8);
    jPanel5.add(jLabel9);
    jPanel5.add(jLabel10);

    return jPanel5;
}

private JPanel createCorrectionPanel() {
    JPanel jPanel7 = new JPanel(new GridLayout(3, 1));
    jPanel7.setBackground(new Color(0, 204, 51));
    jPanel7.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JLabel jLabel11 = new JLabel("FACTOR DE", SwingConstants.CENTER);
    JLabel jLabel12 = new JLabel("CORRECCIÓN", SwingConstants.CENTER);
    JLabel jLabel13 = new JLabel("XXX", SwingConstants.CENTER);

    jLabel11.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    jLabel12.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    jLabel13.setFont(new Font("Helvetica Neue", Font.BOLD, 13));

    jPanel7.add(jLabel11);
    jPanel7.add(jLabel12);
    jPanel7.add(jLabel13);

    return jPanel7;
}

    private JPanel createPanelNote() {
        JPanel jPanel8 = new JPanel(new GridLayout(2, 1));
        jPanel8.setBackground(Color.WHITE);
        jPanel8.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel jLabel6 = new JLabel("TENER EN CUENTA:", SwingConstants.CENTER);
        JLabel jLabel7 = new JLabel("VALOR REAL=( LECTURA + FACTOR DE CORRECCIÓN)", SwingConstants.CENTER);

        jLabel6.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        jLabel7.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

        jPanel8.add(jLabel6);
        jPanel8.add(jLabel7);

        return jPanel8;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        Main = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        Correccion = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Main.setBackground(new java.awt.Color(255, 255, 255));
        Main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        header.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 100, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Certificados de");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("calibración No");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        header.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 140, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Serial No.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        header.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, -1));

        jTextField1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        header.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 160, 60));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INSTRUMENTO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        header.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTextField4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        header.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 100, 60));

        Main.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 770, 60));

        Correccion.setBackground(new java.awt.Color(255, 255, 255));
        Correccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Correccion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("CORRECCIÓN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        Correccion.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 40));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TENER EN CUENTA:");
        jPanel8.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 7, 390, -1));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("VALOR REAL=( LECTURA + FACTOR DE CORRECCIÓN)");
        jPanel8.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 43, 380, -1));

        Correccion.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 390, 100));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 204, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ESCALA DE CALIBRACIÓN");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, -1));

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Unidades del reporte XXX");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 190, -1));

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("(Unidades SI XXX)");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 40, 190, -1));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 70));

        jPanel7.setBackground(new java.awt.Color(0, 204, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("FACTOR DE ");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, -1));

        jLabel12.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("CORRECCIÓN");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 190, -1));

        jLabel13.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("XXX");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(-3, 50, 190, -1));

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 190, 70));

        Correccion.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, 100));

        Main.add(Correccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 770, 140));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Main, javax.swing.GroupLayout.PREFERRED_SIZE, 937, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel Correccion;
    private javax.swing.JPanel Main;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration                   
}
