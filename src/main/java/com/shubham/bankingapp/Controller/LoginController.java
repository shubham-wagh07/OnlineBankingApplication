package com.shubham.bankingapp.Controller;

import com.shubham.bankingapp.model.AccountOpening;

import com.shubham.bankingapp.model.Customer;
import com.shubham.bankingapp.repository.AccountOpeningRepository;
import com.shubham.bankingapp.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountOpeningRepository accountOpeningRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }
    
    @GetMapping("/")
    public String homePage() {
        return "index"; // Make sure this is the name of your front page HTML template
    }
    
    
    @GetMapping("/account/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login"; // Redirect to the login page after logging out
    }
    

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        Customer customer = customerRepository.findByEmailWithAccountOpening(email);

        if (customer == null || !passwordEncoder.matches(password, customer.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }

        // Store the logged-in customer in the session
        session.setAttribute("loggedInCustomer", customer);

        // Retrieve the AccountOpening ID associated with the customer
        AccountOpening accountOpening = customer.getAccountOpening();
        if (accountOpening != null) {
            Long accountOpeningId = accountOpening.getId();
            // Redirect to the account home page or pass the accountOpeningId as needed
            return "redirect:/account/home?id=" + accountOpeningId;
        } else {
            // Redirect to handle scenarios where there's no account opening
            return "redirect:/account/open";
        }
        
    }
}
