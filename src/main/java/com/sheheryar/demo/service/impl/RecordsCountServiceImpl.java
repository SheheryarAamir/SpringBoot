package com.sheheryar.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheheryar.demo.model.RecordsCount;
import com.sheheryar.demo.repositories.RecordsCountRepository;

@Service("recordsCountService")
@Transactional
public class RecordsCountServiceImpl {

	@Autowired
    private RecordsCountRepository recordCountRepository;
	
	public void saveRecordsCount(RecordsCount recordsCount) {

		Integer count = recordCountRepository.getLastInsertId(recordsCount.getCurrencyCode());
		if(count != null){
			recordCountRepository.updateCounterByName(count+ recordsCount.getCount(), recordsCount.getCurrencyCode());
		}
		else{
			recordCountRepository.save(recordsCount);
		}
    }
}
