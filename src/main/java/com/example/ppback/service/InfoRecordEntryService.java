package com.example.ppback.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.ppback.model.InfoRecordEntry;
import com.example.ppback.model.InfoRecordImportEntity;
import com.example.ppback.repository.InfoRecordEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class InfoRecordEntryService implements UploadPara{
	@Autowired
	private InfoRecordEntryRepository InfoRecordEntryRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<InfoRecordImportEntity> importEntities = ExcelUtil.excel2InfoRecord(workbook);
	    List<InfoRecordEntry> InfoRecordEntries = new ArrayList<>();
	    importEntities.forEach(importEntity -> {
	    	InfoRecordEntry info = new InfoRecordEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        String vendor = importEntity.getVendor().replaceFirst("^0+(?!$)", "");//不要头部的0
	        VendorPDCLMapper VendorMapper = new VendorPDCLMapper();
	        vendor = VendorMapper.getVendorName(vendor);
	        info.setVendor(vendor);
	        InfoRecordEntries.add(info);
	        }
	        );
	    InfoRecordEntryRepository.saveAll(InfoRecordEntries);
	}
	
	public String getVendorByPN(String PN) {
	    String sql = "SELECT TOP 1 vendor FROM info_record_entry WHERE product_number = ?";
	    List<String> vendors = jdbcTemplate.query(sql, new Object[] { PN }, (resultSet, rowNum) -> {
	        return resultSet.getString("vendor");
	    });
	    if (vendors.isEmpty()) {
	        return null;
	    } else {
	        return vendors.get(0);
	    }
	}
	
	@Override
	public String getUploaderType() {
		// TODO Auto-generated method stub
		return "InfoRecordEntry";
	}
	@Override
	public boolean isFileValid(Workbook workbook) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private static class SumResult {
        private List<Integer> kks;

        public List<Integer> getValues() {
            return kks;
        }

        public int getSumIndex(int index) {
            return kks.get(index);
        }
    }

}
