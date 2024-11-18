package poo;

import cjb.ci.CtrlInterfaz;
import cjb.ci.Mensajes;
import cjb.ci.Validaciones;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import pack1.Controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author DELL
 */
public class Registro extends javax.swing.JFrame
{

    /**
     * Creates new form Registro
     */
    static String c;
    static int vc;
    static int ca;
    static String n, pA, sA, matri;

    public Registro()
    {

        try
        {
            // Crear un JScrollPane y configurar las barras de desplazamient
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        siguineteRA = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        cbxCarrR = new javax.swing.JComboBox<>();
        chechMasculinoR = new javax.swing.JCheckBox();
        checkFemeR = new javax.swing.JCheckBox();
        label7 = new java.awt.Label();
        cbxViveR = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        segApReg = new javax.swing.JTextField();
        CveRegistro = new javax.swing.JTextField();
        NomReg1 = new javax.swing.JTextField();
        apellidoReg = new javax.swing.JTextField();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Nombre");
        jPanel1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, -1, -1));

        siguineteRA.setText("Siguiente");
        siguineteRA.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                siguineteRAActionPerformed(evt);
            }
        });
        jPanel1.add(siguineteRA, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 440, -1, -1));

        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                CancelarActionPerformed(evt);
            }
        });
        jPanel1.add(Cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, -1, -1));

        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("cve");
        jPanel1.add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, -1, -1));

        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Primer Apellido");
        jPanel1.add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, -1, -1));

        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("Segundo Apellido");
        jPanel1.add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, -1, -1));

        label5.setForeground(new java.awt.Color(255, 255, 255));
        label5.setText("Sexo:");
        jPanel1.add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, -1, -1));

        label6.setForeground(new java.awt.Color(255, 255, 255));
        label6.setText("Carrera");
        jPanel1.add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, -1, -1));

        cbxCarrR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "LISW", "LIP", "LSC", "LIPI", "LIM", "LIC" }));
        cbxCarrR.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                cbxCarrRItemStateChanged(evt);
            }
        });
        cbxCarrR.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbxCarrRActionPerformed(evt);
            }
        });
        jPanel1.add(cbxCarrR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        chechMasculinoR.setBackground(new java.awt.Color(0, 0, 51));
        chechMasculinoR.setForeground(new java.awt.Color(255, 255, 255));
        chechMasculinoR.setText("Masculino");
        chechMasculinoR.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                chechMasculinoRActionPerformed(evt);
            }
        });
        jPanel1.add(chechMasculinoR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, -1, -1));

        checkFemeR.setBackground(new java.awt.Color(0, 0, 51));
        checkFemeR.setForeground(new java.awt.Color(255, 255, 255));
        checkFemeR.setText("Femenino");
        checkFemeR.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkFemeRActionPerformed(evt);
            }
        });
        jPanel1.add(checkFemeR, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        label7.setForeground(new java.awt.Color(255, 255, 255));
        label7.setText("Vive con:");
        jPanel1.add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, -1, -1));

        cbxViveR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Papá", "Mamá", "Ambos" }));
        cbxViveR.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                cbxViveRItemStateChanged(evt);
            }
        });
        cbxViveR.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbxViveRActionPerformed(evt);
            }
        });
        jPanel1.add(cbxViveR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 340, -1, 20));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTRO ALUMNO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));

        segApReg.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                segApRegActionPerformed(evt);
            }
        });
        segApReg.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                segApRegKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                segApRegKeyTyped(evt);
            }
        });
        jPanel1.add(segApReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 190, -1));

        CveRegistro.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                CveRegistroActionPerformed(evt);
            }
        });
        CveRegistro.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                CveRegistroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                CveRegistroKeyTyped(evt);
            }
        });
        jPanel1.add(CveRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 190, -1));

        NomReg1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                NomReg1ActionPerformed(evt);
            }
        });
        NomReg1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                NomReg1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                NomReg1KeyTyped(evt);
            }
        });
        jPanel1.add(NomReg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 190, -1));

        apellidoReg.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                apellidoRegActionPerformed(evt);
            }
        });
        apellidoReg.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                apellidoRegKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                apellidoRegKeyTyped(evt);
            }
        });
        jPanel1.add(apellidoReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 190, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxViveRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbxViveRActionPerformed
    {//GEN-HEADEREND:event_cbxViveRActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbxViveRActionPerformed

    private void checkFemeRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkFemeRActionPerformed
    {//GEN-HEADEREND:event_checkFemeRActionPerformed
        // TODO add your handling code here:
        c = "F";
        chechMasculinoR.setSelected(false);
    }//GEN-LAST:event_checkFemeRActionPerformed

    private void chechMasculinoRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chechMasculinoRActionPerformed
    {//GEN-HEADEREND:event_chechMasculinoRActionPerformed
        // TODO add your handling code here:
        c = "M";
        checkFemeR.setSelected(false);
    }//GEN-LAST:event_chechMasculinoRActionPerformed

    private void cbxCarrRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbxCarrRActionPerformed
    {//GEN-HEADEREND:event_cbxCarrRActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbxCarrRActionPerformed

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_CancelarActionPerformed
    {//GEN-HEADEREND:event_CancelarActionPerformed
        dispose();
    }//GEN-LAST:event_CancelarActionPerformed

    private void siguineteRAActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_siguineteRAActionPerformed
    {//GEN-HEADEREND:event_siguineteRAActionPerformed

        if (cbxViveR.getSelectedItem().toString().equals("--") || cbxCarrR.getSelectedItem().toString().equals("--") || ((!chechMasculinoR.isSelected() && !checkFemeR.isSelected()) || (chechMasculinoR.isSelected() && checkFemeR.isSelected())) || CveRegistro.getText().length() == 0 || segApReg.getText().length() == 0 || apellidoReg.getText().length() == 0 || segApReg.getText().length() == 0)
        {
            Mensajes.error(this, "Debe llenar todos los campos");

        } else
        {
            n = Controlador.capitalizarPrimerasLetras(NomReg1.getText());
            pA = Controlador.capitalizarPrimerasLetras(apellidoReg.getText());
            sA = Controlador.capitalizarPrimerasLetras(segApReg.getText());
            matri = CveRegistro.getText();

            RegistroPadec re = new RegistroPadec();
            re.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_siguineteRAActionPerformed

    private void segApRegActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_segApRegActionPerformed
    {//GEN-HEADEREND:event_segApRegActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_segApRegActionPerformed

    private void segApRegKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_segApRegKeyPressed
    {//GEN-HEADEREND:event_segApRegKeyPressed
        // TODO add your handling code here:
        Validaciones.enter(this, evt, CveRegistro);
        Validaciones.validaCopyPaste(evt, this, segApReg);
    }//GEN-LAST:event_segApRegKeyPressed

    private void CveRegistroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_CveRegistroActionPerformed
    {//GEN-HEADEREND:event_CveRegistroActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_CveRegistroActionPerformed

    private void CveRegistroKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_CveRegistroKeyPressed
    {//GEN-HEADEREND:event_CveRegistroKeyPressed
        // TODO add your handling code here:
        Validaciones.enter(this, evt, NomReg1);
        Validaciones.validaCopyPaste(evt, this, CveRegistro);
        if (evt.getKeyChar() == '\n' && CveRegistro.getText() != null)
        {
                CtrlInterfaz.cambia(CveRegistro);

        }
    }//GEN-LAST:event_CveRegistroKeyPressed

    private void NomReg1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_NomReg1ActionPerformed
    {//GEN-HEADEREND:event_NomReg1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_NomReg1ActionPerformed

    private void NomReg1KeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_NomReg1KeyPressed
    {//GEN-HEADEREND:event_NomReg1KeyPressed
        // TODO add your handling code here:
        Validaciones.validaCopyPaste(evt, this, NomReg1);
        Validaciones.enter(this, evt, apellidoReg);
    }//GEN-LAST:event_NomReg1KeyPressed

    private void apellidoRegActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_apellidoRegActionPerformed
    {//GEN-HEADEREND:event_apellidoRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoRegActionPerformed

    private void apellidoRegKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_apellidoRegKeyPressed
    {//GEN-HEADEREND:event_apellidoRegKeyPressed
        // TODO add your handling code here:
        Validaciones.validaCopyPaste(evt, this, apellidoReg);
        Validaciones.enter(this, evt, segApReg);
    }//GEN-LAST:event_apellidoRegKeyPressed

    private void CveRegistroKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_CveRegistroKeyTyped
    {//GEN-HEADEREND:event_CveRegistroKeyTyped
        // TODO add your handling code here:
        Validaciones.validaEntero(evt, 7, CveRegistro.getText());
    }//GEN-LAST:event_CveRegistroKeyTyped

    private void NomReg1KeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_NomReg1KeyTyped
    {//GEN-HEADEREND:event_NomReg1KeyTyped
        // TODO add your handling code here:
        Validaciones.validaAlfabeticos(evt, 20, NomReg1.getText());
    }//GEN-LAST:event_NomReg1KeyTyped

    private void apellidoRegKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_apellidoRegKeyTyped
    {//GEN-HEADEREND:event_apellidoRegKeyTyped
        // TODO add your handling code here:
        Validaciones.validaAlfabeticos(evt, 20, apellidoReg.getText());
    }//GEN-LAST:event_apellidoRegKeyTyped

    private void segApRegKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_segApRegKeyTyped
    {//GEN-HEADEREND:event_segApRegKeyTyped
        // TODO add your handling code here:
        Validaciones.validaAlfabeticos(evt, 20, segApReg.getText());
    }//GEN-LAST:event_segApRegKeyTyped

    private void cbxViveRItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_cbxViveRItemStateChanged
    {//GEN-HEADEREND:event_cbxViveRItemStateChanged
        // TODO add your handling code here:
        vc = cbxViveR.getSelectedIndex();

    }//GEN-LAST:event_cbxViveRItemStateChanged

    private void cbxCarrRItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_cbxCarrRItemStateChanged
    {//GEN-HEADEREND:event_cbxCarrRItemStateChanged
        // TODO add your handling code here: 
        ca = cbxCarrR.getSelectedIndex();

    }//GEN-LAST:event_cbxCarrRItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Registro.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Registro.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Registro.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Registro.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
//                try
//                {
//                    // Crear un JScrollPane y configurar las barras de desplazamient
//                    UIManager.setLookAndFeel(new MetalLookAndFeel());
//                } catch (UnsupportedLookAndFeelException ex)
//                {
//                    Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
//                }
                new Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelar;
    private javax.swing.JTextField CveRegistro;
    private javax.swing.JTextField NomReg1;
    private javax.swing.JTextField apellidoReg;
    private javax.swing.JComboBox<String> cbxCarrR;
    private javax.swing.JComboBox<String> cbxViveR;
    private javax.swing.JCheckBox chechMasculinoR;
    private javax.swing.JCheckBox checkFemeR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private javax.swing.JTextField segApReg;
    private javax.swing.JButton siguineteRA;
    // End of variables declaration//GEN-END:variables

    private void SetFocused(boolean b)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public JComboBox<String> getCbxCarrR()
    {
        return cbxCarrR;
    }

    public JComboBox<String> getCbxViveR()
    {
        return cbxViveR;
    }
    
    
}