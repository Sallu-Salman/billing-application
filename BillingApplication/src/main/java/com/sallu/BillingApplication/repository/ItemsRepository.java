package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItemNameIn(List<String> itemNames);
    List<Item> findByItemQuantityLessThan(int quantity);
    long count();
}
