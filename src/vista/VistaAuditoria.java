package vista;

import servicios.GestorAuditoria;
import java.awt.Color;
import javax.swing.JOptionPane;

public class VistaAuditoria extends javax.swing.JFrame
{

    public VistaAuditoria()
    {
        initComponents();

        setLocationRelativeTo(null);

        cargarLogs();
    }

    private void cargarLogs()
    {
        try
        {
            String contenidoLog = GestorAuditoria.leerLogCifrado();

            txtAreaLogs.setText(contenidoLog);

            txtAreaLogs.setCaretPosition(0);

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar registros:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents()
    {

        panelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        scrollLogs = new javax.swing.JScrollPane();
        txtAreaLogs = new javax.swing.JTextArea();
        btnActualizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Auditoría del Sistema");
        setResizable(false);

        panelPrincipal.setBackground(new Color(0, 0, 51));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("RASTREO DE MOVIMIENTOS DEL SISTEMA");

        txtAreaLogs.setEditable(false);
        txtAreaLogs.setBackground(new java.awt.Color(15, 15, 15));
        txtAreaLogs.setColumns(20);
        txtAreaLogs.setFont(new java.awt.Font("Consolas", 0, 14));
        txtAreaLogs.setForeground(new java.awt.Color(0, 255, 120));
        txtAreaLogs.setRows(5);
        txtAreaLogs.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollLogs.setViewportView(txtAreaLogs);

        btnActualizar.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnActualizar.setForeground(new java.awt.Color(0, 0, 0));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(255, 255, 255));
        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnCerrar.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);

        panelPrincipalLayout.setHorizontalGroup(
                panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollLogs)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(25, 25, 25))
        );

        panelPrincipalLayout.setVerticalGroup(
                panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addGap(20, 20, 20)
                                .addComponent(scrollLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(panelPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt)
    {
        cargarLogs();

        JOptionPane.showMessageDialog(
                this,
                "Registros actualizados correctamente.",
                "Actualización",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt)
    {
        dispose();
    }

    // Variables declaration
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JScrollPane scrollLogs;
    private javax.swing.JTextArea txtAreaLogs;
    // End of variables declaration
}