package DB;
import java.io.FileOutputStream;        
import javax.swing.JOptionPane;
import MainPackage.Controll;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CoursePdfLab
{
	private Controll TempControll ;
	private Font CellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.NORMAL);
    private Font BoldCellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD);
    private String FILE, CourseNo,CourseTitle,ExamYear,Session,Term,CourseCredit;
    private int NumberOfStudents,PageToBeCreated,Counter,CurrentPage;
    private String[] TH ={"th","st","nd","rd","th"}; 
    
	
    
    public CoursePdfLab(Controll X)
    {
    	TempControll = X;
    }
    
    private void addMetaData(Document Doc) 
	{
		Doc.addTitle("Course_"+this.CourseNo+"_Examination_"+this.ExamYear);
		Doc.addSubject("Using iText");
		Doc.addKeywords("Java, PDF, iText");
		Doc.addAuthor("Term Result Calculator");
		Doc.addCreator("Term Result Calculator");
	}
	
	private void addContent(Document Doc) throws DocumentException 
	{
		Paragraph Heading;
    
		Heading = new Paragraph("Khulna University of Engineering and Technology", new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
     
		Heading = new Paragraph("Department of Computer Science and Engineering",  new Font(Font.FontFamily.TIMES_ROMAN, 13,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph("Course Wise Tabulation Sheet (Lab)",new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph(this.Term,new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.NORMAL));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
		
		for(int i=0;i<3;i++)
			Doc.add(new Paragraph(" "));
		
		PdfPTable ForcedTable = new PdfPTable(2);
		ForcedTable.setWidthPercentage(100);
		float [] ForcedWidth = {50,50};
		ForcedTable.setWidths(ForcedWidth);
		
		PdfPCell ForcedCell;
		
		ForcedCell = new PdfPCell(new Phrase("Course No : "+this.CourseNo,BoldCellFont));		ForcedCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		ForcedCell.setFixedHeight(15f);
		ForcedCell.setBorder(Rectangle.NO_BORDER);
		ForcedTable.addCell(ForcedCell);
		
		ForcedCell = new PdfPCell(new Phrase("Credit Hours : "+this.CourseCredit,BoldCellFont));
		ForcedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		ForcedCell.setFixedHeight(15f);
		ForcedCell.setBorder(Rectangle.NO_BORDER);
		ForcedTable.addCell(ForcedCell);
		
		
		ForcedCell = new PdfPCell(new Phrase("Course Title : "+this.CourseTitle,BoldCellFont));
		ForcedCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		ForcedCell.setFixedHeight(15f);
		ForcedCell.setBorder(Rectangle.NO_BORDER);
		ForcedTable.addCell(ForcedCell);
		
		ForcedCell = new PdfPCell(new Phrase("Academic Session : "+this.Session,BoldCellFont));
		ForcedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		ForcedCell.setFixedHeight(15f);
		ForcedCell.setBorder(Rectangle.NO_BORDER);
		ForcedTable.addCell(ForcedCell);
		Doc.add(ForcedTable);
		Doc.add(new Paragraph(" ",new Font(Font.FontFamily.TIMES_ROMAN, 2,Font.BOLD)));
		
		Doc.add(createTable());
		
		for(int i=0;i<4;i++)
			Doc.add(new Paragraph(" "));
		
		String [] EndingSpeech = {":)","Verified by : ","  ","1. ________________________________________", "________________________________________","2. ________________________________________","Chairman of Examination Committee"," ","page "+Integer.toString(this.CurrentPage)+" of "+Integer.toString(this.PageToBeCreated)};
		Font [] EndingFont = {BoldCellFont,BoldCellFont,BoldCellFont,BoldCellFont,BoldCellFont,BoldCellFont,BoldCellFont,BoldCellFont,new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.NORMAL)};
		int [] EndingAlignment = {Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT};
				
		PdfPTable EndingTable = new PdfPTable(2);
		EndingTable.setWidthPercentage(100);
		EndingTable.setWidths(ForcedWidth);
		
		PdfPCell EndingCell;
		
		for(int i=1;i<=8;i++)
		   {
			EndingCell = new PdfPCell(new Phrase(EndingSpeech[i],EndingFont[i]));	
			EndingCell.setHorizontalAlignment(EndingAlignment[i]);
			EndingCell.setFixedHeight(15f);
			EndingCell.setBorder(Rectangle.NO_BORDER);
			EndingTable.addCell(EndingCell);
		   }
		Doc.add(EndingTable);
		this.CurrentPage++;
		Doc.newPage();
	}

	private PdfPTable createTable()throws DocumentException 
	{
		String [] ColumnName = {"SL.\nNO","Roll No","  Name","Total Marks\n(100)","Letter Grade","Grade Point","Remarks"}; 
		PdfPTable table = new PdfPTable(7);
   
		table.setWidthPercentage(100);
	
		float[] Width = {5f,10f,35f,13.8f,11.6f,10.7f,13.4f};
		
	    table.setWidths(Width);
   
		PdfPCell c1;
		for(int i=0;i<7;i++)
		   {
			c1 = new PdfPCell(new Phrase(ColumnName[i],new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD)));
			if(i!=2)
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(35f);
			table.addCell(c1);
		   }
		table.setHeaderRows(1);
		
		int Capability = this.Counter+30;
		
		for(int i=this.Counter;i<Capability && this.Counter<this.NumberOfStudents;i++,this.Counter++)
			{
				PdfPCell X;
				X = new PdfPCell(new Phrase(DataTransfer.Serial.elementAt(i),CellFont));
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.Roll.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase("  "+DataTransfer.Name.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_LEFT);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.Total.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.LetterGrade.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase(DataTransfer.GradePoint.elementAt(i),CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
    
				X = new PdfPCell(new Phrase("     ",CellFont));
				X.setHorizontalAlignment(Element.ALIGN_CENTER);
				X.setVerticalAlignment(Element.ALIGN_CENTER);
				X.setFixedHeight(20f);
				table.addCell(X);
			}
		return table;
	}

	public boolean createPDF(String CourseNo,String ExamYear,int NumberOfStudents)
	{
		
		boolean flag = true;
		int X,Y, XX,YY;
		this.CourseNo = CourseNo;
		this.CourseTitle = DataTransfer.CourseName;
		this.CourseCredit = DataTransfer.CourseCredit;
		this.NumberOfStudents = NumberOfStudents;
		this.ExamYear = ExamYear;
		
		X = Integer.parseInt(this.ExamYear);
		Y = X-1;
		this.Session = Integer.toString(Y)+"-"+Integer.toString(X);
		
		
		XX = this.CourseNo.charAt(this.CourseNo.length()-4)-'0' ;
		YY = this.CourseNo.charAt(this.CourseNo.length()-3)-'0' ;//CSE-1107
		this.Term = XX+TH[XX]+" Year "+YY+TH[YY]+" Term Examination "+ this.ExamYear;
		
		this.PageToBeCreated = this.NumberOfStudents/30;
		if(this.NumberOfStudents%30>0)
			this.PageToBeCreated++;
		this.Counter = 0;
		this.CurrentPage = 1;
		
		
		FILE = System.getProperty("user.home")+"/TermResultCalculator/CourseDocs/"+"Course_"+this.CourseNo+"_Examination_"+this.ExamYear+".pdf";                     //FirstPdf.pdf";
		
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