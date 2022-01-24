package com.shopme.admin.user.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.shopme.common.entity.User;

public class AbstractExporter {
	
	public void setResponseHeader(HttpServletResponse response,String contentType,String extension) throws IOException {
		DateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName="user_"+timestamp+extension;
	
		
		response.setContentType(contentType);
        String headKey ="Content-Disposition";
        String headValue="attachment; filename="+fileName;
        response.setHeader(headKey, headValue);

}

}
