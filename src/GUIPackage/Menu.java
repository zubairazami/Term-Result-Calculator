package GUIPackage;
import java.awt.*;         
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import MainPackage.Controll;


public class Menu extends JFrame{
	
	private Controll TempController;
    private JTabbedPane MenuTabbed;
    private JPanel MenuPanel;
    private Icon DisabledIcon = new ImageIcon(getClass().getResource("/Icons/x.jpg"));
    private Color NotSelected = new Color(112,146,190);
    private Color Selected =  Color.BLACK;//new Color(138,86,57);
    
    private final JPanel panel = new JPanel();
    
	public Menu(Controll x)
	{
	  TempController = x;
	}
	
	
	public void createMenuFrame()
	{
		MenuPanel = new JPanel(new BorderLayout())
	       {
		     protected void paintComponent(Graphics g)
			 {
			    super.paintComponent(g);
			    g.drawImage(new ImageIcon(getClass().getResource("/Icons/5.jpg")).getImage(), 0, 0, 840,590, null);
			 }
	       };
	 
	 UIManager.put("TabbedPane.tabsOpaque", false);
	 UIManager.put("TabbedPane.contentOpaque", false);
	 UIManager.put("TabbedPane.tabsOverlapBorder", true);
	 
	 MenuTabbed= new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
	 MenuTabbed.setOpaque(false);
	 
     MenuTabbed.addTab("    Enrollment                      >>",new ImageIcon (getClass().getResource("/Icons/0.jpg")),TempController.EnrollmentPanelObject.getEnrollmentPanelScroll() );
     MenuTabbed.addTab("    Data Entry                        >>",new ImageIcon (getClass().getResource("/Icons/1.jpg")),TempController.DataHandlePanelObject.getDataHandlePanel() );
     MenuTabbed.addTab("    Result                               >>",new ImageIcon (getClass().getResource("/Icons/2.jpg")),TempController.ResultHandlePanelObject.getResultHandlePanel() );     
     
     for(int i =1;i<3;i++)
        MenuTabbed.setForegroundAt(i,NotSelected);
     MenuTabbed.setForegroundAt(0, Selected);
     
     MenuTabbed.addChangeListener(new ChangeListener(){ 
    	         int Index ;
		         
    	         @Override
                 public void stateChanged(ChangeEvent CE) 
		         {
		           Index =((JTabbedPane)CE.getSource()).getSelectedIndex();
		           MenuTabbed.setIconAt(Index,new ImageIcon(getClass().getResource("/Icons/"+Integer.toString(Index)+"a.jpg")));	
		           MenuTabbed.setForegroundAt(Index,Selected);
		           
		           for(int i=0;i<3;i++)
	                  {
		        	   if (Index == i) continue;
	            	   else {
	            		     MenuTabbed.setIconAt(i,new ImageIcon(getClass().getResource("/Icons/"+Integer.toString(i)+".jpg")));
	            	         MenuTabbed.setForegroundAt(i, NotSelected);            	           
	            	        }
	                  }
		         }
	 });
     
     MenuPanel.add(MenuTabbed);
     getContentPane().add(MenuPanel);
     setTitle("Term Result Calculator");
	 setResizable (false);
     setLocation(250,100);
     setSize(840,565);
     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     addWindowListener(new java.awt.event.WindowAdapter() 
           {
    	      @Override
    	      public void windowClosing(java.awt.event.WindowEvent windowEvent) 
    	       {
    	        if( JOptionPane.showConfirmDialog(null, "Do you want to exit Term Result Calculator ?", "Comfirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION )
    	    	   System.exit(0);
    	       }
    	   });
     setVisible(true);	
     
	}
}