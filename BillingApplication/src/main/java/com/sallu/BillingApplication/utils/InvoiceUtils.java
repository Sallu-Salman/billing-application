package com.sallu.BillingApplication.utils;

import com.sallu.BillingApplication.constants.AccountsConstants;
import com.sallu.BillingApplication.constants.InvoiceConstants;
import com.sallu.BillingApplication.dao.LineItemDao;
import com.sallu.BillingApplication.entity.ChartOfAccounts;
import com.sallu.BillingApplication.entity.Invoice;
import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.entity.LineItem;
import com.sallu.BillingApplication.repository.ChartOfAccountsRepository;
import com.sallu.BillingApplication.repository.InvoicesRepository;
import com.sallu.BillingApplication.repository.ItemsRepository;

import java.util.*;

public class InvoiceUtils {

    InvoicesRepository invoicesRepository;
    ItemsRepository itemsRepository;
    ChartOfAccountsRepository chartOfAccountsRepository;

    LineItemDao lineItemDao;
    public InvoiceUtils(InvoicesRepository invoicesRepository, LineItemDao lineItemDao, ItemsRepository itemsRepository, ChartOfAccountsRepository chartOfAccountsRepository) {
        this.invoicesRepository = invoicesRepository;
        this.lineItemDao = lineItemDao;
        this.itemsRepository = itemsRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    public void feedItemToLineItems(Invoice invoice) {
        List<String> lineItemNames = new ArrayList<>();
        List<LineItem> emptyLineItems = new ArrayList<>();

        for(LineItem lineItem : invoice.getLineItems()) {
            if(lineItem.getItemName() == null) {
                emptyLineItems.add(lineItem);
            }
            else{
                lineItemNames.add(lineItem.getItemName());
            }
        }
        refactorLineItems(invoice, emptyLineItems);
        List<Item> items = itemsRepository.findByItemNameIn(lineItemNames);

        for(LineItem lineItem : invoice.getLineItems()) {
            Item itemFromName = findItemFromName(items, lineItem.getItemName());
            lineItem.setItem(itemFromName);
            lineItem.setItemCostPrice((float)itemFromName.getItemCostPrice());
        }
    }

    public Item findItemFromName(List<Item> items, String itemName) {
        Item item = null;
        for(Item i : items) {
            if(i.getItemName().equals(itemName)) {
                item = i;
                break;
            }
        }
        return item;
    }

    public void updateInvoice(Invoice newInvoice){

        // Keeping it for future use like update inventory, chart of accounts etc..

        Invoice oldInvoice = invoicesRepository.findById(newInvoice.getInvoiceId()).orElse(null);

        deleteOldLineItems(oldInvoice, newInvoice);
    }

    public void refactorLineItems(Invoice invoice, List<LineItem> emptyLineItems) {
        invoice.getLineItems().removeAll(emptyLineItems);
    }

    public void deleteOldLineItems(Invoice oldInvoice, Invoice newInvoice) {
        HashSet<Integer> set = new HashSet<>();

        for(LineItem lineItem : oldInvoice.getLineItems()) {
            set.add(lineItem.getLineItemId());
        }

        for(LineItem lineItem : newInvoice.getLineItems()) {
            if(set.contains(lineItem.getLineItemId())){
                set.remove(lineItem.getLineItemId());
            }
        }

        for(int id : set) {
            lineItemDao.deleteLineItem(id, oldInvoice);
        }

    }

    public Map<Integer, Float> getItemTaxMap() {
        Map<Integer, Float> itemTaxMap = new HashMap<>();

        List<Item> items = itemsRepository.findAll();

        for(Item item : items) {
            itemTaxMap.put(item.getItemId(), item.getItemTax().getTaxPercentage());
        }

        return itemTaxMap;
    }

    public void markAsPaid(int invoiceId){
        Invoice invoice = invoicesRepository.findById(invoiceId).orElse(null);
        invoice.setStatus(InvoiceConstants.PAID);
        invoicesRepository.save(invoice);

        ChartOfAccounts accReceivable = chartOfAccountsRepository.findById(AccountsConstants.ACCOUNTS_RECEIVABLE).orElse(null);
        ChartOfAccounts cash = chartOfAccountsRepository.findById(AccountsConstants.CASH_EQUIVALENT).orElse(null);

        accReceivable.setCredit(accReceivable.getCredit() + invoice.getTotalCost());
        cash.setDebit(cash.getDebit() + invoice.getTotalCost());

        chartOfAccountsRepository.saveAll(new ArrayList<>(){{
            add(accReceivable);
            add(cash);
        }});
    }
    public void markAsUnpaid(int invoiceId) {
        Invoice invoice = invoicesRepository.findById(invoiceId).orElse(null);
        invoice.setStatus(InvoiceConstants.UNPAID);
        invoicesRepository.save(invoice);

        ChartOfAccounts accReceivable = chartOfAccountsRepository.findById(AccountsConstants.ACCOUNTS_RECEIVABLE).orElse(null);
        ChartOfAccounts cash = chartOfAccountsRepository.findById(AccountsConstants.CASH_EQUIVALENT).orElse(null);

        accReceivable.setCredit(accReceivable.getCredit() - invoice.getTotalCost());
        cash.setDebit(cash.getDebit() - invoice.getTotalCost());

        chartOfAccountsRepository.saveAll(new ArrayList<>(){{
            add(accReceivable);
            add(cash);
        }});
    }

    public void changeStatus(int invoiceId, String status) {
        Invoice invoice = invoicesRepository.findById(invoiceId).orElse(null);
        invoice.setStatus(status);
        invoicesRepository.save(invoice);

        int key = (status == InvoiceConstants.PAID? 1 : -1);

        ChartOfAccounts accReceivable = chartOfAccountsRepository.findById(AccountsConstants.ACCOUNTS_RECEIVABLE).orElse(null);
        ChartOfAccounts cash = chartOfAccountsRepository.findById(AccountsConstants.CASH_EQUIVALENT).orElse(null);

        accReceivable.setCredit(accReceivable.getCredit() + (key * invoice.getTotalCost()));
        cash.setDebit(cash.getDebit() + (key * invoice.getTotalCost()));

        chartOfAccountsRepository.saveAll(new ArrayList<>(){{
            add(accReceivable);
            add(cash);
        }});
    }

    public void tailorInvoice(Invoice invoice) {
        if(invoice.getSubject().isEmpty()) {
            invoice.setSubject(null);
        }
        if(invoice.getCustomerNotes().isEmpty()) {
            invoice.setCustomerNotes(null);
        }
        if(invoice.getTermsAndConditions().isEmpty()) {
            invoice.setTermsAndConditions(null);
        }
    }
}















