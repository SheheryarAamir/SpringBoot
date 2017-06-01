package com.sheheryar.demo.service;

import java.util.Collection;

import com.sheheryar.demo.model.FaultyRecords;
import com.sheheryar.demo.model.Records;

public interface RecordService {
	public String fetchRecords(String fileName);
	public <T extends Records> void bulkSaveRecords(Collection<T> entities);
	public <T extends FaultyRecords> void bulkSaveFaultyRecords(Collection<T> entities);
}
