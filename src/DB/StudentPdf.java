package DB;
import java.io.FileOutputStream;        
import javax.swing.JOptionPane;
import MainPackage.Controll;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class StudentPdf
{
	private Controll TempControll ;
	private Font CellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.NORMAL);
    private Font BoldCellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD);
    private String FILE, RollNo,Session,Taken,Completed,GPA;
    private int NumberOfCourses,PageToBeCreated,Counter,CurrentPage;
    
    public StudentPdf(Controll X)
    {
    	TempControll = X;
    }
    
    private void addMetaData(Document Doc) 
	{
		Doc.addTitle("Roll_"+this.RollNo+"_Session_"+this.Session);
		Doc.addSubject("Using iText");
		Doc.addKeywords("Java, PDF, iText");
		Doc.addAuthor("Term Result Calculator");
		Doc.addCreator("Term Result Calculator");
	}
	
	private void addContent(Document Doc) throws DocumentException 
	{
		Paragraph Heading;
    
		Heading = new Paragraph("page "+this.CurrentPage+" of "+this.PageToBeCreated, new Font(Font.FontFamily.TIMES_ROMAN, 6,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_RIGHT);
		Doc.add(Heading);
		
		Heading = new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_RIGHT);
		Doc.add(Heading);
		
		Heading = new Paragraph("Khulna University of Engineering and Technology", new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
     
		Heading = new Paragraph("Department of Computer Science and Engineering",  new Font(Font.FontFamily.TIMES_ROMAN, 13,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph("Student Wise Tabulation Sheet",new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph("Roll : "+this.RollNo,new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
		
		Heading = new Paragraph("Session : "+this.Session,new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
		
		for(int i=0;i<2;i++)
			Doc.add(new Paragraph(" "));
		
		Doc.add(createTable());
		
		for(int i=0;i<2;i++)
			Doc.add(new Paragraph(" "));
		
		
		String[] ForcedTableElement = { this.Taken,this.Completed,this.GPA};
		PdfPTable ForcedTable = new PdfPTable(3);
		ForcedTable.setWidthPercentage(100);
		ForcedTable.setWidths(new float[] {34f,33f,33f});
		
		
		for(int i=0;i<3;i++)
		   {
			PdfPCell ForcedCell;
			ForcedCell = new PdfPCell(new Phrase(ForcedTableElement[i],BoldCellFont));		
			if(i==0)
			  ForcedCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			else if(i==1)
				  ForcedCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			else
				ForcedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			ForcedCell.setFixedHeight(14f);
			ForcedCell.setBorder(Rectangle.NO_BORDER);
			ForcedTable.addCell(ForcedCell);
		   }
		Doc.add(ForcedTable);
		
		this.CurrentPage++;
		Doc.newPage();
	}

	private PdfPTable createTable()throws DocumentException 
	{
		String [] ColumnName = {"SL NO","Course No","Letter Grade","Course Credit","ExamType"}; 
		PdfPTable table = new PdfPTable(5);
   
		table.setWidthPercentage(100);
	    table.setWidths(new float[] {16f,21f,21f,21f,21f});
   
		PdfPCell c1;
		for(int i=0;i<5;i++)
		   {
			c1 = new PdfPCell(new Phrase(ColumnName[i],new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setFixedHeight(30f);
			table.addCell(c1);
		   }
		table.setHeaderRows(1);
		
		int Capability = this.Counter+30;
		for(int i=this.Counter;i<Capability && this.Counter<this.NumberOfCourses;i++,this.Counter++)
			{
				PdfPCell X;
				X = new PdfPCell(new Phrase(DataTransfer.Serial.elementAt(i),CellFont));
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.Courses.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.LetterGrade.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.Credits.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.ExamType.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
			}
		return table;
	}

	public boolean createPDF(String RollNo,String Session,int NumberOfCourses,String Taken, String Completed,String GPA)
	{
		boolean flag = true;	
		this.RollNo = RollNo;
		this.Session = Session;
		this.Taken = Taken;
		this.Completed = Completed;
		this.GPA = GPA;
		this.NumberOfCourses = NumberOfCourses;
		
		
		this.PageToBeCreated = this.NumberOfCourses/30;
		if(this.NumberOfCourses%30>0)
			this.PageToBeCreated++;
		this.Counter = 0;
		this.CurrentPage = 1;
		
		
		FILE = System.getProperty("user.home")+"/TermResultCalculator/StudentDocs/"+"Roll_"+this.RollNo+"_Session_"+this.Session+".pdf";                     //FirstPdf.pdf";
		
		Document Doc = new Document(com.itextpdf.text.PageSize.LEGAL);
		try 
	     {
			PdfWriter.getInstance(Doc, new FileOutputStream(FILE));
			Doc.open();
			addMetaData(Doc);
			
			for(int i=0; i<this.PageToBeCreated; i++)
				addContent(Doc);
			Doc.close();
	     }
		catch (Exception e) 
		 {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    	flag = false;
	     }
		return flag;
	}
} 