package com.scrumoftheearth.springbootapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        // custom query for getting all workers tied by a busID
        @NamedQuery(name = "Worker.findbybusid",
        query = "SELECT w FROM Worker w WHERE w.busId = ?1")
})
@Table(name = "table_worker")
@ApiModel(description = "Worker Model")
public class Worker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="id",required = true,value = "1")
    //Identification number of worker
    private Long id;
    @OneToOne
    @MapsId
    @ApiModelProperty(name="User", required = false)
    //The user account which this worker belongs to
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="business_id")
    @ApiModelProperty(name="business")
    //The business that this worker belongs to
    private Business business;
    @OneToOne
    @JoinColumn(name = "workerWState_id")
    @ApiModelProperty(name="WorkerWState")
    //State of this worker, checks if the worker is deactivated
    private WorkerWState workerWState;
    @ElementCollection
    @ApiModelProperty(name="Services", required = false)
    //The list of services that this worker will provide
    private List<String> services;
    @NotBlank
    @ApiModelProperty(name="Description", required = false)
    //Descriptive information that is important enough to be stored in the worker
    private String description;
    @ElementCollection
    @JsonFormat(pattern = ("HH:mm:ss"))
    @ApiModelProperty(name="availableStartTimes")
    //The sql.Time values for each day of the week that the worker is available to start
    private List<java.sql.Time> availableStartTimes;
    @ElementCollection
    @JsonFormat(pattern = ("HH:mm:ss"))
    @ApiModelProperty(name="availableEndTimes")
    //The sql.Time values for each day of the week that the worker is available to end
    private List<java.sql.Time> availableEndTimes;
    @ElementCollection
    @JsonFormat(pattern = ("yyyy-MM-dd'T'HH:mm:ss"))
    @ApiModelProperty(name="shiftStartTimes")
    //The sql.TimeStamp which keeps shift start times for a worker
    private List<java.sql.Timestamp> shiftStartTimes;
    @ElementCollection
    @JsonFormat(pattern = ("yyyy-MM-dd'T'HH:mm:ss"))
    @ApiModelProperty(name="shiftEndTimes")
    //The sql.TimeStamp which keeps shift end times for a worker
    private List<java.sql.Timestamp> shiftEndTimes;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(name="createdAt")
    //The Date that the worker was created
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(name="updatedAt")
    //The Date that the worker was last updated
    private Date updatedAt;
    //Holds business id that the worker belongs to
    @ApiModelProperty(name="busId")
    private long busId;

    public Worker(){
    }

    //Worker constructor for creating worker object
    public Worker(User user, List<String> services, @NotBlank String description, Business business,
                  List<java.sql.Time> availableStartTimes, List<java.sql.Time> availableEndTimes,
                  List<java.sql.Timestamp> shiftStartTimes, List<java.sql.Timestamp> shiftEndTimes) {
        //this.workerWState = workerWState;
        this.user = user;
        this.business = business;
        this.services = services;
        this.description = description;
        this.availableStartTimes = availableStartTimes;
        this.availableEndTimes = availableEndTimes;
        this.shiftStartTimes = shiftStartTimes;
        this.shiftEndTimes = shiftEndTimes;
        this.busId = business.getId();
    }
    //Gets Identification number of Worker
    public Long getId(){
        return id;
    }

    //Sets Identification number of Worker
    public void setId(Long id){
        this.id = id;
    }

    //Gets description that belongs to worker
    public String getDescription(){
        return description;
    }

    //Sets description that belongs to worker
    public void setDescription(String description){
        this.description = description;
    }

    //Gets the date value of when the worker was created
    public Date getCreatedAt(){
        return createdAt;
    }

    //Sets the date value of when the worker was created
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    //Gets the date value of when the worker was last updated
    public Date getUpdatedAt(){
        return updatedAt;
    }

    //Sets the date value of when the worker was last updated
    public void setUpdatedAt(Date updatedAt){
        this.updatedAt = updatedAt;
    }

    @PrePersist
    //Automatically creates a createdAt date value for the worker when created
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    //Automatically creates a updatedAt date value for the worker when updated
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    //Gets the state of a worker if it is deactivated or not
    public WorkerWState getWorkerWState() {
        return workerWState;
    }

    //Sets the state of a worker to deactivated or activated
    public void setWorkerWState(WorkerWState workerWState) {
        this.workerWState = workerWState;
    }

    //Gets the list of services provided by the worker
    public List<String> getServices() {
        return services;
    }

    //Sets the list of services provided by the worker
    public void setServices(List<String> services) {
        this.services = services;
    }

    //Adds a service to the list of services for a worker
    public void addService(String service){
        this.services.add(service);
    }

    //Gets the user that is associated with this worker
    public User getUser() {
        return user;
    }

    //Sets the user that is associated with this worker
    public void setUser(User user) {
        this.user = user;
    }

    //Gets the business that this worker belongs to
    public Business getBusiness() {
        return business;
    }

    //Sets the business that this worker belongs to
    public void setBusiness (Business business) {
        this.business = business;
    }

    //Gets a list of all available end times for each day of the week of worker
    public List<java.sql.Time> getAvailableEndTimes() {
        return availableEndTimes;
    }

    //Sets a list of all available end Times for each day of the week for a worker
    public void setAvailableEndTimes(List<java.sql.Time> availableEndTimes) {
        this.availableEndTimes = availableEndTimes;
    }

    //Gets a list of all available start times for each day of the week of worker
    public List<java.sql.Time> getAvailableStartTimes() {
        return availableStartTimes;
    }

    //Sets a list of all available start Times for each day of the week for a worker
    public void setAvailableStartTimes(List<java.sql.Time> availableStartTimes) {
        this.availableStartTimes = availableStartTimes;
    }

    //Gets a list of shift start dates and times for worker
    public List<java.sql.Timestamp> getShiftStartTimes() {
        return shiftStartTimes;
    }

    //Sets a list of shift start dates and times for worker
    public void setShiftStartTimes(List<java.sql.Timestamp> shiftStartTimes) {
        this.shiftStartTimes = shiftStartTimes;
    }

    //Gets a list of end dates and times for a worker
    public List<java.sql.Timestamp> getShiftEndTimes() {
        return shiftEndTimes;
    }

    //Sets a list of end dates and times for a worker
    public void setShiftEndTimes(List<java.sql.Timestamp> shiftEndTimes) {
        this.shiftEndTimes = shiftEndTimes;
    }

    //Gets the id for the business that the worker belongs to
    public long getBusId() {
        return busId;
    }

    //Sets the id for the business that the worker belongs to
    public void setBusId(long busId) {
        this.busId = busId;
    }

}
