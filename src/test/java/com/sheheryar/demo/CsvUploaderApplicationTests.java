package com.sheheryar.demo;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sheheryar.demo.exceptions.FileNameAlreadyExist;
import com.sheheryar.demo.model.Records;
import com.sheheryar.demo.service.impl.RecordsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvUploaderApplicationTests {

	 private RecordsServiceImpl recordService;

	 
	 @Before
	 public void setUp() {
		 recordService = Mockito.mock(RecordsServiceImpl.class);
	 }
	 /*
	@Test(expected = FileNameAlreadyExist.class)
	 public void uploadSameFileAgain() throws Exception {
		 doThrow(new FileNameAlreadyExist("")).when(recordService).fetchRecords(eq("records.csv"));
		 Records record = new Records();
		 record.setDealID("123");
		 record.setFileName("records.csv");
		 record.setOrderingCurrency("AED");
		 recordService.saveRecord(record);
	 }*/
	 
	@Test
	public void contextLoads() {
	}
	


}
