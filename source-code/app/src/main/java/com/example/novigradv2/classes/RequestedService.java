package com.example.novigradv2.classes;

import java.io.Serializable;
import java.util.List;

public class RequestedService implements Serializable {
    private Service service;
    private String uid;
    private String status;
    private Branch branch;
    private List<Entry> form;

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public RequestedService(List<Entry> list,Branch branch, Service service, String uid, String status) {
        this.form = list;
        this.branch = branch;
        this.service = service;
        this.uid = uid;
        this.status = status;
    }

    public RequestedService() {
    }

    public Service getService() {
        return service;
    }

    public List<Entry> getForm() {
        return form;
    }


    public void setService(Service service) {
        this.service = service;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
