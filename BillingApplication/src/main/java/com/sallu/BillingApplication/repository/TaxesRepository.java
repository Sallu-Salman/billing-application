package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxesRepository extends JpaRepository<Tax, Integer> {
}
