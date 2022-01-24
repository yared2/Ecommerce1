package com.shopme.admin.user.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;

public class UserPdfExporter extends AbstractExporter{
	
	
	public UserPdfExporter(){
		
	}
	   
	    public void export(List<User> listUsers ,HttpServletResponse response) throws DocumentException, IOException {
	    	super.setResponseHeader(response, "application/pdf",".pdf" );
	    	
	    	Document document = new Document(PageSize.A4);
	    	 PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph paragraph = new Paragraph("List of Users", font);
	        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(paragraph);
	         
	    
	        PdfPTable table = new PdfPTable(6) ; //6column
	       table.setWidthPercentage(100f);
	       table.setSpacingBefore(10); // this set spacing before the table is created 
	       table.setWidths(new float[] {1.2f,3.5f,3.0f,3.0f,3.0f,1.6f});
	
	       writeTableHeader(table);  //write the celles in this method and add to the document  everything is added to the doc
	       writeTableData(table,listUsers); // after writing table header we write table data 
	       document.add(table);
	 
	        document.close();
	         
	    }

		private void writeTableData(PdfPTable table, List<User> listUsers) {
			for(User user : listUsers) {
				table.addCell(String.valueOf(user.getId()));
				table.addCell(user.getEmail());
				table.addCell(user.getFirstName());
				table.addCell(user.getLastName());
				table.addCell(user.getRoles().toString());
				table.addCell(String.valueOf(user.isEnabled()));
			}
			
		}

		private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.blue);
		cell.setPadding(5);
		
		  Font font = FontFactory.getFont(FontFactory.HELVETICA);
	      font.setColor(Color.WHITE);
	      
	      cell.setPhrase(new Phrase("User ID",font));
	      table.addCell(cell);                           //add first cell to the table
	      cell.setPhrase(new Phrase("E-mail",font));
	      table.addCell(cell);
	      cell.setPhrase(new Phrase("First Name",font));
	      table.addCell(cell);
	      cell.setPhrase(new Phrase("Last Name",font));
	      table.addCell(cell);
	      cell.setPhrase(new Phrase("Roles",font));
	      table.addCell(cell);
	      cell.setPhrase(new Phrase("Enabled ",font));
	      table.addCell(cell);
	
		}
	

}
