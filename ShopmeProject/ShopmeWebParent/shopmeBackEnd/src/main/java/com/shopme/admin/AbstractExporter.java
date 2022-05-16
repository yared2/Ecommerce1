package com.shopme.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response,String contentType,
			String extension,String prefix) throws IOException {
		DateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		//String fileName="user_"+timestamp+extension;
		String fileName =prefix +timestamp+extension;


		response.setContentType(contentType);
        String headKey ="Content-Disposition";
        String headValue="attachment; filename="+fileName;
        response.setHeader(headKey, headValue);

}

}
