package DB;

import java.io.FileOutputStream;              
import java.util.Vector;
import javax.swing.JOptionPane;
import MainPackage.Controll;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class TermPdf
{
	private Controll TempControll ;
    private String FILE, ExamYear,Session,Term;
    private int NumberOfStudents,PageToBeCreated,Counter,CurrentPage;
   
    private String [][] Data;
    private Vector<String>Theory = new Vector<String>();
    private Vector<String>Sessional = new Vector<String>();
    private Vector<String>ColumnName = new Vector<String>();
    
    public TermPdf(Controll X)
    {
    	TempControll = X;
    }
    
    private void addMetaData(Document Doc) 
	{
		Doc.addTitle("Course_"+this.Term+"_Examination_"+this.ExamYear);
		Doc.addSubject("Using iText");
		Doc.addKeywords("Java, PDF, iText");
		Doc.addAuthor("Term Result Calculator");
		Doc.addCreator("Term Result Calculator");
	}
	
	private void addContent(Document Doc) throws DocumentException 
	{
		
		Paragraph Heading ;
		Heading = new Paragraph("Page "+this.CurrentPage+" of " +this.PageToBeCreated, new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_RIGHT);
		Doc.add(Heading);
     
		Heading = new Paragraph("Khulna University of Engineering & Technology",  new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph("Tabulation Sheet of "+this.Term+", Examination "+this.ExamYear,new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
    
		Heading = new Paragraph("Department of Computer Science and Engineering",new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
		
		Heading = new Paragraph("Current Term ("+this.Session+")",new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.NORMAL));
		Heading.setAlignment(Element.ALIGN_CENTER);
		Doc.add(Heading);
		
		for(int i=0;i<2;i++)
			Doc.add(new Paragraph(" "));
		Doc.add(createTable());
		for(int i=0;i<2;i++)
			Doc.add(new Paragraph(" "));
		
		String [] EndingSpeech = {" ", "Tebulators' Signature (with Date)"," ","1.","___________________________________________","2.","Chairman, Examination Comittee"};
		int [] EndingAlignment = {Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT,Element.ALIGN_LEFT,Element.ALIGN_RIGHT};
				
		PdfPTable EndingTable = new PdfPTable(2);
		EndingTable.setWidthPercentage(100);
		EndingTable.setWidths(new float[] {50f,50f});
		
		
		PdfPCell EndingCell;
		
		for(int i=1;i<=6;i++)
		   {
			EndingCell = new PdfPCell(new Phrase(EndingSpeech[i],new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD)));	
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
		PdfPTable Table = new PdfPTable(18);
		Table.setWidthPercentage(100f);
        Table.setWidths(new float[]{5f,10f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,4.16f,5f, 7f,6f,20f});
        
        
        PdfPCell Cell;
        
        for(int i=0;i<7;i++)
           {
        	 Cell = new PdfPCell(new Phrase(this.ColumnName.elementAt(i),new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.BOLD)));
        	 Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
             
             if(i==0||i==3||i==4||i==5)
               Cell.setRotation(90);
             
             if(i==2)
                Cell.setColspan(12);	
             else
               Cell.setRowspan(2);
             
             Table.addCell(Cell);
           }
     
        for(int i=1;i<=12;i++)
           {
       	    Cell = new PdfPCell(new Phrase(this.ColumnName.elementAt(i+6),new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.BOLD)));
       	    Cell.setFixedHeight(60f);
       	    Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            Cell.setRotation(90);
            Table.addCell(Cell);
            }

        Table.setHeaderRows(2);
        int Capability = this.Counter+30;
        
        for(int i=this.Counter;i<Capability && this.Counter<this.NumberOfStudents;i++,this.Counter++)
        	for(int j=0;j<18;j++)
        	   {
        		 Cell = new PdfPCell(new Phrase(Data[i][j],new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.NORMAL)));
                 Cell.setFixedHeight(22f);
        		 Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                 Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 Table.addCell(Cell);
        	   }
		return Table;
	}

	public boolean createPDF(String Term, String ExamYear, int NumberOfStudents,Vector<String>Theory,Vector<String>Sessional,String [][]Data)
	{
		boolean Flag = true;
		int X,Y;
		this.ExamYear = ExamYear;
		this.Term = Term;
		this.Data = Data;
		this.NumberOfStudents = NumberOfStudents;
		
		X = Integer.parseInt(this.ExamYear);
		Y = X-1;
		this.Session = Integer.toString(Y)+"-"+Integer.toString(X);
		
		this.PageToBeCreated = this.NumberOfStudents/30;
		if(this.NumberOfStudents%30>0)
			this.PageToBeCreated++;
		this.Counter = 0;
		this.CurrentPage = 1;
		
		this.Theory.clear();
		this.Sessional.clear();
		this.Theory.addAll(Theory);
		this.Sessional.addAll(Sessional);
		
		setUpColumnName();
		
		FILE = System.getProperty("user.home")+"/TermResultCalculator/TermDocs/"+this.Term+"_"+this.ExamYear+".pdf";                     //FirstPdf.pdf";
		
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
	    	Flag = false;
	     }
		return Flag;
	}
	
	private void setUpColumnName()
	{
		this.ColumnName.clear();
		this.ColumnName.add("SL No.");
		this.ColumnName.add("Roll");
		this.ColumnName.add("Courses");
		this.ColumnName.add("Earned Credit");
		this.ColumnName.add("Weighted GP");
		this.ColumnName.add("GPA");
		this.ColumnName.add("Remarks");
		this.ColumnName.addAll(this.Theory);
		for(int i=this.Theory.size()+1;i<=6;i++)
		    this.ColumnName.add(" ");
		this.ColumnName.addAll(this.Sessional);
		for(int i=this.Sessional.size()+1;i<=6;i++)
		    this.ColumnName.add(" ");
	}
} 