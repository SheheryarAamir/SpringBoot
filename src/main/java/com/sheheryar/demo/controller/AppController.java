package com.sheheryar.demo.controller;

import java.io.IOException;
import java.util.List;

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

import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;
import com.sheheryar.demo.service.impl.RecordsServiceImpl;
import com.sheheryar.demo.utils.CSVUtil;

@Controller
public class AppController {
	
	public static final Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	RecordsServiceImpl recordsServiceImpl = new RecordsServiceImpl();
	
	private static String UPLOADED_FOLDER = "F://temp//";
	@RequestMapping("/")
    String index() {
        //modal.addAttribute("title","CSV Upload");
        return "upload";
    }
	
	@PostMapping("/upload") // 
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();            
            String completeData = new String(bytes);
            String[] rows = completeData.split("\n");
            Records record = new Records();            
            FaultyRecords faultyRecords = new FaultyRecords();
            for (String sRow : rows) {
            	List<String> line = CSVUtil.parseLine(sRow);
            	logger.info("Fetching & Validating records {}", "Record [id= " + line.get(0) + ", From code= " + line.get(1) + " , To Code=" + line.get(2) + " , Timestamp=" + line.get(3) + " , Amount=" + line.get(4)  + "]");
            	record.setDealID(line.get(0) );
            	record.setOrderingCurrency(line.get(1) );
            	record.setToCurrency(line.get(2) );
            	record.setTimestamp(line.get(3) );
            	record.setDealAmount(line.get(4) );
            	recordsServiceImpl.saveRecords(record);
			}
            /*Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);*/

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
	
}
