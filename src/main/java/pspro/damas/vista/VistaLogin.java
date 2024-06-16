package pspro.damas.vista;

import pspro.damas.controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VistaLogin extends JFrame {

    private final Controlador controlador;
    private JPasswordField campoContrasenia;
    private JTextField campoUsuario;

    public VistaLogin(Controlador controlador) {
        this.controlador = controlador;
        SwingUtilities.invokeLater(this::inicializarComponentes);
    }

    public void inicializarComponentes() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Damas");
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Creando un panel principal
        JPanel panelPrincipal = new JPanel();
        getContentPane().add(panelPrincipal);
        panelPrincipal.setLayout(null);

        // Creando el titulo
        JLabel titulo = new JLabel("Inicie sesión o Registrese");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
        titulo.setBounds(137, 38, 311, 40);
        panelPrincipal.add(titulo);

        // Creando el icono de usuario
        JLabel iconoUsuario = new JLabel();
        iconoUsuario.setBounds(137, 152, 40, 40);
        ImageIcon imagenUsuario = new ImageIcon("src/main/resources/user.png");
        iconoUsuario.setIcon(new ImageIcon(imagenUsuario.getImage().getScaledInstance(iconoUsuario.getWidth(),
                iconoUsuario.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        panelPrincipal.add(iconoUsuario);

        // Creando el textfield de usuario
        campoUsuario = new JTextField();
        campoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
        iconoUsuario.setLabelFor(campoUsuario);
        campoUsuario.setBounds(185, 160, 215, 32);
        panelPrincipal.add(campoUsuario);
        campoUsuario.setColumns(10);

        // Creando el icono de contrasenia
        JLabel iconoContrasenia = new JLabel();
        ImageIcon imagenCandado = new ImageIcon("src/main/resources/bloquear.png");
        iconoContrasenia.setIcon(
                new ImageIcon(imagenCandado.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH)));
        iconoContrasenia.setBounds(137, 249, 45, 58);
        panelPrincipal.add(iconoContrasenia);

        // Creando el textfield de la contrasenia
        campoContrasenia = new JPasswordField();
        campoContrasenia.setFont(new Font("Tahoma", Font.PLAIN, 15));
        campoContrasenia.setColumns(10);
        campoContrasenia.setBounds(185, 260, 215, 32);
        panelPrincipal.add(campoContrasenia);

        // Creando el boton para iniciar sesion
        JButton btnInicioSesion = new JButton("Iniciar Sesion");
        btnInicioSesion.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnInicioSesion.setBounds(110, 423, 138, 40);
        panelPrincipal.add(btnInicioSesion);
        btnInicioSesion.addActionListener(e ->
            controlador.iniciarSesion()
        );

        // Creando el boton para registrarse
        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegistro.setBounds(340, 423, 138, 40);
        panelPrincipal.add(btnRegistro);
        btnRegistro.addActionListener(e ->
                controlador.registrarse()
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.finalizarPrograma();
            }
        });
    }

    public void mostrarVentana() {
        setVisible(true);
    }

    public void cerrarVentana() {
        dispose();
    }

    public String getNombreUsuario() {
        return campoUsuario.getText();
    }

    public String getContrasenia() {
        return new String(campoContrasenia.getPassword());
    }

    public void vaciarCampos() {
        campoUsuario.setText("");
        campoContrasenia.setText("");

    }

    public void mostrarDialogoLoginExitoso() {
        JOptionPane.showMessageDialog(VistaLogin.this, "Inicio de sesión exitoso. Bienvenido " + campoUsuario.getText());
    }

    public void mostrarDialogoFallido(String mensajeError) {
        JOptionPane.showMessageDialog(VistaLogin.this, mensajeError);
    }

    public void mostrarDialogoRegistroExitoso() {
        JOptionPane.showMessageDialog(VistaLogin.this, "Registro exitoso. Inicie sesión " + campoUsuario.getText());
    }

}
