package com.sheheryar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sheheryar.demo.model.Records;

@Repository
public interface RecordRepository extends JpaRepository<Records, Long>{
	
}
