package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.repository.ItemsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class Items {
    ItemsRepository itemsRepository;

    public Items(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
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
        model.addAttribute("item", new Item());
        model.addAttribute("item_form", true);
        return "common-space";
    }
    @GetMapping("/editItem")
    public String showEditForm(@RequestParam("itemId") int itemId, Model model){
        Item item = itemsRepository.findById(itemId).orElse(null);
        model.addAttribute("item", item);
        model.addAttribute("item_form", true);
        return "common-space";
    }
    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam("itemId") int itemId){
        itemsRepository.deleteById(itemId);
        return "redirect:/items";
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemsRepository.save(item);
        return "redirect:/items";
    }
}
