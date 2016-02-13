package DB;

public class Calculation 
{
	private static float RealMarks;
	private static String LetterGrade;
	private static String GradePoint;
	
	public static String getLetterGrade(String Marks)
	{
		RealMarks = Float.parseFloat(Marks);
		
		if(RealMarks>=80 && RealMarks <=100)
			LetterGrade = "A+";
		else if(RealMarks>=75 && RealMarks < 80)
			LetterGrade = "A";
		else if(RealMarks>=70 && RealMarks < 75)
			LetterGrade = "A-";
		else if(RealMarks>=65 && RealMarks < 70)
			LetterGrade = "B+";
		else if(RealMarks>=60 && RealMarks < 65)
			LetterGrade = "B";
		else if(RealMarks>=55 && RealMarks < 60)
			LetterGrade = "B-";
		else if(RealMarks>=50 && RealMarks < 55)
			LetterGrade = "C+";
		else if(RealMarks>=45 && RealMarks < 50)
			LetterGrade = "C";
		else if(RealMarks>=40 && RealMarks < 45)
			LetterGrade = "D";
		else 
			LetterGrade = "F";
		
		return LetterGrade;
	}
	
	
	public static String getGradePoint(String Marks)
	{
		RealMarks = Float.parseFloat(Marks);
		
		if(RealMarks>=80 && RealMarks <=100)
			GradePoint = "4.00";
		else if(RealMarks>=75 && RealMarks < 80)
			GradePoint = "3.75";
		else if(RealMarks>=70 && RealMarks < 75)
			GradePoint = "3.50";
		else if(RealMarks>=65 && RealMarks < 70)
			GradePoint = "3.25";
		else if(RealMarks>=60 && RealMarks < 65)
			GradePoint = "3.00";
		else if(RealMarks>=55 && RealMarks < 60)
			GradePoint = "2.75";
		else if(RealMarks>=50 && RealMarks < 55)
			GradePoint = "2.50";
		else if(RealMarks>=45 && RealMarks < 50)
			GradePoint = "2.25";
		else if(RealMarks>=40 && RealMarks < 45)
			GradePoint = "2.00";
		else 
			GradePoint = "0.00";
		
		return GradePoint;
	}
	
	public static float getGradePointFromletterGrade(String LetterGrade)
	{
		float GradePoint ;
		
		if(LetterGrade.equals("A+"))
			GradePoint = (float) 4.00 ;
		else if(LetterGrade.equals("A"))
			GradePoint = (float) 3.75;
		else if(LetterGrade.equals("A-"))
			GradePoint = (float) 3.50;
		else if(LetterGrade.equals("B+"))
			GradePoint = (float) 3.25;
		else if(LetterGrade.equals("B"))
			GradePoint = (float) 3.00;
		else if(LetterGrade.equals("B-"))
			GradePoint = (float) 2.75;
		else if(LetterGrade.equals("C+"))
			GradePoint = (float) 2.50;
		else if(LetterGrade.equals("C"))
			GradePoint = (float) 2.25;
		else if(LetterGrade.equals("D"))
			GradePoint = (float) 2.00;
		else 
			GradePoint = (float) 0.00;	
		return GradePoint;
	}
}
