package com.scrumoftheearth.springbootapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

// Store the state of one business
@Entity
@ApiModel(description = "BusinessBState Model")
@Table(name = "table_business_bstate")
public class BusinessBState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="id",required = true,value = "1")
    // Business state id
    private long id;

    @OneToOne
    @ApiModelProperty(name="business",required = true)
    // Business this state belongs to
    private Business business;

    @OneToOne
    @JoinColumn(name = "business_state_id")
    @ApiModelProperty(name="businessState",required = true)
    // business state
    private BusinessState BusinessState;

    public BusinessBState() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public BusinessState getBState() {
        return BusinessState;
    }

    public void setBState(BusinessState BusinessState) {
        this.BusinessState = BusinessState;
    }

}
