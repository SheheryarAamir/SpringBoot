package com.sheheryar.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sheheryar.demo.RecordVO;
import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;

public class CSVUtil {
	
	
	HashMap<String, String> isoCurrencies = new  HashMap<String, String>();
	
	String fileName;
	
	public List<RecordVO> processInputFile(File inputFile) {
		
		isoCurrencies.put("AED", "United Arab Emirates Dirham");
		isoCurrencies.put("USD", "United States Dollar");
		isoCurrencies.put("AUD", "Australia Dollar");
		isoCurrencies.put("BDT", "Bangladesh Taka");
		isoCurrencies.put("CAD", "Canada Dollar");
		
	    List<RecordVO> inputList = new ArrayList<RecordVO>();
	    try{
	      //File inputF = new File(inputFilePath);
	      InputStream inputFS = new FileInputStream(inputFile);
	      fileName = inputFile.getName();
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	      // skip the header of the csv
	      inputList = br.lines().map(mapToItem).collect(Collectors.toList());
	      br.close();
	    } catch (IOException e) {
	      
	    }
	    return inputList ;
	}
	private Function<String, RecordVO> mapToItem = (line) -> {
		  String[] p = line.split(",");// a CSV has comma separated lines
		  String error = "false";
		  RecordVO record = new RecordVO();
		  FaultyRecords frecords = new FaultyRecords();
		  record.setDealID(p[0] ); //Unique ID
	      //Ordering Currency
		  if (p[1] == null && p[1].trim().length() == 0 && !isoCurrencies.containsKey(p[1])) {
			  error = "true";
		  }
		  
		  //To Currency ISO Code
		  if (p[2] == null && p[2].trim().length() == 0 && !isoCurrencies.containsKey(p[2])) {
			  error = "true";
		  }
		  
		  //Deal timestamp
		  if (p[3] == null && p[3].trim().length() == 0) {
			  error = "true";
		  }
		  
		  //Deal Amount
		  if (p[4] == null && p[4].trim().length() == 0) {
			  error = "true";
		  }
		  record.setOrderingCurrency(p[1] ); //From Currency ISO Code
		  record.setToCurrency(p[2] );
		  record.setTimestamp(p[3] );
		  record.setDealAmount(p[4] );
		  record.setFileName(fileName);
		  record.setError(error);
		  //more initialization goes here
		  return record;
		};
		
}
