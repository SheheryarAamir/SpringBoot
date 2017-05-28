package com.sheheryar.demo.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {
	
	@RequestMapping(value="/csvuploader", method = RequestMethod.POST)
	public void downloadFile(@RequestParam(value = "fileName", required = true) String fileName,HttpServletResponse response) throws IOException {

	    File dir = new File("C:\\file");
	    File fileToDownload = new File(dir,fileName);
	    if (dir.isDirectory() && fileToDownload.exists()){
	        //read fileToDownload and send stream to to response.getOutputStream();

	    }else {
	        System.out.println("no such file "+ fileToDownload.toString());
	        response.sendError(404);
	        return;
	    }

	}
}
