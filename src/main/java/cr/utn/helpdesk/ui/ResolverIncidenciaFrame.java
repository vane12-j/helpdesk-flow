package cr.utn.helpdesk.ui;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.service.IncidenciaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResolverIncidenciaFrame extends JFrame {

    private final IncidenciaService service;
    private final Incidencia incidencia;
    private final DefaultTableModel modeloTabla;
    private final int fila;

    private JTextField txtTitulo;
    private JTextField txtEstado;
    private JTextField txtFechaCreacion;
    private JTextField txtFechaCierre;

    private JTextArea txtSolucion;

    private JComboBox<Estado> cbEstado;

    private JButton btnActualizar;
    private JButton btnCerrar;

    public ResolverIncidenciaFrame(JFrame padre,
                                   IncidenciaService service,
                                   Incidencia incidencia,
                                   DefaultTableModel modeloTabla,
                                   int fila) {

        this.service = service;
        this.incidencia = incidencia;
        this.modeloTabla = modeloTabla;
        this.fila = fila;

        setTitle("Resolver incidencia");
        setSize(550,500);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        agregarComponentes();
        configurarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        txtTitulo = new JTextField(25);
        txtTitulo.setEditable(false);

        txtEstado = new JTextField(20);
        txtEstado.setEditable(false);

        txtFechaCreacion = new JTextField(20);
        txtFechaCreacion.setEditable(false);

        txtFechaCierre = new JTextField(20);
        txtFechaCierre.setEditable(false);

        txtSolucion = new JTextArea(5,25);
        txtSolucion.setLineWrap(true);
        txtSolucion.setWrapStyleWord(true);

        cbEstado = new JComboBox<>();

        cargarSiguienteEstado();

        txtTitulo.setText(incidencia.getTitulo());
        txtEstado.setText(incidencia.getEstado().toString());
        txtFechaCreacion.setText(incidencia.getFechaCreacion().toString());

        if (incidencia.getFechaCierre() != null) {
            txtFechaCierre.setText(
                    incidencia.getFechaCierre().toString());
        }

        if (incidencia.getSolucion() != null) {
            txtSolucion.setText(
                    incidencia.getSolucion());
        }

        btnActualizar = new JButton("Actualizar estado");
        btnCerrar = new JButton("Cerrar incidencia");

        cbEstado = new JComboBox<>();

        cargarSiguienteEstado();

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


    private void agregarComponentes() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Estado actual:"), gbc);

        gbc.gridx = 1;
        panel.add(txtEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fecha creación:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFechaCreacion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fecha cierre:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFechaCierre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nuevo estado:"), gbc);

        gbc.gridx = 1;
        panel.add(cbEstado, gbc);

        gbc.gridx = 2;
        panel.add(btnActualizar, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Solución:"), gbc);

        gbc.gridx = 1;
        panel.add(new JScrollPane(txtSolucion), gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        panel.add(btnCerrar, gbc);

        add(panel);

    }

    private void cargarSiguienteEstado() {

        cbEstado.removeAllItems();

        switch (incidencia.getEstado()) {

            case REGISTRADA:
                cbEstado.addItem(Estado.LISTA);
                break;

            case LISTA:
                cbEstado.addItem(Estado.EN_DESARROLLO);
                break;

            case EN_DESARROLLO:
                cbEstado.addItem(Estado.EN_VALIDACION);
                break;

            case EN_VALIDACION:
                cbEstado.addItem(Estado.FINALIZADA);
                break;

            case FINALIZADA:
                btnActualizar.setEnabled(false);
                btnCerrar.setEnabled(false);
                txtSolucion.setEditable(false);
                break;
        }
    }

    private void configurarEventos() {

        btnActualizar.addActionListener(e -> actualizarEstado());

        btnCerrar.addActionListener(e -> cerrarIncidencia());

    }

    private void actualizarEstado() {

        try {

            Estado nuevoEstado = (Estado) cbEstado.getSelectedItem();

            service.cambiarEstado(
                    incidencia.getId(),
                    nuevoEstado,
                    null
            );

            txtEstado.setText(
                    incidencia.getEstado().toString()
            );

            modeloTabla.setValueAt(
                    incidencia.getEstado(),
                    fila,
                    3
            );

            cargarSiguienteEstado();
            if (incidencia.getEstado() == Estado.EN_VALIDACION) {

                btnActualizar.setEnabled(false);
                btnCerrar.setEnabled(true);

            }

            JOptionPane.showMessageDialog(
                    this,
                    "Estado actualizado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    private void cerrarIncidencia() {

        try {

            if (txtSolucion.getText().isBlank()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe escribir la solución."
                );

                return;

            }

            service.cambiarEstado(
                    incidencia.getId(),
                    Estado.FINALIZADA,
                    txtSolucion.getText()
            );

            txtEstado.setText(
                    incidencia.getEstado().toString()
            );

            txtFechaCierre.setText(
                    incidencia.getFechaCierre().toString()
            );

            modeloTabla.setValueAt(
                    incidencia.getEstado(),
                    fila,
                    3
            );

            modeloTabla.setValueAt(
                    incidencia.getFechaCierre(),
                    fila,
                    6
            );

            btnActualizar.setEnabled(false);
            btnCerrar.setEnabled(false);
            txtSolucion.setEditable(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Incidencia finalizada correctamente."
            );
            dispose();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }
}