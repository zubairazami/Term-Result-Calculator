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
import DB.DataTransfer;


public class DataEditPanelTheory implements ActionListener
{
	
	private Controll TempControll;
	private JScrollPane Scroll;
	private JCheckBox CheckBox;
	private JDialog DEPT;
	private JPanel Panel,GridPanel;
	private JLabel ColumnName1,CourseLabel,ExamYearLabel; 
	private JButton UpdateButton,AddButton;
	private MyObject My[];
	private int NumberOfStudents, DrawingSize, UpdateResponse,NumberOfTotalStudents;
	private String Course,Batch,ExamYear;
	private Border OKStatus = new BevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.DARK_GRAY);
	private Border WrongStatus = new BevelBorder(BevelBorder.LOWERED,Color.RED,Color.RED);
	private Vector <String> ListOfSelected = new Vector<String>();
	private JScrollBar VerticalScrollBar;
	private String BatchPortionOfRoll;
	
	public DataEditPanelTheory(Controll x)
	{
		TempControll = x;
	}
	
	public void createDataEditPanelTheory(String Course,String ExamYear,int NOS)
	{
        this.Course = Course;
        this.ExamYear = ExamYear;
        this.NumberOfStudents = NOS;
        this.NumberOfTotalStudents = NOS;
        ListOfSelected.clear();
        
        DEPT = new JDialog();     
        if((NumberOfStudents+NumberOfStudents/2)*40+250>600)
          DrawingSize = (NumberOfStudents+NumberOfStudents/2)*40+250;
        else
          DrawingSize = 600;
        	
          
        final int Final = DrawingSize;
		
		Panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/6.jpg")).getImage(),0,0,916,Final,null);
			   }
		};
		Panel.setPreferredSize(new Dimension(715,NumberOfStudents*40+250));  // 100+50+(100*6+3*5)+50, 50+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60
		Panel.setLayout(null);
		
        setComponentsOnTheGrid();
		
        ColumnName1 = new JLabel("        Roll                     Section-A            Section-B          Perfromance        Exam-Type",SwingConstants.LEFT);
		ColumnName1.setForeground(Color.WHITE);
		ColumnName1.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName1.setBounds(50, 120, 815, 30);
		Panel.add(ColumnName1);
		
		CourseLabel = new JLabel("Course : "+Course,SwingConstants.CENTER);
		CourseLabel.setForeground(Color.WHITE);
		CourseLabel.setFont(new Font("SERRIF",Font.BOLD,20));
		CourseLabel.setBounds(0, 20, 715, 30);
		Panel.add(CourseLabel);
		 
		ExamYearLabel = new JLabel("Examination : "+ExamYear,SwingConstants.CENTER);
		ExamYearLabel.setForeground(Color.WHITE);
		ExamYearLabel.setFont(new Font("SERRIF",Font.BOLD,20));
		ExamYearLabel.setBounds(0, 60, 715, 30);
		Panel.add(ExamYearLabel);
		
		CheckBox = new JCheckBox("Uncheck all");
	    CheckBox.setForeground(Color.WHITE);
	    CheckBox.setContentAreaFilled(false);
	    CheckBox.setOpaque(false);
	    CheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
	    CheckBox.setSelected(true); 
	    CheckBox.setBounds(50,150+NumberOfStudents*40,250,30);
	    Panel.add(CheckBox);
	    CheckBox.addActionListener(this);
		
		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("SERRIF",Font.BOLD,16));
		UpdateButton.setForeground(Color.BLACK);
		UpdateButton.setBounds(350,175+NumberOfTotalStudents*40+15,150,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
		UpdateButton.setOpaque(false);
		UpdateButton.setBorder(OKStatus);
		Panel.add(UpdateButton);
		UpdateButton.addActionListener(this);
		
		AddButton = new JButton("Add Students");
		AddButton.setFont(new Font("SERRIF",Font.BOLD,16));
		AddButton.setForeground(Color.BLACK);
		AddButton.setBounds(185,175+NumberOfTotalStudents*40+15,150,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
		AddButton.setOpaque(false);
		AddButton.setBorder(OKStatus);
		Panel.add(AddButton);
		AddButton.addActionListener(this);
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		DEPT.add(Scroll);
		DEPT.setModal(true);
		DEPT.setTitle("Data Edit");
		DEPT.setResizable (false);
		DEPT.setLocation(300,100);
	    DEPT.setSize(740,565);
		DEPT.setVisible(true);
		DEPT.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	private void setComponentsOnTheGrid()
	{
		GridPanel = new JPanel(new GridLayout(0,5,5,10));
		My = new MyObject[NumberOfStudents+(NumberOfStudents/2)+1];
		
		for(int i = 0;i<NumberOfStudents;i++)
		   {
			My[i] = new MyObject(i);
			GridPanel.add(My[i].RollCheckBox);
			GridPanel.add(My[i].SectionA);
			GridPanel.add(My[i].SectionB);
			GridPanel.add(My[i].Performance);
			GridPanel.add(My[i].ExamType);	
			ListOfSelected.add(My[i].RollCheckBox.getText());
		   }
		GridPanel.setBounds(50, 150,615,40*NumberOfStudents-10); // 6*100+3*5,NumberOfStudents*30+(NumberOfStudents-1)*10
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
		BatchPortionOfRoll = My[0].RollCheckBox.getText().substring(0, 2);
		DataTransfer.refreshAll();
	}
	
	private class MyObject
	{
		JCheckBox RollCheckBox;
		JTextField Performance,SectionA,SectionB;
		JLabel ExamType;
		String x;
		
		public MyObject(int i)
		{
		    RollCheckBox = new JCheckBox(DataTransfer.Roll.elementAt(i));
		    RollCheckBox.setForeground(Color.WHITE);
		    RollCheckBox.setContentAreaFilled(false);
		    RollCheckBox.setOpaque(false);
		    RollCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		    RollCheckBox.setSelected(true);  
			
			Performance = new JTextField(DataTransfer.Performance.elementAt(i));
			Performance.setForeground(Color.WHITE);
			Performance.setFont(new Font("SERRIF",Font.BOLD,15));
			Performance.setOpaque(false);
			Performance.setHorizontalAlignment(SwingConstants.CENTER);
			
			SectionA = new JTextField(DataTransfer.SectionA.elementAt(i));
			SectionA.setForeground(Color.WHITE);
			SectionA.setFont(new Font("SERRIF",Font.BOLD,15));
			SectionA.setOpaque(false);
			SectionA.setHorizontalAlignment(SwingConstants.CENTER);
			
			SectionB = new JTextField(DataTransfer.SectionB.elementAt(i));
			SectionB.setForeground(Color.WHITE);
			SectionB.setFont(new Font("SERRIF",Font.BOLD,15));
			SectionB.setHorizontalAlignment(SwingConstants.CENTER);
			SectionB.setOpaque(false);
		    
			ExamType = new JLabel(DataTransfer.ExamType.elementAt(i),SwingConstants.CENTER);
			ExamType.setOpaque(false);
			ExamType.setForeground(Color.WHITE);
			ExamType.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		}
		
		public MyObject(String i)
		{
		    RollCheckBox = new JCheckBox(i);
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
			
			SectionA = new JTextField();
			SectionA.setForeground(Color.WHITE);
			SectionA.setFont(new Font("SERRIF",Font.BOLD,15));
			SectionA.setOpaque(false);
			SectionA.setHorizontalAlignment(SwingConstants.CENTER);
			
			SectionB = new JTextField();
			SectionB.setForeground(Color.WHITE);
			SectionB.setFont(new Font("SERRIF",Font.BOLD,15));
			SectionB.setOpaque(false);
			SectionB.setHorizontalAlignment(SwingConstants.CENTER);
		    
			ExamType = new JLabel("",SwingConstants.CENTER);
			if(i.substring(0, 2).equals(BatchPortionOfRoll))
			   ExamType.setText("regular");
			else
			   ExamType.setText(TempControll.DataEntryObject.getExamType(i,ExamYear,Course));
			ExamType.setOpaque(false);
			ExamType.setForeground(Color.WHITE);
			ExamType.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent AE) 
	{  
		if(AE.getSource()==CheckBox)
		 {
			boolean Selection = CheckBox.isSelected();
			
			if(Selection)
				CheckBox.setText("Uncheck All");
			else
				CheckBox.setText("Check All");
				
			for(int i=0;i<NumberOfTotalStudents;i++)
			    My[i].RollCheckBox.setSelected(Selection);
		 }
		
		if(AE.getSource()==AddButton)
		  {
			String NewRoll =  JOptionPane.showInputDialog("Enter roll number : ");
			
			if(NewRoll!=null && !NewRoll.equals(""))
			if(NumberOfTotalStudents!=NumberOfStudents*2+NumberOfStudents/2-1)	
			  if(!ListOfSelected.contains(NewRoll))
			    if(TempControll.DataEntryObject.validateStudentOfRoll(NewRoll))
			      
			    	if(TempControll.DataEntryObject.validateNewStudentforDataEdit(NewRoll, Course, ExamYear)||TempControll.DataEntryObject.validateBacklogStudent(NewRoll, Course, ExamYear))
				     {
					  ListOfSelected.add(NewRoll);
					  My[NumberOfTotalStudents] = new MyObject(NewRoll);
					  GridPanel.add(My[NumberOfTotalStudents].RollCheckBox);
					  GridPanel.add(My[NumberOfTotalStudents].SectionA);
					  GridPanel.add(My[NumberOfTotalStudents].SectionB);
					  GridPanel.add(My[NumberOfTotalStudents].Performance);
					  GridPanel.add(My[NumberOfTotalStudents].ExamType);
					  GridPanel.setBounds(50, 150,615,40*(NumberOfTotalStudents)-10);
					  UpdateButton.setBounds(350,175+NumberOfTotalStudents*40+15,150,30);
					  AddButton.setBounds(185,175+NumberOfTotalStudents*40+15,150,30);
					  CheckBox.setBounds(50,150+NumberOfTotalStudents*40,250,30);
					  Panel.setPreferredSize(new Dimension(715,NumberOfTotalStudents*40+250));  // 100+50+(100*6+3*5)+50, 50+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60
					  VerticalScrollBar = Scroll.getVerticalScrollBar();
					  VerticalScrollBar.setValue(VerticalScrollBar.getMaximum());
					  GridPanel.repaint();
					  DEPT.revalidate();
					  NumberOfTotalStudents++;
				    }
				   else
					 JOptionPane.showMessageDialog(DEPT,"Enrolement of  '"+ NewRoll+"'  for  'course : "+this.Course+"'  in 'examination : "+this.ExamYear + "'  is invalid for data entry.", "Invalid Information", JOptionPane.ERROR_MESSAGE);	
			    
			    else 
				  JOptionPane.showMessageDialog(DEPT, "Given Student's information is not valid", "Invalid Student Information", JOptionPane.ERROR_MESSAGE);
			  else
			     JOptionPane.showMessageDialog(DEPT, "Students already added for entry.", "Error", JOptionPane.ERROR_MESSAGE);
			else 
				JOptionPane.showMessageDialog(DEPT, "You're not allowed to enroll more students for data entry.", "Limitation reached", JOptionPane.ERROR_MESSAGE);
		  }
		
		
		if(AE.getSource()==UpdateButton)
		  {
			setToDefault();
			if(!checkProblem())
				JOptionPane.showMessageDialog(DEPT, "Error in entry.\nMake sure you enter the correct data in the indicated field.","Incorect data",JOptionPane.ERROR_MESSAGE);	
			else
			  {
				gatherData();
				UpdateResponse = TempControll.DataEraseEditObject.editExaminationData(this.Course, this.ExamYear, true);
					
				if(NumberOfStudents<NumberOfTotalStudents)
				  {
					
					gatherExtraData();
					TempControll.DataEntryObject.entryData(this.Course, this.ExamYear, NumberOfTotalStudents-NumberOfStudents, DataTransfer.Roll, DataTransfer.SectionA, DataTransfer.SectionB, DataTransfer.Performance, DataTransfer.ExamType);
				    DataTransfer.refreshAll();
				  }
				if(UpdateResponse!=-1)
					JOptionPane.showMessageDialog(null, "Data has been updated successfully for\n          Course           :  "+this.Course+"\n          Examination :  "+this.ExamYear, "Success", JOptionPane.INFORMATION_MESSAGE);
				else 
					JOptionPane.showMessageDialog(null, "Data hasn't been updated for:\nCourse           :  "+this.Course+"\nExamination :  "+this.ExamYear, "Error", JOptionPane.ERROR_MESSAGE);
				
				DEPT.dispose();
			  }
		  }  
	}
	
	private boolean checkTextFieldsText(int i)
	{
	 	boolean Flag = true;
		String Text;
		float Marks;
		
		Text = My[i].SectionA.getText().toString();
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))  // checking if a valid float number
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=105 ){}
	 		  else 
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].SectionA.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].SectionA.setBorder(WrongStatus);
		  }
	    
		
		Text = My[i].SectionB.getText().toString();
		
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=105 ){}
	 		  else
	 			{
	 			  Flag = false;
	 			  My[i].RollCheckBox.setForeground(Color.RED);
	 			  My[i].SectionB.setBorder(WrongStatus);
	 			}
	 	  }
	 	else  
	 	  {
		      Flag = false;
		      My[i].RollCheckBox.setForeground(Color.RED);
		      My[i].SectionB.setBorder(WrongStatus);
	 	  }
	    
		
		Text = My[i].Performance.getText().toString();
		
		if((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals(""))
	 	  {
	 		  Marks = Float.parseFloat(Text);
	 		  if(Marks>=0 && Marks <=90 ){}
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

		return Flag;
	}
	
	private boolean checkProblem()
	{
	 boolean Flag = true;
	 for(int i=0;i<NumberOfTotalStudents;i++)
        {
		 if(My[i].RollCheckBox.isSelected())
    	    if(!checkTextFieldsText(i))
    		  {
    		   My[i].RollCheckBox.setForeground(Color.RED);
    	       Flag = false;
    		  }
        }
	 return Flag;
	}
	
	private void setToDefault()
	{
		for(int i = 0; i < NumberOfTotalStudents;i++)
		   {
			My[i].RollCheckBox.setForeground(Color.WHITE);
			My[i].SectionA.setBorder(OKStatus);
			My[i].SectionB.setBorder(OKStatus);
			My[i].Performance.setBorder(OKStatus);
		   }
	}
	
	private void gatherExtraData()
	{
		DataTransfer.refreshAll();
		for(int i = NumberOfStudents; i < NumberOfTotalStudents;i++)
		   if(My[i].RollCheckBox.isSelected())
		     {
			  DataTransfer.Roll.add(My[i].RollCheckBox.getText());
			  DataTransfer.SectionA.add(My[i].SectionA.getText());
			  DataTransfer.SectionB.add(My[i].SectionB.getText());
			  DataTransfer.Performance.add(My[i].Performance.getText());
			  DataTransfer.ExamType.add(My[i].ExamType.getText());
		     }
	}
	
	private void gatherData()
	{
		DataTransfer.refreshAll();
		for(int i = 0; i < NumberOfStudents;i++)
		   if(My[i].RollCheckBox.isSelected())
		     {
			  DataTransfer.Roll.add(My[i].RollCheckBox.getText());
			  DataTransfer.SectionA.add(My[i].SectionA.getText());
			  DataTransfer.SectionB.add(My[i].SectionB.getText());
			  DataTransfer.Performance.add(My[i].Performance.getText());
		     }
	}
}