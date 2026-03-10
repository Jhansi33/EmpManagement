package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.model.Emp;

public interface EmpRepository extends JpaRepository<Emp, Long> {

	Emp findByEmailAndPassword(String email,String password);
}
