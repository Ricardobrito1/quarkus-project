package com.example.service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.directory.InvalidAttributesException;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    public List<Customer> getCustomer(String customerName) {

         if (StringUtils.isNotBlank(customerName)) {
            return customerRepository.findByName(customerName);
        }

        return customerRepository.listAll();
    }

    public Optional<Customer> findCustomerById(long id) {
        return customerRepository.findByIdOptional(id);
    }

    @Transactional
    public void create(Customer customer) throws InvalidAttributesException {
        if (customer.getId() != null) {
            throw new InvalidAttributesException("Id must not be filled");
        }
        Validate.notNull(customer, "Customer can not be null");
        Validate.notBlank(customer.getName(), "Name can not be empty");
        Validate.notNull(customer.getAge(), "Age can not be empty");
        Validate.notBlank(customer.getLastName(), "Last name can not be empty");

        customerRepository.persist(customer);
    }
    @Transactional
    public Customer replace(long customerId, Customer customer) {
        customer.setId(customerId);
        return customerRepository.update(customer).orElseThrow(() -> new InvalidParameterException("Product not found"));
    }

    @Transactional
    public boolean delete(long customerId) {
        return customerRepository.deleteById(customerId);
    }
}


