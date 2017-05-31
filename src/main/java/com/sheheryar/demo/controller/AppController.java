package com.sheheryar.demo.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheheryar.demo.RecordVO;
import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;
import com.sheheryar.demo.model.RecordsCount;
import com.sheheryar.demo.service.impl.RecordsCountServiceImpl;
import com.sheheryar.demo.service.impl.RecordsServiceImpl;
import com.sheheryar.demo.utils.CSVUtil;

@Controller
public class AppController {
	
	
	
	HashMap<String, Integer> isoCurrencies = new  HashMap<String, Integer>();
	public static final Logger logger = LoggerFactory.getLogger(AppController.class);
	CSVUtil csvUtil = new CSVUtil();
	
	@Autowired
	RecordsServiceImpl recordsServiceImpl = new RecordsServiceImpl();
	
	@Autowired
	RecordsCountServiceImpl recordsCountServiceImpl = new RecordsCountServiceImpl();
	
	private static String UPLOADED_FOLDER = "F://temp//";
	@RequestMapping("/")
    String index() {
        //modal.addAttribute("title","CSV Upload");
        return "upload";
    }
	
	@PostMapping("/upload") // 
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
		
		isoCurrencies.clear();
		
		
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        
        File f;
		try {
			f = multipartToFile(file);
			String rec = recordsServiceImpl.fetchRecords(f.getName());
		        if(rec != null){
					redirectAttributes.addFlashAttribute("message",
		                    "File Already Processed '" + file.getOriginalFilename() + "'");
					return "redirect:/uploadStatus";
				}
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "some technical error. please contact administrator");
            return "redirect:/uploadStatus";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "some technical error. please contact administrator");
            return "redirect:/uploadStatus";
		}
       
        try {
        	List<Records> recordsList = new ArrayList<Records>();
    	    
    	    logger.info("Starting Reading CSV file "+LocalDateTime.now());
        	List<RecordVO> voList = csvUtil.processInputFile(f);
        	logger.info("CSV file reading process ends "+LocalDateTime.now());
        	
        	
        	
        	
        	Runnable runnable = () -> {
        	    try {
        	    	List<FaultyRecords> faultyRecordsList = new ArrayList<FaultyRecords>();
        	    	logger.info("Filter invalid Records"+LocalDateTime.now());
                	faultyRecordsList = voList.stream().filter(map -> "true".equalsIgnoreCase(map.getError())).map(mapToFaultyRecord).collect(Collectors.toList());
                	logger.info("ivalid Records filtration ends"+LocalDateTime.now());
                	logger.info("Insert invalid Records"+LocalDateTime.now());
                	recordsServiceImpl.bulkSaveFaultyRecords(faultyRecordsList);
                	logger.info("Insert invalid Records Ends"+LocalDateTime.now());
        	    }
        	    catch (Exception e) {
        	        e.printStackTrace();
        	    }
        	};

        	Thread thread = new Thread(runnable);
        	thread.start();
        	
        	
	    	

        	logger.info("Filter valid Records"+LocalDateTime.now());
        	recordsList = voList.stream().filter(map -> "false".equalsIgnoreCase(map.getError())).map(mapToRecord).collect(Collectors.toList());
        	logger.info("valid Records filtration ends"+LocalDateTime.now());
        	
        	logger.info("Insert valid Records"+LocalDateTime.now());
        	recordsServiceImpl.bulkSaveRecords(recordsList);
        	logger.info("Insert valid Records Ends"+LocalDateTime.now());
        	
        	
        	logger.info("Updating Currency Counter"+LocalDateTime.now());
        	RecordsCount recCount; 
        	for (String key : isoCurrencies.keySet()) {
        		recCount = new RecordsCount();
        		recCount.setCount(isoCurrencies.get(key));
        		recCount.setCurrencyCode(key);
        		recordsCountServiceImpl.saveRecordsCount(recCount);
			}
        	
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (Exception e) {
        	redirectAttributes.addFlashAttribute("message", "some technical error. please contact administrator");
            return "redirect:/uploadStatus";
        }

        return "redirect:/uploadStatus";
    }
	
	private Function<RecordVO, Records> mapToRecord = (recordVO) -> {
		  Records record = new Records();
		  record.setDealID(recordVO.getDealID());
		  record.setOrderingCurrency(recordVO.getOrderingCurrency()); //From Currency ISO Code
		  record.setToCurrency(recordVO.getToCurrency() );
		  record.setTimestamp(recordVO.getTimestamp() );
		  record.setDealAmount(recordVO.getDealAmount() );
		  record.setFileName(recordVO.getFileName());
		  if(isoCurrencies.containsKey(recordVO.getOrderingCurrency())){
			  isoCurrencies.put(recordVO.getOrderingCurrency(), 1 + isoCurrencies.get(recordVO.getOrderingCurrency()));
		  }else if(csvUtil.getIsoCurrencies().containsKey(recordVO.getOrderingCurrency())){
			  isoCurrencies.put(recordVO.getOrderingCurrency(), 1);
		  }
		  return record;
		};
		
		private Function<RecordVO, FaultyRecords> mapToFaultyRecord = (recordVO) -> {
			  FaultyRecords record = new FaultyRecords();
			  record.setDealID(recordVO.getDealID());
			  record.setOrderingCurrency(recordVO.getOrderingCurrency()); //From Currency ISO Code
			  record.setToCurrency(recordVO.getToCurrency() );
			  record.setTimestamp(recordVO.getTimestamp() );
			  record.setDealAmount(recordVO.getDealAmount() );
			  record.setFileName(recordVO.getFileName());
			  return record;
			};
	
	
	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
	    File convFile = new File( multipart.getOriginalFilename());
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
	
}
