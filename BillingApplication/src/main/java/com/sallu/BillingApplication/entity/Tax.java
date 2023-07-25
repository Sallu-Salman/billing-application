package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "taxes")
@Data
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private int taxId;

    @NotEmpty(message = "Please enter a Tax name")
    @Column(name = "tax_name")
    private String taxName;

    @Min(value = 0, message = "Please enter a valid percentage")
    @Max(value = 0, message = "Please enter a valid percentage")
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
