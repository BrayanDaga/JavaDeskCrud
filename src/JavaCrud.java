import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JFrame frame;
	private JTextField txtName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	/**
	 * Connection per Database
	 */

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/javacrud?verifyServerCertificate=false&useSSL=true", "root", "");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 711, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(255, 11, 168, 37);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(11, 59, 317, 144);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 35, 88, 17);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 70, 88, 17);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 106, 88, 17);
		panel.add(lblNewLabel_1_1_1);

		txtName = new JTextField();
		txtName.setBounds(119, 35, 171, 20);
		panel.add(txtName);
		txtName.setColumns(10);

		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(119, 70, 171, 20);
		panel.add(txtEdition);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(119, 106, 171, 20);
		panel.add(txtPrice);

		JScrollPane DbUtils = new JScrollPane();
		DbUtils.setBounds(338, 71, 347, 213);
		frame.getContentPane().add(DbUtils);

		table = new JTable();
		DbUtils.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(11, 301, 309, 66);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_1_2 = new JLabel("Book Id");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_2.setBounds(10, 22, 88, 17);
		panel_1.add(lblNewLabel_1_1_2);

		txtId = new JTextField();
	
		txtId.setColumns(10);
		txtId.setBounds(119, 22, 171, 20);
		panel_1.add(txtId);

		JButton btnSave = new JButton("Save");
		
		
		btnSave.setBounds(21, 214, 89, 43);
		frame.getContentPane().add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(119, 214, 89, 43);
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setText("");
				txtName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
			}
		});
		btnClear.setBounds(221, 214, 89, 43);
		frame.getContentPane().add(btnClear);

		final JButton btnUpdate = new JButton("Update");
	
		btnUpdate.setBounds(408, 313, 89, 43);
		frame.getContentPane().add(btnUpdate);

		final JButton btnDelete = new JButton("Delete");
		
		btnDelete.setBounds(523, 313, 89, 43);
		frame.getContentPane().add(btnDelete);
		
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name, edition, price;
				name = txtName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				try {
					pst = con.prepareStatement("insert into books(name,edition,price)values(?,?,?)");
					pst.setString(1, name);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					table_load();

					txtName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id;
				id  = txtId.getText();
				
				 try {
						pst = con.prepareStatement("delete from books where id =?");
				
			            pst.setString(1, id);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
			            table_load();
			           
			            txtName.setText("");
			            txtEdition.setText("");
			            txtPrice.setText("");
			            txtName.requestFocus();
			            btnDelete.setEnabled(false);
			            btnUpdate.setEnabled(false);
					}

		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,edition,price,id;
			
			
			name = txtName.getText();
			edition = txtEdition.getText();
			price = txtPrice.getText();
			id  = txtId.getText();
			
			 try {
					pst = con.prepareStatement("update books set name= ?,edition=?,price=? where id =?");
					pst.setString(1, name);
		            pst.setString(2, edition);
		            pst.setString(3, price);
		            pst.setString(4, id);
		            pst.executeUpdate();
		            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
		            table_load();
		           
		            txtName.setText("");
		            txtEdition.setText("");
		            txtPrice.setText("");
		            txtName.requestFocus();
		            btnDelete.setEnabled(false);
		            btnUpdate.setEnabled(false);
		            txtId.setText("");
				}

	            catch (SQLException e1) {
					
					e1.printStackTrace();
				}

			}
		});
		
		txtId.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				 try {
			          
			            String id = txtId.getText();

			                pst = con.prepareStatement("select name,edition,price from books where id = ?");
			                pst.setString(1, id);
			                ResultSet rs = pst.executeQuery();

			            if(rs.next()==true)
			            {
			              
			                String name = rs.getString(1);
			                String edition = rs.getString(2);
			                String price = rs.getString(3);
			                
			                txtName.setText(name);
			                txtEdition.setText(edition);
			                txtPrice.setText(price);
			                
			                btnDelete.setEnabled(true);
			                btnUpdate.setEnabled(true);
			      
			            }   
			            else
			            {
			            	txtName.setText("");
			            	txtEdition.setText("");
			                txtPrice.setText("");
			                 
			            }
			            


			        } 
				
				 catch (SQLException ex) {
			           
			        }
			}
		});


		
		
	}

	public void table_load() {
		try {
			pst = con.prepareStatement("select * from books");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
