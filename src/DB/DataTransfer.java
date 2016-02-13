package DB;
import java.util.Vector; 

public class DataTransfer 
{
	public static Vector<String> Serial = new Vector<String>();
	public static Vector<String> Roll = new Vector<String>();
	public static Vector<String> SectionA = new Vector<String>();
	public static Vector<String> SectionB = new Vector<String>();
	public static Vector<String> Performance = new Vector<String>();
	public static Vector<String> Attendance = new Vector<String>();
	public static Vector<String> Quiz = new Vector<String>();
	public static Vector<String> CentralViva = new Vector<String>();
	public static Vector<String> ExamType = new Vector<String>();
	public static Vector<String> Name = new Vector<String>();
	public static Vector<String> Contact = new Vector<String>();
	public static Vector<String> ExamYears = new Vector<String>(); 
	public static Vector<String> Courses = new Vector<String>(); 
	public static Vector<String> Batches = new Vector<String>(); 
	public static Vector<String> Total = new Vector<String>();
	public static Vector<String> LetterGrade = new Vector<String>();
	public static Vector<String> GradePoint = new Vector<String>();
	public static Vector<String> Credits = new Vector<String>();
	
	public static String CourseNo = new String();
	public static String CourseName = new String();
	public static String CourseCredit= new String();
	
	
	public static void refreshAll()
	{
		Serial.clear();
		Roll.clear();
		Performance.clear();
		SectionA.clear();
		SectionB.clear();
		Attendance.clear();
		CentralViva.clear();
		Quiz.clear();
		ExamType.clear();
		Name.clear();
		Contact.clear();
		ExamYears.clear();
		Courses.clear();
		Batches.clear();
		Total.clear();
		LetterGrade.clear();
		GradePoint.clear();
		CourseNo = "";
		CourseName = "";
		CourseCredit = "";
	    Credits.clear();
	}

}