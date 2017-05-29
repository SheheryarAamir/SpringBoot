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

import com.sheheryar.demo.model.Records;

public class CSVUtil {
	
	
	HashMap<String, String> isoCurrencies = new  HashMap<String, String>();
	
	String fileName;
	
	public List<Records> processInputFile(File inputFile) {
		
		isoCurrencies.put("AED", "United Arab Emirates Dirham");
		isoCurrencies.put("USD", "United States Dollar");
		isoCurrencies.put("AUD", "Australia Dollar");
		isoCurrencies.put("BDT", "Bangladesh Taka");
		isoCurrencies.put("CAD", "Canada Dollar");
		
	    List<Records> inputList = new ArrayList<Records>();
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
	private Function<String, Records> mapToItem = (line) -> {
		  String[] p = line.split(",");// a CSV has comma separated lines
		  Records record = new Records();
		  
		  record.setDealID(p[0] ); //Unique ID
	      //Ordering Currency
		  if (p[1] != null && p[1].trim().length() > 0 && isoCurrencies.containsKey(p[1])) {
			  record.setOrderingCurrency(p[1] ); //From Currency ISO Code
		  }
		  
		  //To Currency ISO Code
		  if (p[2] != null && p[2].trim().length() > 0 && isoCurrencies.containsKey(p[2])) {
			  record.setToCurrency(p[2] );
		  }
		  
		  //Deal timestamp
		  if (p[3] != null && p[3].trim().length() > 0) {
			  record.setTimestamp(p[3] );
		  }
		  
		  //Deal Amount
		  if (p[4] != null && p[4].trim().length() > 0) {
			  record.setDealAmount(p[4] );
		  }
		  
		  record.setFileName(fileName);
		  //more initialization goes here
		  return record;
		};
		
}
