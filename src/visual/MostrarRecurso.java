package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.GestionEvento;
import logico.Recurso;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MostrarRecurso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnCancelar;
	private JButton btnEliminar;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private Recurso recur;
	
	private static Object[] rows;
	private static DefaultTableModel model;
	private JButton btnDescrip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MostrarRecurso dialog = new MostrarRecurso(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MostrarRecurso(ArrayList<Recurso> recaux) {
		setTitle("Recursos");
		setBounds(100, 100, 544, 349);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					model = new DefaultTableModel();
					String[] columnas = {"Código","Tipo","Disponibilidad","Ubicación"};
					model.setColumnIdentifiers(columnas);
					
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							int rowSelected = -1;
							rowSelected = table.getSelectedRow();
							if(rowSelected>=0){
							   btnEliminar.setEnabled(true);
							   btnDescrip.setEnabled(true);
							   recur = GestionEvento.getInstance().buscarrecurso(table.getValueAt(rowSelected, 0).toString());
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
			buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.setEnabled(false);
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GestionEvento.getInstance().eliminarRecurso(recur.getCodigo());
						JOptionPane.showMessageDialog(null, "Recurso eliminado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						cargardatos(recaux);
						GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
						btnEliminar.setEnabled(false);
					}
				});
				btnEliminar.setActionCommand("OK");
				buttonPane.add(btnEliminar);
				getRootPane().setDefaultButton(btnEliminar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				{
					btnDescrip = new JButton("Descripci\u00F3n");
					btnDescrip.setEnabled(false);
					btnDescrip.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(null, recur.getDescripcion(), "Descripción", JOptionPane.INFORMATION_MESSAGE);
							  btnDescrip.setEnabled(false);
						}
					});
					buttonPane.add(btnDescrip);
				}
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		cargardatos(recaux);
	}
	
	public void cargardatos(ArrayList<Recurso> recaux) {
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		
		if(recaux == null)
		{
			for (int i = 0; i < GestionEvento.getInstance().getRecursos().size(); i++) {
				  
				rows[0] = GestionEvento.getInstance().getRecursos().get(i).getCodigo();
				rows[1] = GestionEvento.getInstance().getRecursos().get(i).getTipo();
				
				if(GestionEvento.getInstance().getRecursos().get(i).getdisponible() == true)
					rows[2] = "Disponible";
				else
					rows[2] = "No disponible";
				
				rows[3] = GestionEvento.getInstance().getRecursos().get(i).getUbicacion();
			   
			   model.addRow(rows);
			}
		}
			
		else
		{
			for (int i = 0; i < recaux.size(); i++) {
				  
				rows[0] = recaux.get(i).getCodigo();
				rows[1] = recaux.get(i).getTipo();
				rows[2] = "No disponible";
				rows[3] = recaux.get(i).getUbicacion();
			   
			   model.addRow(rows);
			}
		}
			
	}

}


