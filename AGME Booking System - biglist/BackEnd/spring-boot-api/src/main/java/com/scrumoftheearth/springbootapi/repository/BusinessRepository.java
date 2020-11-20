package com.scrumoftheearth.springbootapi.repository;

import com.scrumoftheearth.springbootapi.model.Business;
import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
