package com.sheheryar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheheryar.demo.model.RecordsCount;

@Repository
public interface RecordsCountRepository  extends JpaRepository<RecordsCount, Long>{
	@Modifying
    @Query(value = "UPDATE records_count SET count = ?1 WHERE name = ?2", nativeQuery = true)
    int updateCounterByName(int count, String name);

    @Query(value = "SELECT count from records_count where currency_code = ?1", nativeQuery = true)
    Integer getLastInsertId(@Param("currencycode")String currencyCode);
}
