    package com.scrumoftheearth.springbootapi.service;

import com.scrumoftheearth.springbootapi.model.Business;
import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.model.Worker;
import com.scrumoftheearth.springbootapi.model.WorkerWState;
import com.scrumoftheearth.springbootapi.repository.BusinessRepository;
import com.scrumoftheearth.springbootapi.repository.UserRepository;
import com.scrumoftheearth.springbootapi.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WorkerService {
    private UserRepository userRepository;
    private BusinessRepository businessRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, UserRepository userRepository, BusinessRepository businessRepository){
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.businessRepository = businessRepository;
    }

    public Worker getById(long Id) throws Throwable {
        Optional<Worker> result = workerRepository.findById(Id);
        return result.orElseThrow(() -> {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource " + Id + " Not Found!");
        });
    }

    public List<Worker> getAllWorkers(){
        List<Worker> workers = workerRepository.findAll();
        return workers;
    }

    public Worker saveWorker(WorkerWState workerWState, Long userId, @NotBlank String description,
                             List<String> services, Long businessId,
                             List<java.sql.Time> availableStartTimes, List<java.sql.Time> availableEndTimes,
                             List<java.sql.Timestamp> shiftStartTimes, List<java.sql.Timestamp> shiftEndTimes) {

        //To add appropriate starting days, 14 implies 14th of september which is on a Monday (good starting point for default date value)
        int day = 14;

        //Get user based on ID passed through the json request
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist: " + userId));

        if(availableStartTimes.isEmpty() || availableEndTimes.isEmpty()) {

            //Create 7 new start Times and 7 new end times for workers availability one for each day of the week
            for (int i = 0; i < 7; i++) {
                availableStartTimes.add(java.sql.Time.valueOf("00:00:00"));
            }
            for (int i = 0; i < 7; i++) {
                availableEndTimes.add(java.sql.Time.valueOf("00:00:00"));
            }
        }

        if(shiftStartTimes.isEmpty() || shiftEndTimes.isEmpty()) {
            for (int i = 0; i < 7; i++) {
                shiftStartTimes.add(java.sql.Timestamp.valueOf("2020-09-" + day + " 00:00:00"));
                day++;
            }
            day = 14;
            for (int i = 0; i < 7; i++) {
                shiftEndTimes.add(java.sql.Timestamp.valueOf("2020-09-" + day + " 00:00:00"));
                day++;
            }
        }

        Business business = businessRepository.findById(businessId).orElseThrow(() ->
                new RuntimeException("User does not exist: " + userId));

        return workerRepository.save(new Worker(user, services, description, business, availableStartTimes,
                                                availableEndTimes, shiftStartTimes, shiftEndTimes));
    }

    public Worker updateWorker(Worker updatedWorker){
        return workerRepository.save(updatedWorker);
    }

    public Worker addService(String service, Worker worker){
        worker.addService(service);
        return workerRepository.save(worker);
    }

    public List<Worker> getWorkerByBusId(long busid){
        return workerRepository.findbybusid(busid);
    }
}
