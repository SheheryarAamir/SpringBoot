package com.sheheryar.demo.controller;

import java.io.File;
import java.io.IOException;

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

import com.sheheryar.demo.service.impl.RecordsServiceImpl;
import com.sheheryar.demo.utils.CSVUtil;

@Controller
public class AppController {
	
	public static final Logger logger = LoggerFactory.getLogger(AppController.class);
	CSVUtil csvUtil = new CSVUtil();
	
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
            recordsServiceImpl.bulkSave(csvUtil.processInputFile(multipartToFile(file)));
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
	
	
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
