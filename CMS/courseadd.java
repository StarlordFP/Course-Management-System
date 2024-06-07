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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
public class courseadd extends winForm{

	public JFrame frame;
	private JTextField txtCname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					courseadd window = new courseadd();
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
	public courseadd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 364, 217);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSaveTeacher = new JButton("Add");
		btnSaveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection connection = database.connect()) {
					String name =  txtCname.getText();
					boolean active = true;
					
		            String query = "INSERT INTO courses (course_name,active) VALUES(?,?)";
		            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                preparedStatement.setString(1, name);
		                preparedStatement.setBoolean(2, active);
		               
		                int rowsInserted = preparedStatement.executeUpdate();
		                if (rowsInserted > 0) {
		                	 
	        				courseList tc = new courseList();
	        				tc.display(tc.frmCourses);
	        				frame.dispose();
		        			 
	        				 
			                System.out.println("A new course was inserted successfully!");
			            }
		                
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		             
		        }
			}
		});
		btnSaveTeacher.setBounds(138, 126, 92, 38);
		frame.getContentPane().add(btnSaveTeacher);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				courseList cl = new courseList();
				cl.display(cl.frmCourses);
			}
		});
		btnCancel.setBounds(239, 126, 92, 38);
		frame.getContentPane().add(btnCancel);
		
		txtCname = new JTextField();
		txtCname.setBounds(138, 63, 194, 38);
		frame.getContentPane().add(txtCname);
		txtCname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Course name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(25, 63, 103, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Add Course");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 10, 267, 33);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
