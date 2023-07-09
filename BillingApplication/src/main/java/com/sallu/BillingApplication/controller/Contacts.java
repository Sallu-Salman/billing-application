package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.entity.Contact;
import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.repository.ContactsRepositoty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class Contacts {

    ContactsRepositoty contactsRepositoty;

    public Contacts(ContactsRepositoty contactsRepositoty) {
        this.contactsRepositoty = contactsRepositoty;
    }

    @GetMapping
    public String getContacts(Model model) {
        model.addAttribute("contact_page", true);
        List<Contact> contacts = contactsRepositoty.findAll();
        model.addAttribute("contactsList", contacts);
        return "common-space";
    }

    @GetMapping("/showContactsForm")
    public String showContactsForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("contact_form", true);
        return "common-space";
    }
    @GetMapping("/editContact")
    public String showEditForm(@RequestParam("contactId") int contactId, Model model){
        Contact contact = contactsRepositoty.findById(contactId).orElse(null);
        model.addAttribute("contact", contact);
        model.addAttribute("contact_form", true);
        return "common-space";
    }

    @PostMapping("/deleteContact")
    public String deleteContact(@RequestParam("contactId") int contactId){
        contactsRepositoty.deleteById(contactId);
        return "redirect:/contacts";
    }
    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact contact) {
        contactsRepositoty.save(contact);
        return "redirect:/contacts";
    }
}
