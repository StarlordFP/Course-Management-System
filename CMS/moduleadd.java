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

public class moduleadd extends winForm{

	public JFrame frame;
	private JTextField txtMcode;
	private JTextField txtMname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					moduleadd window = new moduleadd();
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
	public moduleadd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 364, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnAddModule = new JButton("Add");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection connection = database.connect()) {
					String code = txtMcode.getText();
					String name =  txtMname.getText();
					 
					
		            String query = "INSERT INTO modules (code,module_name) VALUES(?,?)";
		            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                preparedStatement.setString(1, code);
		                preparedStatement.setString(2, name);
		               
		                int rowsInserted = preparedStatement.executeUpdate();
		                if (rowsInserted > 0) {
		                	 
	        				moduleList tc = new moduleList();
	        				tc.display(tc.frame);
	        				frame.dispose();
		        			 
	        				 
			                System.out.println("A new module was inserted successfully!");
			            }
		                
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		             
		        }
			}
		});
		btnAddModule.setBounds(139, 189, 92, 38);
		frame.getContentPane().add(btnAddModule);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				moduleList ml= new moduleList();
				ml.display(ml.frame);
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
		
		JLabel lblNewLabel_1 = new JLabel("Add Module");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 10, 358, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtMname = new JTextField();
		txtMname.setColumns(10);
		txtMname.setBounds(137, 123, 194, 38);
		frame.getContentPane().add(txtMname);
		
		JLabel lblModuleId = new JLabel("Module name");
		lblModuleId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModuleId.setBounds(24, 123, 103, 36);
		frame.getContentPane().add(lblModuleId);
	}
}
