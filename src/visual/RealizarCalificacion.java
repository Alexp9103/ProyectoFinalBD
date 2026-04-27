package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.GestionEvento;
import logico.TrabajoCientifico;

public class RealizarCalificacion extends JDialog {

    private final JPanel contentPanel = new JPanel();

    private DefaultTableModel modelComision;
    private DefaultTableModel modelTrabajo;
    private JTable tableComision;
    private JTable tableTrabajo;

    private JSpinner spnCalificacion;
    private JButton btnCalificar;
    private JButton btnCerrar;

    private Comision comisionSeleccionada = null;
    private TrabajoCientifico trabajoSeleccionado = null;

    public RealizarCalificacion() {
        setTitle("Realizar Calificaci\u00F3n");
        setBounds(100, 100, 750, 450);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBackground(SystemColor.activeCaption);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(null);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // ---- Panel Comisiones ----
        JPanel panelComi = new JPanel();
        panelComi.setBorder(new TitledBorder("Comisiones"));
        panelComi.setLayout(new BorderLayout());
        panelComi.setBounds(10, 10, 330, 330);
        contentPanel.add(panelComi);

        modelComision = new DefaultTableModel();
        modelComision.setColumnIdentifiers(new String[]{"C\u00F3digo", "\u00C1rea", "Presidente"});
        tableComision = new JTable(modelComision);
        tableComision.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableComision.getSelectedRow();
                if (row >= 0) {
                    String idComi = modelComision.getValueAt(row, 0).toString();
                    comisionSeleccionada = GestionEvento.getInstance().buscacomision(idComi);
                    trabajoSeleccionado = null;
                    btnCalificar.setEnabled(false);
                    spnCalificacion.setValue(0.0);
                    cargarTrabajos();
                }
            }
        });
        panelComi.add(new JScrollPane(tableComision), BorderLayout.CENTER);

        // ---- Panel Trabajos ----
        JPanel panelTrab = new JPanel();
        panelTrab.setBorder(new TitledBorder("Trabajos de la comisi\u00F3n"));
        panelTrab.setLayout(new BorderLayout());
        panelTrab.setBounds(355, 10, 370, 330);
        contentPanel.add(panelTrab);

        modelTrabajo = new DefaultTableModel();
        modelTrabajo.setColumnIdentifiers(new String[]{"C\u00F3digo", "T\u00EDtulo", "Propietario", "Calificaci\u00F3n"});
        tableTrabajo = new JTable(modelTrabajo);
        tableTrabajo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableTrabajo.getSelectedRow();
                if (row >= 0 && comisionSeleccionada != null) {
                    String codTrab = modelTrabajo.getValueAt(row, 0).toString();
                    // Buscar directamente en la comision seleccionada (misma referencia que la tabla)
                    trabajoSeleccionado = null;
                    for (TrabajoCientifico t : comisionSeleccionada.getTrabajos()) {
                        if (t.getCodigo().equals(codTrab)) {
                            trabajoSeleccionado = t;
                            break;
                        }
                    }
                    // Resetear spinner y habilitar boton
                    spnCalificacion.setValue(0.0);
                    if (trabajoSeleccionado != null) {
                        spnCalificacion.setValue((double) trabajoSeleccionado.getCalificacion());
                        btnCalificar.setEnabled(true);
                    } else {
                        btnCalificar.setEnabled(false);
                    }
                }
            }
        });
        panelTrab.add(new JScrollPane(tableTrabajo), BorderLayout.CENTER);

        // ---- Panel inferior: nota + boton ----
        JPanel panelNota = new JPanel();
        panelNota.setLayout(null);
        panelNota.setBackground(SystemColor.activeCaption);
        panelNota.setBounds(10, 350, 715, 40);
        contentPanel.add(panelNota);

        JLabel lblNota = new JLabel("Calificaci\u00F3n (0 - 100):");
        lblNota.setFont(new Font("Candara", Font.BOLD, 13));
        lblNota.setBounds(0, 10, 160, 20);
        panelNota.add(lblNota);

        spnCalificacion = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.5));
        spnCalificacion.setBounds(165, 8, 90, 24);
        panelNota.add(spnCalificacion);

        btnCalificar = new JButton("Guardar calificaci\u00F3n");
        btnCalificar.setEnabled(false);
        btnCalificar.setBounds(270, 7, 185, 26);
        btnCalificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (trabajoSeleccionado == null) return;
                double nota = (double) spnCalificacion.getValue();
                GestionEvento.getInstance().calificarTrabajo(
                    trabajoSeleccionado.getCodigo(), (float) nota);
                JOptionPane.showMessageDialog(null,
                    "Calificaci\u00F3n guardada: " + nota,
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
                trabajoSeleccionado = null;
                btnCalificar.setEnabled(false);
                cargarTrabajos();   // refresca la nota en la tabla
            }
        });
        panelNota.add(btnCalificar);

        // ---- Botones inferiores ----
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCerrar);

        cargarComisiones();
    }

    private void cargarComisiones() {
        modelComision.setRowCount(0);
        for (Comision c : GestionEvento.getInstance().getComisiones()) {
            String presidente = (c.getPresidente() != null) ? c.getPresidente().getNombre() : "-";
            modelComision.addRow(new Object[]{c.getIdcomision(), c.getArea(), presidente});
        }
    }

    private void cargarTrabajos() {
        modelTrabajo.setRowCount(0);
        if (comisionSeleccionada == null) return;
        for (TrabajoCientifico t : comisionSeleccionada.getTrabajos()) {
            String propietario = (t.getPropietario() != null) ? t.getPropietario().getNombre() : "-";
            modelTrabajo.addRow(new Object[]{
                t.getCodigo(), t.getTitulo(), propietario, t.getCalificacion()
            });
        }
    }
}
