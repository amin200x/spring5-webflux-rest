package guru.springframework.controllers;

import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;
    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(new Vendor(), new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        BDDMockito.given(vendorRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void save() {
        BDDMockito.given(vendorRepository.save(BDDMockito.any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        webTestClient.post()
                .uri("/api/v1/vendors")
                .bodyValue(new Vendor())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        BDDMockito.given(vendorRepository.save(BDDMockito.any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        webTestClient.put()
                .uri("/api/v1/vendors/someid")
                .bodyValue(new Vendor())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void patchWithNoChange() {
        Vendor vendor = new Vendor();
        vendor.setName("Vendor");
        BDDMockito.given(vendorRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(vendor));

        BDDMockito.given(vendorRepository.save(BDDMockito.any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        Vendor vendorToUpdate = new Vendor();
        vendorToUpdate.setName("Vendor");
        webTestClient.patch()
                .uri("/api/v1/vendors/someid")
                .bodyValue(vendorToUpdate)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository, Mockito.never()).save(Mockito.any());
    }
    @Test
    void patchWithChange() {
        Vendor vendor = new Vendor();
        vendor.setName("Vendor");
        BDDMockito.given(vendorRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(vendor));

        BDDMockito.given(vendorRepository.save(BDDMockito.any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        Vendor vendorToUpdate = new Vendor();
        vendorToUpdate.setName("Vendor01");
        webTestClient.patch()
                .uri("/api/v1/vendors/someid")
                .bodyValue(vendorToUpdate)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deleteById() {
        webTestClient.delete()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectStatus()
                .isOk();
    }
}