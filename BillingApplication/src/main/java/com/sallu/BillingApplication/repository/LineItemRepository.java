package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
}
