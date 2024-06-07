package w10;



import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class winForm {

	

    

    
    public void display(JFrame frame) {
        
        SwingUtilities.invokeLater(() -> {
            
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // Add components to the frame
            
            frame.setVisible(true);
        });
    }

    
}
