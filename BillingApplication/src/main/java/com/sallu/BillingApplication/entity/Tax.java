package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "taxes")
@Data
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private int taxId;

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "tax_percentage")
    private float taxPercentage;

    public Tax() {
    }

    public Tax(int taxId, String taxName, float taxPercentage) {
        this.taxId = taxId;
        this.taxName = taxName;
        this.taxPercentage = taxPercentage;
    }
}
