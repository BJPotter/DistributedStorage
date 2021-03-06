package com.example.server.model;


public class StorageFileInfoNode {
    private String address;
    private String scope;//0-20

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "StorageFileInfoNode{" +
                "address='" + address + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
