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
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class courseEdit extends winForm{

	public JFrame frame;
	private JTextField txtCname;
	private int cid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					courseEdit window = new courseEdit(1);
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
	public courseEdit(int id) {
		cid=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JRadioButton radYes = new JRadioButton("Yes");
		JRadioButton radNo = new JRadioButton("No");
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 364, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSaveTeacher = new JButton("Save");
		btnSaveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtCname.getText();

				boolean active = true;
				if(radYes.isSelected())
					active=true;
				else
					active=false;

		        try (Connection connection = database.connect()) {
		            String sql = "UPDATE courses SET course_name = ?, active = ? WHERE id = ?";
		            PreparedStatement statement = connection.prepareStatement(sql);
		            statement.setString(1, name);
		            statement.setBoolean(2, active);		            
		            statement.setInt(3, cid);
		            
		            int rowsUpdated = statement.executeUpdate();

		            if (rowsUpdated > 0) {
		                JOptionPane.showMessageDialog(frame, "Course updated successfully.");
		                frame.dispose();
		                courseList tl = new courseList();
		                tl.display(tl.frmCourses);
		            } else {
		                JOptionPane.showMessageDialog(frame, "No course found with ID: " + cid, "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnSaveTeacher.setBounds(139, 189, 92, 38);
		frame.getContentPane().add(btnSaveTeacher);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				courseList cl = new courseList();
				cl.display(cl.frmCourses);
			}
		});
		btnCancel.setBounds(240, 189, 92, 38);
		frame.getContentPane().add(btnCancel);
		
		txtCname = new JTextField();
		txtCname.setBounds(138, 63, 194, 38);
		frame.getContentPane().add(txtCname);
		txtCname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Course name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(25, 63, 103, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Course");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 10, 267, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblIsActive = new JLabel("Is Active?");
		lblIsActive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIsActive.setBounds(25, 125, 103, 36);
		frame.getContentPane().add(lblIsActive);
		
		
		radYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radNo.setSelected(false);
			}
		});
		radYes.setBounds(139, 134, 54, 21);
		frame.getContentPane().add(radYes);
		
		
		radNo.setBounds(207, 134, 54, 21);
		frame.getContentPane().add(radNo);
		radNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radYes.setSelected(false);
			}
		});
		
		try (Connection connection = database.connect()) {

			
            String q = "SELECT * FROM courses WHERE id="+cid + " LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(q);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	
                txtCname.setText(resultSet.getString("course_name"));
                boolean active= resultSet.getBoolean("active");
                
                if(active) {
                	radYes.setSelected(true);
                	radNo.setSelected(false);
                }
                else {
                	radNo.setSelected(true);
                	radYes.setSelected(false);
                }
            } 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
	}
}
