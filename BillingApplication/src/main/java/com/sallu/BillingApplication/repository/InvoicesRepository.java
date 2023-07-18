package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesRepository extends JpaRepository<Invoice, Integer> {
}
