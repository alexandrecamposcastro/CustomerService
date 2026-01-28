package br.com.alexandrecamposcastro.customer;

import br.com.alexandrecamposcastro.customer.model.CustomerEntity;
import br.com.alexandrecamposcastro.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    public String viewDashboard(Model model) {
        List<CustomerEntity> customers = repository.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("newCustomer", new CustomerEntity());
        return "customers";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("newCustomer") CustomerEntity customer) {
        repository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String removeCustomer(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}