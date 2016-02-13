package GUIPackage;

import javax.swing.*;     
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import MainPackage.Controll;

import java.awt.*;    
import java.awt.event.*;
import java.util.Vector;



public class BatchEnrollmentDialog{
	
	private int NumberOfStudents;
	private int BatchYear;
	
	private JDialog Dialog;
	private JLabel ColumnName ;
	private JButton EnrollButton ;
	private JPanel Panel ;
	private JScrollPane Scroll;
	private MyJObject[] My;
	private Vector<String>Roll;
	private Vector<String>Name;
	private Vector<String>Contact;
	private Controll TempControll;
	//private Color MyGrkayColor = new Color(88,88,88);
	
	
	private Border OKStatus = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.WHITE);
	private Border WrongStatus = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.RED);
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.WHITE);
	
	public BatchEnrollmentDialog(Controll x)
	{
		TempControll = x;
	}
	public void createDialogBox(int Batch,int NumberOfStudents)
	{
		Dialog = new JDialog(); 
		
		Roll = new Vector<String>();
		Name = new Vector<String>();
		Contact = new Vector<String>();
		
		this.NumberOfStudents = NumberOfStudents;
		this.BatchYear = Batch;
				
		ColumnName =new JLabel("",SwingConstants.CENTER);
		EnrollButton = new JButton("Enroll");
		
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
		
		ColumnName.setText("  ROLL                                     NAME                                      CONTACT                                    BATCH");
		ColumnName.setForeground(Color.WHITE);
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName.setBounds(50, 20, 815, 30);
		Panel.add(ColumnName);
		
		EnrollButton.setFont(new Font("SERRIF",Font.BOLD,16));
		EnrollButton.setForeground(Color.WHITE);
		EnrollButton.setBounds(333,75+NumberOfStudents*40,250,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
		EnrollButton.setContentAreaFilled(false);
		EnrollButton.setOpaque(false);
		EnrollButton.addActionListener(new ItemHandler());
		Panel.add(EnrollButton);
		
		setComponentsOnTheGrid();
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Dialog.add(Scroll);
		
		Dialog.setModal(true);
		Dialog.setTitle("Batch Enrollment Dialog Box ");
		Dialog.setResizable (false);
		Dialog.setLocation(250,150);
		Dialog.setSize(915,(NumberOfStudents*40+160<500)? NumberOfStudents*40+160:500); // if Dialog is longer then panel then dialog size decrease 
		Dialog.setVisible(true);
	}
	
	private void setComponentsOnTheGrid()
	{
		JPanel GridPanel = new JPanel(new GridLayout(NumberOfStudents,4,5,10));
		My = new MyJObject[NumberOfStudents];
		
		for(int i = 0;i<NumberOfStudents;i++)
		   {
			My[i] = new MyJObject(i+1);
			GridPanel.add(My[i].Roll);
			GridPanel.add(My[i].Name);
			GridPanel.add(My[i].Contact);
			GridPanel.add(My[i].Batch);
		   }
		GridPanel.setBounds(50, 50,815 , 40*NumberOfStudents-10); // 4*200+3*5,NumberOfStudents*30+(NumberOfStudents-1)*10
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
	}
	
	private class ItemHandler implements ActionListener
	{
		Boolean HasEmpty; 
		public void actionPerformed(ActionEvent AE) 
		{
		  if(AE.getSource()==EnrollButton)
		  if(TempControll.ConnectionManagerObject.createConnection())
		    {	 
			 setToDefault();
			 checkProblem();
			 if (HasEmpty)	 
				  JOptionPane.showMessageDialog(null,"Empty Title Field Exists in the Dialog Box","Warning!!",JOptionPane.WARNING_MESSAGE);		 
			 else
				 {
				   for(int i=0;i<NumberOfStudents;i++)
				      {
					   Roll.add(My[i].Roll.getText());
					   Name.add(My[i].Name.getText());
					   Contact.add(My[i].Contact.getText());
				      }
				   
				   if(TempControll.EnrollmentObject.enrollBatch(Roll, Name, Contact, BatchYear, NumberOfStudents))
				     {
					  JOptionPane.showMessageDialog(null, Integer.toString(NumberOfStudents)+" students are Enrolled as student of Department of CSE of "+Integer.toString(BatchYear)+" batch.","Success!!",JOptionPane.INFORMATION_MESSAGE);
					  Dialog.dispose();
				     }
				   Roll.clear();
				   Name.clear();
				   Contact.clear();
				 }
		    }
		}
		
		private void checkProblem()
		{
			HasEmpty = false;
			for(int i=0;i<NumberOfStudents;i++)
		       { 
		    	 if(My[i].Name.getText().toString().equals(""))
		    		{
		    		 My[i].Name.setBorder(WrongStatus);
		    		 HasEmpty =true;
		    		} 
		    	 if(My[i].Contact.getText().toString().equals(""))
		    	   {
		    		 My[i].Contact.setBorder(WrongStatus);
			    	 HasEmpty =true;	 
		    	   }
		       }	
		}
		
		private void setToDefault()
		{
		   for(int i=0;i<NumberOfStudents;i++)
		      {
			   My[i].Contact.setBorder(OKStatus);
			   My[i].Name.setBorder(OKStatus);
		      }
		}	
	}
	private class MyJObject 
	{
		JLabel Roll,Batch;
		JTextField Name,Contact;
		String RollStr;
	    String TempBatchYear;
		
		public MyJObject(int i)
		{
			TempBatchYear = (BatchYear%100<10)?"0"+Integer.toString(BatchYear%100):Integer.toString(BatchYear%100);
			if(i<10)
				RollStr = TempBatchYear+"0700"+Integer.toString(i);	
			else if(i<100)
				RollStr = TempBatchYear+"070"+Integer.toString(i);	
			else
				RollStr = TempBatchYear+"07"+Integer.toString(i);
			
			Roll = new JLabel(RollStr,SwingConstants.CENTER);
			Roll.setForeground(Color.WHITE);
			Roll.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			
			Batch = new JLabel(Integer.toString(BatchYear),SwingConstants.CENTER);
			Batch.setForeground(Color.WHITE);
			Batch.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			
			Name = new JTextField();
			Name.setForeground(Color.WHITE);
			Name.setFont(new Font("SERRIF",Font.ITALIC,15));
			Name.setOpaque(false);
			Name.setBorder(OKStatus);
			Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			Contact = new JTextField();
			Contact.setForeground(Color.WHITE);
			Contact.setFont(new Font("SERRIF",Font.PLAIN,15));
			Contact.setOpaque(false);
			Contact.setBorder(OKStatus);
			Contact.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}