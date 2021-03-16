package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    public static final String NAME = "Cat1";
    public static final String ID = "0XX1";
    WebTestClient webTestClient;
        CategoryRepository categoryRepository;
        CategoryController categoryController;
    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(new Category(), new Category()));
        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);
        BDDMockito.given(categoryRepository.findById(BDDMockito.anyString()))
                .willReturn(Mono.just(category));

        webTestClient.get()
                .uri("/api/v1/categories/"+ID)
                .exchange()
                .expectBody(Category.class);

    }
}