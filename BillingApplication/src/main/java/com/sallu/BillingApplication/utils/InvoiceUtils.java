package com.sallu.BillingApplication.utils;

import com.sallu.BillingApplication.constants.AccountsConstants;
import com.sallu.BillingApplication.constants.InvoiceConstants;
import com.sallu.BillingApplication.dao.LineItemDao;
import com.sallu.BillingApplication.entity.*;
import com.sallu.BillingApplication.repository.ChartOfAccountsRepository;
import com.sallu.BillingApplication.repository.ContactsRepositoty;
import com.sallu.BillingApplication.repository.InvoicesRepository;
import com.sallu.BillingApplication.repository.ItemsRepository;
import org.springframework.ui.Model;

import java.util.*;

public class InvoiceUtils {

    InvoicesRepository invoicesRepository;
    ItemsRepository itemsRepository;

    ContactsRepositoty contactsRepositoty;
    ChartOfAccountsRepository chartOfAccountsRepository;

    LineItemDao lineItemDao;
    public InvoiceUtils(InvoicesRepository invoicesRepository, LineItemDao lineItemDao, ItemsRepository itemsRepository, ChartOfAccountsRepository chartOfAccountsRepository, ContactsRepositoty contactsRepositoty) {
        this.invoicesRepository = invoicesRepository;
        this.lineItemDao = lineItemDao;
        this.itemsRepository = itemsRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.contactsRepositoty = contactsRepositoty;
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

        invoice.getLineItems().removeAll(emptyLineItems);
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

    public void feedModelForInvoiceForm(Model model) {
        List<Contact> contactList = contactsRepositoty.findAll();
        List<Item> itemList = itemsRepository.findAll();
        model.addAttribute("invoice_form", true);
        model.addAttribute("contactList", contactList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("itemTaxMap", getItemTaxMap());
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















