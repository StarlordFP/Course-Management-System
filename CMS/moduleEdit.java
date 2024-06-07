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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class moduleEdit extends winForm{

	public JFrame frame;
	private JTextField txtMcode;
	private int cid;
	private JTextField txtMname;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					moduleEdit window = new moduleEdit(1);
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
	public moduleEdit(int id) {
		cid=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 364, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSaveModule = new JButton("Save");
		btnSaveModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = txtMcode.getText();

				String name = txtMname.getText();
				

		        try (Connection connection = database.connect()) {
		            String sql = "UPDATE modules SET code = ?, module_name = ? WHERE id = ?";
		            PreparedStatement statement = connection.prepareStatement(sql);
		            statement.setString(1, code);
		            statement.setString(2, name);		            
		            statement.setInt(3, cid);
		            
		            int rowsUpdated = statement.executeUpdate();

		            if (rowsUpdated > 0) {
		                JOptionPane.showMessageDialog(frame, "Module updated successfully.");
		                frame.dispose();
		                moduleList cl = new moduleList();
						cl.display(cl.frame);
		            } else {
		                JOptionPane.showMessageDialog(frame, "No module found with ID: " + cid, "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnSaveModule.setBounds(139, 189, 92, 38);
		frame.getContentPane().add(btnSaveModule);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				moduleList cl = new moduleList();
				cl.display(cl.frame);
			}
		});
		btnCancel.setBounds(240, 189, 92, 38);
		frame.getContentPane().add(btnCancel);
		
		txtMcode = new JTextField();
		txtMcode.setBounds(138, 63, 194, 38);
		frame.getContentPane().add(txtMcode);
		txtMcode.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Module code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(25, 63, 103, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Module");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 10, 267, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblIsActive = new JLabel("Module name");
		lblIsActive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIsActive.setBounds(25, 125, 103, 36);
		frame.getContentPane().add(lblIsActive);
		
		txtMname = new JTextField();
		txtMname.setColumns(10);
		txtMname.setBounds(139, 123, 194, 38);
		frame.getContentPane().add(txtMname);
		
		try (Connection connection = database.connect()) {

			
            String q = "SELECT * FROM modules WHERE id="+cid + " LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(q);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	
                txtMcode.setText(resultSet.getString("code"));
                txtMname.setText(resultSet.getString("module_name"));
                
                 
            } 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
	}

}
