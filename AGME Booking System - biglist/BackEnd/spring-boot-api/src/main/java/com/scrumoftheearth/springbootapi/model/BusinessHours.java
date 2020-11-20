package com.scrumoftheearth.springbootapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

// POJO for businessHours
@Entity
@NamedQueries({
        // custom query for getting all business time
        @NamedQuery(name = "BusinessHours.findAllBusTime",
                query = "SELECT h FROM BusinessHours h WHERE h.business_id = ?1 ORDER BY h.day")
})
@ApiModel(description = "BusinessTime Model")
@Table(name = "table_business_hours")
public class BusinessHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id",required = true,value = "2")
    // business id
    private long id;

    @NotNull(message = "BusinessHours must be associated with valid business_id")
    @ApiModelProperty(name = "business_id",required = true,value = "1")
    // foreign key to business table,
    private long business_id;

    @NotNull(message = "Day integer is required")
    @ApiModelProperty(name = "day",required = true,value = "5")
    // int to save day (monday = 1, tuesday = 2 etc)
    private int day;

    @JsonFormat(pattern = ("HH:mm"))
    @ApiModelProperty(name="openingTime",value = "09:00:00")
    // opening time
    private Timestamp openingTime;

    @JsonFormat(pattern = ("HH:mm"))
    @ApiModelProperty(name = "closingTime",value = "17:00:00")
    // closing time
    private Timestamp closingTime;

    // blank constructor for production uses
    protected BusinessHours(){
    }

    // constructor for junit testing
    public BusinessHours(long id,long business_id,int day,Timestamp openingTime,Timestamp closingTime){
        this.id = id;
        this.business_id = business_id;
        this.day = day;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // getter and setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(long business_id) {
        this.business_id = business_id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Timestamp getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Timestamp openingTime) {
        this.openingTime = openingTime;
    }

    public Timestamp getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Timestamp closingTime) {
        this.closingTime = closingTime;
    }
}
