package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    public Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    public Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    public Mono<Category> save(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/categories/{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/categories/{id}")
    public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
        Category foundCategory = categoryRepository.findById(id).block();
        if (!foundCategory.getName().equals(category.getName())) {
            foundCategory.setName(category.getName());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/v1/categories/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return categoryRepository.deleteById(id);
    }
}
