package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.entity.Item;
import com.sallu.BillingApplication.repository.ItemsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class Home {
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("dashboard", true);
        return "common-space";
    }

}
