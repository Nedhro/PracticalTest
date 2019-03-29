package com.bits.common;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public class CommonServiceImpl<T extends BaseEntity<Long>> {
	
	private  JpaRepository<T,Long> repository;
	
	public CommonServiceImpl(JpaRepository<T,Long> repository){		
		this.repository = repository;		
	}
	
	public List<T> findAll() {
		 return repository.findAll();
	}
	
	
	public Page<T> findAll(int page, int size) {		
		return repository.findAll(new PageRequest(page, size));
	}
	
	@Transactional
	public void create(T obj) {		
		repository.save(obj);
		
	}

	@Transactional
	public void delete(T obj) {
		repository.delete(obj);		
	}


	public T findById(long Id) {
		 return repository.findOne(Id);
	}
	
}
