package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.constants.InvoiceConstants;
import com.sallu.BillingApplication.dao.LineItemDao;
import com.sallu.BillingApplication.entity.Contact;
import com.sallu.BillingApplication.entity.Invoice;
import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.entity.LineItem;
import com.sallu.BillingApplication.repository.*;
import com.sallu.BillingApplication.utils.InvoiceUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class Invoices {

    InvoicesRepository invoicesRepository;
    ContactsRepositoty contactsRepositoty;
    LineItemRepository lineItemRepository;
    ItemsRepository itemsRepository;

    ChartOfAccountsRepository chartOfAccountsRepository;
    LineItemDao lineItemDao;
    InvoiceUtils invoiceUtils;

    public Invoices(InvoicesRepository invoicesRepository, ContactsRepositoty contactsRepositoty, LineItemRepository lineItemRepository, LineItemDao lineItemDao, ItemsRepository itemsRepository, ChartOfAccountsRepository chartOfAccountsRepository) {
        this.invoicesRepository = invoicesRepository;
        this.contactsRepositoty = contactsRepositoty;
        this.lineItemRepository = lineItemRepository;
        this.itemsRepository = itemsRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.lineItemDao = lineItemDao;

        invoiceUtils = new InvoiceUtils(invoicesRepository, lineItemDao, itemsRepository, chartOfAccountsRepository);
    }

    @GetMapping
    public String getInvoices(Model model) {
        List<Invoice> invoiceList = invoicesRepository.findAll();
        model.addAttribute("invoice_page", true);
        model.addAttribute("invoiceList", invoiceList);
        return "common-space";
    }

    @GetMapping("/showInvoiceForm")
    public String showInvoiceForm(Model model) {
        Invoice invoice = new Invoice();
        invoice.setContact(new Contact());
        invoice.setLineItems(new ArrayList<>());
        List<Contact> contactList = contactsRepositoty.findAll();
        List<Item> itemList = itemsRepository.findAll();

        model.addAttribute("invoice_form", true);
        model.addAttribute("invoice", invoice);
        model.addAttribute("contactList", contactList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("itemTaxMap", invoiceUtils.getItemTaxMap());
        return "common-space";
    }

    @PostMapping("/saveInvoice")
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice, @RequestParam("contactId") int contactId) {
        feedContact(contactId, invoice);
        invoiceUtils.feedItemToLineItems(invoice);
        invoice.setStatus(InvoiceConstants.UNPAID);
        if(invoice.getInvoiceId() != 0 ){
            invoiceUtils.updateInvoice(invoice);
        }
        invoicesRepository.save(invoice);

        return "redirect:/invoices";
    }

    public void feedContact(int contactId, Invoice invoice) {
        invoice.setContact(contactsRepositoty.findById(contactId).orElse(null));
    }

    @GetMapping("/editInvoice")
    public String editInvoiceForm(Model model, @RequestParam("invoiceId") int invoiceId){
        List<Contact> contactList = contactsRepositoty.findAll();
        List<Item> itemList = itemsRepository.findAll();
        Invoice invoice = invoicesRepository.findById(invoiceId).orElse(null);
        contactList.remove(invoice.getContact());
        model.addAttribute("invoice_form", true);
        model.addAttribute("invoice", invoice);
        model.addAttribute("contactList", contactList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("itemTaxMap", invoiceUtils.getItemTaxMap());
        return "common-space";
    }

    @PostMapping("/deleteInvoice")
    public String deleteInvoice(@RequestParam("invoiceId") int invoiceId) {
        invoicesRepository.deleteById(invoiceId);
        return "redirect:/invoices";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@RequestParam("invoiceId") int invoiceId, @RequestParam("status") String status) {
        if(status.equals(InvoiceConstants.PAID)){
            invoiceUtils.changeStatus(invoiceId, InvoiceConstants.UNPAID);
        }
        else{
            invoiceUtils.changeStatus(invoiceId, InvoiceConstants.PAID);
        }
        return "redirect:/invoices";
    }

    @GetMapping("/viewInvoice")
    public String viewInvoice(@RequestParam("invoiceId") int invoiceId, Model model) {
        Invoice invoice = invoicesRepository.findById(invoiceId).orElse(null);

        invoiceUtils.tailorInvoice(invoice);
        model.addAttribute("invoice_view_page", true);
        model.addAttribute("invoice", invoice);

        return "common-space";
    }
}
