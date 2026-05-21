package vista;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.conexion.DBConnection;

public class LoginDialog extends javax.swing.JDialog {

    // Variables de sesión
    private boolean autenticado = false;
    private int idUsuario = -1;
    private String nombre = "";
    private String rol = "";

    /**
     * Creates new form LoginDialog
     */
    public LoginDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);

        // Fondo del dialog
        getContentPane().setBackground(new Color(0, 0, 51));
    }

    // Getters
    public boolean isAutenticado() {
        return autenticado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Método para encriptar contraseña con SHA-256
     */
    private String encriptarSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException("Error al encriptar contraseña", ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inicio de Sesión");
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(0, 0, 51));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("INICIO DE SESIÓN");

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuario.setText("Usuario:");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Contraseña:");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));

        btnIngresar.setBackground(new java.awt.Color(255, 255, 255));
        btnIngresar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnIngresar.setForeground(new java.awt.Color(0, 0, 0));
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);

        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblPassword))
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsuario)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitulo)
                .addGap(35, 35, 35)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(panelPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

    /**
     * Evento botón ingresar
     */
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {

        String usuarioIngresado = txtUsuario.getText().trim();

        // Obtener contraseña de forma segura
        String passwordIngresado = new String(txtPassword.getPassword());

        // Validar campos vacíos
        if (usuarioIngresado.isEmpty() || passwordIngresado.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, complete todos los campos.",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Generar hash SHA-256
        String hashPassword = encriptarSHA256(passwordIngresado);

        // Consulta SQL segura
        String sql = """
                     SELECT Id_Usuario, Rol
                     FROM usuario
                     WHERE Username = ?
                     AND Password = ?
                     """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, usuarioIngresado);
            pstmt.setString(2, hashPassword);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    // Usuario autenticado
                    this.idUsuario = rs.getInt("Id_Usuario");
                    this.rol = rs.getString("Rol");
                    this.autenticado = true;
                    this.nombre = usuarioIngresado;

                    JOptionPane.showMessageDialog(
                            this,
                            "Bienvenido, " + rol,
                            "Acceso Concedido",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    this.dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Usuario o contraseña incorrectos.",
                            "Error de Autenticación",
                            JOptionPane.ERROR_MESSAGE
                    );

                    txtPassword.setText("");
                }
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error de conexión:\n" + ex.getMessage(),
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Evento botón salir
     */
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    // Variables declaration
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration
}