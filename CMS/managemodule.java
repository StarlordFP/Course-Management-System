package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public class managemodule extends winForm {

	public JFrame frmManageModule;
	private JLabel lblCourse;
	private JLabel lblLevel;
	private JLabel lblSemester;
	private JLabel lblAvailableModule;
	private JLabel lblSelectedModules;

	private JComboBox<Object> cbLevel = new JComboBox<Object>();
	private JComboBox<Object> cbSemester = new JComboBox<Object>();
	
	private JPanel pnlOptional = new JPanel();
	
	private JTable atable = new JTable();
	private JTable stable = new JTable();
	private JTable otable = new JTable();
	
	private DefaultTableModel smodel = new DefaultTableModel();
	private DefaultTableModel omodel = new DefaultTableModel();
	
	private int _cid;
	private String _cname;
	
	private int level = -1;
	private int semester =  -1;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					managemodule window = new managemodule();
//					window.frmManageModule.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public managemodule(int id, String cname) {
		_cid=id;
		_cname=cname;
		initialize();
		
		frmManageModule.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                courseList ap = new courseList();
                ap.display(ap.frmCourses);
            }
        });
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManageModule = new JFrame();
		frmManageModule.setTitle("Manage Module");
		frmManageModule.getContentPane().setBackground(new Color(255, 255, 204));
		frmManageModule.setBounds(100, 100, 711, 676);
		frmManageModule.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManageModule.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Course :");
		lblNewLabel.setBounds(28, 10, 69, 29);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmManageModule.getContentPane().add(lblNewLabel);
		cbLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showhideOptional();
				
				saveModule(level,semester);
				updateCompulosryMod();
				updateOptionalMod();
			}
		});
		
		
		cbLevel.setModel(new DefaultComboBoxModel<Object>(new String[] {"4", "5", "6"}));
		cbLevel.setBounds(104, 53, 89, 26);
		frmManageModule.getContentPane().add(cbLevel);
		cbSemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				saveModule(level,semester);
				
				updateCompulosryMod();
				updateOptionalMod();
			}
		});
		
		
		cbSemester.setModel(new DefaultComboBoxModel<Object>(new String[] {"1", "2"}));
		cbSemester.setBounds(305, 53, 69, 26);
		frmManageModule.getContentPane().add(cbSemester);
		
		lblCourse = new JLabel("abc");
		lblCourse.setBounds(107, 10, 69, 29);
		lblCourse.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmManageModule.getContentPane().add(lblCourse);
		
		lblLevel = new JLabel("Level");
		lblLevel.setBounds(28, 49, 69, 29);
		lblLevel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmManageModule.getContentPane().add(lblLevel);
		
		lblSemester = new JLabel("Semester");
		lblSemester.setBounds(221, 49, 69, 29);
		lblSemester.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmManageModule.getContentPane().add(lblSemester);
		
		DefaultTableModel amodel = new DefaultTableModel();
		amodel.addColumn("ID");
		amodel.addColumn("Module Code");
		amodel.addColumn("Module Name");
       

		
		smodel.addColumn("ID");
		smodel.addColumn("Module Code");
		smodel.addColumn("Module Name");
		

		omodel.addColumn("ID");
		omodel.addColumn("Module Code");
		omodel.addColumn("Module Name");
        
        try (
        	Connection connection = database.connect())
        {
        	 
        	String cmids="";
        	level = Integer.parseInt((String) cbLevel.getSelectedItem()) ;
			semester =  Integer.parseInt((String) cbSemester.getSelectedItem());
        	//Load compulsory modules
            String cq = "SELECT cm.courseid,cm.moduleid,m.code,m.module_name " +
            		"FROM course_module cm "+
            		"INNER JOIN modules m ON cm.moduleid = m.id " +
            		"WHERE cm.courseid=? and cm.isoptional=?";
            
            PreparedStatement cqs = connection.prepareStatement(cq);
            cqs.setInt(1, _cid);
            cqs.setBoolean(2, false);
            
            ResultSet crs = cqs.executeQuery();
            while (crs.next()) {
//                Object[] row = {
//                		crs.getInt("moduleid"), 
//                		crs.getString("code"),
//                		crs.getString("module_name")                		                		
//                };
                cmids+=crs.getInt("moduleid")+",";
//                smodel.addRow(row);
                
            }
        	
          	//Load optional modules
            String oq = "SELECT cm.courseid,cm.moduleid,m.code,m.module_name " +
            		"FROM course_module cm "+
            		"INNER JOIN modules m ON cm.moduleid = m.id " +
            		"WHERE cm.courseid=? and cm.isoptional=?";
            
            PreparedStatement oqs = connection.prepareStatement(oq);
            oqs.setInt(1, _cid);
            oqs.setBoolean(2, true);
            
            ResultSet ors = oqs.executeQuery();
            while (ors.next()) {
//                Object[] row = {
//                		ors.getInt("moduleid"), 
//                		ors.getString("code"),
//                		ors.getString("module_name")                		                		
//                };
                cmids+=ors.getInt("moduleid")+",";
//                omodel.addRow(row);
                
            }
            
            String newStr = "";
            String sql ="";
            if (cmids.equals("")) {
            	sql = "SELECT * FROM modules ";
            }
            else {
            	newStr = cmids.substring(0, cmids.length() - 1);
            	sql = "SELECT * FROM modules WHERE id NOT IN (" +newStr +")";
            }
        	 
        	
        	
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                		resultSet.getInt("id"), 
                		resultSet.getString("code"),
                		resultSet.getString("module_name")                		                		
                };
                amodel.addRow(row);
                
            }
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		lblCourse.setText(_cname);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Integer.parseInt((String) cbLevel.getSelectedItem()) ;
				semester =  Integer.parseInt((String) cbSemester.getSelectedItem());
				
				saveModule(level,semester);
				
				 
				courseList tc = new courseList();
				tc.display(tc.frmCourses);
				frmManageModule.dispose();	
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBounds(10, 596, 87, 37);
		frmManageModule.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmManageModule.dispose();
				courseList ap = new courseList();
                ap.display(ap.frmCourses);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancel.setBounds(122, 596, 87, 37);
		frmManageModule.getContentPane().add(btnCancel);
		
		JPanel panel = new JPanel();
		panel.setBounds(326, 108, 361, 273);
		frmManageModule.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		
		stable.setModel(smodel);
		
		stable.getColumnModel().getColumn(0).setMinWidth(0);
		stable.getColumnModel().getColumn(0).setMaxWidth(0);
		stable.getColumnModel().getColumn(0).setWidth(0);
		
		JScrollPane spSelected = new JScrollPane(stable);
		spSelected.setBounds(81, 39, 270, 228);
		panel.add(spSelected);
		
		JButton btnDeselect = new JButton("<");
		btnDeselect.setBounds(10, 100, 61, 37);
		panel.add(btnDeselect);
		btnDeselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = stable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = stable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    amodel.addRow(rowData);

                    // Remove the row from the source table
                    smodel.removeRow(selectedRow);
                }
			}
		});
		btnDeselect.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnSelect = new JButton(">");
		btnSelect.setBounds(10, 47, 61, 37);
		panel.add(btnSelect);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = atable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = atable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    smodel.addRow(rowData);

                    // Remove the row from the source table
                    amodel.removeRow(selectedRow);
                }
			}
		});
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(282, 6, 69, 23);
		panel.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < smodel.getRowCount(); i++) {
					Object[] rowData = new Object[3];
                    for (int j = 0; j < 3; j++) {
                        rowData[j] = stable.getValueAt(i, j);
                    }

		            amodel.addRow(rowData);
		            
		        }
				smodel.setRowCount(0);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblSelectedModules = new JLabel("Compulsory Modules");
		lblSelectedModules.setBounds(10, 1, 181, 29);
		panel.add(lblSelectedModules);
		lblSelectedModules.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 108, 306, 484);
		frmManageModule.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		

		        // Set the model to the table
        atable.setModel(amodel);
        
        atable.getColumnModel().getColumn(0).setMinWidth(0);
        atable.getColumnModel().getColumn(0).setMaxWidth(0);
        atable.getColumnModel().getColumn(0).setWidth(0);
        

        JScrollPane spAvailable = new JScrollPane(atable);
        spAvailable.setBounds(0, 45, 306, 439);
        panel_1.add(spAvailable);
        
        lblAvailableModule = new JLabel("Available Modules");
        lblAvailableModule.setBounds(10, 10, 181, 29);
        panel_1.add(lblAvailableModule);
        lblAvailableModule.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        
        pnlOptional.setBounds(326, 391, 361, 201);
        frmManageModule.getContentPane().add(pnlOptional);
        pnlOptional.setLayout(null);
        
        JLabel lblOptionalModules = new JLabel("Optional Modules");
        lblOptionalModules.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblOptionalModules.setBounds(10, 5, 181, 29);
        pnlOptional.add(lblOptionalModules);
        
        
        
        otable.setModel(omodel);
		
        otable.getColumnModel().getColumn(0).setMinWidth(0);
        otable.getColumnModel().getColumn(0).setMaxWidth(0);
        otable.getColumnModel().getColumn(0).setWidth(0);
		
        JScrollPane spOptional = new JScrollPane(otable);
        spOptional.setBounds(81, 44, 270, 147);
        pnlOptional.add(spOptional);
        
        JButton btnClear_1 = new JButton("Clear");
        btnClear_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnClear_1.setBounds(282, 10, 69, 23);
        pnlOptional.add(btnClear_1);
        btnClear_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i = 0; i < omodel.getRowCount(); i++) {
					Object[] rowData = new Object[3];
                    for (int j = 0; j < 3; j++) {
                        rowData[j] = otable.getValueAt(i, j);
                    }

		            amodel.addRow(rowData);
		            
		        }
				omodel.setRowCount(0);
        	}
        });
        
        
        JButton btnDeselectOp = new JButton("<");
        btnDeselectOp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = otable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = otable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    amodel.addRow(rowData);

                    // Remove the row from the source table
                    omodel.removeRow(selectedRow);
                }
        	}
        });
        btnDeselectOp.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDeselectOp.setBounds(10, 97, 61, 37);
        pnlOptional.add(btnDeselectOp);
        
        JButton btnSelectOp = new JButton(">");
        btnSelectOp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = atable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = atable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    omodel.addRow(rowData);

                    // Remove the row from the source table
                    amodel.removeRow(selectedRow);
                }
        	}
        });
        btnSelectOp.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSelectOp.setBounds(10, 44, 61, 37);
        pnlOptional.add(btnSelectOp);
        
        showhideOptional();
		
        updateCompulosryMod();
	}

	private void updateCompulosryMod() {
		try (
	        	Connection connection = database.connect())
	        {
	        	level = Integer.parseInt((String) cbLevel.getSelectedItem()) ;
				semester =  Integer.parseInt((String) cbSemester.getSelectedItem());
	        	//Load compulsory modules
	            String cq = "SELECT cm.courseid,cm.moduleid,m.code,m.module_name " +
	            		"FROM course_module cm "+
	            		"INNER JOIN modules m ON cm.moduleid = m.id " +
	            		"WHERE cm.courseid=? and cm.isoptional=? and cm.level=? and cm.semester=?";
	            
	            PreparedStatement cqs = connection.prepareStatement(cq);
	            cqs.setInt(1, _cid);
	            cqs.setBoolean(2, false);
	            cqs.setInt(3, level);
	            cqs.setInt(4, semester);
	            
	            ResultSet crs = cqs.executeQuery();
	            smodel.setRowCount(0);
	            while (crs.next()) {
	                Object[] row = {
	                		crs.getInt("moduleid"), 
	                		crs.getString("code"),
	                		crs.getString("module_name")                		                		
	                };
	                
	                smodel.addRow(row);
	                
	            }
	        	
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	private void updateOptionalMod() {
		try (
	        	Connection connection = database.connect())
	        {
				level = Integer.parseInt((String) cbLevel.getSelectedItem()) ;
				semester =  Integer.parseInt((String) cbSemester.getSelectedItem()); 
	        		        	
	          	//Load optional modules
	            String oq = "SELECT cm.courseid,cm.moduleid,m.code,m.module_name " +
	            		"FROM course_module cm "+
	            		"INNER JOIN modules m ON cm.moduleid = m.id " +
	            		"WHERE cm.courseid=? and cm.isoptional=? and cm.level=? and cm.semester=?";
	            
	            PreparedStatement oqs = connection.prepareStatement(oq);
	            oqs.setInt(1, _cid);
	            oqs.setBoolean(2, true);
	            oqs.setInt(3, level);
	            oqs.setInt(4, semester);
	            
	            ResultSet ors = oqs.executeQuery();
	            omodel.setRowCount(0);
	            while (ors.next()) {
	                Object[] row = {
	                		ors.getInt("moduleid"), 
	                		ors.getString("code"),
	                		ors.getString("module_name")                		                		
	                };
	                 
	                omodel.addRow(row);
	                
	            }
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	private void saveModule(int level, int semester) {
		try (Connection connection = database.connect()) {
			
			//Save Compulsory Module
			if(smodel.getRowCount()>0) {
				String q= "DELETE FROM course_module WHERE level=? and semester=? and courseid=? and isoptional=? and moduleid=?";
				PreparedStatement cs = connection.prepareStatement(q);
				cs.setInt(1, level);	
				cs.setInt(2, semester);
				cs.setInt(3, _cid);
				cs.setBoolean(4, false);
				
				for (int i = 0; i < smodel.getRowCount(); i++) {
					
					int mid = (int) stable.getValueAt(i, 0);
					cs.setInt(5, mid);
					
					cs.executeUpdate();
					
					String inq = "INSERT INTO course_module (level,semester,courseid,moduleid,isoptional) VALUES(?,?,?,?,?)";
					PreparedStatement ins = connection.prepareStatement(inq);
					ins.setInt(1, level);	
					ins.setInt(2, semester);
					ins.setInt(3, _cid);
					ins.setInt(4, mid);
					ins.setBoolean(5, false);
				 
					int rowsInserted = ins.executeUpdate();
	                if (rowsInserted > 0) {
	                	     
		                System.out.println("A new course module successfully managed!");
		            }
					
		        }
			}
			
			
			
			//Save Optional Module
			if(omodel.getRowCount()>0) {
				String q= "DELETE FROM course_module WHERE level=? and semester=? and courseid=? and isoptional=? and moduleid=? ";
				PreparedStatement cs = connection.prepareStatement(q);
				cs.setInt(1, level);	
				cs.setInt(2, semester);
				cs.setInt(3, _cid);
				cs.setBoolean(4, true);
				
				for (int i = 0; i < omodel.getRowCount(); i++) {
					
					int mid = (int) otable.getValueAt(i, 0);
					cs.setInt(5, mid);
					
					cs.executeUpdate();
					
					String inq = "INSERT INTO course_module (level,semester,courseid,moduleid,isoptional) VALUES(?,?,?,?,?)";
					PreparedStatement ins = connection.prepareStatement(inq);
					ins.setInt(1, level);	
					ins.setInt(2, semester);
					ins.setInt(3, _cid);
					ins.setInt(4, mid);
					ins.setBoolean(5, true);
				 
					int rowsInserted = ins.executeUpdate();
	                if (rowsInserted > 0) {
	                	 
		                System.out.println("A new course module successfully managed!");
		            }
					
		        }
			}
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
	}
	private void showhideOptional() {
		int level = Integer.parseInt((String) cbLevel.getSelectedItem()) ;
		if(level>5) {
			pnlOptional.setVisible(true);
		}
		else
			pnlOptional.setVisible(false);
	}
}
