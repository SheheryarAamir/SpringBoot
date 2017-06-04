package com.sheheryar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheheryar.demo.model.Records;

@Repository
public interface RecordRepository extends JpaRepository<Records, String>{
	
	@Query(value ="SELECT file_name FROM Records r WHERE r.file_Name= ?1", nativeQuery = true)
	String findByFileName(@Param("fileName")String fileName);
	
	
}
