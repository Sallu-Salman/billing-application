package com.sallu.BillingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "line_items")
@Data
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int lineItemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_cost_price")
    private float itemCostPrice;

    @Column(name = "item_selling_price")
    private float itemSellingPrice;

    @Column(name = "item_quantity")
    private int itemQuantity;

    @Column(name = "tax_amount")
    private float taxAmount;

    @Column(name = "total_amount")
    private float totalAmount;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public LineItem() {
    }

    public LineItem(int lineItemId, String itemName, float itemCostPrice, float itemSellingPrice, int itemQuantity, float taxAmount, float totalAmount) {
        this.lineItemId = lineItemId;
        this.itemName = itemName;
        this.itemCostPrice = itemCostPrice;
        this.itemSellingPrice = itemSellingPrice;
        this.itemQuantity = itemQuantity;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
    }
}
