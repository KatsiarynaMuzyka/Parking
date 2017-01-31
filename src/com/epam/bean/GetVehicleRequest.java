package com.epam.bean;


public class GetVehicleRequest extends Request  {
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    private String regNumber;
}

