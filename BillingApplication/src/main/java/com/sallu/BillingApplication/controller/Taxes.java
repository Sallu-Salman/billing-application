package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.entity.Tax;
import com.sallu.BillingApplication.repository.TaxesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/taxes")
public class Taxes {

    TaxesRepository taxesRepository;

    public Taxes(TaxesRepository taxesRepository) {
        this.taxesRepository = taxesRepository;
    }

    @GetMapping
    public String getTaxes(Model model) {
        model.addAttribute("tax_page", true);
        List<Tax> taxes = taxesRepository.findAll();
        model.addAttribute("taxesList", taxes);
        return "common-space";
    }

    @GetMapping("/showTaxesForm")
    public String showTaxesForm(Model model) {
        model.addAttribute("tax", new Tax());
        model.addAttribute("tax_form", true);
        return "common-space";
    }

    @PostMapping("/saveTax")
    public String saveTax(@ModelAttribute("tax") Tax tax) {
        taxesRepository.save(tax);
        return "redirect:/taxes";
    }
}
