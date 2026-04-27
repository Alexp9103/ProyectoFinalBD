package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.GestionEvento;
import logico.TrabajoCientifico;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;

public class MostrarTrabajo extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private TrabajoCientifico trabajocientifico;

    

    private static Object[] rows;
    private static DefaultTableModel model;
    private JButton btnCerrar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            MostrarTrabajo dialog = new MostrarTrabajo(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public MostrarTrabajo(ArrayList<TrabajoCientifico> prioridad) {
        setTitle("Trabajos");
        setBounds(100, 100, 705, 341);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(new BorderLayout(0, 0));
            {
                JScrollPane scrollPane = new JScrollPane();
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    model = new DefaultTableModel();
                    String[] columnas = {"C�digo", "T�tulo", "Propietario", "Calificaci�n"};
                    model.setColumnIdentifiers(columnas);

                    table = new JTable();
                    scrollPane.setViewportView(table);

                    table.setModel(model);
                }
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new CompoundBorder());
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                btnCerrar = new JButton("Cerrar");
                btnCerrar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                btnCerrar.setActionCommand("Cerrar");
                buttonPane.add(btnCerrar);
            }
        }
        cargardatos(prioridad);
    }

    public void cargardatos(ArrayList<TrabajoCientifico> prioridad) {
        model.setRowCount(0);
        rows = new Object[model.getColumnCount()];

        for (TrabajoCientifico trab : GestionEvento.getInstance().getTrabajos()) {
            rows[0] = trab.getCodigo();
            rows[1] = trab.getTitulo();
            rows[2] = (trab.getPropietario() != null) ? trab.getPropietario().getNombre() : "-";
            rows[3] = trab.getCalificacion();
            model.addRow(rows);
        }
    }
}
