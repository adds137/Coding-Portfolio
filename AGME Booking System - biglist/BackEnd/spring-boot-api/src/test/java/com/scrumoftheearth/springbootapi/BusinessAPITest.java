package com.scrumoftheearth.springbootapi;

import com.scrumoftheearth.springbootapi.model.Business;
import com.scrumoftheearth.springbootapi.repository.BusinessRepository;
import com.scrumoftheearth.springbootapi.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BusinessAPITest {

    @Autowired
    private BusinessService businessService;

    @MockBean
    private BusinessRepository businessRepository;

    // set up test database with 3 Businesses
    @BeforeEach
    public void setup(){
        Business testcase1 = new Business(1,"bus1","blurb1","description1","address1","123");
        Business testcase2 = new Business(2,"bus2","blurb2","description2","address2","123");
        Business testcase3 = new Business(3,"bus3","blurb3","description3","address3","123");
        businessRepository.save(testcase1);
        businessRepository.save(testcase2);
        businessRepository.save(testcase3);
    }

    // Testing adding a new business
    @Test
    public void Test_add(){
        Business bus4 = new Business(4,"bus4","blurb4","description4","address4","123");
        businessService.saveOrUpdate(bus4);
        when(businessRepository.save(bus4)).thenReturn(bus4);
        assertEquals(bus4,businessService.saveOrUpdate(bus4));
    }

    // Testing updating an already added business
    @Test
    public void Test_update(){
        Business testcase1 = new Business(1,"bus1","blurb1","description1","address1","123");
        long toupdate = 1;
        testcase1.setBlurb("service1updated");
        when(businessRepository.save(testcase1)).thenReturn(testcase1);
        assertEquals(testcase1,businessService.saveOrUpdate(testcase1));
    }

    // Testing if the GetAll() gets all the setup() businesses
    @Test
    public void Test_GetAll(){
        Business testcase1 = new Business(1,"bus1","blurb1","description1","address1","123");
        Business testcase2 = new Business(2,"bus2","blurb2","description2","address2","123");
        Business testcase3 = new Business(3,"bus3","blurb3","description3","address3","123");
        List<Business> exepcted = new ArrayList<Business>();
        exepcted.add(testcase1);
        exepcted.add(testcase2);
        exepcted.add(testcase3);
        when(businessRepository.findAll()).thenReturn(exepcted);
        assertEquals(3,businessService.getAll().size());
    }

    // Testing to get the Business information based on their id
    @Test
    public void Test_GetById(){
        long idTofind = 2;
        Business testcase2 = new Business(2,"bus2","blurb2","description2","address2","123");
        when(businessRepository.findById(idTofind)).thenReturn(Optional.of(testcase2));
        assertEquals(Optional.of(testcase2),businessService.getById(idTofind));
    }

    // testing if the deleting of a business works as intended
    @Test
    public void Test_DeleteById(){
        Business testcase3 = new Business(3,"bus3","blurb3","description3","address3","123");
        businessService.deleteById(testcase3.getId());
        verify(businessRepository,times(1)).deleteById(testcase3.getId());
    }
}

