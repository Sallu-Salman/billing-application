package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "status")
    private String status;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "terms_and_conditions")
    private String termsAndConditions;

    @Column(name = "customer_notes")
    private String customerNotes;

    @Column(name = "sub_total")
    private float subTotal;

    @Column(name = "discount")
    private float discount;

    @Column(name = "charges")
    private float charges;

    @Column(name = "tax")
    private float tax;

    @Column(name = "total_cost")
    private float totalCost;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Contact contact;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<LineItem> lineItems;

    public Invoice() {
    }

    public Invoice(int invoiceId, String status, String invoiceDate, String subject, String termsAndConditions, String customerNotes, float subTotal, float discount, float charges, float tax, float totalCost,  Contact contact) {
        this.invoiceId = invoiceId;
        this.status = status;
        this.invoiceDate = invoiceDate;
        this.subject = subject;
        this.termsAndConditions = termsAndConditions;
        this.customerNotes = customerNotes;
        this.subTotal = subTotal;
        this.discount = discount;
        this.charges = charges;
        this.tax = tax;
        this.totalCost = totalCost;
        this.contact = contact;
    }
}
