package com.scrumoftheearth.springbootapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scrumoftheearth.springbootapi.model.Service;
import com.scrumoftheearth.springbootapi.model.Worker;
import com.scrumoftheearth.springbootapi.model.WorkerWState;
import com.scrumoftheearth.springbootapi.service.WorkerService;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.time.LocalTime;
import java.util.*;


@RestController
@RequestMapping("/api/worker")
@ApiOperation(value = "/api/worker",tags = "Worker Object Controller")
public class WorkerController {

    private WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> READWorker(@PathVariable("id") Long id) throws Throwable {
        Worker worker = workerService.getById(id);
        return new ResponseEntity<Worker>(worker, HttpStatus.FOUND);
    }

    @PostMapping("")
    @ApiOperation(value = "Add a new Worker",response = Iterator.class,
            notes = "used to create a new Worker object and add it to the database")
    public ResponseEntity<?> CREATEWorker(@Valid @RequestBody Worker worker, BindingResult bindingResult){

        Optional<ResponseEntity<Map<String, Map<String, String>>>> fieldErrors = getFieldErrors(bindingResult);

        if(fieldErrors.isPresent()){
            return fieldErrors.get();
        }

        System.out.println(worker.getShiftEndTimes());
        worker = workerService.saveWorker(worker.getWorkerWState(), worker.getUser().getId(), worker.getDescription(),
                                          worker.getServices(), worker.getBusiness().getId(), worker.getAvailableStartTimes(),
                                          worker.getAvailableEndTimes(), worker.getShiftStartTimes(),
                                          worker.getShiftEndTimes());

        return new ResponseEntity<Worker>(worker, HttpStatus.CREATED);
    }

    @GetMapping("")
    @ApiOperation(value = "Getting a List of all Workers",response = Iterable.class,
            notes = "used get all information about every worker in the database")
    public List<Worker> getAllWorkers(){
        return workerService.getAllWorkers();
    }

    @PutMapping("/update={id}")
    @ApiOperation(value = "Updating a Worker with the given id",response = Iterable.class,
            notes = "used to update worker information that has the given id, needs all unchanged variable in request")
    public ResponseEntity<?> UPDATEWorker(@Valid @RequestBody Worker updatedWorker,
                                          BindingResult bindingResult) throws Throwable {

        Worker worker = workerService.updateWorker(updatedWorker);

        return new ResponseEntity<>(updatedWorker, HttpStatus.OK);

    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    @ApiOperation(value = "Patching a Worker with the given id",response = Iterable.class,
            notes = "used to patch worker information that has the given id, needs operation value, variable to change name, " +
                    "and new value for that updated variable")
    public ResponseEntity<Worker> UPDATEWorker(@PathVariable("id") Long id,
                                               @RequestBody JsonPatch patch) throws Throwable {
        try {
            Worker worker = workerService.getById(id);
            Worker workerPatched = applyPatchToWorker(patch, worker);
            workerService.updateWorker(workerPatched);
            return ResponseEntity.ok(workerPatched);
        } catch (JsonPatchException | JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Patch functionality",response = Iterable.class,
            notes = "This is the main functionality for the patch method")
    private Worker applyPatchToWorker(
            JsonPatch patch, Worker targetWorker) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetWorker, JsonNode.class));
        System.out.println(patched.toPrettyString());
        return objectMapper.treeToValue(patched, Worker.class);
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

    @GetMapping("/findWorkerbyBusId={id}")
    @ApiOperation(value = "Getting all the worker associated with the given Business id",response = Iterator.class,
            notes = "used to get a List<Worker> that has a busId variable the same as the given id")
    public List<Worker> getbybusId(@PathVariable long id){
        return workerService.getWorkerByBusId(id);
    }
}
