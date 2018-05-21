package com.bayzat.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bayzat.entities.Dependant;

@Repository
public interface DependantRepo extends JpaRepository<Dependant, Long> {
	// I used the forign key
	Collection<Dependant> findByEmployeeId(Long employeeId);

}
