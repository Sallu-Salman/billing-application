package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.ChartOfAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartOfAccountsRepository extends JpaRepository<ChartOfAccounts, Integer> {
}
