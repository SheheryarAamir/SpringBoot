package com.sheheryar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sheheryar.demo.model.FaultyRecords;

@Repository
public interface FaultyRecordRepository  extends JpaRepository<FaultyRecords, Long>{

}
