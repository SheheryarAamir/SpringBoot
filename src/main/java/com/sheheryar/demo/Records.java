package com.sheheryar.demo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(name="records")
public class Records {
	
	@Column(name="deal_id")
	 private String dealID;	

}
