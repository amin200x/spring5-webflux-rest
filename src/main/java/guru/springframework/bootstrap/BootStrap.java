package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        customers();
        vendors();
        categories();
    }

    private void customers() {
        Customer customer1 = new Customer();
        customer1.setLastName("Saeed");
        customer1.setFirstName("Mahmoodi");

        Customer customer2 = new Customer();
        customer2.setLastName("Nasim");
        customer2.setFirstName("Namdar");

        Customer customer3 = new Customer();
        customer3.setLastName("Sasan");
        customer3.setFirstName("Alavi");

        customerRepository.save(customer1).block();
        customerRepository.save(customer2).block();
        customerRepository.save(customer3).block();

        System.out.println("Customers Loaded: " + customerRepository.count().block());
    }

    private void vendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor001");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor002");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Vendor003");

        vendorRepository.save(vendor1).block();
        vendorRepository.save(vendor2).block();
        vendorRepository.save(vendor3).block();

        System.out.println("Vendors Loaded: " + vendorRepository.count().block());


    }

    private void categories() {
        Category category1 = new Category();
        category1.setName("Category001");

        Category category2 = new Category();
        category2.setName("Category002");

        Category category3 = new Category();
        category3.setName("Category003");

        Category category4 = new Category();
        category4.setName("Category004");

        categoryRepository.save(category1).block();
        categoryRepository.save(category2).block();
        categoryRepository.save(category3).block();
        categoryRepository.save(category4).block();

        System.out.println("Categories Loaded: " + categoryRepository.count().block());

    }
}
