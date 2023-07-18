package com.sallu.BillingApplication.repository;

import com.sallu.BillingApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
}
