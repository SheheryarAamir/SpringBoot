package com.sheheryar.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sheheryar.demo.model.Records;

public class CSVUtil {
	
	public List<Records> processInputFile(File inputFile) {
	    List<Records> inputList = new ArrayList<Records>();
	    try{
	      //File inputF = new File(inputFilePath);
	      InputStream inputFS = new FileInputStream(inputFile);
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
		  record.setDealID(p[0] );
	      record.setOrderingCurrency(p[1] );
	      record.setToCurrency(p[2] );
	      record.setTimestamp(p[3] );
	      record.setDealAmount(p[4] );
		  if (p[3] != null && p[3].trim().length() > 0) {
			  
		  }
		  //more initialization goes here
		  return record;
		};
		
}
