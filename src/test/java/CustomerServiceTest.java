import com.acedemand.model.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.HttpStatus;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

public class CustomerServiceTest {

    private Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server(8080);
        server.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(getClass().getClassLoader());
        server.addHandler(webAppContext);
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testFindCustomerById() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/acedemand/customer/1");
        Customer customer = webTarget.request(MediaType.APPLICATION_JSON).get(Customer.class);
        Assert.assertEquals(customer.getId().intValue(), 1);
    }

    @Test
    public void testFindAllCustomers(){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/acedemand/customer");
        Collection<Customer> customerCollection = webTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<Collection<Customer>>(){} );
        Assert.assertEquals(customerCollection.size(), 2);
    }

    @Test
    public void testCreateCustomer(){

        Customer customer = createCustomer();

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/acedemand/customer");
        Response response=  webTarget.request().post(Entity.entity(customer,MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), HttpStatus.ORDINAL_201_Created);
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(5);
        customer.setName("Pamir");
        return customer;
    }

    @Test
    public void testUpdateCustomer(){
        Customer customer = createCustomer();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/acedemand/customer/5");
        Response response = webTarget.request().put(Entity.entity(customer,MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), HttpStatus.ORDINAL_200_OK);
    }
}
