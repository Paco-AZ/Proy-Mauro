/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import modelo.conexion.DBConnection;
import modelo.seguridad.SesionActual;
import vista.LoginDialog;
import vista.Principal;

/**
 *
 * @author HP
 */
public class Main
{

    public static void main(String[] args)
    {
        /* Nimbus */
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                // Mostrar login primero
                LoginDialog login = new LoginDialog(null, true);

                login.setVisible(true);

                // Verificar autenticación
                if (login.isAutenticado())
                {
                    SesionActual.getInstance().iniciarSesion(login.getIdUsuario(), login.getNombre(), login.getRol());
                    Principal principal = new Principal();
                    principal.setVisible(true);
                } else
                {
                    System.exit(0);
                }
            }
        });
    }
}
