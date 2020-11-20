package com.scrumoftheearth.springbootapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "table_service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Worker worker;
    @OneToOne
    @JoinColumn(name = "serviceSState_id")
    private ServiceSState serviceSState;
    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    public Service(){

    }

    public Service(Worker worker, ServiceSState serviceSState, @NotBlank String description) {
        this.worker = worker;
        this.serviceSState = serviceSState;
        this.description = description;
    }

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Worker getWorker(){
        return worker;
    }

    public void setWorker(Worker worker){
        this.worker = worker;
    }

    public ServiceSState getServiceSState(){
        return serviceSState;
    }

    public void setServiceSState(ServiceSState serviceSState){
        this.serviceSState = serviceSState;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Date getCreated_At(){
        return created_At;
    }

    public void setCreated_At(Date created_at){
        this.created_At = created_at;
    }

    public Date getUpdated_At(){
        return updated_At;
    }

    public void setUpdated_At(Date updated_At){
        this.updated_At = updated_At;
    }
}
