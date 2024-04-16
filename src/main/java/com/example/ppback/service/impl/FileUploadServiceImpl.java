package com.example.ppback.service.impl;
import java.io.Console;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.HttpsResponseEnum;
import com.example.ppback.service.UploadPara;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	 @Autowired
	 private List<UploadPara> uploaders;

	@Override
	public BaseHttpResponse uploadPara(String fileType, MultipartFile file,String para)
			throws Exception {
		BaseHttpResponse resp = new BaseHttpResponse<>();
		Workbook workbook = null;
		String filePath = file.getOriginalFilename();
		if (filePath.toLowerCase().endsWith("xlsx")) {
			workbook = new XSSFWorkbook(file.getInputStream()); // 处理XLSX格式
		} else if (filePath.toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(file.getInputStream()); // 处理XLS格式
		} else {
			
			throw new IllegalArgumentException("Unsupported Excel format");
		}
		// XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

		UploadPara upload = getUploadType(fileType);
		if (upload == null) {
			resp.setFailed();
		} else if (!upload.isFileValid(workbook)) {
			resp.setFailed(HttpsResponseEnum.INVALID_FILE_TYPE);
		} else {
			upload.uploadPara(workbook, para,resp);
		}
		return resp;
	}

	private UploadPara getUploadType(String fileType) {
		for (UploadPara u : uploaders) {
			if (u.getUploaderType().equals(fileType))
				return u;
		}
		return null;
	}

}


