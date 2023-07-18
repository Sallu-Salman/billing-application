package com.sallu.BillingApplication.controller;

import com.sallu.BillingApplication.constants.AccountsConstants;
import com.sallu.BillingApplication.entity.Authority;
import com.sallu.BillingApplication.entity.ChartOfAccounts;
import com.sallu.BillingApplication.entity.User;
import com.sallu.BillingApplication.repository.ChartOfAccountsRepository;
import com.sallu.BillingApplication.repository.ContactsRepositoty;
import com.sallu.BillingApplication.repository.ItemsRepository;
import com.sallu.BillingApplication.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
public class Home {

    ChartOfAccountsRepository chartOfAccountsRepository;
    ItemsRepository itemsRepository;
    ContactsRepositoty contactsRepositoty;

    UsersRepository usersRepository;

    public Home(ChartOfAccountsRepository chartOfAccountsRepository, ItemsRepository itemsRepository, ContactsRepositoty contactsRepositoty, UsersRepository usersRepository) {
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.contactsRepositoty = contactsRepositoty;
        this.itemsRepository = itemsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public RedirectView redirectToDashboard() {
        return new RedirectView("/dashboard");
    }
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        ChartOfAccounts receivable = chartOfAccountsRepository.findById(AccountsConstants.ACCOUNTS_RECEIVABLE).orElse(null);
        model.addAttribute("dashboard", true);
        model.addAttribute("received", receivable.getCredit());
        model.addAttribute("notReceived", (receivable.getDebit() - receivable.getCredit()));
        model.addAttribute("itemCount", itemsRepository.count());
        model.addAttribute("contactCount", contactsRepositoty.count());
        model.addAttribute("lowCountItems", itemsRepository.findByItemQuantityLessThan(50));
        return "common-space";
    }

    @GetMapping("/signup")
    public String showFormSignUp(Model model) {
        model.addAttribute("user", new User());
        return "signup-form";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword("{noop}" + user.getPassword());
        user.setAuthority(new Authority("MANAGER", user));
        user.setEnabled(1);
        usersRepository.save(user);
        return "redirect:/login-form";
    }

    @GetMapping("/generalLedger")
    public String getGeneralLedger(Model model) {
        List<ChartOfAccounts> chartOfAccounts = chartOfAccountsRepository.findAll();
        model.addAttribute("general_ledger", true);
        model.addAttribute("accounts", chartOfAccounts);
        return "common-space";
    }

    @GetMapping("/getUserDetails")
    public String getUserDetails(Model model, Authentication authentication) {
        User user = usersRepository.findById(authentication.getName()).orElse(null);
        model.addAttribute("user", user);
        return "user-details";
    }

    @GetMapping("/login-form")
    public String login(){
        return "login-form";
    }

}
