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
                .uri("/api/v1/customers/Id0055XXZZ")
                .exchange()
                .expectBody(Customer.class);

    }
}