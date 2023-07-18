package com.sallu.BillingApplication.dao;

import com.sallu.BillingApplication.entity.Invoice;

public interface LineItemDao {
    public void deleteLineItem(int lineItemId, Invoice invoice);
}
