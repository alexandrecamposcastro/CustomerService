package br.com.alexandrecamposcastro.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alexandrecamposcastro.customer.model.CustomerEntity;
import br.com.alexandrecamposcastro.customer.repository.CustomerRepository;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    public String dashboard(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("customers", repository.findByNameContainingIgnoreCase(search));
        } else {
            model.addAttribute("customers", repository.findAll());
        }
        model.addAttribute("newCustomer", new CustomerEntity());
        return "customers";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CustomerEntity customer) {
        repository.save(customer); 
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        CustomerEntity customer = repository.findById(id).orElse(new CustomerEntity());
        model.addAttribute("customers", repository.findAll());
        model.addAttribute("newCustomer", customer); 
        return "customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}