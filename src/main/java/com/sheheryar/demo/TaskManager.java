package com.sheheryar.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.config.Task;

@RepositoryRestResource
public interface TaskManager extends CrudRepository<Task, Integer>{
	

}
