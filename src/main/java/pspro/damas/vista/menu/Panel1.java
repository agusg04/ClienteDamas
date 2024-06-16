package pspro.damas.vista.menu;

import pspro.damas.controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Panel1 extends JPanel {

    private final Controlador controlador;
    private Map<Integer, String> usuariosDisponibles;
    private JList<String> listaUsuariosDisponibles;

    public Panel1(Controlador controlador) {
        this.controlador = controlador;
        SwingUtilities.invokeLater(this::inicializarComponentes);
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Configurar el JScrollPane con la JList
        listaUsuariosDisponibles = new JList<>();

        JScrollPane scrollUsuarios = new JScrollPane(listaUsuariosDisponibles);
        scrollUsuarios.setPreferredSize(new Dimension(300, 250)); // Tamaño personalizado
        //scrollUsuarios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollUsuarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollUsuarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Configurar la etiqueta de título
        JLabel labelUsuariosDisponibles = new JLabel("Usuarios Disponibles");
        labelUsuariosDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
        labelUsuariosDisponibles.setFont(new Font("Arial", Font.BOLD, 14));
        labelUsuariosDisponibles.setPreferredSize(new Dimension(120, 30));

        // Crear el panel central con BorderLayout
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(150, 100, 10, 40));
        panelCentral.add(labelUsuariosDisponibles, BorderLayout.NORTH);
        panelCentral.add(scrollUsuarios, BorderLayout.CENTER);

        // Crear el botón "Empezar Partida"
        JButton btnEmpezarPartida = new JButton("Empezar Partida");
        btnEmpezarPartida.setPreferredSize(new Dimension(160, 30)); // Tamaño personalizado
        btnEmpezarPartida.addActionListener(e -> empezaPartida());

        // Crear el panel para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.add(Box.createRigidArea(new Dimension(0, 100))); // Espacio entre el botón y la parte inferior
        panelBoton.add(Box.createVerticalGlue()); // Espacio en la parte superior
        panelBoton.add(btnEmpezarPartida);
        panelBoton.add(Box.createVerticalGlue());

        add(panelCentral, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.EAST);
    }

    public void actualizarUsuarios(Map<Integer, String> usuariosConectados) {
        usuariosDisponibles = usuariosConectados;
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String nombre : usuariosConectados.values()) {
            model.addElement(nombre);
        }
        listaUsuariosDisponibles.setModel(model);
    }

    public void empezaPartida() {
        String nombreSeleccionado = listaUsuariosDisponibles.getSelectedValue();
        if (nombreSeleccionado != null) {
            for (Map.Entry<Integer, String> entry : usuariosDisponibles.entrySet()) {
                if (entry.getValue().equals(nombreSeleccionado)) {
                    int idSeleccionado = entry.getKey();
                    controlador.iniciarPartida(idSeleccionado);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para empezar la partida.");
        }
    }




}
