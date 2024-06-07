package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JScrollPane;

public class courseList extends winForm{

	public JFrame frmCourses;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					courseList window = new courseList();
					window.frmCourses.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public courseList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCourses = new JFrame();
		frmCourses.setTitle("Courses");
		frmCourses.getContentPane().setBackground(new Color(255, 255, 204));
		frmCourses.getContentPane().setLayout(null);
		
		JButton btnAddCourse = new JButton("Add");
		btnAddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				courseadd ca = new courseadd();
				ca.display(ca.frame);
				frmCourses.dispose();
			}
		});
		btnAddCourse.setBounds(10, 10, 87, 26);
		frmCourses.getContentPane().add(btnAddCourse);
		
		
		
		
		// Create a model for the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Is Active");

        // Fetch data from the database and populate the model
        JTable table = new JTable();
        try (
        	Connection connection = database.connect())
        {
            String sql = "SELECT * FROM courses ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                		resultSet.getInt("id"), 
                		resultSet.getString("course_name"),
                		resultSet.getBoolean("active")                		
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the model to the table
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 46, 710, 369);
		frmCourses.getContentPane().add(scrollPane);
		
		JButton btnDelCourse = new JButton("Remove");
		btnDelCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmCourses, "Please select a course to remove.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        String tn = (String) table.getValueAt(selectedRow, 1);
		        
		        int result = JOptionPane.showConfirmDialog(null, "Are you sure to remove '"+tn+"?", "Confirmation", JOptionPane.YES_NO_OPTION);

		        // Process the user's choice
		        if (result == JOptionPane.YES_OPTION) {
		        	try (Connection connection = database.connect()) {
		                String sql = "DELETE FROM courses WHERE id = ?";
		                PreparedStatement statement = connection.prepareStatement(sql);
		                statement.setLong(1, tid);
		                int rowsDeleted = statement.executeUpdate();

		                if (rowsDeleted > 0) {
		                    JOptionPane.showMessageDialog(frmCourses, "Course deleted successfully.");
		                    frmCourses.dispose();
		                    
		                    courseList tl= new courseList();
		                    tl.display(tl.frmCourses);
		                    
		                } else {
		                    JOptionPane.showMessageDialog(frmCourses, "No course found.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(frmCourses, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else if (result == JOptionPane.NO_OPTION) {
		            System.out.println("User clicked No.");
		        } else {
		            System.out.println("User clicked Cancel or closed the dialog.");
		        }
		        
				 
			}
		});
		btnDelCourse.setBounds(204, 10, 87, 26);
		frmCourses.getContentPane().add(btnDelCourse);
		
		JButton btnMngModule = new JButton("Manage Module");
		btnMngModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmCourses, "Please select a course.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        String cname = (String) table.getValueAt(selectedRow, 1);
		        
				managemodule te= new managemodule(tid,cname);
				te.display(te.frmManageModule);
				
				frmCourses.dispose();
			}
		});
		btnMngModule.setBounds(592, 13, 128, 26);
		frmCourses.getContentPane().add(btnMngModule);
		
		JButton btnEditCourse = new JButton("Edit");
		btnEditCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmCourses, "Please select a course to update.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        
				courseEdit te= new courseEdit(tid);
				te.display(te.frame);
				
				frmCourses.dispose();
			}
		});
		btnEditCourse.setBounds(107, 10, 87, 26);
		frmCourses.getContentPane().add(btnEditCourse);
		frmCourses.setBounds(100, 100, 744, 462);
		frmCourses.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
