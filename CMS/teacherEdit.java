package w10;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class teacherEdit extends winForm{

	public JFrame frmEditTeacher;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JPasswordField txtPwd;
	
	private int tid;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					teacherEdit window = new teacherEdit(3);
//					window.frmEditTeacher.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public teacherEdit(int id) {
		tid=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEditTeacher = new JFrame();
		frmEditTeacher.setTitle("Edit Teacher");
		frmEditTeacher.getContentPane().setBackground(new Color(255, 255, 204));
		frmEditTeacher.setBounds(100, 100, 395, 700);
		frmEditTeacher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditTeacher.getContentPane().setLayout(null);
		
		JButton btnSaveTeacher = new JButton("Save");
		btnSaveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
		        String name = txtFirstName.getText();
		        String lastname = txtLastName.getText();
		        String email = txtEmail.getText();
		        String pwd = new String(txtPwd.getPassword());

		        try (Connection connection = database.connect()) {
		            String sql = "UPDATE user SET name = ?, lastname = ?, email = ?, pwd = ? WHERE id = ?";
		            PreparedStatement statement = connection.prepareStatement(sql);
		            statement.setString(1, name);
		            statement.setString(2, lastname);
		            statement.setString(3, email);
		            statement.setString(4, pwd);
		            statement.setInt(5, tid);
		            
		            int rowsUpdated = statement.executeUpdate();

		            if (rowsUpdated > 0) {
		                JOptionPane.showMessageDialog(frmEditTeacher, "Teacher updated successfully.");
		                frmEditTeacher.dispose();
		                teacherList tl = new teacherList();
		                tl.display(tl.frmTeachers);
		            } else {
		                JOptionPane.showMessageDialog(frmEditTeacher, "No teacher found with ID: " + tid, "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frmEditTeacher, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
						 
		        
			}
		});
		btnSaveTeacher.setBounds(165, 618, 92, 38);
		frmEditTeacher.getContentPane().add(btnSaveTeacher);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmEditTeacher.dispose();
				teacherList tl = new teacherList();
				tl.display(tl.frmTeachers);
			}
		});
		btnCancel.setBounds(266, 618, 92, 38);
		frmEditTeacher.getContentPane().add(btnCancel);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(134, 21, 194, 38);
		frmEditTeacher.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(21, 21, 103, 36);
		frmEditTeacher.getContentPane().add(lblNewLabel);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(134, 69, 194, 38);
		frmEditTeacher.getContentPane().add(txtLastName);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLastName.setBounds(21, 69, 103, 36);
		frmEditTeacher.getContentPane().add(lblLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(134, 117, 194, 38);
		frmEditTeacher.getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(21, 117, 103, 36);
		frmEditTeacher.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(21, 165, 103, 36);
		frmEditTeacher.getContentPane().add(lblPassword);
		
		txtPwd = new JPasswordField();
		txtPwd.setBounds(134, 165, 194, 36);
		frmEditTeacher.getContentPane().add(txtPwd);
		
		JLabel lblModules = new JLabel("Modules");
		lblModules.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModules.setBounds(21, 227, 103, 36);
		frmEditTeacher.getContentPane().add(lblModules);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 263, 361, 345);
		frmEditTeacher.getContentPane().add(scrollPane);
		
		try (Connection connection = database.connect()) {
//			String firstName =  txtFirstName.getText();
//			String lastName =txtLastName.getText();
//			String email =txtEmail.getText();
//			String pwd=new String(txtPwd.getPassword());
//			String role="Teacher";
			
            String q = "SELECT * FROM USER WHERE id="+tid + " LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(q);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	
                txtFirstName.setText(resultSet.getString("name"));
                txtLastName.setText(resultSet.getString("lastname"));
                txtEmail.setText(resultSet.getString("email"));
                txtPwd.setText(resultSet.getString("pwd"));
            } 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
	}

}
