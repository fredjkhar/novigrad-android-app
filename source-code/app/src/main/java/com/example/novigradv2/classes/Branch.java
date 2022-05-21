package com.example.novigradv2.classes;

import java.io.Serializable;
import java.util.List;

public class Branch implements Serializable {
    private String id;
    private String city;
    private String province;
    private String address;
    private String codeZIP;
    private List<WorkHours> workHoursList;
    private List<Service> servicesList;

    private int[] ratings;

    public Branch(String id, String city, String province, String address, String codeZIP, List<WorkHours> workHoursList, List<Service> servicesList) {
        this.id = id;
        this.city = city;
        this.province = province;
        this.address = address;
        this.codeZIP = codeZIP;
        this.workHoursList = workHoursList;
        this.servicesList = servicesList;
        this.ratings = new int[100];
    }

    public Branch() {
    }

    public int[] getRatings() {
        return ratings;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getAddress() {
        return address;
    }

    public String getCodeZIP() {
        return codeZIP;
    }

    public List<WorkHours> getWorkHoursList() {
        return workHoursList;
    }

    public List<Service> getServicesList() {
        return servicesList;
    }
}
