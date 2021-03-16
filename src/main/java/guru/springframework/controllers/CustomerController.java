package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/api/v1/customers")
     public Flux<Customer> list(){
        return customerRepository.findAll();
    }

    @GetMapping("/api/v1/customers/{id}")
    public Mono<Customer> getById(@PathVariable String id){
        return customerRepository.findById(id);
    }
}
