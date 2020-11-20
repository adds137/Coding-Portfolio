package com.scrumoftheearth.springbootapi.service;

import com.scrumoftheearth.springbootapi.model.BusinessHours;
import com.scrumoftheearth.springbootapi.repository.BusinessHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessHourService {
    @Autowired
    private BusinessHourRepository businessHourRepository;

    // for newBusinessHour() and updateBusinessHour()
    public BusinessHours saveOrUpdate(BusinessHours hours){
        return businessHourRepository.save(hours);
    }

    // for getById()
    public Optional<BusinessHours> getById(long id){
        return businessHourRepository.findById(id);
    }

    // for deleteHour()
    public void deleteById(long id){
        businessHourRepository.deleteById(id);
    }

    // for getByBusinessId()
    public List<BusinessHours> getTimeByBusId(long busid){
        return businessHourRepository.findAllBusTime(busid);
    }
}
