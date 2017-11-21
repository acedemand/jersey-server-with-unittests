package com.acedemand.resources;

import com.acedemand.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

@Path("customer")
public class CustomerResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        return createCustomer(id);

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Customer> findAllCustomers() {
        Collection<Customer> customerCollection = new ArrayList<Customer>() {{
            add(createCustomer(1));
            add(createCustomer(2));
        }};
        return customerCollection;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createCustomer(Customer customer){
        System.out.println("customer.getId() = " + customer.getId());
        return Response.ok().status(Response.Status.CREATED).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateCustomer(Customer customer){
        System.out.println("update customer = " + customer);
        return Response.ok().build();
    }
    
    
    private Customer createCustomer(int id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Pamir");
        customer.setLastName("Erdem");
        return customer;
    }
}
