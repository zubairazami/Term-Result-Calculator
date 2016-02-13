package DB;

import java.sql.ResultSet;      
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import MainPackage.Controll;

public class DataEntry {
	
	private ConnectionManager TempCM ;
	private Statement EStatement;
	private ResultSet EResultSet;
	private Vector<String>EnrolledCourses = new Vector<String>();
	private Vector<String>EnrolledBatches = new Vector<String>();
	private Vector<String>EnrolledExamYears = new Vector<String>();
	
	public DataEntry(Controll x)
	{
	 TempCM = x.ConnectionManagerObject;  
	}
	
	
	public boolean validateStudentOfRoll(String Roll)
	{
		closeAll();
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT * FROM students_information WHERE roll = '"+Roll+"';");
			 if(!EResultSet.next())
		       {
				 closeAll();
				 return false;
		       }
		     else
		       {
		    	 closeAll();
		    	 return true;
		       }
		    }
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in validateStudentOfRoll() method ", JOptionPane.ERROR_MESSAGE);
		       
			 return false;
		    }
		
		
	}
	
	public String getExamType(String Roll,String ExamYear,String Course)
	{
		closeAll();
		String DataTable = getDataTable(Course);
		String ExamType;
		float TotalMarks;
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT exam_type, total FROM "+DataTable+" WHERE roll = '"+Roll+"' AND exam_year < '"+ExamYear+"' AND course_no = '"+Course+"' ORDER BY exam_year DESC;");    
		     if(!EResultSet.next())   // if he didn't sit in his regular exam year or dropped any course of batch.
		       {
		        closeAll();	 
		        return "incomplete";  // will be treated as incomplete
		       }
		     else 
		       { 
		    	 ExamType = EResultSet.getObject(1).toString();
		         TotalMarks = Float.parseFloat(EResultSet.getObject(2).toString());
		    	 closeAll();
		         if(ExamType.equalsIgnoreCase("regular")||ExamType.equalsIgnoreCase("incomplete"))  
		           {
		        	 if(TotalMarks<40)          // have to sit for backlog exam 
		        		 return "backlog";
		        	 else                       // don't need to sit for backlog so will be treated as previous (regular/incomplete)
		        	 {
		        		 ExamType = ExamType.toLowerCase();
		        		 return ExamType;
		        	 }
		           }
		         else return "backlog";         // he previously sat for a backlog exam and failed so again he will be treated as backlog student .. .. . :)
		       }
		    }
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getExamType() method ", JOptionPane.ERROR_MESSAGE);
			 return "";
		    }
	}
	
	public boolean validateBacklogStudent(String Roll,String Course,String Year)
	{
		closeAll();
		String DataTable = getDataTable(Course); 
		String AlternateRoll = Roll.substring(0, 4)+"___"; // to determine batch
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT total FROM "+DataTable+" WHERE roll = '"+Roll+"' AND exam_year < '"+Year+"' AND course_no = '"+Course+"' ORDER BY exam_year DESC;");
			 if(!EResultSet.next())   // checking whether the given roll exists for backlog course enrolement
		       {
				 EResultSet = EStatement.executeQuery("SELECT roll FROM "+DataTable+" WHERE exam_year < '"+Year+"' AND course_no = '"+Course+"' AND roll LIKE '"+AlternateRoll+"';");
				 if(!EResultSet.next())  // if no data found as previous condition then it checks if that batch really sat for the exam previously     
				   {
					 closeAll();
					 System.out.println(AlternateRoll);
				     return false;
				   }
				 else                    //It means that the student we are going to enroll didn't sit for the exam previously & could be added as incomplete ;
				   {
					 closeAll();
				     return true;  
				   }
		       }
		     else                      
		       {
                 if(Float.parseFloat(EResultSet.getObject(1).toString())<40)   // if the student is found then checks is his previous exam's mark is worthy for  enrolement of Backlog exam
                   {
            	     closeAll();
            	     return true;
                   }
                 else
                   {
            	     closeAll();
            	     return false; 
                   }
		       }
		    }
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in validateBacklogStudent() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		    }
		
	}
	
	public boolean validateNewStudentforDataEdit(String Roll,String Course,String Year)
	{
		closeAll();
		String DataTable = getDataTable(Course); 
		String AlternateRoll = Roll.substring(0, 4)+"___"; // to determine batch
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT total FROM "+DataTable+" WHERE roll = '"+Roll+"' AND exam_year = '"+Year+"' AND course_no = '"+Course+"' ORDER BY exam_year DESC;");
			 if(!EResultSet.next())   // checking whether the given roll exists for backlog course enrolement
		       {
				 EResultSet = EStatement.executeQuery("SELECT roll FROM "+DataTable+" WHERE exam_year = '"+Year+"' AND course_no = '"+Course+"' AND roll LIKE '"+AlternateRoll+"';");
				 if(!EResultSet.next())  // if no data found as previous condition then it checks if that batch really sat for the exam previously     
				   {
					 closeAll();
				     return false;
				   }
				 else                    //It means that the student we are going to enroll didn't sit for the exam previously & could be added as incomplete ;
				   {
					 closeAll();
				     return true;  
				   }
		       }
		     else                      
		       {
                 if(Float.parseFloat(EResultSet.getObject(1).toString())<40)   // if the student is found then checks is his previous exam's mark is worthy for  enrolement of Backlog exam
                   {
            	     closeAll();
            	     return true;
                   }
                 else
                   {
            	     closeAll();
            	     return false; 
                   }
		       }
		    }
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in validateNewStudentForDataEdit() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		    }
		
	}
	
	
	public  Vector<String> getEnrolledCourses()
	{
		EnrolledCourses.clear();
		closeAll();
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT course_no FROM courses");
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return EnrolledCourses;
		       }
		     else
		       EResultSet.previous();  
		      
             while(EResultSet.next())
            	 EnrolledCourses.add( EResultSet.getObject(1).toString() );
		     closeAll();
             return EnrolledCourses;
		    } 
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getEnrolledCourses() method ", JOptionPane.ERROR_MESSAGE);
			 return EnrolledCourses;
		    }
	}
	
	public  Vector<String> getEnrolledCourses(int i, int j)
	{
		EnrolledCourses.clear();
		closeAll();
		
		String Temp = Integer.toString(i)+Integer.toString(j);
		String Command =  "SELECT course_no FROM courses WHERE course_no LIKE '____"+Temp+"__' OR course_no LIKE '___"+Temp+"__' OR course_no LIKE '_____"+Temp+"__';";
		
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery(Command);
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return EnrolledCourses;
		       }
		     else
		       EResultSet.previous();  
		      
             while(EResultSet.next())
            	 EnrolledCourses.add( EResultSet.getObject(1).toString() );
		     closeAll();
             return EnrolledCourses;
		    } 
		catch (SQLException e) 
		    {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getEnrolledCourses() method ", JOptionPane.ERROR_MESSAGE);
			 return EnrolledCourses;
		    }
	}
	
	
	public  Vector<String> getEnrolledBatches()
	{
		EnrolledBatches.clear();
		closeAll();
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT batch_no FROM batch");
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return EnrolledBatches;
		       }
		     else
		       EResultSet.previous();  
		      
             while(EResultSet.next())
            	 EnrolledBatches.add( EResultSet.getObject(1).toString() );
		     closeAll();
             return EnrolledBatches;
		    } 
		catch (SQLException e) 
		    {
			 closeAll();
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getEnrolledBatches() method ", JOptionPane.ERROR_MESSAGE);
			 return EnrolledBatches;
		    }
	}
	
	public boolean validateDataEntry(String Course,String Year,String Batch)
	{
		
		String Table,sql;
		try {
			 Table = getDataTable(Course);
			 sql = "SELECT roll FROM "+Table+" WHERE batch = '"+Batch+"' AND course_no = '"+Course+"' AND exam_year = '"+Year+"' ;";
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery(sql);
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return true;
		       }
		     else
		       {
		    	 closeAll();
                 return false;
		       }
			} 
		catch (SQLException e) 
		    {
			 closeAll();
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in validateDataEntry() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		    }		    
	}
	
	private void  closeAll()
	{
		if(EResultSet!=null)
		   EResultSet = null;
		if(EStatement!=null)
		   EStatement = null;
	}
    
	private String getDataTable(String Course)
	{
		int Year,Type;
		String Table="";
		
		if(Course.length()==8)   //like cse ,eee,ece
		  {
			Year = Integer.parseInt(Character.toString(Course.charAt(4)));
			Type = Integer.parseInt(Character.toString(Course.charAt(7)))%2;
		  }
		else if(Course.length()==9)
		  {
			 Year = Integer.parseInt(Character.toString(Course.charAt(5)));
			 Type = Integer.parseInt(Character.toString(Course.charAt(8)))%2;
		  }
		else   // me,ce,le
		  {
			 Year = Integer.parseInt(Character.toString(Course.charAt(3)));
			 Type = Integer.parseInt(Character.toString(Course.charAt(6)))%2;
		  }
		
		if(Type == 0)
		 {
		  if(Year == 1)
			  Table = "lab_first_year";
		  else if(Year == 2)
			  Table = "lab_second_year";
		  else if(Year == 3)
			  Table = "lab_third_year";
		  else
			  Table = "lab_fourth_year";
		 }
		else
		 {
		  if(Year == 1)
			  Table="theory_first_year";
		  else if(Year == 2)
			  Table="theory_second_year";
		  else if(Year == 3)
			  Table="theory_third_year";
		  else
			  Table="theory_fourth_year";
		 }
		
		return Table;
	}

	public Vector<String> getEnrolledExamYearAsCourses(String Course) 
	{
		String  Table = getDataTable(Course);
		String Year;
		EnrolledExamYears.clear();
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT exam_year FROM "+Table+" WHERE course_no = '"+Course+"' ;");
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return EnrolledExamYears;
		       }
		     else
		    	EResultSet.previous();
		    	
			 while(EResultSet.next())
			      {
				   Year = EResultSet.getObject(1).toString();
				   if(!EnrolledExamYears.contains(Year))
				      EnrolledExamYears.add(Year);
			      }
			 closeAll();
		     return EnrolledExamYears;
			} 
		catch (SQLException e) 
		    {
			 closeAll();
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getEnrolledExamYearsAsCourses() method ", JOptionPane.ERROR_MESSAGE);
			 return EnrolledExamYears;
		    }	
	}
		
		public Vector<String> getRollsofBatch(String Batch)
		{
			Vector<String>Roll = new Vector<String>(); 
			Roll.clear();
			try {
				 EStatement = TempCM.MyConnection.createStatement();
				 EResultSet = EStatement.executeQuery("SELECT Roll FROM `students_information` WHERE batch = '"+Batch+"' ORDER BY Roll ASC;");
				 if(!EResultSet.next())
			       {
			  	    closeAll();
			  	    return Roll;
			       }	    	
				 EResultSet.previous();
				 while(EResultSet.next())
					  Roll.add(EResultSet.getObject(1).toString());
				 closeAll();
			     return Roll;
				} 
			catch (SQLException e) 
			    {
				 closeAll();
				 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getRollsofBatch() method ", JOptionPane.ERROR_MESSAGE);
				 return Roll;
			    }	
		}
		
		public void entryData(String Course,String ExamYear,int number,Vector<String>Roll,Vector<String>SecA,Vector<String>SecB,Vector<String>Per,Vector<String>ExamType)
		{
			closeAll();
		    String Table = getDataTable(Course);
		    int Batch;
		    float Marks,A,B,P;
		    try
		       {
		    	  EStatement = TempCM.MyConnection.createStatement();
				  for(int i = 0;i<number;i++)
				     { 
					   Batch = 2000 + Integer.parseInt(Roll.elementAt(i).substring(0, 2)); 
					   A = Float.parseFloat(SecA.elementAt(i));
					   B = Float.parseFloat(SecB.elementAt(i));
					   P = Float.parseFloat(Per.elementAt(i));
					   Marks = ((A+B+P)/300)*100;
					   EStatement.executeUpdate("INSERT INTO `"+Table+"` (`roll` ,`exam_year` ,`course_no` ,`batch` ,`exam_type` ,`m_performance` ,`m_section_a` ,`m_section_b` ,`total`)VALUES ('"+Roll.elementAt(i)+"', '"+ExamYear+"', '"+Course+"', '"+Batch+"', '"+ExamType.elementAt(i)+"', '"+P+"', '"+A+"', '"+B+"', '"+Marks+"');");  
				     }
				  closeAll();
		       }
		     catch (SQLException e) 
		       {
		    	 closeAll();
		    	 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in entryData() method ", JOptionPane.ERROR_MESSAGE);
			   }
		}
		
		public void entryData(String Course,String ExamYear,int number,Vector<String>Roll,Vector<String>Performance,Vector<String>Quiz,Vector<String>Attendance,Vector<String>CentralViva,Vector<String>ExamType)
		{
			closeAll();
		    String Table = getDataTable(Course);
		    int Batch;
		    float Marks,P,Q,A,C;
		    try
		       {
				  EStatement = TempCM.MyConnection.createStatement();
				  for(int i = 0;i<number;i++)
				     { 
					   Batch = 2000 + Integer.parseInt(Roll.elementAt(i).substring(0, 2)); 
					   P = Float.parseFloat(Performance.elementAt(i));
					   Q = Float.parseFloat(Quiz.elementAt(i));
					   A = Float.parseFloat(Attendance.elementAt(i));
					   C = Float.parseFloat(CentralViva.elementAt(i));
					   Marks = P+Q+A+C ;
					   EStatement.executeUpdate("INSERT INTO `"+Table+"` (`roll` ,`exam_year` ,`course_no` ,`batch` ,`exam_type` ,`attendance_mark` ,`quiz_mark` ,`cv_mark` ,`performance_mark` ,`total`)VALUES ('"+Roll.elementAt(i)+"', '"+ExamYear+"', '"+Course+"', '"+ Batch +"', '"+ExamType.elementAt(i)+"', '"+A+"', '"+Q+"', '"+C+"', '"+P+"', '"+Marks+"');");  
				     }
				  closeAll();
		       }
		    catch (SQLException e) 
		       {
		    	 closeAll();
		    	 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in entryData() method ", JOptionPane.ERROR_MESSAGE);
			   }
		}
}