package com.example.ppback.service;
import org.apache.poi.ss.usermodel.Workbook;



public interface UploadPara {
	void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception;

    String getUploaderType();

    boolean isFileValid(Workbook workbook);
}

