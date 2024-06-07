package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class teacherAdd extends winForm{

	public JFrame frame;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JPasswordField txtPwd;

	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					teacherAdd window = new teacherAdd();
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
	public teacherAdd() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 388, 372);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSaveTeacher = new JButton("Add");
		btnSaveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection connection = database.connect()) {
					String firstName =  txtFirstName.getText();
					String lastName =txtLastName.getText();
					String email =txtEmail.getText();
					String pwd=new String(txtPwd.getPassword());
					String role="Teacher";
					
		            String query = "INSERT INTO user (name,lastname,email,pwd,role) VALUES(?,?,?,?,?)";
		            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                preparedStatement.setString(1, firstName);
		                preparedStatement.setString(2, lastName);
		                preparedStatement.setString(3, email);
		                preparedStatement.setString(4, pwd);
		                preparedStatement.setString(5, role);

		                int rowsInserted = preparedStatement.executeUpdate();
		                if (rowsInserted > 0) {
		                	 
	        				teacherList tc = new teacherList();
	        				tc.display(tc.frmTeachers);
	        				frame.dispose();
		        			 
	        				 
			                System.out.println("A new user was inserted successfully!");
			            }
		                
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		             
		        }
						 
		        
			}
		});
		btnSaveTeacher.setBounds(138, 275, 92, 38);
		frame.getContentPane().add(btnSaveTeacher);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				teacherList tl = new teacherList();
				tl.display(tl.frmTeachers);
			}
		});
		btnCancel.setBounds(239, 275, 92, 38);
		frame.getContentPane().add(btnCancel);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(138, 63, 194, 38);
		frame.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(25, 63, 103, 36);
		frame.getContentPane().add(lblNewLabel);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(138, 111, 194, 38);
		frame.getContentPane().add(txtLastName);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLastName.setBounds(25, 111, 103, 36);
		frame.getContentPane().add(lblLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(138, 159, 194, 38);
		frame.getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(25, 159, 103, 36);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(25, 207, 103, 36);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Add Teacher");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 10, 358, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtPwd = new JPasswordField();
		txtPwd.setBounds(138, 207, 194, 36);
		frame.getContentPane().add(txtPwd);
	}
}
