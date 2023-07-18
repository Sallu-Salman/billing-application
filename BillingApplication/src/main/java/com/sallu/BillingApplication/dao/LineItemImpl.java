package com.sallu.BillingApplication.dao;

import com.sallu.BillingApplication.entity.Invoice;
import com.sallu.BillingApplication.entity.LineItem;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class LineItemImpl implements LineItemDao{

    private EntityManager entityManager;

    public LineItemImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void deleteLineItem(int lineItemId, Invoice invoice) {
        LineItem lineItem = entityManager.find(LineItem.class, lineItemId);
        invoice = entityManager.find(Invoice.class, invoice.getInvoiceId());
        invoice.getLineItems().remove(lineItem);
        entityManager.remove(lineItem);
    }
}
