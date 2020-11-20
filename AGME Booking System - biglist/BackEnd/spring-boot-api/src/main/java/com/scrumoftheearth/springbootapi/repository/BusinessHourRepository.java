package com.scrumoftheearth.springbootapi.repository;

import com.scrumoftheearth.springbootapi.model.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessHourRepository extends JpaRepository<BusinessHours, Long> {
    // Used to get all the Time for a bus
    List<BusinessHours> findAllBusTime(long busId);
}
