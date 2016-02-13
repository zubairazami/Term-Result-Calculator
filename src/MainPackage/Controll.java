package MainPackage;      

import DB.*;
import GUIPackage.*;

public class Controll {
	 //It's the controller class, internal interaction of all other class will go through this  
	 public  DBMSPanel DBMSPanelObject;
	 public  OpeningFrame OpeningFrameObject;
     public  Menu MenuObject;
     public  UserManagement UserManagementObject;
     public  SignInCore SignInCoreObject ;
     public  ConnectionManager ConnectionManagerObject;
     public  Enrollment EnrollmentObject;
     public  DataEntry  DataEntryObject;
     public  DataEntryPanelTheory DataEntryPanelTheoryObject;
     public  DataEntryPanelLab DataEntryPanelLabObject;
     public  DataHandlePanel DataHandlePanelObject;
     public  EnrollmentPanel EnrollmentPanelObject;
     public  BatchEnrollmentDialog BatchEnrollmentDialogObject;
     public  MultipleCourseEnrollmentDialog MultipleCourseEnrollmentDialogObject; 
     public  DataEraseEdit DataEraseEditObject;
	 public  DataEditPanelTheory DataEditPanelTheoryObject;
	 public  DataEditPanelLab DataEditPanelLabObject;
     public  DataEditPanelCourse DataEditPanelCourseObject;
     public  DataEditPanelBatch DataEditPanelBatchObject;
     public  ResultHandlePanel ResultHandlePanelObject;
     public  Result ResultObject;
     public  ResultPanelStudent ResultPanelStudentObject;
     public  ResultPanelCourseLab ResultPanelCourseLabObject;
     public  ResultPanelCourseTheory ResultPanelCourseTheoryObject;
     public  ResultPanelTerm ResultPanelTermObject;
     public  Calculation CalculationObject;
     public CoursePdfTheory CoursePdfTheoryObject;
     public CoursePdfLab CoursePdfLabObject;
     public TermPdf TermPdfObject;
     public StudentPdf StudentPdfObject;
     
     public static void main(String[] args) 
     {
       Controll Controller = new Controll();   
       
       Controller.ConnectionManagerObject = new ConnectionManager(Controller);
       		  Controller.SignInCoreObject = new SignInCore(Controller);
       		  Controller.EnrollmentObject = new Enrollment(Controller);
       		  Controller.DataEntryObject = new DataEntry(Controller);
       		  Controller.DataEraseEditObject = new DataEraseEdit(Controller);
       		  Controller.ResultObject = new Result(Controller);
       		  Controller. CoursePdfTheoryObject = new CoursePdfTheory(Controller);
              Controller.CoursePdfLabObject = new CoursePdfLab(Controller);
              Controller.TermPdfObject = new TermPdf(Controller);
              Controller.StudentPdfObject = new StudentPdf(Controller);
       		  
       Controller.OpeningFrameObject = new OpeningFrame(Controller);   
       		  Controller.DBMSPanelObject = new DBMSPanel(Controller); 
       		  Controller.UserManagementObject = new UserManagement(Controller,Controller.OpeningFrameObject);
       		  Controller.MenuObject = new Menu(Controller);    
                  Controller.EnrollmentPanelObject = new EnrollmentPanel(Controller);
                     Controller.BatchEnrollmentDialogObject = new BatchEnrollmentDialog(Controller);
                     Controller.MultipleCourseEnrollmentDialogObject = new MultipleCourseEnrollmentDialog(Controller);
                  Controller.DataHandlePanelObject = new DataHandlePanel(Controller) ;
                     Controller.DataEntryPanelTheoryObject = new DataEntryPanelTheory(Controller);
                     Controller.DataEntryPanelLabObject = new DataEntryPanelLab(Controller);
                     Controller.DataEditPanelTheoryObject = new DataEditPanelTheory(Controller);
                     Controller.DataEditPanelLabObject = new DataEditPanelLab(Controller);
                     Controller.DataEditPanelCourseObject = new DataEditPanelCourse(Controller);
                     Controller.DataEditPanelBatchObject = new DataEditPanelBatch(Controller);
                  Controller.ResultHandlePanelObject = new ResultHandlePanel(Controller);       
                     Controller.ResultPanelStudentObject = new ResultPanelStudent(Controller); 
                     Controller.ResultPanelCourseTheoryObject = new ResultPanelCourseTheory(Controller); 
                     Controller.ResultPanelCourseLabObject = new ResultPanelCourseLab(Controller); 
                     Controller.ResultPanelTermObject = new ResultPanelTerm(Controller); 
	          
	          Controller.ConnectionManagerObject.createConnection()   ;
	          Controller.OpeningFrameObject.createOpeningFrame() ;
	        
	}
}