package com.scrumoftheearth.springbootapi;

import com.scrumoftheearth.springbootapi.model.Service;
import com.scrumoftheearth.springbootapi.model.Worker;
import com.scrumoftheearth.springbootapi.repository.ServiceRepository;
import com.scrumoftheearth.springbootapi.repository.WorkerRepository;
import com.scrumoftheearth.springbootapi.service.ServiceService;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceAPITests {

    @Autowired
    private ServiceService serviceService;

    @MockBean
    private ServiceRepository serviceRepository;

    @Test
    public void getWorkerTest(){
        Service testCase1 = new Service(null, null, "Test Service 1");
        Service testCase2 = new Service(null, null, "Test Service 2");
        Service testCase3 = new Service(null, null, "Test Service 3");
        List<Service> testRepo = new ArrayList<Service>();
        testRepo.add(testCase1);
        testRepo.add(testCase2);
        testRepo.add(testCase3);

        //Test code to mock a stream (Using a reference for if needed in the future)
        //when(repository.findAll()).thenReturn(Stream.of(new Service(null, null, "Test Service")).collect(Collectors.toList()));

        when(serviceRepository.findAll()).thenReturn(testRepo);
        assertEquals(3, serviceService.getAllServices().size());
    }

    @Test
    public void createWorkerTest(){
        Service service = new Service(null, null, "TEST DUMMY SERVICE INSTANCE");
        when(serviceRepository.save(service)).thenReturn(service);
        assertEquals(service, serviceService.saveService(service));
    }

    @Test
    public void getWorkerByIdTest() throws Throwable {
        Service service = new Service(null, null, "TEST DUMMY SERVICE INSTANCE");
        service.setId((long)1);
        when(serviceRepository.findById((long)1)).thenReturn(java.util.Optional.of(service));
        assertEquals(service, serviceService.getById((long)1));
    }
}