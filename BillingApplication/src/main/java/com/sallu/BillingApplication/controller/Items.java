package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.entity.Tax;
import com.sallu.BillingApplication.repository.ChartOfAccountsRepository;
import com.sallu.BillingApplication.repository.ItemsRepository;
import com.sallu.BillingApplication.repository.TaxesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class Items {
    ItemsRepository itemsRepository;
    TaxesRepository taxesRepository;
    ChartOfAccountsRepository chartOfAccountsRepository;

    public Items(ItemsRepository itemsRepository, TaxesRepository taxesRepository, ChartOfAccountsRepository chartOfAccountsRepository) {
        this.itemsRepository = itemsRepository;
        this.taxesRepository = taxesRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @GetMapping
    public String getItems(Model model){
        model.addAttribute("item_page", true);
        List<Item> items = itemsRepository.findAll();
        model.addAttribute("itemsList", items);
        return "common-space";
    }
    @GetMapping("/showItemsForm")
    public String showItemsForm(Model model) {
        Item item = new Item();
        item.setItemTax(new Tax());
        model.addAttribute("item", item);
        model.addAttribute("taxList", taxesRepository.findAll());
        model.addAttribute("item_form", true);
        return "common-space";
    }
    @GetMapping("/editItem")
    public String showEditForm(@RequestParam("itemId") int itemId, Model model){
        Item item = itemsRepository.findById(itemId).orElse(null);
        List<Tax> taxes = taxesRepository.findAll();
        taxes.remove(item.getItemTax());
        model.addAttribute("item", item);
        model.addAttribute("taxList", taxes);
        model.addAttribute("item_form", true);
        return "common-space";
    }
    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam("itemId") int itemId){
        itemsRepository.deleteById(itemId);
        return "redirect:/items";
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute("item") Item item, @RequestParam("itemTax") int taxId) {
        Tax tax = taxesRepository.findById(taxId).orElse(null);
        item.setItemTax(tax);
        itemsRepository.save(item);
        return "redirect:/items";
    }
}
