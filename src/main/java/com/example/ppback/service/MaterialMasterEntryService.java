package com.example.ppback.service;


import java.util.ArrayList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ppback.model.MaterialMasterEntry;
import com.example.ppback.model.MaterialMasterImportEntity;
import com.example.ppback.repository.MaterialMasterEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class MaterialMasterEntryService implements UploadPara{
	@Autowired
	private MaterialMasterEntryRepository MaterialMasterEntryRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<MaterialMasterImportEntity> importEntities = ExcelUtil.excel2MaterialMaster(workbook);
	    List<MaterialMasterEntry> MaterialMasterEntries = new ArrayList<>();
	    importEntities.forEach(importEntity -> {
	    	MaterialMasterEntry info = new MaterialMasterEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setYearMonth(para);
	        VendorPDCLMapper PDCLMapper = new VendorPDCLMapper();
	        String PDCL = PDCLMapper.getPDCL(importEntity.getProfitCenter());
	        info.setPDCL(PDCL);//
	        MaterialMasterEntries.add(info);
	        }
	        );
	    if (!mongoTemplate.collectionExists("MaterialMasterEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("MaterialMasterEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    MaterialMasterEntryRepository.saveAll(MaterialMasterEntries);
	}
	
	   
	public void deleteEntriesWithYearMonth(String para) {
		Query query = new Query(Criteria.where("yearMonth").is(para));
		mongoTemplate.remove(query, MaterialMasterEntry.class);
	}


	@Override
	public String getUploaderType() {
		// TODO Auto-generated method stub
		return "MaterialMasterEntry";
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
