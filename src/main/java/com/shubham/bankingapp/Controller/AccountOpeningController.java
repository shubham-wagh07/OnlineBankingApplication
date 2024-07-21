package com.shubham.bankingapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;
import com.shubham.bankingapp.model.AccountOpening;
import com.shubham.bankingapp.model.Customer;
import com.shubham.bankingapp.repository.AccountOpeningRepository;
import com.shubham.bankingapp.repository.CustomerRepository;

@Controller
@RequestMapping("/account")
public class AccountOpeningController {

    @Autowired
    private AccountOpeningRepository accountOpeningRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    // Display the account opening form
    @GetMapping("/open")
    public String showAccountOpeningForm(Model model) {
        model.addAttribute("accountOpening", new AccountOpening());
        return "accountOpeningForm";
    }

    // Process account opening form submission
    @Transactional
    @PostMapping("/open")
    public String openAccount(@ModelAttribute("accountOpening") AccountOpening accountOpening, Model model) {
        try {
            // Check if the customer already exists
            Customer customer = customerRepository.findByEmail(accountOpening.getEmail());
            if (customer == null) {
                // If customer does not exist, create a new one
                customer = new Customer();
                customer.setEmail(accountOpening.getEmail());
                // Set other customer details as needed

                // Save the customer
                customerRepository.save(customer);
            }
            
            // Set the customer for the account opening
            accountOpening.setCustomer(customer);

            // Save the account opening details to the database
            accountOpeningRepository.save(accountOpening);

            return "redirect:/account/success"; // Redirect to success page
        } catch (Exception e) {
            model.addAttribute("error", "Account opening failed: " + e.getMessage());
            return "accountOpeningForm"; // Return to the form with error message
        }
    }

    // Success page after account opening
    @GetMapping("/success")
    public String showSuccessPage() {
        return "AccountSuccess";
    }
}
