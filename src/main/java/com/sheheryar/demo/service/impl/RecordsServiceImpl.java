package com.sheheryar.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;
import com.sheheryar.demo.repositories.FaultyRecordRepository;
import com.sheheryar.demo.repositories.RecordRepository;

@Service("recordService")
@Transactional
public class RecordsServiceImpl {
	
	@Autowired
    private RecordRepository recordRepository;
	
	@Autowired
    private FaultyRecordRepository faultyRecordRepository;
	
	public Records fetchRecords(String fileName) {
		return recordRepository.findByFileName(fileName);
    }
	
	public void saveFaultyRecords(FaultyRecords faultyRecord) {
		faultyRecordRepository.save(faultyRecord);
    }
	
	@PersistenceContext
	private EntityManager entityManager;
	private int batchSize = 1000;
	 
	public <T extends Records> void bulkSaveRecords(Collection<T> entities) {
	  //final List<T> savedEntities = new ArrayList<T>(entities.size());
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
	  //final List<T> savedEntities = new ArrayList<T>(entities.size());
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
