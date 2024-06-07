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

public class moduleList extends winForm{

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					moduleList window = new moduleList();
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
	public moduleList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Modules");
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.getContentPane().setLayout(null);
		
		JButton btnAddModule = new JButton("Add");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moduleadd ma = new moduleadd();
				ma.display(ma.frame);
				frame.dispose();
			}
		});
		btnAddModule.setBounds(10, 10, 87, 26);
		frame.getContentPane().add(btnAddModule);
		
		
		
		
		// Create a model for the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Module Code");
        model.addColumn("Module Name");

        // Fetch data from the database and populate the model
        JTable table = new JTable();
        try (
        	Connection connection = database.connect())
        {
            String sql = "SELECT * FROM modules";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                		resultSet.getInt("id"), 
                		resultSet.getString("code"), 
                		resultSet.getString("module_name")              		
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
		
		JButton btnDelModule = new JButton("Remove");
		btnDelModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a module to remove.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        String tn = (String) table.getValueAt(selectedRow, 1);
		        
		        int result = JOptionPane.showConfirmDialog(null, "Are you sure to remove '"+tn+"?", "Confirmation", JOptionPane.YES_NO_OPTION);

		        // Process the user's choice
		        if (result == JOptionPane.YES_OPTION) {
		        	try (Connection connection = database.connect()) {
		                String sql = "DELETE FROM modules WHERE id = ?";
		                PreparedStatement statement = connection.prepareStatement(sql);
		                statement.setLong(1, tid);
		                int rowsDeleted = statement.executeUpdate();

		                if (rowsDeleted > 0) {
		                    JOptionPane.showMessageDialog(frame, "Module deleted successfully.");
		                    frame.dispose();
		                    
		                    moduleList tl= new moduleList();
		                    tl.display(tl.frame);
		                    
		                } else {
		                    JOptionPane.showMessageDialog(frame, "No module found.", "Error", JOptionPane.ERROR_MESSAGE);
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
		btnDelModule.setBounds(202, 10, 87, 26);
		frame.getContentPane().add(btnDelModule);
		
		JButton btnEditmodule = new JButton("Edit");
		btnEditmodule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a module to update.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int tid= (int) table.getValueAt(selectedRow, 0);
		        
				moduleEdit te= new moduleEdit(tid);
				te.display(te.frame);
				
				frame.dispose();
			}
		});
		btnEditmodule.setBounds(107, 10, 87, 26);
		frame.getContentPane().add(btnEditmodule);
		frame.setBounds(100, 100, 744, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
