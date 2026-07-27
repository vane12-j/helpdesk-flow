package cr.utn.helpdesk.ui;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.service.IncidenciaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResolverIncidenciaFrame extends JFrame {

private final IncidenciaService service; // Servicio que realiza las operaciones sobre las incidencias.
private final Incidencia incidencia; // Incidencia seleccionada para trabajar.
    // Modelo de la tabla del formulario principal para actualizar los datos visualmente.
private final DefaultTableModel modeloTabla;

private final int fila; // Fila de la tabla donde se encuentra la incidencia.
private JTextField txtTitulo; // Campos de texto para mostrar información de la incidencia.
private JTextField txtEstado;
private JTextField txtFechaCreacion;
private JTextField txtFechaCierre;
private JTextArea txtSolucion; // Área donde el usuario escribe la solución de la incidencia.
private JComboBox<Estado> cbEstado; // ComboBox que permite seleccionar el siguiente estado permitido.
private JButton btnActualizar; // Botones para actualizar el estado y cerrar definitivamente la incidencia.
private JButton btnCerrar;

    // Constructor de la ventana.
    public ResolverIncidenciaFrame(JFrame padre, IncidenciaService service, Incidencia incidencia, DefaultTableModel modeloTabla, int fila) {
      // Guarda las referencias recibidas.
        this.service = service;
        this.incidencia = incidencia;
        this.modeloTabla = modeloTabla;
        this.fila = fila;

      // Configuración básica de la ventana.
        setTitle("Resolver incidencia");
        setSize(550,500);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();// Inicializa los componentes.

        agregarComponentes(); // Agrega los componentes a la ventana.
        configurarEventos(); // Configura los eventos de los botones.
        setVisible(true); // Hace visible la ventana.
    }
    // Crea e inicializa todos los componentes gráficos.
    private void inicializarComponentes() {

        // Campo del título (solo lectura).
        txtTitulo = new JTextField(25);
        txtTitulo.setEditable(false);

        // Campo del estado actual (solo lectura).
        txtEstado = new JTextField(20);
        txtEstado.setEditable(false);

        // Campo de la fecha de creación (solo lectura).
        txtFechaCreacion = new JTextField(20);
        txtFechaCreacion.setEditable(false);

        // Campo de la fecha de cierre (solo lectura).
        txtFechaCierre = new JTextField(20);
        txtFechaCierre.setEditable(false);

        // Área para escribir la solución.
        txtSolucion = new JTextArea(5,25);
        txtSolucion.setLineWrap(true);
        txtSolucion.setWrapStyleWord(true);

        // ComboBox donde aparecerá únicamente el siguiente estado permitido.
        cbEstado = new JComboBox<>();

        // Llena el ComboBox con el siguiente estado disponible.
        cargarSiguienteEstado();

        // Muestra los datos actuales de la incidencia.
        txtTitulo.setText(incidencia.getTitulo());
        txtEstado.setText(incidencia.getEstado().toString());
        txtFechaCreacion.setText(incidencia.getFechaCreacion().toString());

        // Si ya existe fecha de cierre la muestra.
        if (incidencia.getFechaCierre() != null) {
            txtFechaCierre.setText(
                    incidencia.getFechaCierre().toString());
        }

        // Si ya existe una solución la muestra.
        if (incidencia.getSolucion() != null) {
            txtSolucion.setText(
                    incidencia.getSolucion());
        }

        // Crea los botones.
        btnActualizar = new JButton("Actualizar estado");
        btnCerrar = new JButton("Cerrar incidencia");

        // Se vuelve a crear el ComboBox.
        cbEstado = new JComboBox<>();

        // Se vuelve a cargar el siguiente estado.
        cargarSiguienteEstado();

        // Se vuelven a cargar los datos de la incidencia.
        txtTitulo.setText(incidencia.getTitulo());
        txtEstado.setText(incidencia.getEstado().toString());
        txtFechaCreacion.setText(incidencia.getFechaCreacion().toString());

        if (incidencia.getFechaCierre() != null) {
            txtFechaCierre.setText(incidencia.getFechaCierre().toString());
        }

        if (incidencia.getSolucion() != null) {
            txtSolucion.setText(incidencia.getSolucion());
        }
    }


    // Organiza todos los componentes dentro de la ventana.
    private void agregarComponentes() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta y campo del título.
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);

        // Etiqueta y campo del estado actual.
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Estado actual:"), gbc);

        gbc.gridx = 1;
        panel.add(txtEstado, gbc);

        // Etiqueta y campo de la fecha de creación.
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fecha creación:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFechaCreacion, gbc);

        // Etiqueta y campo de la fecha de cierre.
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fecha cierre:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFechaCierre, gbc);

        // Etiqueta y ComboBox del nuevo estado.
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nuevo estado:"), gbc);

        gbc.gridx = 1;
        panel.add(cbEstado, gbc);

        // Botón para actualizar el estado.
        gbc.gridx = 2;
        panel.add(btnActualizar, gbc);

        // Área para escribir la solución.
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Solución:"), gbc);

        gbc.gridx = 1;
        panel.add(new JScrollPane(txtSolucion), gbc);

        // Botón para cerrar la incidencia.
        gbc.gridx = 1;
        gbc.gridy++;
        panel.add(btnCerrar, gbc);

        add(panel);

    }

    // Carga únicamente el siguiente estado permitido según el estado actual.
    private void cargarSiguienteEstado() {

        // Limpia el ComboBox.
        cbEstado.removeAllItems();

        switch (incidencia.getEstado()) {

            // Si está registrada pasa a LISTA.
            case REGISTRADA:
                cbEstado.addItem(Estado.LISTA);
                break;

            // Si está en LISTA pasa a EN_DESARROLLO.
            case LISTA:
                cbEstado.addItem(Estado.EN_DESARROLLO);
                break;

            // Si está en desarrollo pasa a validación.
            case EN_DESARROLLO:
                cbEstado.addItem(Estado.EN_VALIDACION);
                break;

            // Si está en validación permite finalizarla.
            case EN_VALIDACION:
                cbEstado.addItem(Estado.FINALIZADA);
                break;

            // Si ya está finalizada deshabilita los controles.
            case FINALIZADA:

                if (btnActualizar != null) {
                    btnActualizar.setEnabled(false);
                }
                if (btnCerrar != null) {
                    btnCerrar.setEnabled(false);
                }
                txtSolucion.setEditable(false);
                break;
        }

        // El botón de cerrar solo se habilita cuando la incidencia está en validación.
        if (btnCerrar != null) {
            btnCerrar.setEnabled(
                    incidencia.getEstado() == Estado.EN_VALIDACION
            );
        }
    }

    // Asocia las acciones de los botones con sus respectivos métodos.
    private void configurarEventos() {

        btnActualizar.addActionListener(e -> actualizarEstado());

        btnCerrar.addActionListener(e -> cerrarIncidencia());

    }

    // Actualiza el estado de la incidencia.
    private void actualizarEstado() {

        try {

            // Obtiene el estado seleccionado.
            Estado nuevoEstado = (Estado) cbEstado.getSelectedItem();

            // Llama al servicio para cambiar el estado.
            service.cambiarEstado(
                    incidencia.getId(),
                    nuevoEstado,
                    null
            );

            // Actualiza el campo de estado.
            txtEstado.setText(
                    incidencia.getEstado().toString()
            );

            // Actualiza la tabla principal.
            modeloTabla.setValueAt(
                    incidencia.getEstado(),
                    fila,
                    3
            );

            // Recarga el siguiente estado disponible.
            cargarSiguienteEstado();

            // Si llegó a validación, deshabilita actualizar y habilita cerrar.
            if (incidencia.getEstado() == Estado.EN_VALIDACION) {

                btnActualizar.setEnabled(false);
                btnCerrar.setEnabled(true);

            }

            // Mensaje de confirmación.
            JOptionPane.showMessageDialog(
                    this,
                    "Estado actualizado correctamente."
            );

        } catch (Exception ex) {

            // Si ocurre un error se muestra al usuario.
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    // Finaliza la incidencia guardando la solución y la fecha de cierre.
    private void cerrarIncidencia() {

        try {

            // Valida que la solución no esté vacía.
            if (txtSolucion.getText().isBlank()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe escribir la solución."
                );

                return;

            }

            // Cambia el estado a FINALIZADA y guarda la solución.
            service.cambiarEstado(
                    incidencia.getId(),
                    Estado.FINALIZADA,
                    txtSolucion.getText()
            );

            // Actualiza el estado mostrado.
            txtEstado.setText(
                    incidencia.getEstado().toString()
            );

            // Muestra la fecha de cierre.
            txtFechaCierre.setText(
                    incidencia.getFechaCierre().toString()
            );

            // Actualiza el estado en la tabla principal.
            modeloTabla.setValueAt(
                    incidencia.getEstado(),
                    fila,
                    3
            );

            // Actualiza la fecha de cierre en la tabla principal.
            modeloTabla.setValueAt(
                    incidencia.getFechaCierre(),
                    fila,
                    6
            );

            // Deshabilita los botones para evitar más modificaciones.
            btnActualizar.setEnabled(false);
            btnCerrar.setEnabled(false);
            txtSolucion.setEditable(false);

            // Mensaje de confirmación.
            JOptionPane.showMessageDialog(
                    this,
                    "Incidencia finalizada correctamente."
            );

            // Cierra la ventana.
            dispose();

        } catch (Exception ex) {

            // Muestra cualquier error ocurrido.
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }
}