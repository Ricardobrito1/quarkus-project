package com.example.controller;

import com.example.entity.Customer;
import com.example.service.CustomerService;

import javax.inject.Inject;
import javax.naming.directory.InvalidAttributesException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/costumer")
public class CustomerController {
    @Inject
    CustomerService customerService;

    @GET
    public Response getCustomers(
            @QueryParam("name") @DefaultValue("") String customerName

    ) {
        try {
            return Response.ok(customerService.getCustomer(customerName)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id){
        try {
            final var productByIdOpt = customerService.findCustomerById(id);
            if (productByIdOpt.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(productByIdOpt.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @POST
    @Transactional
    public Response create(Customer customer) {
        try {
            customerService.create(customer);
            return Response.noContent().build();
        } catch (Exception e) {
            if (e instanceof InvalidAttributesException) {
                return Response.status(Response.Status.CONFLICT).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response replace(@PathParam("id") long customerId, Customer customer) {
        try {
            return Response.ok(customerService.replace(customerId, customer)).build();
        } catch (Exception e) {
            if (e instanceof InvalidParameterException) {
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") long customerId) {
        var isDeleted = customerService.delete(customerId);
        if (!isDeleted) {
            return Response.notModified().build();
        }
        return Response.noContent().build();
    }

}
