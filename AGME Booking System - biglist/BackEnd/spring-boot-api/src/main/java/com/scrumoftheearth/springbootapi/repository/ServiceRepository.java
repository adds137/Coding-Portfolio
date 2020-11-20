package com.scrumoftheearth.springbootapi.repository;

import com.scrumoftheearth.springbootapi.model.Service;
import com.scrumoftheearth.springbootapi.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Long> {

    @Override
    List<Service> findAll();
}
