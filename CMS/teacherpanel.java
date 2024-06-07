package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class teacherpanel extends winForm{

	public JFrame frame;
	private int _tid;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public teacherpanel(int id) {
		_tid=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 879, 617);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblGoodToSee_1 = new JLabel("Good To See You Again, Teacher!");
		lblGoodToSee_1.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblGoodToSee_1.setBounds(284, 38, 448, 49);
		frame.getContentPane().add(lblGoodToSee_1);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login lg = new login();
				lg.display(lg.frame);
			}
		});
		btnBack.setIconTextGap(16);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("Dialog", Font.BOLD, 18));
		btnBack.setBorderPainted(false);
		btnBack.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBack.setBackground(Color.CYAN);
		btnBack.setBounds(654, 518, 109, 38);
		frame.getContentPane().add(btnBack);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 263, 598);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dashboard");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel.setBounds(57, 71, 137, 38);
		panel.add(lblNewLabel);
		
		JButton btnNewButton_1_2_1_1 = new JButton("Student Marking");
		btnNewButton_1_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teacherStudent ts= new teacherStudent(_tid);
				ts.display(ts.frame);
				
				frame.dispose();
			}
		});
		btnNewButton_1_2_1_1.setIconTextGap(15);
		btnNewButton_1_2_1_1.setForeground(Color.WHITE);
		btnNewButton_1_2_1_1.setFont(new Font("Dialog", Font.BOLD, 18));
		btnNewButton_1_2_1_1.setBorderPainted(false);
		btnNewButton_1_2_1_1.setBorder(null);
		btnNewButton_1_2_1_1.setBackground(new Color(0, 204, 153));
		btnNewButton_1_2_1_1.setBounds(55, 180, 166, 38);
		panel.add(btnNewButton_1_2_1_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(538, 97, 215, 161);
		frame.getContentPane().add(panel_1_1);
		
		JLabel lblsc = new JLabel("0");
		lblsc.setHorizontalAlignment(SwingConstants.CENTER);
		lblsc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblsc.setBounds(10, 10, 195, 45);
		panel_1_1.add(lblsc);
		
		JLabel lblStudents = new JLabel("Students");
		lblStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudents.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblStudents.setBounds(10, 84, 195, 45);
		panel_1_1.add(lblStudents);
		
		
		JTable table = new JTable();
		DefaultTableModel amodel = new DefaultTableModel();
				 
		amodel.addColumn("Module Code");
		amodel.addColumn("Module Name");
		
		try (
	        	Connection connection = database.connect())
	        {
	        	 
	        	 
	        	String cmids="";
	        	//Load compulsory modules
	            String cq = "SELECT cm.moduleid,m.code,m.module_name " +
	            		"FROM teacher_module cm "+
	            		"INNER JOIN modules m ON cm.moduleid = m.id " +
	            		"WHERE cm.userid=? ";
	            
	            PreparedStatement cqs = connection.prepareStatement(cq);
	            cqs.setInt(1, _tid);
	             
	            
	            ResultSet crs = cqs.executeQuery();
	            while (crs.next()) { 	            	
	            	 Object[] row = {		                		
	            			 crs.getString("code"),
	            			 crs.getString("module_name")                		                		
		                };
		            amodel.addRow(row);
	                cmids+=crs.getInt("moduleid")+",";               
	            }
	        	
	          		            
	            String newStr = "";
	            String sql ="";
	            if (!cmids.equals("")) {
	            	newStr = cmids.substring(0, cmids.length() - 1);           	
	            	
	            	sql = "SELECT  count( DISTINCT userid) as count FROM student_module WHERE id IN (" +newStr +")";
	            	
	            	PreparedStatement statement = connection.prepareStatement(sql);
		            ResultSet resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                 
		               lblsc.setText(resultSet.getString("count"));
		            }
		            
	            }
	                     
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		table.setModel(amodel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(284, 97, 237, 459);
		frame.getContentPane().add(scrollPane);
		
		
		
		
	}
}
