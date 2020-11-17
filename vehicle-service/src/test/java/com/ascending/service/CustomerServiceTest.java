package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.Customer;
import com.ascending.model.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class CustomerServiceTest {
    private Logger logger = LoggerFactory.getLogger((CustomerServiceTest.class));

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    private Customer testCustomer;
    private Order testOrder;

    @Before
    public void setup(){
        testOrder = orderService.getOrderById(1);
        testCustomer = new Customer();
        testCustomer.setName("testCustomer");
        customerService.saveCustomer(testCustomer,testOrder);
    }
    @After
    public void cleanUp(){customerService.deleteCustomerByName(testCustomer.getName());}

    @Test
    public void saveCustomerTest(){
        Customer customer = testCustomer;
        customerService.saveCustomer(customer,testOrder);
        Assert.assertEquals("customer objects comparison",testCustomer,customer);
    }

    @Test
    public void deleteCustomerTest(){
        boolean ifDelete = customerService.deleteCustomer(testCustomer);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateCustomerTest(){
        testCustomer.setName("updateTest");
        boolean ifUpdate = customerService.updateCustomer(testCustomer,testOrder);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllCustomersTest(){
        List<Customer> customers = customerService.getAllCustomers();
        Assert.assertEquals("customer objects amount comparison",5,customers.size());
    }

    @Test
    public void deleteCustomerByNameTest(){
        boolean ifDelete = customerService.deleteCustomerByName(testCustomer.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getCustomerByIdTest(){
        long id = testCustomer.getId();
        Customer customer = customerService.getCustomerById(id);
        Assert.assertEquals("customer ids comparison",testCustomer.getId(),customer.getId());
    }

    @Test
    public void getCustomerByNameTest(){
        String name = testCustomer.getName();
        Customer customer = customerService.getCustomerByName(name);
        Assert.assertEquals("customer names comparison",testCustomer.getName(),customer.getName());
    }
}
