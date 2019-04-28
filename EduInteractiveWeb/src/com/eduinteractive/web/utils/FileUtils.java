package com.eduinteractive.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eduinteractive.web.config.ConfigurationManager;

public class FileUtils {

	private static Logger logger = LogManager.getLogger(FileUtils.class);
	
	private static final String UPLOAD_DIRECTORY = ConfigurationManager.getInstance().getParameter("upload.directory");
	
	public static void readDocument(HttpServletResponse response, String email) {
		if(ValidationUtils.emailValidator(email) != null) {
			String urlBase = UPLOAD_DIRECTORY.concat(ParameterUtils.getFileName(email));
			File file = new File(urlBase);
			
			try {
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				
				while (fis.read(buffer) > 0) {
					response.getOutputStream().write(buffer);
					response.flushBuffer();
				}
				fis.close();
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
	}
	
	public static void loadDocument (String email, FileItem fileItem) {
		// constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = "C:"
                + File.separator + UPLOAD_DIRECTORY;
         
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
		
		String fileName = new File(ParameterUtils.getFileName(email)).getName();
        String filePath = uploadPath + File.separator + fileName;
        File storeFile = new File(filePath);

        try {
			fileItem.write(storeFile);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
        logger.debug("Upload has been done successfully!");
	}
	
}
