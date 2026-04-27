package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.border.TitledBorder;

import logico.GestionEvento;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private Dimension dim = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GestionEvento.cargarDatos("DatosEventosPUCMM.dat");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}  

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setTitle("GESTION EVENTO PUCMM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 365);
		dim = getToolkit().getScreenSize();
		setSize(dim.width, dim.height-40);
		setLocationRelativeTo(null);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
            }
		});

		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		menuBar.setBackground(SystemColor.activeCaption);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Persona");
		mnNewMenu.setBackground(SystemColor.activeCaption);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPersona aux = new RegistrarPersona(null);
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Mostrar Participante");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarParticipante aux = new MostrarParticipante();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Mostrar Jurado");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MostrarJurado aux = new MostrarJurado(null);
				aux.setModal(true);
				aux.setVisible(true);
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Trabajo");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar Trabajo");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarTrabajo aux = new RegistrarTrabajo(null);
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Mostrar Trabajo");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarTrabajo aux = new MostrarTrabajo(null);
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_2 = new JMenu("Comisiones");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Registrar");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarComision aux = new RegistrarComision();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Mostrar Comsiones");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarComision aux = new MostrarComision(null);
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_11);
		
		JMenu mnNewMenu_3 = new JMenu("Eventos");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Crear Evento");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrarEvento aux = new RegistrarEvento();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Mostrar Eventos");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarEvento aux = new MostrarEvento();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_7);
		
		JMenu mnNewMenu_4 = new JMenu("Recursos");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("A\u00F1adir Recursos");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				RegistrarRecurso aux= new RegistrarRecurso();
				aux.setModal(true);
				aux.setVisible(true);
			
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_8);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Mostrar Recurso");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarRecurso aux = new MostrarRecurso(null);
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_9);
		
		JMenu mnNewMenu_5 = new JMenu("Calificaciones");
		menuBar.add(mnNewMenu_5);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Realizar Calificaci\u00F3n");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RealizarCalificacion aux = new RealizarCalificacion();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_10);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}