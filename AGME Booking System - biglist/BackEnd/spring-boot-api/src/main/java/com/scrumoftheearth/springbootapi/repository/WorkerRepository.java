package com.scrumoftheearth.springbootapi.repository;

import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends CrudRepository<Worker, Long> {

    @Override
    List<Worker> findAll();

    @Override
    Optional<Worker> findById(Long id);

    List<Worker> findbybusid(long busid);
}
