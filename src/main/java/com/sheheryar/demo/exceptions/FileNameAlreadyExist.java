package com.sheheryar.demo.exceptions;

public class FileNameAlreadyExist extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public FileNameAlreadyExist(String fileName) {
        super("File Name already processed " + fileName);
    }
    
}
