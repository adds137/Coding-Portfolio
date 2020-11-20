package com.scrumoftheearth.springbootapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "BusinessState Model")
@Table(name = "table_business_state")
public class BusinessState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="id",required = true,value = "1")
    // id of BusinessState
    private long id;

    @OneToOne
    @ApiModelProperty(name="id",required = true)
    // businessBstate
    private BusinessBState businessBState;

    public BusinessState() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BusinessBState getBusinessBState() {
        return businessBState;
    }

    public void setBusinessBState(BusinessBState businessBState) {
        this.businessBState = businessBState;
    }
}
