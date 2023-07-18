package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepositoty extends JpaRepository<Contact, Integer> {
    long count();
}
