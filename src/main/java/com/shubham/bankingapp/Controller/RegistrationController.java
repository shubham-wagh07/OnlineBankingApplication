package com.shubham.bankingapp.Controller;

import com.shubham.bankingapp.model.Customer;
import com.shubham.bankingapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("customer") Customer customer, Model model) {
        if (customerRepository.existsByUsername(customer.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        if (customer.getPassword().length() < 5) {
            model.addAttribute("error", "Password must be at least 5 characters long");
            return "register";
        }

        // Encrypt password and save customer
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        customerRepository.save(customer);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String registrationSuccess() {
        return "success";
    }
}