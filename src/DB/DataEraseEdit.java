package DB;

import MainPackage.*; 

import java.sql.ResultSet;       
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataEraseEdit {
	
	private ConnectionManager TempCM ;
	private Statement EStatement;
	private ResultSet EResultSet;
	private String DataTable[];
	
	public DataEraseEdit(Controll x)
	{
	 TempCM = x.ConnectionManagerObject;  
	 DataTable = new String[8];
	 DataTable[0]="lab_first_year";
	 DataTable[1]="lab_second_year";
	 DataTable[2]="lab_third_year";
	 DataTable[3]="lab_fourth_year";
	 DataTable[4]="theory_first_year";
	 DataTable[5]="theory_second_year";
	 DataTable[6]="theory_third_year";
	 DataTable[7]="theory_fourth_year";
	}
	
//	private boolean isErasableCourse(String Course)
//	{
//		String Table = getDataTable(Course);
//		closeAll();
//		try 
//		  {
//			EStatement = TempCM.MyConnection.createStatement();
//			EResultSet = EStatement.executeQuery("SELECT roll FROM "+Table+" WHERE course_no = '"+Course+"'  ORDER BY exam_year DESC;");
//			if(!EResultSet.next())    // if any data doesn't exist involving the Course to be deleted .
//			  {
//				closeAll();
//				return true;          // then that Course can be deleted.
//			  }
//			else
//			  {
//				closeAll();
//				return false;        // else you can't delete that course. You've to erase the data related to the course first.
//			  }
//		  } 
//		catch (SQLException e) 
//		  {
//			 closeAll(); 
//			 JOptionPane.showMessageDialog(null, "Error in isErasableCourse\n"+e.getErrorCode()+" : "+e.getMessage());
//			 return false;
//		  }
//	}
//	
//	
//	private boolean isErasableBatch(String Batch)
//	{
//		boolean Erasable = true;
//		closeAll();
//		try 
//		  {
//			EStatement = TempCM.MyConnection.createStatement();
//			for(int i =0 ; i<8;i++)
//			   {
//			    EResultSet = EStatement.executeQuery("SELECT roll FROM "+DataTable[i]+" WHERE batch = '"+Batch+"';");
//		        if(EResultSet.next())
//		        	Erasable = false;
//			   }
//			closeAll();
//			return Erasable;
//			
//		  }
//		catch (SQLException e) 
//		  {
//			 closeAll(); 
//			 JOptionPane.showMessageDialog(null, "Error in isErasableBatch\n"+e.getErrorCode()+" : "+e.getMessage());
//			 return false;
//		  }
//	}
	
	public boolean eraseCourse(String Course)
	{
		closeAll();
		int EffectedRows = 0;
		String Table = getDataTable(Course);
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EffectedRows+= EStatement.executeUpdate("DELETE FROM courses WHERE course_no = '"+Course+"';");
			EffectedRows+= EStatement.executeUpdate("DELETE FROM "+Table+" WHERE course_no = '"+Course+"';"); // not needed if we use isErasableCourse() method
			closeAll();
		    if(EffectedRows>0)
		       return true;
		    else 
		       return false;	
		  } 
		catch (SQLException e) 
		  {
			closeAll();
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in eraseCourse() method ", JOptionPane.ERROR_MESSAGE);
			return false;
		  }
	}
	
	public boolean eraseBatch(String Batch)
	{
		closeAll();
		int EffectedRow = 0;
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();    
		    EffectedRow += EStatement.executeUpdate("DELETE FROM batch WHERE batch_no = '"+Batch+"';");
		    EffectedRow += EStatement.executeUpdate("DELETE FROM students_information WHERE batch = '"+Batch+"';");
            
		    for(int i=0;i<8;i++)
		    	EffectedRow += EStatement.executeUpdate("DELETE FROM "+DataTable[i]+" WHERE batch = '"+Batch+"';"); // // not needed if we use isErasableCourse() method
		   
		    if(EffectedRow>0)
		      return true;
		    else 
		      return false;	
		  } 
		catch (SQLException e) 
		  {
			closeAll();
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in eraseBatch() method ", JOptionPane.ERROR_MESSAGE);
	    	return false;
		  }
	}
	
	public boolean eraseExamination(String Course, String ExamYear)
	{
		String Table = getDataTable(Course);
		int NumberOfEffectedRows;
		closeAll();
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			NumberOfEffectedRows = EStatement.executeUpdate("DELETE FROM "+Table+" WHERE course_no = '"+Course+"' AND exam_year= '"+ExamYear+"';");
			closeAll();
			if(NumberOfEffectedRows>0)
				return true;
			else return false;
		  }
		catch (SQLException e) 
		  {
			 closeAll(); 
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in eraseExamination() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		  }
	}
	
	public boolean hasExistingExaminationData(String Course, String ExamYear, boolean IsTheoryCourse)
	{
		String Table = getDataTable(Course);
		String SQL;
		
		if(IsTheoryCourse)
			SQL = "SELECT roll, m_section_a, m_section_b, m_performance, exam_type FROM "+Table+" WHERE course_no = '"+Course+"' AND exam_year = '"+ExamYear+"' ORDER BY batch DESC, exam_type DESC ,roll ASC;";
		else
			SQL = "SELECT roll, performance_mark, quiz_mark, attendance_mark, cv_mark, exam_type FROM "+Table+" WHERE course_no = '"+Course+"' AND exam_year = '"+ExamYear+"' ORDER BY batch DESC, exam_type DESC , roll ASC;";
		closeAll();
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
		    EResultSet = EStatement.executeQuery(SQL);
		    if(!EResultSet.next())
		      {
		    	closeAll();
		    	return false;
		      }
		    else
		      {
		    	DataTransfer.refreshAll();		    	
		    	if(EResultSet.getMetaData().getColumnCount()==5)
		    	  { 
		    		EResultSet.previous();
		    		while(EResultSet.next())
		    	        {
		    		      DataTransfer.Roll.add(EResultSet.getObject(1).toString());
		    		      DataTransfer.SectionA.add(EResultSet.getObject(2).toString());
		    		      DataTransfer.SectionB.add(EResultSet.getObject(3).toString());
		    		      DataTransfer.Performance.add(EResultSet.getObject(4).toString());
		    		      DataTransfer.ExamType.add(EResultSet.getObject(5).toString());
		    	        }
		    	  }
		    	else
		    	  {
		    		EResultSet.previous();
		    		while(EResultSet.next())
		    	        {
		    		      DataTransfer.Roll.add(EResultSet.getObject(1).toString());
		    		      DataTransfer.Performance.add(EResultSet.getObject(2).toString());
		    		      DataTransfer.Quiz.add(EResultSet.getObject(3).toString());
		    		      DataTransfer.Attendance.add(EResultSet.getObject(4).toString());
		    		      DataTransfer.CentralViva.add(EResultSet.getObject(5).toString());
		    		      DataTransfer.ExamType.add(EResultSet.getObject(6).toString());
		    	        }
		    	  }
		      }
		    closeAll();
		    return true;
		  } 
		catch (SQLException e) 
		  {
			closeAll();
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in hasExistingExaminationData() method ", JOptionPane.ERROR_MESSAGE);
		    return false;
		  }
	}
	
	public int editExaminationData(String Course, String ExamYear, boolean IsTheoryCourse)  // for editing data
	{
		String Table = getDataTable(Course);
		closeAll();
		int x=0,Size;
		String SQL;
		float Total;
		if(IsTheoryCourse)
		  {
		    try 
		      {
			   EStatement = TempCM.MyConnection.createStatement();
			   Size= DataTransfer.Roll.size();
			   x=0;
			   for(int i=0;i<Size;i++)
			      {
			 	   Total =  (Float.parseFloat(DataTransfer.SectionA.elementAt(i))+Float.parseFloat(DataTransfer.SectionB.elementAt(i))+Float.parseFloat(DataTransfer.Performance.elementAt(i)))/3;    // (total/300)*100 getting marks in % 
				   SQL = "UPDATE "+Table+" SET m_performance = '"+DataTransfer.Performance.elementAt(i)+"', m_section_a = '"+DataTransfer.SectionA.elementAt(i)+"', m_section_b = '"+DataTransfer.SectionB.elementAt(i)+"', total = '"+Float.toString(Total)+"' WHERE roll = '"+DataTransfer.Roll.elementAt(i)+"' AND exam_year ='"+ExamYear+"' AND course_no = '"+Course+"';";
                   x += EStatement.executeUpdate(SQL); 
			      }
			   
			   closeAll();
			   DataTransfer.refreshAll();
			   return x;
		      } 
		    catch (SQLException e) 
		      {
			   closeAll();
			   DataTransfer.refreshAll();
			   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in editExaminationdata() method ", JOptionPane.ERROR_MESSAGE);
		       return -1;
		      }
		  }
		else
		  {
			try 
		      {
			   EStatement = TempCM.MyConnection.createStatement();
			   Size = DataTransfer.Roll.size();
			   
			   for(int i=0;i<Size;i++)
			      {
			 	    Total =  Float.parseFloat(DataTransfer.Performance.elementAt(i))+Float.parseFloat(DataTransfer.Quiz.elementAt(i))+Float.parseFloat(DataTransfer.Attendance.elementAt(i))+Float.parseFloat(DataTransfer.CentralViva.elementAt(i));    // total is 100 so this will be in % 
				    SQL = "UPDATE "+Table+" SET performance_mark = '"+DataTransfer.Performance.elementAt(i)+"', quiz_mark = '"+DataTransfer.Quiz.elementAt(i)+"', attendance_mark = '"+DataTransfer.Attendance.elementAt(i)+"', cv_mark = '"+ DataTransfer.CentralViva.elementAt(i) +"', total = '"+Float.toString(Total)+"' WHERE roll = '"+DataTransfer.Roll.elementAt(i)+"' AND exam_year ='"+ExamYear+"' AND course_no = '"+Course+"';";
                    x += EStatement.executeUpdate(SQL); 
			      }
			   closeAll();
			   DataTransfer.refreshAll();
			   return x;
		      } 
		    catch (SQLException e) 
		      {
			   closeAll();
			   DataTransfer.refreshAll();
			   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in editExaminationdata() method ", JOptionPane.ERROR_MESSAGE);
		       return -1;
		      }
		  }	
	}
	
	public boolean hasStudentsDataOfBatch(String Batch)
	{
		boolean FLAG = true;
		closeAll();
		DataTransfer.refreshAll();
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery("SELECT * FROM students_information WHERE batch = '"+Batch+"' ORDER BY roll ASC;");
			if(EResultSet.next())
			  {
				EResultSet.previous();
				while(EResultSet.next())
				     {
					  DataTransfer.Roll.add(EResultSet.getObject(1).toString());
					  DataTransfer.Name.add(EResultSet.getObject(2).toString());
					  DataTransfer.Contact.add(EResultSet.getObject(3).toString());
				     }
			  }
			else
			  FLAG = false;
		  } 
		 catch (SQLException e) 
		  {
			 FLAG = false;
			 DataTransfer.refreshAll();
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in hasStudentDataOfBatch() method ", JOptionPane.ERROR_MESSAGE);
		       
		  }
		closeAll();
		return FLAG;
	}
	
	public int editBatchInformation(String Batch)
	{
		int EffectedRows = 0;
		int Size = DataTransfer.Roll.size();
		closeAll();
		try 
		 {
		     EStatement = TempCM.MyConnection.createStatement();
		     for(int i = 0; i<Size; i++)
		         EffectedRows += EStatement.executeUpdate("UPDATE students_information SET name = '"+DataTransfer.Name.elementAt(i)+"',contact = '"+DataTransfer.Contact.elementAt(i)+"' WHERE roll = '"+DataTransfer.Roll.elementAt(i)+"';");		    
		 } 
		catch (SQLException e) 
		 {
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in editBatchInformation() method ", JOptionPane.ERROR_MESSAGE);
		       
			EffectedRows = -1;
		 }
		closeAll();
		DataTransfer.refreshAll();
		return EffectedRows;
	}
	
	public boolean getCourseNameAndCourseCredit(String CourseNo)
	{
		DataTransfer.CourseName = "";
		DataTransfer.CourseCredit = "";
		try 
		   {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery("Select * FROM courses WHERE course_no = '"+CourseNo+"' ;");
		    if(EResultSet.next())
		      {
		    	DataTransfer.CourseName = (String) EResultSet.getObject(2);
		        DataTransfer.CourseCredit = EResultSet.getObject(3).toString();
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
		    JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getCourseNameAndCourseCredit() method ", JOptionPane.ERROR_MESSAGE);
		    return false;
		   }
	}
	
	public boolean editCourse(String CourseNo,String CourseName,String CourseCredit)
	{
		int EffectedRow = 0;
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
		    EffectedRow = EStatement.executeUpdate("UPDATE courses SET course_name = '"+CourseName+"', course_credit = '"+CourseCredit+"' WHERE course_no = '"+CourseNo+"';");
		    closeAll();
		    if(EffectedRow==1)
		    	return true;
		    else 
		    	return false;
		  } 
		catch (SQLException e) 
		  {
			closeAll();
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in editCourse() method ", JOptionPane.ERROR_MESSAGE);
		    return false;
		  }
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
		  Table =DataTable[Year-1]; // for lab_******_year, see the constructor
		else
		  Table =DataTable[Year+3]; // for theory_******_year, see the constructor
		
		return Table;
	}
	
	private void  closeAll()
	{
		if(EResultSet!=null)
		   EResultSet = null;
		if(EStatement!=null)
		   EStatement = null;
	}
    
}