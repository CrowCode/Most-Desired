package newview.sajjad.mostdesired;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GraphFrame extends JFrame {
	
	public GraphFrame() {
		
		setTitle("Example of Split Pane");
        setSize(150, 150);
         
        JPanel jsp1 = new JPanel();
        JPanel jsp2 = new JPanel();
        JLabel j1 = new JLabel("Area 1");
        JLabel j2 = new JLabel("Area 2");
         
        jsp1.add(j1);
        jsp2.add(j2);
         
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                true, jsp1, jsp2);
         
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(.5d);
        getContentPane().add(splitPane);
        
        setVisible(true);
		
	}

}
