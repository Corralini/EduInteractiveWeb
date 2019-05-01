package com.eduinteractive.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eduinteractive.web.config.ConfigurationManager;
import com.eduinteractive.web.controller.ConstantsValues;

public class FileUtils {

	private static Logger logger = LogManager.getLogger(FileUtils.class);

	private static final String UPLOAD_DIRECTORY = ConfigurationManager.getInstance().getParameter("upload.directory");
	private static final String UPLOAD_DISK = ConfigurationManager.getInstance().getParameter("upload.disk");

	public static void readDocument(HttpServletResponse response, String nombreFile) {

		String urlBase = UPLOAD_DISK.concat(File.separator).concat(UPLOAD_DIRECTORY).concat(File.separator).concat(nombreFile);
		if(logger.isDebugEnabled()) logger.debug("Reading file {}", urlBase);
		File file = new File(urlBase);
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			response.setContentType ("application/pdf");
			response.setHeader ("Content-Disposition", "inline; filename=cert-"+nombreFile);

			while (fis.read(buffer) > 0) {
				response.getOutputStream().write(buffer);
				response.flushBuffer();
			}
			if(logger.isDebugEnabled()) logger.debug("Read has been done successfully");
			fis.close();
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
	}

	public static void loadDocument (String nameFile, FileItem fileItem) {
		// constructs the directory path to store upload file
		// this path is relative to application's directory
		String uploadPath = UPLOAD_DISK.concat(File.separator).concat(UPLOAD_DIRECTORY);

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		String fileName = new File(nameFile).getName();
		String filePath = uploadPath + File.separator + fileName;
		File storeFile = new File(filePath);
		if(logger.isDebugEnabled()) logger.debug("Upload file {} to {}", fileItem, uploadPath);
		try {
			fileItem.write(storeFile);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if(logger.isDebugEnabled()) logger.debug("Upload has been done successfully");
	}

	public static Boolean checkFileExtension(FileItem fileItem, String extension) {
		if(logger.isDebugEnabled()) logger.debug("Comparing file:{} with the extension: {}", fileItem.getName(), extension);
		if(fileItem == null || extension == null) {
			return Boolean.FALSE;
		}
		String extensionFile = ConstantsValues.DOT.concat(FilenameUtils.getExtension(fileItem.getName()));
		if(logger.isDebugEnabled()) logger.debug("File extension: {}", extensionFile);
		if(extension.compareTo(extensionFile) == 0) {
			return Boolean.TRUE;
		}else {
			return Boolean.FALSE;
		}

	}
}
