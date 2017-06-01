package com.sheheryar.demo.service.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheheryar.demo.exceptions.FileNameAlreadyExist;
import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;
import com.sheheryar.demo.model.RecordsCount;
import com.sheheryar.demo.repositories.RecordsCountRepository;
import com.sheheryar.demo.repositories.RecordRepository;

@Service("recordService")
@Transactional
public class RecordsServiceImpl {
	
	@Autowired
    private RecordRepository recordRepository;
	
	
	public void saveRecord(Records record) {
		recordRepository.save(record);
    }
	
	
	public void fetchRecords(String fileName) {
		String result = recordRepository.findByFileName(fileName);
		if(result != null){
			throw new FileNameAlreadyExist(result);
		}
    }
	
	
	
	
	@PersistenceContext
	private EntityManager entityManager;
	private int batchSize = 1000;
	 
	public <T extends Records> void bulkSaveRecords(Collection<T> entities) {

	  int i = 0;
	  for (T t : entities) {
		entityManager.persist(t);
	    i++;
	    if (i % batchSize == 0) {
	      // Flush a batch of inserts and release memory.
	      entityManager.flush();
	      entityManager.clear();
	    }
	  }
	}
	
	 
	public <T extends FaultyRecords> void bulkSaveFaultyRecords(Collection<T> entities) {

	  int i = 0;
	  for (T t : entities) {
		entityManager.persist(t);
	    i++;
	    if (i % batchSize == 0) {
	      // Flush a batch of inserts and release memory.
	      entityManager.flush();
	      entityManager.clear();
	    }
	  }
	}
	 

	
}
