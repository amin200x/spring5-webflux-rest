package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> findById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Vendor> save(@RequestBody Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();
        if (!foundVendor.getName().equals(vendor.getName())) {
            foundVendor.setName(vendor.getName());
            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/v1/vendors/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return vendorRepository.deleteById(id);
    }
}
