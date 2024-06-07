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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class teacherList extends winForm{

	public JFrame frmTeachers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					teacherList window = new teacherList();
					window.frmTeachers.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public teacherList() {
		initialize();
		
		frmTeachers.addWindowListener(new WindowAdapter() {
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
		frmTeachers = new JFrame();
		frmTeachers.setTitle("Teachers");
		frmTeachers.getContentPane().setBackground(new Color(255, 255, 204));
		frmTeachers.getContentPane().setLayout(null);
		
		JButton btnAddTeacher = new JButton("Add");
		btnAddTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teacherAdd ta = new teacherAdd();
				ta.display(ta.frame);
				frmTeachers.dispose();
			}
		});
		btnAddTeacher.setBounds(10, 10, 87, 26);
		frmTeachers.getContentPane().add(btnAddTeacher);
		
		
		
		
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
            String sql = "SELECT * FROM user where role='Teacher'";
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
		frmTeachers.getContentPane().add(scrollPane);
		
		JButton btnEditTeacher = new JButton("Edit");
		btnEditTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmTeachers, "Please select a teacher to update.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        
				teacherEdit te= new teacherEdit(tid);
				te.display(te.frmEditTeacher);
				
				frmTeachers.dispose();
				
			}
		});
		btnEditTeacher.setBounds(107, 10, 87, 26);
		frmTeachers.getContentPane().add(btnEditTeacher);
		
		JButton btnDelTeacher = new JButton("Remove");
		btnDelTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmTeachers, "Please select a teacher to remove.", "Error", JOptionPane.ERROR_MESSAGE);
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
		                    JOptionPane.showMessageDialog(frmTeachers, "Teacher deleted successfully.");
		                    frmTeachers.dispose();
		                    
		                    teacherList tl= new teacherList();
		                    tl.display(tl.frmTeachers);
		                    
		                } else {
		                    JOptionPane.showMessageDialog(frmTeachers, "No teacher found.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(frmTeachers, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else if (result == JOptionPane.NO_OPTION) {
		            System.out.println("User clicked No.");
		        } else {
		            System.out.println("User clicked Cancel or closed the dialog.");
		        }
		        
				 
			}
		});
		btnDelTeacher.setBounds(209, 10, 87, 26);
		frmTeachers.getContentPane().add(btnDelTeacher);
		
		JButton btnAssignMod = new JButton("Assign Module");
		btnAssignMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frmTeachers, "Please select a teacher to assign module.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        String name = (String)table.getValueAt(selectedRow, 1);
		        
				teacherModule tm= new teacherModule(tid,name);
				tm.display(tm.frmTeacherModule);
				
				frmTeachers.dispose();
			}
		});
		btnAssignMod.setBounds(558, 10, 162, 26);
		frmTeachers.getContentPane().add(btnAssignMod);
		frmTeachers.setBounds(100, 100, 744, 462);
		frmTeachers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
