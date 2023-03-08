package com.example.repository;

import com.example.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.text.WordUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public List<Customer> findByName(String productName) {
        return this.list("name", WordUtils.capitalize(productName));
    }


    public void persist(Customer customer) {
        var productName = WordUtils.capitalize(customer.getName());
        var email = WordUtils.capitalize(customer.getEmail());

        customer.setName(productName);
        customer.setEmail(email);

        PanacheRepository.super.persist(customer);
    }
    public Optional<Customer> update(Customer customer) {
        final var id = customer.getId();
        var savedOpt = this.findByIdOptional(id);
        if (savedOpt.isEmpty()) {
            return Optional.empty();
        }

        var saved = savedOpt.get();
        saved.setName(customer.getName());
        saved.setEmail(customer.getEmail());
        saved.setAge(customer.getAge());

        return Optional.of(saved);
    }
}
