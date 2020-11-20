package com.scrumoftheearth.springbootapi.controller;

import com.scrumoftheearth.springbootapi.model.Service;
import com.scrumoftheearth.springbootapi.model.Worker;
import com.scrumoftheearth.springbootapi.service.ServiceService;
import com.scrumoftheearth.springbootapi.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
public class ServiceController {


    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> READWorker(@PathVariable("id") Long id) throws Throwable {
        Service service = serviceService.getById(id);
        return new ResponseEntity<Service>(service, HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> CREATEService(@Valid @RequestBody Service service, BindingResult bindingResult){

        Optional<ResponseEntity<Map<String, Map<String, String>>>> fieldErrors = getFieldErrors(bindingResult);

        if(fieldErrors.isPresent()){
            return fieldErrors.get();
        }

        service = serviceService.saveService(service);
        return new ResponseEntity<Service>(service, HttpStatus.CREATED);
    }

    @PutMapping("/update={id}")
    public ResponseEntity<?> UPDATEWorker(@PathVariable("id") Long id, @Valid @RequestBody Service updatedService,
                                          BindingResult bindingResult) throws Throwable {

        //Optional variable to hold service found by Id, need an optional version to check if service exists
        Optional<Service> service = Optional.ofNullable(serviceService.getById(id));

        //Optional variable to hold service which will be updated
        Service serviceToChange = serviceService.getById(id);

        Optional<ResponseEntity<Map<String, Map<String, String>>>> fieldErrors = getFieldErrors(bindingResult);

        //If no worker was found by the Id provided then return an error that this worker to update does not exist
        if(!service.isPresent()) {
            return new ResponseEntity<String>("Service doesnt exist", HttpStatus.BAD_REQUEST);
        }

        if(fieldErrors.isPresent()) {
            return fieldErrors.get();
        }

        //Updates service with relative information to the updated variable specifications and values
        serviceToChange = serviceService.updateService(serviceToChange, updatedService);

        return new ResponseEntity<>(serviceToChange, HttpStatus.OK);

    }

    private Optional<ResponseEntity<Map<String, Map<String, String>>>> getFieldErrors(BindingResult bindingResult) {
        /* Error Handling Based on article by Baeldung.
            Baeldung 2020, Validation in Spring Boot, Baeldung, Viewed on 15/08/2000
            <https://www.baeldung.com/spring-boot-bean-validation>
         */

        Map<String, String> fieldErrors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                fieldErrors.put(((FieldError) error).getField(), error.getDefaultMessage());
            });
        }

        if (!fieldErrors.isEmpty()) {
            Map<String, Map<String, String>> jsonResponse = new HashMap<>();
            jsonResponse.put("errors", fieldErrors);
            return Optional.of(new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }
}
