package com.shubham.bankingapp.Controller;

import com.shubham.bankingapp.model.AccountOpening;

import com.shubham.bankingapp.model.Customer;
import com.shubham.bankingapp.model.Transaction;
import com.shubham.bankingapp.repository.AccountOpeningRepository;
import com.shubham.bankingapp.repository.TransactionRepository;
import com.shubham.bankingapp.service.MockarooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountHomeController {

    private final AccountOpeningRepository accountOpeningRepository;
    private final TransactionRepository transactionRepository;
    private final MockarooService mockarooService;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    
    @Autowired
    public AccountHomeController(AccountOpeningRepository accountOpeningRepository, 
                                 TransactionRepository transactionRepository,
                                 MockarooService mockarooService) {
        this.accountOpeningRepository = accountOpeningRepository;
        this.transactionRepository = transactionRepository;
        this.mockarooService = mockarooService;
    }

    @GetMapping("/home")
    public String showAccountHomePage(@SessionAttribute("loggedInCustomer") Customer loggedInCustomer, Model model) {
        Long userId = loggedInCustomer.getId();
        AccountOpening accountOpening = accountOpeningRepository.findById(userId).orElse(null);

        if (accountOpening != null) {
            model.addAttribute("accountOpening", accountOpening);
        } else {
            model.addAttribute("error", "No Account Opening Details found for user ID: " + userId);
        }

        return "accountHomePage";
    }

    @GetMapping("/information")
    @ResponseBody
    public ResponseEntity<AccountOpening> getAccountInformation(@SessionAttribute("loggedInCustomer") Customer loggedInCustomer) {
        Long userId = loggedInCustomer.getId();
        AccountOpening accountOpening = accountOpeningRepository.findById(userId).orElse(null);

        if (accountOpening != null) {
            return ResponseEntity.ok(accountOpening);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/deposit")
    public String showDepositForm(Model model) {
        return "depositMoneyPage";
    }

    @PostMapping("/deposit")
    public String processDeposit(@SessionAttribute("loggedInCustomer") Customer loggedInCustomer,
                                 @RequestParam("depositAmount") Double depositAmount,
                                 @RequestParam("paymentMethod") String paymentMethod,
                                 @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                 @RequestParam(value = "expiryDate", required = false) String expiryDate,
                                 @RequestParam(value = "cvv", required = false) String cvv,
                                 @RequestParam(value = "cardHolderName", required = false) String cardHolderName,
                                 Model model) {

        Long userId = loggedInCustomer.getId();
        AccountOpening accountOpening = accountOpeningRepository.findById(userId).orElse(null);

        if (accountOpening != null) {
            // Get the current balance or set it to 0 if null
            Double initialDeposit = accountOpening.getInitialDeposit() != null ? accountOpening.getInitialDeposit() : 0.0;
            // Update the balance
            accountOpening.setInitialDeposit(initialDeposit + depositAmount);

            // Save the updated account information
            accountOpeningRepository.save(accountOpening);
            String formattedBalance = decimalFormat.format(accountOpening.getInitialDeposit());
            // Create a new transaction record
            Transaction transaction = new Transaction();
            transaction.setAccountOpening(accountOpening);
            transaction.setAmount(depositAmount);
            transaction.setPaymentMethod(paymentMethod);
            transaction.setCardNumber(cardNumber);
            transaction.setExpiryDate(expiryDate);
            transaction.setCvv(cvv);
            transaction.setCardHolderName(cardHolderName);

            transactionRepository.save(transaction);

          
            return "redirect:/account/depositSuccess?newBalance=" + formattedBalance;
        } else {
            model.addAttribute("errorMessage", "Error processing deposit for user ID: " + userId);
            return "errorPage"; // Create an errorPage.html for displaying errors
        }
    }

    @GetMapping("/depositSuccess")
    public String showDepositSuccessPage(@RequestParam("newBalance") String newBalance, Model model) {
        model.addAttribute("newBalance", newBalance);
        return "depositSuccess";
    }

    @GetMapping("/transactionHistory")
    public String showTransactionHistory(@SessionAttribute("loggedInCustomer") Customer loggedInCustomer, Model model) {
        Long userId = loggedInCustomer.getId();
        List<Transaction> transactions = transactionRepository.findByAccountOpeningId(userId);
        model.addAttribute("transactions", transactions);
        return "transactionHistoryPage"; // Create transactionHistoryPage.html to display transactions
    }
}
