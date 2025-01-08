
package com.login;
// Importaciones básicas de iText 7
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

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
    private int alturaTotal;
    

public Login() {
    initDialog();
    initComponents();
    
    CONTROL = createControlPanel();
    
    // Cambiar el layout del Main a AbsoluteLayout
    Main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    
    // Crear los paneles de corrección
    createCorreccionPanels();
    
    // Agregar un JScrollPane para permitir scroll cuando sea necesario
    JScrollPane scrollPane = new JScrollPane(Main);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    
    // Mantener el borde y el fondo del panel principal
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    setContentPane(scrollPane);
    
    // Establecer un tamaño inicial para la ventana
    setSize(1100, 800);
    
    // Revalidar y repintar el frame
    revalidate();
    repaint();
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
    // Eliminar los paneles existentes primero
    Main.removeAll();
    
    // Volver a agregar el header ya que removeAll() lo eliminó
    Main.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 770, 60));

    // Crear un nuevo contenedor para los paneles de corrección con layout vertical
    JPanel correccionContainer = new JPanel();
    correccionContainer.setLayout(new BoxLayout(correccionContainer, BoxLayout.Y_AXIS));
    correccionContainer.setBackground(Color.WHITE);

    // Crear y agregar los paneles de corrección
    for (int i = 0; i < numeroRepeticiones; i++) {
        JPanel panelCopia = createCorreccionPanelCopy();
        correccionContainer.add(panelCopia);
        if (i < numeroRepeticiones - 1) {
            correccionContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }

    // Calcular la altura total necesaria
    int alturaPanel = calcularAlturaPanelCorreccion();
    int espacioEntrePaneles = 20;
    alturaTotal = (alturaPanel * numeroRepeticiones) + 
                 (espacioEntrePaneles * (numeroRepeticiones - 1));

// Agregar el contenedor de corrección
    Main.add(correccionContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(
        40,    // x
        80,    // y inicial
        770,   // ancho
        alturaTotal  // altura total
    ));

    // Agregar el panel de calibración inmediatamente después del último panel de corrección
    Main.add(CALIBRACION, new org.netbeans.lib.awtextra.AbsoluteConstraints(
        40,           // x 
        80 + alturaTotal,  // y (justo después del último panel de corrección)
        770,          // ancho
        180           // altura
    ));

    Main.add(CONTROL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 770, 180));
    Main.revalidate();
    Main.repaint();

    Main.add(CONTROL, new org.netbeans.lib.awtextra.AbsoluteConstraints(
        40,           // x 
        80 + alturaTotal + 180,  // y (después del panel CALIBRACION)
        770,          // ancho
        180           // altura
    ));

    // Agregar el botón generar PDF
    Main.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(
        870, 
        80 + alturaTotal + 300 + 10,  // Posicionado después del panel CALIBRACION con un pequeño margen
        -1, 
        -1
    ));

    // Actualizar el tamaño del panel principal para incluir todos los componentes
Main.setPreferredSize(new Dimension(
    1056,  // ancho original
    80 + alturaTotal + 300 + 180 + 50  // altura total incluyendo CONTROL
));

    // Revalidar y repintar
    SwingUtilities.invokeLater(() -> {
        revalidate();
        repaint();
    });
}
private int calcularAlturaPanelCorreccion() {
    // Calcular altura del panel de corrección de manera dinámica
    int alturaHeader = 40;  // Altura del header del panel
    int alturaFilaTabla = 30;  // Altura de cada fila de tabla
    int alturaNotaPanel = 70;  // Altura del panel de nota
    int espaciadoInterno = 10;  // Espacio interno entre elementos

    // Calcular altura total del panel
    int alturaPanel = alturaHeader + 
                     (numeroFilasTabla * alturaFilaTabla) + 
                     alturaNotaPanel +
                     (2 * espaciadoInterno);  // Espacio arriba y abajo

    return alturaPanel;
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
    private JPanel createCalibracionPanel() {
    JPanel calibracionPanel = new JPanel();
    calibracionPanel.setBackground(Color.WHITE);
    calibracionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    calibracionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    // Encabezado
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(204, 204, 204));
    headerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    JLabel headerLabel = new JLabel("CALIBRACIÓN", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
    headerPanel.setLayout(new BorderLayout());
    headerPanel.add(headerLabel, BorderLayout.CENTER);
    calibracionPanel.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 40));

    // Contenido
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Ajustar las filas/columnas
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel label1 = new JLabel("Estado del Instrumento:", SwingConstants.RIGHT);
    JTextField field1 = new JTextField("Valor");
    JLabel label2 = new JLabel("Fecha de Última Calibración:", SwingConstants.RIGHT);
    JTextField field2 = new JTextField("yyyy-MM-dd");

    contentPanel.add(label1);
    contentPanel.add(field1);
    contentPanel.add(label2);
    contentPanel.add(field2);

    calibracionPanel.add(contentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 750, 100));

    return calibracionPanel;
}
private JPanel createControlPanel() {
    JPanel CONTROL = new JPanel();
    CONTROL.setBackground(new Color(255, 255, 255));
    CONTROL.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    CONTROL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    // Panel superior con título "CONTROL"
    JPanel jPanel9 = new JPanel();
    jPanel9.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    
    JLabel jLabel14 = new JLabel();
    jLabel14.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
    jLabel14.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel14.setText("CONTROL");

    GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel14)
            .addContainerGap(9, Short.MAX_VALUE))
    );

    CONTROL.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 40));

    // Panel izquierdo (ACTUALIZADO POR)
    JPanel leftPanel = createLeftPanel();
    CONTROL.add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 390, 140));

    // Panel derecho (REVISADO Y APROBADO POR)
    JPanel rightPanel = createRightPanel();
    CONTROL.add(rightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 380, 140));

    return CONTROL;
}

private JPanel createLeftPanel() {
    JPanel jPanel10 = new JPanel();
    jPanel10.setBackground(new Color(255, 255, 255));
    jPanel10.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    // Sección "ACTUALIZADO POR"
    JPanel jPanel11 = new JPanel();
    jPanel11.setBackground(new Color(255, 255, 255));
    jPanel11.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    JLabel jLabel15 = new JLabel();
    jLabel15.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
    jLabel15.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel15.setText("ACTUALIZADO POR");
    jPanel11.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 20));

    jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

    // Panel para el Analista
    JPanel jPanel12 = new JPanel();
    jPanel12.setBackground(new Color(255, 255, 255));
    jPanel12.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    JLabel jLabel16 = new JLabel();
    jLabel16.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel16.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel16.setText("_______________________");

    JLabel jLabel17 = new JLabel();
    jLabel17.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel17.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel17.setText("Analista");

    GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
    jPanel12.setLayout(jPanel12Layout);
    jPanel12Layout.setHorizontalGroup(
        jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(jLabel17, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        .addGroup(jPanel12Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel16, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel12Layout.setVerticalGroup(
        jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(jPanel12Layout.createSequentialGroup()
            .addContainerGap(48, Short.MAX_VALUE)
            .addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel17))
    );

    jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 100));

    // Sección de fecha
    JPanel dateHeaderPanel = new JPanel();
    dateHeaderPanel.setBackground(new Color(255, 255, 255));
    dateHeaderPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    dateHeaderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    JLabel dateLabel = new JLabel();
    dateLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
    dateLabel.setText("FECHA");
    dateHeaderPanel.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 10, 190, -1));

    jPanel10.add(dateHeaderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 190, 40));

    // Campo de fecha
    JPanel datePanel = createDatePanel();
    jPanel10.add(datePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 190, 100));

    return jPanel10;
}

private JPanel createRightPanel() {
    JPanel jPanel14 = new JPanel();
    jPanel14.setBackground(new Color(255, 255, 255));
    jPanel14.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    // Panel superior con "REVISADO Y APROBADO POR"
    JPanel jPanel17 = new JPanel();
    jPanel17.setBackground(new Color(255, 255, 255));
    jPanel17.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    JLabel jLabel18 = new JLabel();
    jLabel18.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel18.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel18.setText("REVISADO Y");
    jPanel17.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 178, -1));

    JLabel jLabel19 = new JLabel();
    jLabel19.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel19.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel19.setText("APROBADO POR");
    jPanel17.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 178, -1));

    jPanel14.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 40));

    // Panel para Coordinador Técnico
    JPanel jPanel15 = new JPanel();
    jPanel15.setBackground(new Color(255, 255, 255));
    jPanel15.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    JLabel jLabel21 = new JLabel();
    jLabel21.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel21.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel21.setText("Coordinador Técnico");

    JLabel jLabel22 = new JLabel();
    jLabel22.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    jLabel22.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel22.setText("_______________________");

    GroupLayout jPanel15Layout = new GroupLayout(jPanel15);
    jPanel15.setLayout(jPanel15Layout);
    jPanel15Layout.setHorizontalGroup(
        jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(jLabel21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel15Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel22)
            .addContainerGap(11, Short.MAX_VALUE))
    );
    jPanel15Layout.setVerticalGroup(
        jPanel15Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
        .addGroup(jPanel15Layout.createSequentialGroup()
            .addGap(0, 44, Short.MAX_VALUE)
            .addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel21))
    );

    jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 180, 100));

    // Panel de fecha derecho - Header
    JPanel rightDateHeader = new JPanel();
    rightDateHeader.setBackground(new Color(255, 255, 255));
    rightDateHeader.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    rightDateHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    JLabel dateLabel2 = new JLabel();
    dateLabel2.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    dateLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    dateLabel2.setText("FECHA");
    rightDateHeader.add(dateLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 10, 190, -1));

    jPanel14.add(rightDateHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 200, 40));

    // Panel de fecha derecho - Campo
    JPanel rightDatePanel = createDatePanel();
    jPanel14.add(rightDatePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 200, 100));

    return jPanel14;
}

private JPanel createDatePanel() {
    JPanel datePanel = new JPanel();
    datePanel.setBackground(new Color(255, 255, 255));
    datePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    JLabel dateUnderline = new JLabel();
    dateUnderline.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    dateUnderline.setHorizontalAlignment(SwingConstants.CENTER);
    dateUnderline.setText("_______________________");

    JTextField dateField = new JTextField();
    dateField.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    dateField.setHorizontalAlignment(JTextField.CENTER);
    dateField.setText("Yy/Mm/Dd");
    dateField.setBorder(null);

    GroupLayout datePanelLayout = new GroupLayout(datePanel);
    datePanel.setLayout(datePanelLayout);
    datePanelLayout.setHorizontalGroup(
        datePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(datePanelLayout.createSequentialGroup()
            .addGroup(datePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(datePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(dateField, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
                .addGroup(datePanelLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(dateUnderline)))
            .addGap(12, 12, 12))
    );
    datePanelLayout.setVerticalGroup(
        datePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(datePanelLayout.createSequentialGroup()
            .addGap(39, 39, 39)
            .addComponent(dateUnderline, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dateField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    return datePanel;
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        Main = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        Serial = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Instrumento = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Certificados = new javax.swing.JTextField();
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
        jButton1 = new javax.swing.JButton();
        CALIBRACION = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();

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

        Serial.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        Serial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Serial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Serial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SerialActionPerformed(evt);
            }
        });
        header.add(Serial, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 100, 60));

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

        Instrumento.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        Instrumento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Instrumento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Instrumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InstrumentoActionPerformed(evt);
            }
        });
        header.add(Instrumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 160, 60));

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

        Certificados.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        Certificados.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Certificados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        header.add(Certificados, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 100, 60));

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
        CALIBRACION.setBackground(new java.awt.Color(255, 255, 255));
        CALIBRACION.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CALIBRACION.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Configuración del panel CALIBRACION
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 1, 18));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("CALIBRACIÓN");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel14)
                                .addContainerGap(9, Short.MAX_VALUE))
        );

        CALIBRACION.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 40));

// Panel izquierdo (Estado del instrumento y fecha última calibración)
        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(0, 204, 51));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Helvetica Neue", 1, 13));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ESTADO DEL INSTRUMENTO");
        jPanel11.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 20));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

        jTextField7.setFont(new java.awt.Font("Helvetica Neue", 1, 13));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 190, 40));

        jPanel12.setBackground(new java.awt.Color(51, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("FECHA DE ÚLTIMA");

        jLabel17.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CALIBRACIÓN");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 100));

        jTextField8.setFont(new java.awt.Font("Helvetica Neue", 1, 13));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 190, 100));

        CALIBRACION.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 390, 140));

// Panel derecho (Frecuencia de calibración y fecha próxima calibración)
        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(0, 204, 51));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("FRECUENCIA DE");
        jPanel17.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 178, -1));

        jLabel20.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("CALIBRACIÓN");
        jPanel17.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 180, -1));

        jPanel14.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 40));

        jPanel15.setBackground(new java.awt.Color(51, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("FECHA DE PRÓXIMA");

        jLabel21.setFont(new java.awt.Font("Helvetica Neue", 1, 14));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("CALIBRACIÓN");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21)
                                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 180, 100));

        jTextField9.setFont(new java.awt.Font("Helvetica Neue", 1, 13));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 200, 40));

        jTextField10.setFont(new java.awt.Font("Helvetica Neue", 1, 13));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 200, 100));

        CALIBRACION.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 380, 140));

        Main.add(Correccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 770, 140));

        Main.add(CALIBRACION, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                40, // x
                80 + alturaTotal, // y (debajo del último panel)
                770, // ancho
                300  // altura
        ));

        

        jButton1.setText("GENERAR PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Main.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Main, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SerialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SerialActionPerformed

    private void InstrumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InstrumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InstrumentoActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Certificados;
    private javax.swing.JPanel Correccion;
    private javax.swing.JTextField Instrumento;
    private javax.swing.JPanel Main;
    private javax.swing.JTextField Serial;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel CALIBRACION;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private JPanel CONTROL;
    // End of variables declaration//GEN-END:variables
}
