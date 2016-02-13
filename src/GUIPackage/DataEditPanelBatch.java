package GUIPackage;

import javax.swing.*;      
import javax.swing.border.*;

import DB.DataTransfer;
import MainPackage.*;
import java.awt.*;    
import java.awt.event.*;

public class DataEditPanelBatch implements ActionListener
{	
	private int NumberOfStudents,Selected,Effected;
	private String BatchYear;
	private JDialog DEPB;
	private JLabel ColumnName ;
	private JButton UpdateButton ;
	private JPanel Panel ;
	private JScrollPane Scroll;
	private MyJObject[] My;
	private Controll TempControll;
	private JCheckBox CheckAll;
	
	private Border OKStatus = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.WHITE);
	private Border WrongStatus = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.RED);
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.WHITE);
	
	public DataEditPanelBatch(Controll x)
	{
		TempControll = x;
	}
	public void createDialogBox(String Batch)
	{
		DEPB = new JDialog(); 
		
		this.NumberOfStudents = DataTransfer.Roll.size();
		this.BatchYear = Batch;
		final int Final = NumberOfStudents; 
		
		Panel = new JPanel()
		{	
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,916,Final*40+150,null);
			    ButtonBorder.paintBorder(this, g, 332,74+Final*40,252,32);
			   }
		};
		
		Panel.setPreferredSize(new Dimension(916,NumberOfStudents*40+150));  // 50+(200*4+3*5)+50, 50+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60
		Panel.setLayout(null);
		
		ColumnName =new JLabel("",SwingConstants.LEFT);
		ColumnName.setText("        ROLL                                                  NAME                                    CONTACT                                   BATCH");
		ColumnName.setForeground(Color.WHITE);
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName.setBounds(50, 20, 815, 30);
		Panel.add(ColumnName);
		
		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("SERRIF",Font.BOLD,16));
		UpdateButton.setForeground(Color.WHITE);
		UpdateButton.setBounds(333,75+NumberOfStudents*40,250,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
		UpdateButton.setContentAreaFilled(false);
		UpdateButton.setOpaque(false);
		UpdateButton.addActionListener(this);
		Panel.add(UpdateButton);
		
		CheckAll = new JCheckBox("Uncheck all");
		CheckAll.setForeground(Color.WHITE);
		CheckAll.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		CheckAll.setOpaque(false);
		CheckAll.setSelected(true);
		CheckAll.addActionListener(this);
		
		setComponentsOnTheGrid();
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DEPB.add(Scroll);
		
		DEPB.setModal(true);
		DEPB.setTitle("Batch Information ");
		DEPB.setResizable (false);
		DEPB.setLocation(250,150);
		DEPB.setSize(915,(NumberOfStudents*40+160<500)? NumberOfStudents*40+160:500); // if DEPB is longer then panel then dialog size decrease 
		DEPB.setVisible(true);
	}
	
	private void setComponentsOnTheGrid()
	{
		JPanel GridPanel = new JPanel(new GridLayout(0,4,5,10));
		My = new MyJObject[NumberOfStudents+1];
		
		for(int i = 0;i<NumberOfStudents;i++)
		   {
			My[i] = new MyJObject(i);
			GridPanel.add(My[i].RollCheckBox);
			GridPanel.add(My[i].Name);
			GridPanel.add(My[i].Contact);
			GridPanel.add(My[i].Batch);
		   }
		GridPanel.add(CheckAll);
		GridPanel.setBounds(50, 50,815 , 40*NumberOfStudents-10); // 4*200+3*5,NumberOfStudents*30+(NumberOfStudents-1)*10
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent AE) 
	{
	    if(AE.getSource()==CheckAll)
	      {
	    	boolean Selection = CheckAll.isSelected();
	    	if(Selection)
	     	  CheckAll.setText("Uncheck all");
	    	else
	    	  CheckAll.setText("Check all");
	    	for(int i=0;i<NumberOfStudents;i++)
	    		My[i].RollCheckBox.setSelected(Selection);
	      }
	    
		if(AE.getSource()==UpdateButton)
		   if(TempControll.ConnectionManagerObject.createConnection())
		     {
			   setToDefault();
		       if(hasProblem())	 
			      JOptionPane.showMessageDialog(null,"Empty Title Field Exists in the Dialog Box","Warning!!",JOptionPane.WARNING_MESSAGE);		 
		       else
			      {
		    	   gatherData(); 
		    	   this.Effected = TempControll.DataEraseEditObject.editBatchInformation(this.BatchYear);
		    	   if(this.Effected == -1)
		    	     {
		    		    // error shown by the editBatchInformation(this.BatchYear) method itself;  //which part didn't follow MVC structure.
		    	     }
		    	   else if(this.Effected==this.Selected)
		    		       JOptionPane.showMessageDialog(DEPB, "Batch : "+this.BatchYear +"\nInformation for all selected students has been updated.", "Update Successful", JOptionPane.INFORMATION_MESSAGE );
		    	   else
		    		   JOptionPane.showMessageDialog(DEPB, "Warning!!\nBatch : "+this.BatchYear +"\n No or only few selected students information has been updated.", "Partial Update", JOptionPane.INFORMATION_MESSAGE );		    	      
		    	   DEPB.dispose();
			      } 
		     }
	}
		
	private boolean hasProblem()
	{
		boolean Empty = false;
		for(int i=0;i<NumberOfStudents;i++)
	       if(My[i].RollCheckBox.isSelected())
		     { 
	    	   if(My[i].Name.getText().toString().equals(""))
	    		 {
	    		  My[i].Name.setBorder(WrongStatus);
	    		  Empty =true;
	    		 }  
	    	   if(My[i].Contact.getText().toString().equals(""))
	    	     {
	    		  My[i].Contact.setBorder(WrongStatus);
		    	  Empty =true;	 
	    	     }
	         }	
		return Empty;
	}
		
	private void setToDefault()
	{
	   for(int i=0;i<NumberOfStudents;i++)
	      {
		   My[i].Contact.setBorder(OKStatus);
		   My[i].Name.setBorder(OKStatus);
	      }
	}	
	
	private void gatherData()
	{
		DataTransfer.refreshAll();
		this.Selected=0;
		
		for(int i=0;i<NumberOfStudents;i++)
            if(My[i].RollCheckBox.isSelected())
		      {
	           DataTransfer.Roll.add(My[i].RollCheckBox.getText());
	           DataTransfer.Name.add(My[i].Name.getText());
	           DataTransfer.Contact.add(My[i].Contact.getText());
	           this.Selected++;
              }
	}
	
	private class MyJObject 
	{
		JLabel Batch;
		JTextField Name,Contact;
		JCheckBox RollCheckBox;
		
		public MyJObject(int i)
		{
			RollCheckBox = new JCheckBox(DataTransfer.Roll.elementAt(i));
			RollCheckBox.setForeground(Color.WHITE);
			RollCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			RollCheckBox.setOpaque(false);
			RollCheckBox.setSelected(true);
			
			Batch = new JLabel(BatchYear,SwingConstants.CENTER);
			Batch.setForeground(Color.WHITE);
			Batch.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			
			Name = new JTextField(DataTransfer.Name.elementAt(i));
			Name.setForeground(Color.WHITE);
			Name.setFont(new Font("SERRIF",Font.ITALIC,15));
			Name.setOpaque(false);
			Name.setBorder(OKStatus);
			Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			Contact = new JTextField(DataTransfer.Contact.elementAt(i));
			Contact.setForeground(Color.WHITE);
			Contact.setFont(new Font("SERRIF",Font.PLAIN,15));
			Contact.setOpaque(false);
			Contact.setBorder(OKStatus);
			Contact.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}