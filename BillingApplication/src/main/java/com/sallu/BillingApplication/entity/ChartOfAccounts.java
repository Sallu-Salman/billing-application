package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chart_of_accounts")
@Data
public class ChartOfAccounts {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "debit")
    private float debit;

    @Column(name = "credit")
    private float credit;

    public ChartOfAccounts() {
    }

    public ChartOfAccounts(int accountId, String accountName, float debit, float credit) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.debit = debit;
        this.credit = credit;
    }
}
