package com.scrumoftheearth.springbootapi.service;

import com.scrumoftheearth.springbootapi.model.Business;
import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.model.Worker;
import com.scrumoftheearth.springbootapi.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    // for newBusiness() and updateBusiness()
    public Business saveOrUpdate(Business bus){
        return businessRepository.save(bus);
    }

    // for getAllBusiness()
    public List<Business> getAll(){
        return businessRepository.findAll();
    }

    // for getById()
    public Optional<Business> getById(long id){
        return businessRepository.findById(id);
    }

    // for deleteBusiness()
    public void deleteById(long id){
        businessRepository.deleteById(id);
    }

}
