package com.cgg.lrs2020officerapp.model.applicationList;

public class Cluster {
    private String Cluster_id;
    private String Cluster_name;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCluster_id() {
        return Cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        Cluster_id = cluster_id;
    }

    public String getCluster_name() {
        return Cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        Cluster_name = cluster_name;
    }
}
