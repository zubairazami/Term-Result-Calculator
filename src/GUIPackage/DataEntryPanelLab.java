package GUIPackage;
import java.awt.*;        
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import MainPackage.Controll;


public class DataEntryPanelLab implements ActionListener
{
	
	Controll TempControll;
	JScrollPane Scroll,GridScroll;
	JDialog DEPT,Other;
	JPanel Panel,GridPanel;
	JLabel ColumnName1,CourseLabel,ExamYearLabel; 
	JButton EntryButton,AddButton;
	MyObject My[];
	int NumberOfStudents,NumberOfBatchStudents;
	JScrollBar VerticalScrollBar;
	String Course,Batch,ExamYear;
	private Border OKStatus = new BevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.DARK_GRAY);
	private Border WrongStatus = new BevelBorder(BevelBorder.LOWERED,Color.RED,Color.RED);
	Vector <String> RollsOfBatch = new Vector<String> ();
	Vector <String> ListOfSelected = new Vector<String>();
	Vector<String> Roll = new Vector<String> ();
	Vector<String> Quiz = new Vector<String> ();
	Vector<String> Attendance = new Vector<String> ();
	Vector<String> Performance = new Vector<String> ();
	Vector<String> CentralViva = new Vector<String>();
	Vector< String > E_Type  = new Vector< String > ();
	
	
	public DataEntryPanelLab(Controll x)
	{
		TempControll = x;
	}
	
	public void createDataEntryPanelLab(String Course, String Batch,String ExamYear)
	{
        this.Course = Course;
        this.Batch = Batch;
        this.ExamYear = ExamYear;
        DEPT = new JDialog();
        ListOfSelected.clear();
        RollsOfBatch.clear();
		ListOfSelected = RollsOfBatch = getBatchStudents(Batch);
		NumberOfBatchStudents = NumberOfStudents = RollsOfBatch.size();
		final int Final = 2* NumberOfStudents+NumberOfStudents/2;
		
		Panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,950,Final*40+250,null);
			   }
		};
		Panel.setPreferredSize(new Dimension(921,NumberOfStudents*40+250));  // 100+50+(100*8+3*7)+50, 50+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60
		Panel.setLayout(null);
		
        setComponentsOnTheGrid();
		
        ColumnName1 = new JLabel("          Roll        Perfromance         Quiz           Attendance    Central-Viva                              Exam-Type",SwingConstants.LEFT);
		ColumnName1.setForeground(Color.WHITE);
		ColumnName1.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName1.setBounds(50, 120, 815, 30);
		Panel.add(ColumnName1);
		
		CourseLabel = new JLabel("Course : "+Course,SwingConstants.CENTER);
		CourseLabel.setForeground(Color.WHITE);
		CourseLabel.setFont(new Font("SERRIF",Font.BOLD,20));
		CourseLabel.setBounds(0, 20, 915, 30);
		Panel.add(CourseLabel);
		 
		ExamYearLabel = new JLabel("Examination : "+ExamYear,SwingConstants.CENTER);
		ExamYearLabel.setForeground(Color.WHITE);
		ExamYearLabel.setFont(new Font("SERRIF",Font.BOLD,20));
		ExamYearLabel.setBounds(0, 60, 915, 30);
		Panel.add(ExamYearLabel);
		
		
		AddButton = new JButton("Add student of other batches");
		AddButton.setFont(new Font("SERRIF",Font.BOLD,12));
		AddButton.setForeground(Color.BLACK);
		AddButton.setBounds(50,175+NumberOfStudents*40,250,30); 
		AddButton.setOpaque(false);
		AddButton.setBorder(OKStatus);
		AddButton.addActionListener(this);
		Panel.add(AddButton);
		
		
		EntryButton = new JButton("Entry");
		EntryButton.setFont(new Font("SERRIF",Font.BOLD,16));
		EntryButton.setForeground(Color.BLACK);
		EntryButton.setBounds(615,175+NumberOfStudents*40,250,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
		EntryButton.setOpaque(false);
		EntryButton.setBorder(OKStatus);
		Panel.add(EntryButton);
		
		EntryButton.addActionListener(this);
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		DEPT.add(Scroll);
		DEPT.setModal(true);
		DEPT.setTitle("Data Entry");
		DEPT.setResizable (false);
		DEPT.setLocation(200,100);
	    DEPT.setSize(940,565);
		DEPT.setVisible(true);
		DEPT.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	private Vector<String> getBatchStudents(String Batch)
	{
		 Vector<String> Rolls = TempControll.DataEntryObject.getRollsofBatch(Batch);
		 return Rolls;
	}
	
	private void setComponentsOnTheGrid()
	{
		GridPanel = new JPanel(new GridLayout(0,8,5,10));
		
		
		My = new MyObject[2*NumberOfStudents+NumberOfStudents/2];
		
		for(int i = 0;i<NumberOfStudents;i++)
		   {
			My[i] = new MyObject(RollsOfBatch.elementAt(i));
			GridPanel.add(My[i].RollCheckBox);
			GridPanel.add(My[i].Performance);
			GridPanel.add(My[i].Quiz);
			GridPanel.add(My[i].Attendance);
			GridPanel.add(My[i].Central_Viva);
			GridPanel.add(My[i].Regular);
			GridPanel.add(My[i].Backlog);	
			GridPanel.add(My[i].Incomplete);
		   }
		GridPanel.setBounds(50, 150,821 , 40*NumberOfStudents-10); // 8*100+3*7,NumberOfStudents*30+(NumberOfStudents-1)*10
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
	}
	
	private class MyObject
	{
		JCheckBox RollCheckBox;
		JTextField Performance,Central_Viva,Quiz,Attendance;
		JRadioButton Regular,Backlog,Incomplete;
		ButtonGroup ExamType;
		
		public MyObject(String Roll)
		{
		    RollCheckBox = new JCheckBox(Roll);
		    RollCheckBox.setForeground(Color.WHITE);
		    RollCheckBox.setContentAreaFilled(false);
		    RollCheckBox.setOpaque(false);
		    RollCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		    RollCheckBox.setSelected(true);  
			
			Performance = new JTextField();
			Performance.setForeground(Color.WHITE);
			Performance.setFont(new Font("SERRIF",Font.BOLD,15));
			Performance.setOpaque(false);
			Performance.setHorizontalAlignment(SwingConstants.CENTER);
			
			Central_Viva = new JTextField();
			Central_Viva.setForeground(Color.WHITE);
			Central_Viva.setFont(new Font("SERRIF",Font.BOLD,15));
			Central_Viva.setOpaque(false);
			Central_Viva.setHorizontalAlignment(SwingConstants.CENTER);
			
			Quiz = new JTextField();
			Quiz.setForeground(Color.WHITE);
			Quiz.setFont(new Font("SERRIF",Font.BOLD,15));
			Quiz.setOpaque(false);
			Quiz.setHorizontalAlignment(SwingConstants.CENTER);
			
		    Attendance = new JTextField();
		    Attendance.setForeground(Color.WHITE);
		    Attendance.setFont(new Font("SERRIF",Font.BOLD,15));
		    Attendance.setOpaque(false);
		    Attendance.setHorizontalAlignment(SwingConstants.CENTER);
			
			Regular = new JRadioButton("Regular");
			Regular.setContentAreaFilled(false);
			Regular.setHorizontalAlignment(SwingConstants.CENTER);
			Regular.setOpaque(false);
			Regular.setForeground(Color.WHITE);
			Regular.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			Regular.setSelected(true);
			
			Backlog = new JRadioButton("Backlog");
			Backlog.setContentAreaFilled(false);
			Backlog.setHorizontalAlignment(SwingConstants.CENTER);
			Backlog.setOpaque(false);
			Backlog.setForeground(Color.WHITE);
			Backlog.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));	
			
			Incomplete = new JRadioButton("Incomplete");
			Incomplete.setContentAreaFilled(false);
			Incomplete.setHorizontalAlignment(SwingConstants.CENTER);
			Incomplete.setOpaque(false);
			Incomplete.setForeground(Color.WHITE);
			Incomplete.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));			
			
			ExamType = new ButtonGroup();
			ExamType.add(Regular);
			ExamType.add(Backlog);
			ExamType.add(Incomplete);
		}
	}

	@Override
	public void actionPerformed(ActionEvent AE) 
	{
	 if(TempControll.ConnectionManagerObject.createConnection())
	   {  
		if(AE.getSource()==AddButton)
		  {
			String BackRoll =  JOptionPane.showInputDialog("Enter roll number : ");
			if(!BackRoll.equals(""))
			if(NumberOfStudents!=NumberOfBatchStudents*2+NumberOfBatchStudents/2-1)	
			  if(!ListOfSelected.contains(BackRoll))
			    if(TempControll.DataEntryObject.validateStudentOfRoll(BackRoll))
				   if(TempControll.DataEntryObject.validateBacklogStudent(BackRoll, Course, ExamYear))
				     {
					  ListOfSelected.add(BackRoll);
					  My[NumberOfStudents] = new MyObject(BackRoll);
					  GridPanel.add(My[NumberOfStudents].RollCheckBox);
					  GridPanel.add(My[NumberOfStudents].Performance);
					  GridPanel.add(My[NumberOfStudents].Quiz);
					  GridPanel.add(My[NumberOfStudents].Attendance);
					  GridPanel.add(My[NumberOfStudents].Central_Viva);
					  GridPanel.add(My[NumberOfStudents].Regular);
					  GridPanel.add(My[NumberOfStudents].Backlog);
					  GridPanel.add(My[NumberOfStudents].Incomplete);
					  GridPanel.setBounds(50, 150,821, 40*(NumberOfStudents)-10);
					  EntryButton.setBounds(615,175+NumberOfStudents*40,250,30);
					  AddButton.setBounds(50,175+NumberOfStudents*40,250,30);
					  Panel.setPreferredSize(new Dimension(715,NumberOfStudents*40+250));  // 100+50+(100*6+3*5)+50, 50+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60
					  VerticalScrollBar = Scroll.getVerticalScrollBar();
					  VerticalScrollBar.setValue(VerticalScrollBar.getMaximum());
					  GridPanel.repaint();
					  DEPT.revalidate();
					  NumberOfStudents++;
				    }
				  else
					 JOptionPane.showMessageDialog(DEPT,"Enrolement of  '"+ BackRoll+"'  for  'course : "+this.Course+"'  in 'examination : "+this.ExamYear + "'  is invalid for data entry.", "Invalid Information", JOptionPane.ERROR_MESSAGE);	
			    else 
				  JOptionPane.showMessageDialog(DEPT, "Given Student's information is not valid", "Invalid Student Information", JOptionPane.ERROR_MESSAGE);
			  else
			     JOptionPane.showMessageDialog(DEPT, "Students already added for entry.", "Error", JOptionPane.ERROR_MESSAGE);
			else 
				JOptionPane.showMessageDialog(DEPT, "You're not allowed to enroll more students for data entry.", "Limitation reached", JOptionPane.ERROR_MESSAGE);
		  }
		
		
		if(AE.getSource()==EntryButton)
		  {
			setToDefault();
			if(!checkProblem())
				JOptionPane.showMessageDialog(DEPT, "Error in entry.\nMake sure you enter the correct data in the indicated field.","Incorect data",JOptionPane.ERROR_MESSAGE);	
			else
			  {
				gatherData();
				TempControll.DataEntryObject.entryData(this.Course, this.ExamYear, Roll.size(), Roll, Performance, Quiz, Attendance, CentralViva, E_Type);
				JOptionPane.showMessageDialog(null, "Data has been stored successfully for\n          Course           :  "+this.Course+"\n          Examination :  "+this.ExamYear, "Success", JOptionPane.INFORMATION_MESSAGE);
				DEPT.dispose();
			  }
		  }
	   }
	}
	
	private boolean checkTextFieldsText(int i)
	{
	 	boolean Flag = true;
		String Text;
		float Marks;
		
		Text = My[i].Central_Viva.getText().toString();
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))  // checking if a valid float number
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=20 ){}
	 		  else 
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].Central_Viva.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].Central_Viva.setBorder(WrongStatus);
		  }
	    
		
		Text = My[i].Quiz.getText().toString();
		
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=20 ){}
	 		  else
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].Quiz.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].Quiz.setBorder(WrongStatus);
	 	  }
	    
		
		Text = My[i].Performance.getText().toString();
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=50 ){}
	 		  else 
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].Performance.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].Performance.setBorder(WrongStatus);
	 	  }
		
        Text = My[i].Attendance.getText().toString();
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=10 ){}
	 		  else 
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].Attendance.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].Attendance.setBorder(WrongStatus);
	 	  }
		return Flag;
	}
	
	private boolean checkProblem()
	{
	 boolean Flag = true;
	 String ExamType,CP_Roll;
	 for(int i=0;i<NumberOfBatchStudents;i++)
        {
		 if(My[i].RollCheckBox.isSelected())
    	    {
    		  if(My[i].Backlog.isSelected()||My[i].Incomplete.isSelected()||!checkTextFieldsText(i))
    		    {
    			 My[i].RollCheckBox.setForeground(Color.RED);
    	         if(My[i].Backlog.isSelected())
    	        	 My[i].Backlog.setForeground(Color.RED);
    	         if(My[i].Incomplete.isSelected())
    	        	 My[i].Incomplete.setForeground(Color.RED);
    	         Flag = false;
    		    }
    	    }
        }
	 for(int i = NumberOfBatchStudents;i<NumberOfStudents;i++)
	    { 
		 if(My[i].RollCheckBox.isSelected())
   	       {
			 CP_Roll = My[i].RollCheckBox.getText().toString();
	    	 ExamType = TempControll.DataEntryObject.getExamType(CP_Roll,this.ExamYear,this.Course);
	    	 if(!checkTextFieldsText(i))
   		       {
   	            My[i].RollCheckBox.setForeground(Color.RED);
   	            Flag = false;
   		       }
			 if((My[i].Regular.isSelected() && !ExamType.equalsIgnoreCase("regular"))||(My[i].Backlog.isSelected() && !ExamType.equalsIgnoreCase("backlog"))||(My[i].Incomplete.isSelected() && !ExamType.equalsIgnoreCase("incomplete")))
			   {
				Flag = false;
				My[i].RollCheckBox.setForeground(Color.RED);
				if(My[i].Regular.isSelected())
				  My[i].Regular.setForeground(Color.RED);
				else if(My[i].Incomplete.isSelected())
				  My[i].Incomplete.setForeground(Color.RED);
				else
				 My[i].Backlog.setForeground(Color.RED);	
			   }
   	       }
	   }
	 return Flag;
	}
	private void setToDefault()
	{
		for(int i = 0; i < NumberOfStudents;i++)
		   {
			My[i].RollCheckBox.setForeground(Color.WHITE);
			My[i].Performance.setBorder(OKStatus);
			My[i].Quiz.setBorder(OKStatus);
			My[i].Attendance.setBorder(OKStatus);
			My[i].Central_Viva.setBorder(OKStatus);
		    My[i].Regular.setForeground(Color.WHITE);
		    My[i].Backlog.setForeground(Color.WHITE);
		    My[i].Incomplete.setForeground(Color.WHITE);
		   }
	}
	private void gatherData()
	{
		Roll.clear();
		Performance.clear();
		Quiz.clear();
		Attendance.clear();
		CentralViva.clear();
		E_Type.clear();
		for(int i = 0; i < NumberOfStudents;i++)
	 	   if(My[i].RollCheckBox.isSelected())
		     {
	 		   Roll.add(My[i].RollCheckBox.getText());
	 		   Performance.add(My[i].Performance.getText());
	 		   Quiz.add(My[i].Quiz.getText());
	 		   Attendance.add(My[i].Attendance.getText());
	 		   CentralViva.add(My[i].Central_Viva.getText());
	 		   if (My[i].Regular.isSelected())
	 			   E_Type.add("regular");
	 		   else	if (My[i].Backlog.isSelected())
	 			   E_Type.add("backlog");
	 		   else 
	 			  E_Type.add("incomplete");
		   }
	}
}