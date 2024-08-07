package com.example.ppback.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class VendorPDCLMapper {
    private Map<String, String> vendorMap;
    private Map<String, String> profitCenterMap;
    public VendorPDCLMapper() {
        vendorMap = new HashMap<>();
        // 更新frozen文件
        vendorMap.put("4770", "HorP");
        vendorMap.put("54646", "HoP2");
        vendorMap.put("58557", "EcP");
        vendorMap.put("132994", "LoP1");
        vendorMap.put("134104", "DCHK");
        vendorMap.put("134418", "DCEM");
        vendorMap.put("134775", "DCIT");
        vendorMap.put("136236", "DCUS");
        vendorMap.put("136255", "DCCZ");
        vendorMap.put("136715", "DCKR");
        vendorMap.put("137445", "AfP");
        vendorMap.put("138271", "Didactic");
        vendorMap.put("190091", "DCAT");
        vendorMap.put("190093", "AhmP");
        vendorMap.put("190095", "DCFI");
        vendorMap.put("190136", "PkP SVC");
        vendorMap.put("190142", "WujP SVC");
        vendorMap.put("190890", "NuP2");
        vendorMap.put("195301", "VxP");
        vendorMap.put("197635", "FniP");
        vendorMap.put("362753", "DCBR");
        vendorMap.put("370877", "TscP");
        vendorMap.put("371366", "PkP");
        vendorMap.put("372132", "Lohr SVC");
        vendorMap.put("377434", "3RD");
        vendorMap.put("381479", "DCOC");
        vendorMap.put("638788", "WujP");
        vendorMap.put("651165", "HejP");
        vendorMap.put("97032862", "DCOC");
        vendorMap.put("97032864", "DCOC");
        vendorMap.put("97032868", "DCOC");
        vendorMap.put("97081226", "DCIN");
        vendorMap.put("97155076", "MllP");
        vendorMap.put("97323689", "NuP2 MA SVC");
        vendorMap.put("97415951", "GleP");
        vendorMap.put("97469609", "BuP2");
        vendorMap.put("97474483", "3RD");
        vendorMap.put("97505750", "3RD");
        vendorMap.put("97510160", "3RD");
        vendorMap.put("97526489", "3RD");
        vendorMap.put("198207", "DCNL");
        vendorMap.put("97186554", "HydraForce Inc");
        vendorMap.put("634417", "SwP");
        vendorMap.put("4979", "LoP2");
        vendorMap.put("368432", "LoP2 service");
        vendorMap.put("135831", "DCFR");
        
        profitCenterMap = new HashMap<>();
        // 更新PDCL BU PC文件
        profitCenterMap.put("CN355701", "CHY");
        profitCenterMap.put("CN355101", "MAP");
        profitCenterMap.put("CN355109", "MAP");
        profitCenterMap.put("CN355801", "MHS");
        profitCenterMap.put("CN355803", "MHS");
        profitCenterMap.put("CN355401", "OGB");
        profitCenterMap.put("CN5611A0", "HLD");
        profitCenterMap.put("CN5613A0", "HLD");
        profitCenterMap.put("CN511300", "ICS");
        profitCenterMap.put("CN561100", "IHS");
        profitCenterMap.put("CN561200", "IHS");
        profitCenterMap.put("CN561300", "IHS");
        profitCenterMap.put("CN511400", "IPM");
        profitCenterMap.put("CN144100", "EDC");
        profitCenterMap.put("CN144120", "EDC");
        profitCenterMap.put("CN164100", "EES");
        profitCenterMap.put("CN164200", "EES");
        profitCenterMap.put("CN164300", "EES");
        profitCenterMap.put("CN144200", "JRW");
        profitCenterMap.put("CN133500", "AST");
        profitCenterMap.put("CN163100", "AST");
        profitCenterMap.put("CN163200", "AST");
        profitCenterMap.put("CN163300", "AST");
        profitCenterMap.put("CN144300", "JTI");
        profitCenterMap.put("CN163400", "JTI");
        profitCenterMap.put("CN163500", "JTI");
        profitCenterMap.put("CN163600", "JTI");
        profitCenterMap.put("CN155701", "CHY");
        profitCenterMap.put("CN111A00", "HLD");
        profitCenterMap.put("CN1613A0", "HLD");
        profitCenterMap.put("CN111100", "ICO");
        profitCenterMap.put("CN111300", "ICS");
        profitCenterMap.put("CN1558I1", "IHS");
        profitCenterMap.put("CN1558I2", "IHS");
        profitCenterMap.put("CN1558I3", "IHS");
        profitCenterMap.put("CN161100", "IHS");
        profitCenterMap.put("CN161200", "IHS");
        profitCenterMap.put("CN161300", "IHS");
        profitCenterMap.put("CN169002", "IHS");
        profitCenterMap.put("CN111400", "IPM");
        profitCenterMap.put("CN111600", "ISB");
        profitCenterMap.put("CN133100", "LMT");
        profitCenterMap.put("CN163310", "LMT");
        profitCenterMap.put("CN155101", "MAP");
        profitCenterMap.put("CN155501", "MCO");
        profitCenterMap.put("CN155601", "MEL");
        profitCenterMap.put("CN155801", "MHS");
        profitCenterMap.put("CN155802", "MHS");
        profitCenterMap.put("CN155803", "MHS");
        profitCenterMap.put("CN155301", "OEG");
        profitCenterMap.put("CN155401", "OGB");
        profitCenterMap.put("CN155201", "ORP");
        profitCenterMap.put("CN264300", "EES");
        profitCenterMap.put("CN255701", "CHY");
        profitCenterMap.put("CN211A00", "HLD");
        profitCenterMap.put("CN211100", "ICO");
        profitCenterMap.put("CN211300", "ICS");
        profitCenterMap.put("CN25581I", "IHS");
        profitCenterMap.put("CN261100", "IHS");
        profitCenterMap.put("CN261200", "IHS");
        profitCenterMap.put("CN261300", "IHS");
        profitCenterMap.put("CN269900", "IHS");
        profitCenterMap.put("CN211400", "IPM");
        profitCenterMap.put("CN233100", "LMT");
        profitCenterMap.put("CN255101", "MAP");
        profitCenterMap.put("CN255501", "MCO");
        profitCenterMap.put("CN255801", "MHS");
        profitCenterMap.put("CN255802", "MHS");
        profitCenterMap.put("CN644110", "EDC");
        profitCenterMap.put("CN644120", "EDC");
        profitCenterMap.put("CN664100", "EDC");
        profitCenterMap.put("CN664200", "EDC");
        profitCenterMap.put("CN633100", "LMT");
        profitCenterMap.put("CN355108", "MAP");
        profitCenterMap.put("CN511600", "ISB");
        profitCenterMap.put("CN155700", "CHY");
        profitCenterMap.put("CN163010", "ISB");
        profitCenterMap.put("CN161301", "MHS");
        profitCenterMap.put("CN263300", "AST");
        profitCenterMap.put("CN25582I", "IHS");
        profitCenterMap.put("CN633110", "LMT");
        profitCenterMap.put("CN5612A0", "HLD");
        profitCenterMap.put("CN561110", "IHS");
        profitCenterMap.put("CN164900", "EES");
        profitCenterMap.put("CN1610A0", "HLD");
        profitCenterMap.put("CN1611A0", "HLD");
        profitCenterMap.put("CN355409", "OGB");
        profitCenterMap.put("CN111700", "OFE");
        profitCenterMap.put("CN211900", "OFE");
        profitCenterMap.put("CN561302", "IHS");
        profitCenterMap.put("CN211600", "ISB");
        profitCenterMap.put("CN511100", "ICO");
        profitCenterMap.put("CN644310", "JTI");
        profitCenterMap.put("CN355501", "MCO");
        profitCenterMap.put("CN561900", "IHS");
        profitCenterMap.put("CN300000", "GEN");
        profitCenterMap.put("CN100000", "GEN");
        profitCenterMap.put("CN500000", "GEN");
        profitCenterMap.put("CN155301", "OEG");
        profitCenterMap.put("CN111100", "ICO");
        profitCenterMap.put("CN155501", "MCO");
        profitCenterMap.put("CN155701", "CHY");
        profitCenterMap.put("CN155601", "MEL");
        profitCenterMap.put("CN144300", "JTI");
        profitCenterMap.put("CN161300", "IHS");
        profitCenterMap.put("CN133500", "AST");
        profitCenterMap.put("CN144100", "EDC");
        profitCenterMap.put("CN144200", "JRW");
        profitCenterMap.put("CN155803", "MHS");
        profitCenterMap.put("CN133100", "LMT");
        profitCenterMap.put("CN164300", "EES");
        profitCenterMap.put("CN111400", "IPM");
        profitCenterMap.put("CN111300", "ICS");
        profitCenterMap.put("CN155101", "MAP");
        profitCenterMap.put("CN164100", "EES");
        profitCenterMap.put("CN144120", "EDC");
        profitCenterMap.put("CN155401", "OGB");
        profitCenterMap.put("CN155201", "ORP");
        profitCenterMap.put("CN111A00", "HLD");
        profitCenterMap.put("CN1613A0", "HLD");
        profitCenterMap.put("CN169002", "IHS");
        profitCenterMap.put("CN155801", "MHS");
        profitCenterMap.put("CN111700", "OFE");

    }

    public String getVendorName(String vendor) {
        // 获取 vendor 名称，如果不存在则返回未分类的vendor
        return vendorMap.getOrDefault(vendor, vendor);
    }
    public String getPDCL(String profitCenter) {
        // 获取 product class，如果不存在则返回空字符串
        return profitCenterMap.getOrDefault(profitCenter, "");
    }
}
