package com.example.ppback.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ppback.base.DeleteRequest;
import com.example.ppback.base.SearchRequest;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.DataEntryService;
import com.example.ppback.service.GrEntryService;
import com.example.ppback.service.InfoRecordEntryService;
import com.example.ppback.service.MaterialMasterEntryService;
import com.example.ppback.service.SoldEntryService;
import com.example.ppback.service.StockEntryService;
import com.example.ppback.service.ProductListEntryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
// @RequestMapping("/delete")
@RequestMapping("/stage-api/delete")
public class DeleteDataController {
	@Autowired DataEntryService data;
	@Autowired GrEntryService gr;
	@Autowired InfoRecordEntryService info;
	@Autowired MaterialMasterEntryService material;
	@Autowired SoldEntryService sold;
	@Autowired StockEntryService stock;
	@Autowired ProductListEntryService prodcuctlist;
	
	@PostMapping(value = "/data")
	public BaseHttpResponse deleteDATA(@RequestBody DeleteRequest req){
		String yearmonth = req.getSelectedmonth();
		BaseHttpResponse resp = new BaseHttpResponse();
		try {
			data.deleteByMonth(yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/gr")
	public BaseHttpResponse deleteGR(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			gr.deleteByMonth(yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/info")
	public BaseHttpResponse deleteINFO(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			info.deleteByMonth();

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/materialmaster")
	public BaseHttpResponse deleteMATERIALMASTER(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			material.deleteByMonth();

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/sold")
	public BaseHttpResponse deleteSOLD(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			sold.deleteByMonth(yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/stock")
	public BaseHttpResponse deleteSTOCK(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			stock.deleteByMonth(yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
	@PostMapping(value = "/productlist")
	public BaseHttpResponse deletePRODUCTLIST(@RequestBody DeleteRequest req){
		BaseHttpResponse resp = new BaseHttpResponse();
		String yearmonth = req.getSelectedmonth();
		try {
			prodcuctlist.deleteByMonth(yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}
}