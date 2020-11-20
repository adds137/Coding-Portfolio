package com.scrumoftheearth.springbootapi.controller;

import com.scrumoftheearth.springbootapi.model.BusinessHours;
import com.scrumoftheearth.springbootapi.service.BusinessHourService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/BusinessHours")
@ApiOperation(value = "/api/BusinessHours",tags = "Business Time Object Controller")
public class BusinessHoursController {

    private BusinessHourService businessHourService;

    @Autowired
    BusinessHoursController(BusinessHourService businessHourService) {
        this.businessHourService = businessHourService;
    }

    @PostMapping("")
    @ApiOperation(value = "Add a new Business Hours",response = Iterator.class,
            notes = "used to create a new Business Hours object and add it to the database")
    public ResponseEntity<?> newBusinessHour(@Valid @RequestBody BusinessHours businessHours){
        // business_id cant be less than zero
        if(businessHours.getBusiness_id() <= 0)
            return new ResponseEntity<String>("BusinessHours must be associated with valid business_id",HttpStatus.BAD_REQUEST);

        // day integer cant be less than 0 or more than 8
        if(businessHours.getDay() < 1 || businessHours.getDay() > 7)
            return new ResponseEntity<String>("day integer must be between 1 (Monday) to 7 (Sunday)",HttpStatus.BAD_REQUEST);
        BusinessHours toadd = businessHourService.saveOrUpdate(businessHours);
        return new ResponseEntity<BusinessHours>(businessHours, HttpStatus.CREATED);
    }

    @GetMapping("/findByBusId={busId}")
    @ApiOperation(value = "Getting all Business Hours for a business",response = Iterator.class,
            notes = "used to get all business hours that is associated by the given business_id and sort it by day, Ascending")
    public List<BusinessHours> getByBusinessId(@PathVariable long busId){
        return businessHourService.getTimeByBusId(busId);
    }

    @GetMapping("/findById={id}")
    @ApiOperation(value = "Getting a Business Hours by its id",response = Iterator.class,
            notes = "used to get a Business Hours row by its personal id")
    public BusinessHours getById(@PathVariable long id){
        Optional<BusinessHours> businessHour = businessHourService.getById(id);
        return businessHour.get();
    }

    @DeleteMapping("/deleteById={id}")
    @ApiOperation(value = "deleting a Business Hours by its id",response = Iterator.class,
            notes = "used to get a Business Hours row by its personal id and deleting it off the database")
    public void deleteHour(@PathVariable long id){
        businessHourService.deleteById(id);
    }

    @PutMapping("/update={id}")
    @ApiOperation(value = "updating a Business Hours by its id",response = Iterator.class,
            notes = "used to get a Business Hours row by its id and updating it, needs all unchanged variable in request")
    public ResponseEntity<?> updateBusinessHour(@Valid @RequestBody BusinessHours businessHours,@PathVariable long id){
        Optional<BusinessHours> toupdate = businessHourService.getById(id);

        // business_id cant be less than zero
        if(businessHours.getBusiness_id() <= 0)
            return new ResponseEntity<String>("BusinessHours must be associated with valid business_id",HttpStatus.BAD_REQUEST);

        // day integer cant be less than 0 or more than 8
        if(businessHours.getDay() < 1 || businessHours.getDay() > 7)
            return new ResponseEntity<String>("day integer must be between 1 (Monday) to 7 (Sunday)",HttpStatus.BAD_REQUEST);

        //if there is no business associated with the given ID
        if(!toupdate.isPresent())
            return new ResponseEntity<String>("Business hour object with given ID doesnt exist",HttpStatus.BAD_REQUEST);
        businessHours.setId(id);
        businessHourService.saveOrUpdate(businessHours);
        return ResponseEntity.noContent().build();
    }
}
