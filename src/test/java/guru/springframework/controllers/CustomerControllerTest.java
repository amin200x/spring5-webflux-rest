package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {
    WebTestClient webTestClient;
    CustomerRepository customerRepository;
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerController = new CustomerController(customerRepository);
        webTestClient = WebTestClient.bindToController(customerController).build();
    }

    @Test
    void list() {
        BDDMockito.given(customerRepository.findAll())
                .willReturn(Flux.just(new Customer(), new Customer(), new Customer()));

        webTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectBodyList(Customer.class)
                .hasSize(3);
    }

    @Test
    void getById() {
        BDDMockito.given(customerRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(new Customer()));

        webTestClient.get()
                .uri("/api/v1/customers/someId")
                .exchange()
                .expectBody(Customer.class);

    }

      @Test
    void save() {
        BDDMockito.given(customerRepository.save(BDDMockito.any(Customer.class)))
                .willReturn(Mono.just(new Customer()));

        webTestClient.post()
                .uri("/api/v1/customers")
                .bodyValue(new Customer())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        BDDMockito.given(customerRepository.save(BDDMockito.any(Customer.class)))
                .willReturn(Mono.just(new Customer()));

        webTestClient.put()
                .uri("/api/v1/customers/someId")
                .bodyValue(new Customer())
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void patchWithNoChange() {
        Customer customer = new Customer();
        customer.setFirstName("Vahid");
        customer.setLastName("Hanif");

        BDDMockito.given(customerRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(customer));

        BDDMockito.given(customerRepository.save(BDDMockito.any(Customer.class)))
                .willReturn(Mono.just(new Customer()));

        Customer customerToUpdate = new Customer();
        customerToUpdate.setFirstName("Vahid");
        customerToUpdate.setLastName("Hanif");
        webTestClient.patch()
                .uri("/api/v1/customers/someId")
                .bodyValue(customerToUpdate)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(customerRepository, Mockito.never()).save(BDDMockito.any());
    }

    @Test
    void patchWithChange() {
        Customer customer = new Customer();
        customer.setFirstName("Vahid");
        customer.setLastName("Hanif");

        BDDMockito.given(customerRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(customer));

        BDDMockito.given(customerRepository.save(BDDMockito.any(Customer.class)))
                .willReturn(Mono.just(new Customer()));

        Customer customerToUpdate = new Customer();
        customerToUpdate.setFirstName("Ali");
        customerToUpdate.setLastName("Hani");
        webTestClient.patch()
                .uri("/api/v1/customers/someId")
                .bodyValue(customerToUpdate)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(customerRepository, Mockito.times(1)).save(BDDMockito.any());
    }


    @Test
    void deleteById() {
        webTestClient.delete()
                .uri("/api/v1/customers/someid")
                .exchange()
                .expectStatus()
                .isOk();
    }

}