package com.bayzat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bayzat.entities.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

}
