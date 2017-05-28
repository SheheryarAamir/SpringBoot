package com.sheheryar.demo.service.impl;

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
	
	public void saveRecords(Records record) {
		recordRepository.save(record);
    }
	
	public void saveFaultyRecords(FaultyRecords faultyRecord) {
		faultyRecordRepository.save(faultyRecord);
    }
	
}
