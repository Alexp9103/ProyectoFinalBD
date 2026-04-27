package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.GestionEvento;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MostrarComision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JButton btnEliminar;
	private JTable table;
	private Comision comi;
	
	private static Object[] rows;
	private static DefaultTableModel model;
	private JButton btnTrabajos;
	private JButton btnJurados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MostrarComision dialog = new MostrarComision(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MostrarComision(ArrayList<Comision> prioridad) {
	
	    setTitle("Comisiones");
		setBounds(100, 100, 691, 373);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
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
					String[] columnas = {"C�digo","�rea","Presidente","Cantidad de jurados","Cantidad de trabajos"};
					model.setColumnIdentifiers(columnas);
					
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							int rowSelected = -1;
							rowSelected = table.getSelectedRow();
							if(rowSelected>=0){
							   btnEliminar.setEnabled(true);
							   btnJurados.setEnabled(true);
							   btnTrabajos.setEnabled(true);
							   comi = GestionEvento.getInstance().buscacomision(table.getValueAt(rowSelected, 0).toString());
							}
						}
					});
					scrollPane.setViewportView(table);
					
					table.setModel(model);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnJurados = new JButton("Jurados");
				btnJurados.setEnabled(false);
				btnJurados.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MostrarJurado aux = new MostrarJurado(comi.getJurados());
						aux.setModal(true);
						aux.setVisible(true);
						btnJurados.setEnabled(false);
					}
				});
				buttonPane.add(btnJurados);
			}
			{
				btnTrabajos = new JButton("Trabajos");
				btnTrabajos.setEnabled(false);
				btnTrabajos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MostrarTrabajo aux = new MostrarTrabajo(comi.getTrabajos());
						aux.setModal(true);
						aux.setVisible(true);
						btnTrabajos.setEnabled(false);
					}
				});
				buttonPane.add(btnTrabajos);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.setEnabled(false);
				btnEliminar.setActionCommand("OK");
				buttonPane.add(btnEliminar);
				getRootPane().setDefaultButton(btnEliminar);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("btnCancelar");
				buttonPane.add(cancelButton);
			}
		}
		
		cargardatos(prioridad);
	}
	
	public void cargardatos(ArrayList<Comision> prioridad) {
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		
		if(prioridad == null)
		{	
			System.out.println(GestionEvento.getInstance().getComisiones().size());
			for (int i = 0; i < GestionEvento.getInstance().getComisiones().size(); i++) {
				Comision c = GestionEvento.getInstance().getComisiones().get(i);
				rows[0] = c.getIdcomision();
				rows[1] = c.getArea();
				rows[2] = (c.getPresidente() != null) ? c.getPresidente().getNombre() : "-";
				rows[3] = c.getJurados().size();
				rows[4] = c.getTrabajos().size();
			   model.addRow(rows);
			}
		}
		
		else
		{
			for (Comision comision : prioridad) {
				rows[0] = comision.getIdcomision();
				rows[1] = comision.getArea();
				rows[2] = (comision.getPresidente() != null) ? comision.getPresidente().getNombre() : "-";
				rows[3] = comision.getJurados().size();
				rows[4] = comision.getTrabajos().size();
				model.addRow(rows);
			}
		}
		
	}

}