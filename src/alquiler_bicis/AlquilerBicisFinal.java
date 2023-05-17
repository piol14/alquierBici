//Jaime Roselló y Elena Ortega

package alquiler_bicis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class AlquilerBicisFinal {

	/**
	 * Constantes del tamaño de los componentes usados
	 */
	static final int LONGITUD_BTN_PRINCIPAL = 175;
	static final int ALTURA_BTN_PRINCIPAL = 60;
	static final int LONGITUD_BTN_SECUNDARIO = 240;
	static final int ALTURA_BTN_SECUNDARIO = 40;
	static final int LONGITUD_TABLE_SECUNDARIA = 240;
	static final int LONGITUD_TABLE_ACTUALIZAR = 450;
	static final int ALTURA_TABLE = 180;
	static final int LONGITUD_LBL_PRINCIPAL = 200;
	static final int ALTURA_LBL_PRINCIPAL = 25;
	static final int LONGITUD_LBL_SECUNDARIO = 120;
	static final int ALTURA_LBL_SECUNDARIO = 20;
	static final int ALTURA_TF_CB = 25;

	private JFrame frmAlquilerDeBicis;
	private JTextField tfCodBike;
	private JTextField tfCodUser;
	private JTextField tfName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlquilerBicisFinal window = new AlquilerBicisFinal();
					window.frmAlquilerDeBicis.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AlquilerBicisFinal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAlquilerDeBicis = new JFrame();
		frmAlquilerDeBicis
				.setIconImage(Toolkit.getDefaultToolkit().getImage(AlquilerBicisFinal.class.getResource("/img/bike.png")));
		frmAlquilerDeBicis.setResizable(false);
		frmAlquilerDeBicis.setTitle("Alquiler de bicis");
		frmAlquilerDeBicis.setBounds(100, 100, 1120, 630);
		frmAlquilerDeBicis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlquilerDeBicis.getContentPane().setLayout(null);

		/**
		 * Titulo bicicletas
		 */
		JLabel lblBikes = new JLabel("BICICLETAS:");
		lblBikes.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBikes.setBounds(50, 40, LONGITUD_LBL_PRINCIPAL, ALTURA_LBL_PRINCIPAL);
		frmAlquilerDeBicis.getContentPane().add(lblBikes);

		/**
		 * Tabla bicicletas
		 */
		DefaultTableModel bicis = new DefaultTableModel();
		bicis.addColumn("ID Bici");
		bicis.addColumn("Estado");

		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM bici WHERE idbici != 0");
			ResultSet rs_sel = sel_pstmt.executeQuery();
			while (rs_sel.next()) {
				Object[] row = new Object[2];
				row[0] = rs_sel.getInt("idbici");
				int estado = Integer.parseInt(rs_sel.getString("estado"));
				if (estado == 0) {
					row[1] = "Libre";
				} else {
					row[1] = "Ocupado";
				}
				bicis.addRow(row);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmAlquilerDeBicis.getContentPane().setLayout(null);

		JTable tableBikes = new JTable(bicis);
		tableBikes.setBounds(1, 1, 600, 0);
		tableBikes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frmAlquilerDeBicis.getContentPane().add(tableBikes);

		JScrollPane scrollPaneBikes = new JScrollPane(tableBikes);
		scrollPaneBikes.setBounds(50, 80, LONGITUD_TABLE_SECUNDARIA, ALTURA_TABLE);
		frmAlquilerDeBicis.getContentPane().add(scrollPaneBikes);

		/**
		 * Botón mostrar bicicletas
		 */
		JButton btnShowBikes = new JButton("  MOSTRAR BICICLETAS");
		btnShowBikes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					bicis.setRowCount(0);
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM bici WHERE idbici != 0");
					ResultSet rs_sel = sel_pstmt.executeQuery();
					while (rs_sel.next()) {
						Object[] row = new Object[2];
						row[0] = rs_sel.getInt("idbici");
						
						int estado = Integer.parseInt(rs_sel.getString("estado"));
						if (estado == 0) {
							row[1] = "Libre";
						} else {
							row[1] = "Ocupado";
						}
						bicis.addRow(row);
					}

					rs_sel.close();
					sel_pstmt.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "No se pueden mostrar los cambios.", "¡Error!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnShowBikes.setBorder(null);
		btnShowBikes.setBackground(new Color(195, 195, 195));
		btnShowBikes.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/bike30.png")));
		btnShowBikes.setFont(new Font("Dialog", Font.BOLD, 14));
		btnShowBikes.setBounds(50, 290, LONGITUD_BTN_SECUNDARIO, ALTURA_BTN_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(btnShowBikes);

		/**
		 * Titulo usuarios
		 */
		JLabel lblUsers = new JLabel("USUARIOS:");
		lblUsers.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUsers.setBounds(385, 40, LONGITUD_LBL_PRINCIPAL, ALTURA_LBL_PRINCIPAL);
		frmAlquilerDeBicis.getContentPane().add(lblUsers);

		/**
		 * Tabla usuarios
		 */
		DefaultTableModel usuarios = new DefaultTableModel();
		usuarios.addColumn("ID");
		usuarios.addColumn("Nombre");
		usuarios.addColumn("ID Bici");

		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM usuario");
			ResultSet rs_sel = sel_pstmt.executeQuery();
			while (rs_sel.next()) {
				Object[] row = new Object[3];
				row[0] = rs_sel.getInt("idusuario");
				row[1] = rs_sel.getString("nombre");
				row[2] = rs_sel.getInt("bici_idbici");
				usuarios.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmAlquilerDeBicis.getContentPane().setLayout(null);

		JTable tableUsers = new JTable(usuarios);
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int index = tableUsers.getSelectedRow();
				TableModel model = tableUsers.getModel();
			
				tfCodUser.setText(model.getValueAt(index, 0).toString());
				tfName.setText(model.getValueAt(index, 1).toString());
			}
		});
		tableUsers.setBounds(1, 1, 600, 0);
		tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frmAlquilerDeBicis.getContentPane().add(tableUsers);

		JScrollPane scrollPaneUsers = new JScrollPane(tableUsers);
		scrollPaneUsers.setBounds(385, 80, LONGITUD_TABLE_SECUNDARIA, ALTURA_TABLE);
		frmAlquilerDeBicis.getContentPane().add(scrollPaneUsers);

		/**
		 * Botón mostrar usuarios
		 */
		JButton btnShowUsers = new JButton("  MOSTRAR USUARIOS");
		btnShowUsers.setBorder(null);
		btnShowUsers.setBackground(new Color(195, 195, 195));
		btnShowUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					usuarios.setRowCount(0);
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM usuario");
					ResultSet rs_sel = sel_pstmt.executeQuery();
					while (rs_sel.next()) {
						Object[] row = new Object[3];
						row[0] = rs_sel.getInt("idusuario");
						row[1] = rs_sel.getString("nombre");
						row[2] = rs_sel.getInt("bici_idbici");
						usuarios.addRow(row);
					}

					rs_sel.close();
					sel_pstmt.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "No se pueden mostrar los cambios.", "¡Error!",
							JOptionPane.ERROR_MESSAGE);
				}
				scrollPaneUsers.setVisible(true);
			}
		});
		btnShowUsers.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/user.png")));
		btnShowUsers.setFont(new Font("Dialog", Font.BOLD, 14));
		btnShowUsers.setBounds(385, 290, LONGITUD_BTN_SECUNDARIO, ALTURA_BTN_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(btnShowUsers);

		/**
		 * Titulo alquilar
		 */
		JLabel lblRent = new JLabel("ALQUILAR:");
		lblRent.setFont(new Font("Dialog", Font.BOLD, 14));
		lblRent.setBounds(800, 40, LONGITUD_LBL_PRINCIPAL, ALTURA_LBL_PRINCIPAL);
		frmAlquilerDeBicis.getContentPane().add(lblRent);

		/**
		 * Datos usuario y bicicleta para alquilar o devolver
		 */
		JLabel lblCbCodUser = new JLabel("Código usuario:");
		lblCbCodUser.setBounds(800, 120, LONGITUD_LBL_SECUNDARIO, ALTURA_LBL_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(lblCbCodUser);

		JComboBox cbCodUser = new JComboBox();
		cbCodUser.setBorder(null);
		cbCodUser.setBackground(new Color(226, 226, 226));
		cbCodUser.setBounds(950, 120, 105, ALTURA_TF_CB);
		frmAlquilerDeBicis.getContentPane().add(cbCodUser);

		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement sel_pstmt = con.prepareStatement("SELECT idusuario FROM usuario");
			ResultSet rs_sel = sel_pstmt.executeQuery();

			cbCodUser.removeAllItems();

			while (rs_sel.next()) {
				cbCodUser.addItem(rs_sel.getInt("idusuario"));
			}
			rs_sel.close();
			sel_pstmt.close();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "¡Error!", JOptionPane.ERROR_MESSAGE);
		}

		JLabel lblCbCodBike = new JLabel("Código bicicleta:");
		lblCbCodBike.setBounds(800, 200, LONGITUD_LBL_SECUNDARIO, ALTURA_LBL_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(lblCbCodBike);

		JComboBox cbCodBike = new JComboBox();
		cbCodBike.setBorder(null);
		cbCodBike.setBackground(new Color(226, 226, 226));
		cbCodBike.setBounds(950, 200, 105, ALTURA_TF_CB);
		frmAlquilerDeBicis.getContentPane().add(cbCodBike);
		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement sel_pstmt = con.prepareStatement("SELECT idbici FROM bici WHERE idbici != 0");
			ResultSet rs_sel = sel_pstmt.executeQuery();

			cbCodBike.removeAllItems();

			while (rs_sel.next()) {
				cbCodBike.addItem(rs_sel.getInt("idbici"));
			}
			rs_sel.close();
			sel_pstmt.close();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "¡Error!", JOptionPane.ERROR_MESSAGE);
		}

		/**
		 * Botón alquilar bicicleta
		 */
		JButton btnRent = new JButton("  ALQUILAR");
		btnRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionSingleton.getConnection();
					con.setAutoCommit(false);
					PreparedStatement check_pstmt = con.prepareStatement("SELECT estado FROM bici WHERE idbici = ?");
					check_pstmt.setInt(1, (int) cbCodBike.getSelectedItem());
					ResultSet rs = check_pstmt.executeQuery();

					// Verificar que la bicicleta no esté alquilada
					if (rs.next()) {
						if (rs.getInt("estado") == 1) {
							JOptionPane.showMessageDialog(null, "Esta bicicleta ya está alquilada.", "¡Error!",
									JOptionPane.ERROR_MESSAGE);
						} else {
							PreparedStatement check_user_pstmt = con
									.prepareStatement("SELECT bici_idbici FROM usuario WHERE idusuario = ?");
							check_user_pstmt.setInt(1, (int) cbCodUser.getSelectedItem());
							ResultSet rs2 = check_user_pstmt.executeQuery();

							// Verificar que el usuario no tenga ya una bicicleta alquilada
							if (rs2.next()) {
								if (rs2.getInt("bici_idbici") != 0) {
									JOptionPane.showMessageDialog(null,
											"Este usuario ya tiene una bicicleta alquilada.", "¡Error!",
											JOptionPane.ERROR_MESSAGE);

									// Actualizar el estado de la bicicleta y asignarla al usuario
								} else {
									PreparedStatement upd_pstmt1 = con
											.prepareStatement("UPDATE bici SET estado = '1' WHERE idbici = ?");
									upd_pstmt1.setInt(1, (int) cbCodBike.getSelectedItem());
									PreparedStatement upd_pstmt2 = con
											.prepareStatement("UPDATE usuario SET bici_idbici = ? WHERE idusuario = ?");
									upd_pstmt2.setInt(1, (int) cbCodBike.getSelectedItem());
									upd_pstmt2.setInt(2, (int) cbCodUser.getSelectedItem());

									upd_pstmt1.executeUpdate();
									upd_pstmt2.executeUpdate();
									con.commit();

									JOptionPane.showMessageDialog(null, "Bicicleta alquilada correctamente.");

									btnShowUsers.doClick();
									btnShowBikes.doClick();
								}
							}
						}
					}
				} catch (SQLException e9) {
					JOptionPane.showMessageDialog(null, e9.getMessage(), "¡Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRent.setBorder(null);
		btnRent.setBackground(new Color(195, 195, 195));
		btnRent.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/bike-park.png")));
		btnRent.setFont(new Font("Dialog", Font.BOLD, 16));
		btnRent.setBounds(800, 280, 250, ALTURA_BTN_PRINCIPAL);
		frmAlquilerDeBicis.getContentPane().add(btnRent);

		/**
		 * Botón devolver bicicleta
		 */
		JButton btnReturn = new JButton("  DEVOLVER");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection con = ConnectionSingleton.getConnection();
					con.setAutoCommit(false);
					PreparedStatement check_pstmt = con.prepareStatement("SELECT estado FROM bici WHERE idbici = ?");
					check_pstmt.setInt(1, (int) cbCodBike.getSelectedItem());
					ResultSet rs = check_pstmt.executeQuery();

					// Verificar que la bicicleta esté alquilada
					if (rs.next()) {
						if (rs.getInt("estado") == 0) {
							JOptionPane.showMessageDialog(null, "Esta bicicleta no está alquilada.", "¡Error!",
									JOptionPane.ERROR_MESSAGE);
						} else {
							PreparedStatement check_user_pstmt = con
									.prepareStatement("SELECT bici_idbici FROM usuario WHERE idusuario = ?");
							check_user_pstmt.setInt(1, (int) cbCodUser.getSelectedItem());
							ResultSet rs2 = check_user_pstmt.executeQuery();

							// Verificar que el usuario tenga ya una bicicleta alquilada
							if (rs2.next()) {
								if (rs2.getInt("bici_idbici") == 0) {
									JOptionPane.showMessageDialog(null,
											"Este usuario no tiene una bicicleta alquilada.", "¡Error!",
											JOptionPane.ERROR_MESSAGE);

									// Actualizar el estado de la bicicleta y asignarla al usuario
								} else {
									PreparedStatement upd_pstmt1 = con
											.prepareStatement("UPDATE bici SET estado = '0' WHERE idbici = ?");
									upd_pstmt1.setInt(1, (int) cbCodBike.getSelectedItem());
									PreparedStatement upd_pstmt2 = con
											.prepareStatement("UPDATE usuario SET bici_idbici = 0 WHERE idusuario = ?");
									upd_pstmt2.setInt(1, (int) cbCodUser.getSelectedItem());

									upd_pstmt1.executeUpdate();
									upd_pstmt2.executeUpdate();
									con.commit();

									JOptionPane.showMessageDialog(null, "Bicicleta devuelta correctamente.");

									btnShowUsers.doClick();
									btnShowBikes.doClick();
								}
							}
						}
					}
				} catch (SQLException e9) {
					JOptionPane.showMessageDialog(null, e9.getMessage(), "¡Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnReturn.setBorder(null);
		btnReturn.setBackground(new Color(195, 195, 195));
		btnReturn.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/bike-park.png")));
		btnReturn.setFont(new Font("Dialog", Font.BOLD, 16));
		btnReturn.setBounds(800, 400, 250, ALTURA_BTN_PRINCIPAL);
		frmAlquilerDeBicis.getContentPane().add(btnReturn);

		/**
		 * Datos usuario
		 */
		JLabel lblName = new JLabel("Nombre:");
		lblName.setBounds(385, 405, LONGITUD_LBL_SECUNDARIO, ALTURA_LBL_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(lblName);

		tfName = new JTextField();
		tfName.setBackground(new Color(226, 226, 226));
		tfName.setColumns(10);
		tfName.setBounds(464, 405, 161, ALTURA_TF_CB);
		frmAlquilerDeBicis.getContentPane().add(tfName);

		JLabel lblCodUser = new JLabel("Código usuario:");
		lblCodUser.setBounds(385, 455, LONGITUD_LBL_SECUNDARIO, ALTURA_LBL_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(lblCodUser);

		tfCodUser = new JTextField();
		tfCodUser.setBackground(new Color(226, 226, 226));
		tfCodUser.setColumns(10);
		tfCodUser.setBounds(515, 455, 110, ALTURA_TF_CB);
		frmAlquilerDeBicis.getContentPane().add(tfCodUser);

		/**
		 * Botón añadir usuario
		 */
		JButton btnAddUser = new JButton("  AÑADIR USUARIO");
		btnAddUser.addActionListener(new ActionListener() {

			// Evento de boton que añade a un usuario
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(tfCodUser.getText());
					Pattern pat = Pattern.compile("^[A-Za-zÑñÁáÉéÍíÓóÚúÜü ]{1,50}$");
					Matcher mat = pat.matcher(tfName.getText());
					String nombre = tfName.getText();
					if (tfName.getText().isEmpty() || tfCodUser.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Porfavor, rellene los 2 campos");
					} else if (!mat.matches()) {
						JOptionPane.showMessageDialog(null,
								"El nombre no puede tener ni numeros ni simbolos especiales", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

					else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ins_pstmt = con
								.prepareStatement("INSERT INTO usuario (idusuario,nombre)  VALUES (?, ?)");
						ins_pstmt.setInt(1, id); // Primer “?”
						ins_pstmt.setString(2, nombre); // Segundo “?”

						int rowsInserted = ins_pstmt.executeUpdate();
						ins_pstmt.close();
						JOptionPane.showMessageDialog(null, "Usuario añadido ");
						btnShowUsers.doClick();

					}
				} catch (SQLException ex) { // Caso erróneo
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException p2) {
					JOptionPane.showMessageDialog(null, "Introduzca un numero para el id ", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				try {

					int code;

					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement sel1 = con.prepareStatement("SELECT idusuario from usuario ");
					ResultSet rs = sel1.executeQuery();

					cbCodUser.removeAllItems();

					while (rs.next()) {

						code = rs.getInt("idusuario");
						cbCodUser.addItem(code);

					}
					rs.close();
					sel1.close();
					con.close();
				} catch (SQLException e9) {
					JOptionPane.showMessageDialog(null, e9.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnAddUser.setBorder(null);
		btnAddUser.setBackground(new Color(195, 195, 195));
		btnAddUser.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/add.png")));
		btnAddUser.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAddUser.setBounds(385, 505, LONGITUD_BTN_SECUNDARIO, ALTURA_BTN_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(btnAddUser);

		/**
		 * Datos bici
		 */
		JLabel lblCodBike = new JLabel("Código bicicleta:");
		lblCodBike.setBounds(50, 455, LONGITUD_LBL_SECUNDARIO, ALTURA_LBL_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(lblCodBike);

		tfCodBike = new JTextField();
		tfCodBike.setBackground(new Color(226, 226, 226));
		tfCodBike.setBounds(190, 455, 100, ALTURA_TF_CB);
		frmAlquilerDeBicis.getContentPane().add(tfCodBike);
		tfCodBike.setColumns(10);

		/**
		 * Botón añadir bicicleta
		 */
		JButton btnAddBike = new JButton("  AÑADIR BICI");
		btnAddBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con
							.prepareStatement("INSERT INTO bici (idbici, estado) VALUES (?, 0)");

					ins_pstmt.setInt(1, Integer.parseInt(tfCodBike.getText()));
					ins_pstmt.executeUpdate();
					ins_pstmt.close();

					JOptionPane.showMessageDialog(null, "La bicicleta ha sido añadida de forma exitosa.",
							"Añadida con exito", JOptionPane.INFORMATION_MESSAGE);
					btnShowBikes.doClick();

				} catch (SQLIntegrityConstraintViolationException e) {
					JOptionPane.showMessageDialog(null, "La bicicleta con este código ya ha sido creada anteriormente.",
							"¡Error!", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Debe asignar un código a la bicicleta para crearla.",
							"¡Error!", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException p2) {
					JOptionPane.showMessageDialog(null, "Introduzca un numero para el id.", "¡Error!",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					tfCodBike.setText(null);
				}

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement sel1 = con.prepareStatement("SELECT idbici FROM bici WHERE idbici != 0");
					ResultSet rs = sel1.executeQuery();

					cbCodBike.removeAllItems();

					while (rs.next()) {
						cbCodBike.addItem(rs.getInt("idbici"));
					}

					rs.close();
					sel1.close();
					con.close();
				} catch (SQLException e9) {
					JOptionPane.showMessageDialog(null, "Pepe", "¡Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton btnActuUser = new JButton("ACTUALIZAR USUARIO");
		btnActuUser.setFont(new Font("Dialog", Font.BOLD, 14));
		btnActuUser.setBorder(null);
		btnActuUser.setBackground(new Color(195, 195, 195));
		btnActuUser.setBounds(637, 505, 181, 40);
		frmAlquilerDeBicis.getContentPane().add(btnActuUser);
		btnActuUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int id = Integer.parseInt(tfCodUser.getText());
					Pattern pat = Pattern.compile("^[A-Za-zÑñÁáÉéÍíÓóÚúÜü ]{1,50}$");
					Matcher mat = pat.matcher(tfName.getText());
					String nombre = tfName.getText();
					if (tfName.getText().isEmpty() ) {
						JOptionPane.showMessageDialog(null, "Porfavor, rellene el campo");
					} else if (!mat.matches()) {
						JOptionPane.showMessageDialog(null,
								"El nombre no puede tener ni numeros ni simbolos especiales", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

					else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement upd_pstmt = con
								.prepareStatement("UPDATE usuario SET nombre = ? WHERE idusuario= ?;");
						
						upd_pstmt.setString(1, nombre); // Segundo “?”
						upd_pstmt.setInt(2, id); // Segundo “?”
						 upd_pstmt.executeUpdate();
						upd_pstmt.close();
						JOptionPane.showMessageDialog(null, "Usuario actualizado");
						btnShowUsers.doClick();

					}
				} catch (SQLException ex) { // Caso erróneo
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					
					
				
			}
			}
		});
		btnAddBike.setBorder(null);
		btnAddBike.setBackground(new Color(195, 195, 195));
		btnAddBike.setIcon(new ImageIcon(AlquilerBicisFinal.class.getResource("/img/add.png")));
		btnAddBike.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAddBike.setBounds(50, 505, LONGITUD_BTN_SECUNDARIO, ALTURA_BTN_SECUNDARIO);
		frmAlquilerDeBicis.getContentPane().add(btnAddBike);
	}
}