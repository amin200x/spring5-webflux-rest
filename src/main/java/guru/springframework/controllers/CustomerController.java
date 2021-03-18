package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/api/v1/customers")
    public Flux<Customer> list() {
        return customerRepository.findAll();
    }

    @GetMapping("/api/v1/customers/{id}")
    public Mono<Customer> getById(@PathVariable String id) {
        return customerRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/customers")
    Mono<Customer> save(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/customers/{id}")
    Mono<Customer> update(@PathVariable String id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/customers/{id}")
    Mono<Customer> patch(@PathVariable String id, @RequestBody Customer customer) {
        Customer foundCustomer = customerRepository.findById(id).block();
        if (!foundCustomer.getFirstName().equals(customer.getFirstName())
                | !foundCustomer.getLastName().equals(customer.getLastName())) {
            foundCustomer.setFirstName(customer.getFirstName());
            foundCustomer.setLastName(customer.getLastName());

            return customerRepository.save(customer);
        }
        return Mono.just(foundCustomer);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/v1/customers/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return customerRepository.deleteById(id);
    }
}
