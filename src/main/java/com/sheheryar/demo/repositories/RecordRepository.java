package com.sheheryar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sheheryar.demo.model.Records;

@Repository
public interface RecordRepository extends JpaRepository<Records, Long>{
	
	@Query("SELECT r FROM Records r WHERE r.file_Name= ?1 limit 1")
    Records findByFileName(String fileName);
}
