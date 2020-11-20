package com.scrumoftheearth.springbootapi.model;

import javax.persistence.*;

@Entity
@Table(name = "table_worker_wstate")
public class WorkerWState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Connect relationship workerWState to worker, owned by worker
    @OneToOne(mappedBy = "workerWState")
    private Worker worker;
    @OneToOne
    //Connect relationship worker_state to workerWState, owned by workerWState
    @JoinColumn(name = "worker_state_id")
    private WorkerState workerState;

    public WorkerWState() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public WorkerState getWorkerState() {
        return workerState;
    }

    public void setWorkerState(WorkerState workerState) {
        this.workerState = workerState;
    }

}
