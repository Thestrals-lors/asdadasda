package com.bl4nk.psutracerstudy.admin_model;

public class AddressModel {

    String userAddress;

    public AddressModel() {
    }

    public AddressModel(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}

