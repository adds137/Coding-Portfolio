package com.scrumoftheearth.springbootapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "table_service_sstate")
public class ServiceSState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "serviceSState")
    private Service service;
    @OneToOne
    @JoinColumn(name = "service_state_id")
    private ServiceState serviceState;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    public void setId(Long id) {
        this.id = id;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setServiceState(ServiceState serviceState) {
        this.serviceState = serviceState;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }

    public Long getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public ServiceState getServiceState() {
        return serviceState;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public ServiceSState(){

    }
}
