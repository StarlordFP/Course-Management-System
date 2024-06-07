package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JScrollPane;

public class studentList extends winForm{

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					studentList window = new studentList();
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
	public studentList() {
		initialize();
		
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                adminpanel ap = new adminpanel();
                ap.display(ap.frame);
            }
        });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Students");
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.getContentPane().setLayout(null);
		
		JButton btnAddStudent = new JButton("Add");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentAdd sa = new studentAdd();
				sa.display(sa.frame);
				frame.dispose();
			}
		});
		btnAddStudent.setBounds(10, 10, 87, 26);
		frame.getContentPane().add(btnAddStudent);
		
		// Create a model for the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");

        // Fetch data from the database and populate the model
        JTable table = new JTable();
        try (
        	Connection connection = database.connect())
        {
            String sql = "SELECT * FROM user where role='Student'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                		resultSet.getInt("id"), 
                		resultSet.getString("name")+" "+ resultSet.getString("lastname"),
                		resultSet.getString("email")                		
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
		frame.getContentPane().add(scrollPane);
		
		JButton btnEditStudent = new JButton("Edit");
		btnEditStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a student to update.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        
				studentEdit se= new studentEdit(tid);
				se.display(se.frmEditStudent);
				
				frame.dispose();
				
			}
		});
		btnEditStudent.setBounds(107, 10, 87, 26);
		frame.getContentPane().add(btnEditStudent);
		
		JButton btnDelStudent = new JButton("Remove");
		btnDelStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a teacher to remove.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        String tn = (String) table.getValueAt(selectedRow, 1);
		        
		        int result = JOptionPane.showConfirmDialog(null, "Are you sure to remove '"+tn+"?", "Confirmation", JOptionPane.YES_NO_OPTION);

		        // Process the user's choice
		        if (result == JOptionPane.YES_OPTION) {
		        	try (Connection connection = database.connect()) {
		                String sql = "DELETE FROM user WHERE id = ?";
		                PreparedStatement statement = connection.prepareStatement(sql);
		                statement.setLong(1, tid);
		                int rowsDeleted = statement.executeUpdate();

		                if (rowsDeleted > 0) {
		                    JOptionPane.showMessageDialog(frame, "Student deleted successfully.");
		                    frame.dispose();
		                    
		                    studentList tl= new studentList();
		                    tl.display(tl.frame);
		                    
		                } else {
		                    JOptionPane.showMessageDialog(frame, "No student found.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else if (result == JOptionPane.NO_OPTION) {
		            System.out.println("User clicked No.");
		        } else {
		            System.out.println("User clicked Cancel or closed the dialog.");
		        }
		        
				 
			}
		});
		btnDelStudent.setBounds(209, 10, 87, 26);
		frame.getContentPane().add(btnDelStudent);
		frame.setBounds(100, 100, 744, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
