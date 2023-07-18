package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_cost_price")
    private double itemCostPrice;

    @Column(name = "item_selling_price")
    private double itemSellingPrice;

    @Column(name = "item_quantity")
    private int itemQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_tax")
    private Tax itemTax;

    public Item() {
    }

    public Item(int itemId, String itemName, double itemCostPrice, double itemSellingPrice, int itemQuantity, Tax tax) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCostPrice = itemCostPrice;
        this.itemSellingPrice = itemSellingPrice;
        this.itemQuantity = itemQuantity;
        this.itemTax = tax;
    }
}
