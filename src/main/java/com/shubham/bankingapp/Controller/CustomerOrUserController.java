package com.shubham.bankingapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.shubham.bankingapp.model.AccountOpening;
import com.shubham.bankingapp.repository.AccountOpeningRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerOrUserController {

    @Autowired
    private AccountOpeningRepository accountOpeningRepository;

    @GetMapping("/customerOrAdmin")
    public String showCustomerOrUserPage() {
        return "customerOrAdmin";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Your dashboard logic
        return "accountOpeningForm"; // Assuming "dashboard" is the name of your HTML template
    }

    @GetMapping("/maintenance")
    public String maintenance() {
        return "maintenance"; // This will resolve to src/main/resources/templates/maintenance.html
    }
}
