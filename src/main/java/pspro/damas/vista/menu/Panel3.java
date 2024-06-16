package pspro.damas.vista.menu;

import modelo.PartidaTerminada;
import pspro.damas.controlador.Controlador;
import pspro.damas.vista.tablero.RepeticionFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Panel3 extends JPanel {

    HashMap<Integer, PartidaTerminada> repeticionesDisponibles;
    HashMap<Integer, PartidaTerminada> repeticionesCreadas = new HashMap<>();
    private final Controlador controlador;
    private JList<String> listaReplays;


    public Panel3(Controlador controlador) {
        this.controlador = controlador;
        SwingUtilities.invokeLater(this::inicializarComponentes);
        setVisible(true);
    }

    private void inicializarComponentes() {
        JButton btnCargarReplay;

        // Inicializar las listas
        listaReplays = new JList<>();

        // Scroll para partidas
        JScrollPane scrollPartidas = new JScrollPane(listaReplays);
        scrollPartidas.setPreferredSize(new Dimension(300, 250));
        scrollPartidas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPartidas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Etiquetas para las listas
        JLabel labelPartidas = new JLabel("Repeticiones A Cargar");
        labelPartidas.setHorizontalAlignment(SwingConstants.CENTER);
        labelPartidas.setFont(new Font("Arial", Font.BOLD, 14));
        labelPartidas.setPreferredSize(new Dimension(120, 30));

        // Crear panel para las listas con etiquetas
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(150, 160, 10, 40));
        panelCentral.add(labelPartidas, BorderLayout.NORTH);
        panelCentral.add(scrollPartidas, BorderLayout.CENTER);

        // Botón para cargar partida
        btnCargarReplay = new JButton("Cargar Partida");
        btnCargarReplay.setPreferredSize(new Dimension(200, 30)); // Tamaño personalizado
        btnCargarReplay.addActionListener(e -> cargarRepeticionSeleccionada());

        // Crear panel para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.add(Box.createVerticalStrut(100)); // Espacio flexible antes del botón
        panelBoton.add(btnCargarReplay);
        panelBoton.add(Box.createVerticalGlue()); // Espacio flexible después del botón

        // Agregar los paneles al Panel2
        add(panelCentral, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.EAST);
    }

    public void cargarRepeticiones(Map<Integer, PartidaTerminada> repeticionesDisponiblesPasadas) {
        repeticionesDisponibles = (HashMap<Integer, PartidaTerminada>) repeticionesDisponiblesPasadas;
        listaReplays.setModel(crearModeloLista(repeticionesDisponibles));
    }

    private DefaultListModel<String> crearModeloLista(HashMap<Integer, PartidaTerminada> repeticiones) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (PartidaTerminada partidaTerminada : repeticiones.values()) {
            model.addElement("Partida " + partidaTerminada.getIdPartida());
        }
        return model;
    }

    public void cargarRepeticionSeleccionada() {
        String nombreSeleccionado = listaReplays.getSelectedValue();

        if (nombreSeleccionado == null) {
            mostrarMensajeError("Seleccione una partida.");
            return;
        }

        Integer idPartida = extraerIdTablero(nombreSeleccionado);
        if (idPartida == null) {
            mostrarMensajeError("El nombre seleccionado no tiene el formato correcto.");
            return;
        }

        if (estaYaCreado(idPartida)) {
            mostrarMensajeError("La repetición ya está abierto");
            return;
        }

        PartidaTerminada partidaTerminada = encontrarPartidaPorId(idPartida);
        if (partidaTerminada == null) {
            mostrarMensajeError("No se pudo cargar la partida.");
            return;
        }

        abrirPartida(partidaTerminada);
        repeticionesCreadas.put(partidaTerminada.getIdPartida(), partidaTerminada);
    }
    private Integer extraerIdTablero(String nombreSeleccionado) {
        String[] partes = nombreSeleccionado.split(" ");
        if (partes.length > 1) {
            try {
                return Integer.parseInt(partes[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private boolean estaYaCreado(int idPartida) {
        return repeticionesCreadas.containsKey(idPartida);
    }

    private PartidaTerminada encontrarPartidaPorId(int idTablero) {
        return repeticionesDisponibles.get(idTablero);
    }

    private void abrirPartida(PartidaTerminada partidaTerminada) {
        RepeticionFrame repeticionFrame = new RepeticionFrame(partidaTerminada, this);
        if (repeticionFrame != null) {
            repeticionFrame.setVisible(true);
        } else {
            mostrarMensajeError("No se pudo cargar la partida.");
        }
    }

    public void cerrarTablero(PartidaTerminada partidaTerminadaAbierta) {
        if (partidaTerminadaAbierta == null) {
            mostrarMensajeError("Partida nula, no se puede cerrar el tablero.");
            return;
        }

        Integer idPartida = partidaTerminadaAbierta.getIdPartida();

        if (idPartida == null) {
            mostrarMensajeError("ID de partida no válido.");
            return;
        }

        repeticionesCreadas.remove(idPartida);
    }


    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
