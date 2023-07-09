package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item, Integer> {
}
