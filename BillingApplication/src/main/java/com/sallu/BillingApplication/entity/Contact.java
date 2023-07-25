package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "contacts")
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private int contactId;

    @NotEmpty(message = "Please enter contact name")
    @Column(name = "contact_name")
    private String contactName;

    @NotEmpty(message = "Please enter a valid email address")
    @Column(name = "contact_email")
    private String contactEmail;

    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number of 10 digits")
    @Column(name = "contact_phone")
    private String contactPhone;

    public Contact() {
    }

    public Contact(int contactId, String contactName, String contactEmail, String contactPhone) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}
