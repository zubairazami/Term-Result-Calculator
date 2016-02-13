package GUIPackage;

import MainPackage.Controll;

import javax.swing.*;   
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;    
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MultipleCourseEnrollmentDialog{
	
	private int Number;
	private JCheckBox[] Check;
	private JDialog Frame;
	private JLabel ColumnName;
	private JButton EnrollButton;
	private JPanel Panel ;
	private JScrollPane Scroll;
	private MyJObject [] My;
	private Controll TempControll;
	private Border ButtonBorder;
	private JLabel CheckLabel;
	private Color MyColor;
	
	
	
	public MultipleCourseEnrollmentDialog(Controll X) 
	{
		TempControll = X;
	}


	public void createDialogBox(int Number)
	{
		this.Number = Number;
		final int FinalNumber = this.Number;
		
		MyColor = Color.LIGHT_GRAY; //new Color(97,97,97);
		ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.GRAY);
		Frame = new JDialog();
		ColumnName = new JLabel("",SwingConstants.LEFT);
		CheckLabel = new JLabel("Uncheck All",SwingConstants.LEFT);
		EnrollButton = new JButton("Enroll");
		Check = new JCheckBox[Number+1];
		ItemHandler IH = new ItemHandler();
		
		Panel = new JPanel()
		   {
			  protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,1335,FinalNumber*40+150,null);
				ButtonBorder.paintBorder(this, g,542,84+FinalNumber*40,252,32 );
			   }
		   };
		
		
		for(int i=0;i<Number+1;i++)
		   {
			 Check[i] = new JCheckBox();
			 Check[i].setSelected(true);
			 Check[i].setContentAreaFilled(false);
			 Check[i].setOpaque(false);
			 Check[i].setBounds(20, 60+i*40, 30, 30);
			 Check[i].setBorder(new BevelBorder(BevelBorder.LOWERED));
			 Panel.add(Check[i]);
			 Check[i].addItemListener(IH);
		   }
		
		Panel.setPreferredSize(new Dimension(1335,Number*40+150));  // (150*8+35)+100, 50+Number*30+(Number-1)*10+50+60
		Panel.setLayout(null);
		
		ColumnName.setText("          SERIAL NO                  DEPT.                          YEAR                           TERM                  COURSE-SUFFIX              CREDIT                     COURSE NO                    TITLE");
		ColumnName.setForeground(Color.WHITE); 
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName.setBounds(50, 20,150*8+35 , 30);
		Panel.add(ColumnName);
		
		CheckLabel.setForeground(Color.GRAY);
		CheckLabel.setFont(new Font("SERRIF",Font.BOLD,13));
		CheckLabel.setBounds(93, 50+Number*40,200,30);
		Panel.add(CheckLabel);
		
		EnrollButton.setFont(new Font("SERRIF",Font.BOLD,15));
		EnrollButton.setForeground(Color.WHITE);
		EnrollButton.setBounds(543,85+Number*40,250,30); // 50+Number*30+(Number-1)*10+35
		EnrollButton.setContentAreaFilled(false);
		EnrollButton.setOpaque(false);
		EnrollButton.addActionListener(new ItemHandler());
		Panel.add(EnrollButton);
		
		setComponentsOnTheGrid();
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		Frame.add(Scroll);
		Frame.setModal(true);
		Frame.setTitle("Multiple Course Enrollment Dialog Box ");
		Frame.setResizable (false);
		Frame.setLocation(30,150);
		Frame.setSize(1335,(Number*40+160<600)? Number*40+160:600); // if Dialog is longer then panel then dialog size decrease 
		Frame.setVisible(true);
	}
	
	
	private void setComponentsOnTheGrid()
	{
		JPanel GridPanel = new JPanel(new GridLayout(Number,8,5,10));
		My = new MyJObject[Number];
		
		for(int i=0;i<Number;i++)
           {	
			 My[i] = new MyJObject(i);
			 GridPanel.add(My[i].Serial);
		     GridPanel.add(My[i].Dept);
		     GridPanel.add(My[i].Year);
		     GridPanel.add(My[i].Term);
		     GridPanel.add(My[i].Suffix);
		     GridPanel.add(My[i].Credit);
		     GridPanel.add(My[i].CourseNo);
		     GridPanel.add(My[i].CourseName);
           }
		GridPanel.setBounds(50,60,150*8+35,Number*30+(Number-1)*10);
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
	}
	
	
	
	private class ItemHandler implements ActionListener,ItemListener
	{
		private Set<String> Myset = new HashSet<String>();
		private Boolean HasRepeated, HasEmpty,Selection; 
		private int Selected = 0;
		private Vector<String>CourseNo = new Vector<String>();
		private Vector<String>CourseTitle = new Vector<String>();
		private Vector<String>CourseCredit = new Vector<String>(); 
		private Border OKState = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.GRAY);
		private Border WrongState = new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.RED);
		
		public void actionPerformed(ActionEvent AE) 
		{
			 setDefault();
			 checkProblem();
			 if(HasRepeated && HasEmpty )  
				 JOptionPane.showMessageDialog(null,"Identical Course No. & Empty Title Field Exists in the Dialog Box","Warning!!",JOptionPane.WARNING_MESSAGE);
			 else if(HasRepeated)
				JOptionPane.showMessageDialog(null,"Identical Course No. Exists in the Dialog Box","Warning!!",JOptionPane.WARNING_MESSAGE);	
             else if (HasEmpty)	 
				  JOptionPane.showMessageDialog(null,"Empty Title Field Exists in the Dialog Box","Warning!!",JOptionPane.WARNING_MESSAGE);		 
			 else
			    {
				  gatherAll();
				  if(Selected == 0) 
					  JOptionPane.showMessageDialog(null, "No Course Selected.\nAt least , one single course has to be selected","Error!!",JOptionPane.ERROR_MESSAGE);
				  else
				     {
					  if(TempControll.EnrollmentObject.enrollCourses(Selected,CourseNo,CourseTitle,CourseCredit)) 
						 {
						  if(Selected != 1)
						       JOptionPane.showMessageDialog(null, "Selected " + Integer.toString(Selected)+" courses are enrolled successfully.","Success!!",JOptionPane.INFORMATION_MESSAGE);	     
						  else  JOptionPane.showMessageDialog(null, "Selected course is enrolled successfully.","Success!!",JOptionPane.INFORMATION_MESSAGE);	 
						  Frame.dispose();
						 }
					  else
					    {
						   if(!TempControll.EnrollmentObject.getIdenticalCourses().isEmpty())
						     {
						      checkProblem(TempControll.EnrollmentObject.getIdenticalCourses());
						      JOptionPane.showMessageDialog(null," Existing Course found " , "Error", JOptionPane.ERROR_MESSAGE);
						     }
					    }
				     }
			    }
		}
		
		public void itemStateChanged(ItemEvent IE) 
		{
			if(IE.getSource()==Check[Number])
			  {
				
				Selection = Check[Number].isSelected();
				if(Selection)
					CheckLabel.setText("Uncheck All");
				else
					CheckLabel.setText("Check All");
			 
			   for(int i=0;i<=Number;i++)
				   Check[i].setSelected(Selection);
			  }
		}	
		
		private void gatherAll()
		{
			Selected = 0;
			CourseNo.clear();
			CourseTitle.clear();
			CourseCredit.clear();
			
			for(int i=0;i<Number;i++)
			    if(Check[i].isSelected())
		      	  {
			    	CourseNo.add(My[i].CourseNo.getText());
		      		CourseTitle.add(My[i].CourseName.getText());
		      		CourseCredit.add(My[i].Credit.getSelectedItem().toString());
		      		Selected++;
		      	  }   
		}
		
		private void checkProblem()
		{
			HasRepeated = false;
			HasEmpty = false;
			BevelBorder x;
			
			for(int i=0;i<Number;i++)
		       if(Check[i].isSelected()) 
		         {
				  if(!Myset.add( My[i].CourseNo.getText().toString()))  //which means elements already exists
		    		 {
		    		  My[i].Serial.setForeground(Color.RED);
					  My[i].CourseNo.setForeground(Color.RED);
		    		  HasRepeated = true;
		    		 }
				  if(My[i].CourseName.getText().toString().equals(""))
		    		 {
					  My[i].Serial.setForeground(Color.RED);
					  My[i].CourseName.setBackground(Color.RED);
					  My[i].CourseName.setBorder(WrongState);
		    		  HasEmpty =true;
		    		 } 
		         }	
		}
		
		private void checkProblem(Vector<String>Identical)
		{
			for(int i=0;i<Number;i++)
			   if(Identical.contains(My[i].CourseNo.getText()))
				  {
					Check[i].setSelected(false);
					My[i].Serial.setForeground(Color.RED);
					My[i].CourseNo.setForeground(Color.RED);
				  }
		}
		private void setDefault()
		{
			
			Myset.clear(); 
			for(int i=0;i<Number;i++)
		       {
			    My[i].CourseNo.setForeground(Color.GRAY); 
			    My[i].Serial.setForeground(Color.GRAY); 
			    My[i].CourseName.setBorder(OKState);
		       }
		}
		
		
	
	}
	 class MyJObject implements ItemListener {
		
		String DepartmentName[] = { "CSE","EEE","ECE","ME","CE","URP","IEM","LE","PHY","CHEM","MATH","HUM" };
		String YearName[] = { "1st","2nd","3rd","4th" };
		String CourseCredit[] = {"0.75","1.50", "2.00","3.00","4.00"};
		 
		private String Name = new String("");
		public JLabel Serial;
		public JComboBox Dept,Year,Term ,Suffix,Credit;
		public JLabel CourseNo;
		public JTextField CourseName;
		
		private BevelBorder x =new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.LIGHT_GRAY);
		
		public MyJObject (int i)
		{			
		     Serial = new JLabel("Course "+Integer.toString(i+1)+" : ",SwingConstants.CENTER);
			 Serial.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,13));
			 Serial.setForeground(Color.WHITE);
			
			 Dept=new JComboBox();
			 Dept.setMaximumRowCount(5);
			 Dept.setOpaque(false);
			 Dept.setBackground(Color.WHITE);
			 Dept.setForeground(Color.BLACK);
			// Dept.setBorder(x);
			 
			 Year=new JComboBox();
			 Year.setMaximumRowCount(5);
			 Year.setOpaque(false);
			 Year.setBackground(Color.WHITE);
			 Year.setForeground(Color.BLACK);
			 //Year.setBorder(x);
		    
			 Term =new JComboBox();
			 Term.setMaximumRowCount(5);
			 Term.setOpaque(false);
			 Term.setBackground(Color.WHITE);
			 Term.setForeground(Color.BLACK);
			 //Term.setBorder(x);
			 
			 Credit =new JComboBox();
			 Suffix =new JComboBox();
			 Suffix.setMaximumRowCount(5);
			 Suffix.setOpaque(false);
			 Suffix.setBackground(Color.WHITE);
			 Suffix.setForeground(Color.BLACK);
			// Suffix.setBorder(x);
			 
			 Credit.setMaximumRowCount(5);
			 Credit.setOpaque(false);
			 Credit.setBackground(Color.WHITE);
			 Credit.setForeground(Color.BLACK);
			 //Credit.setBorder(x);
			 
			 CourseNo= new JLabel("CSE-1100",SwingConstants.CENTER);
			 CourseNo.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,13));
			 CourseNo.setForeground(Color.WHITE);
			 
			 CourseName= new JTextField();
			 CourseName.setFont(new Font("SERRIF",Font.BOLD,13));
			 CourseName.setForeground(Color.WHITE);
	 	     CourseName.setOpaque(false);
	 	     CourseName.setBorder(new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.WHITE));
	 	     CourseName.setHorizontalAlignment(SwingConstants.CENTER);
			 
		     for(String str : DepartmentName )
		    	 Dept.addItem(str);
		
	         for(String str : YearName )
	        	 Year.addItem(str);
		
	         Term.addItem("1st");
	         Term.addItem("2nd");
		
		     for(int p = 0; p<=99;p++)
		        {
			     if(p<10) Suffix.addItem(Integer.toString(0)+Integer.toString(p));
			     else Suffix.addItem(Integer.toString(p));
		        }
		     
		     for(String str : CourseCredit)
		    	 Credit.addItem(str);	
		     
		     Dept.addItemListener(this);
		     Year.addItemListener(this);
		     Term.addItemListener(this);
		     Suffix.addItemListener(this);
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) 
		{	
			Name = Dept.getSelectedItem().toString()+"-"+Year.getSelectedItem().toString().charAt(0)+Term.getSelectedItem().toString().charAt(0)+Suffix.getSelectedItem().toString();
		    CourseNo.setText(Name);
		}	
	}
}