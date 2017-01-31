package com.epam.bean;

public class RegisterNewVehicleRequest extends Request {
	//todo
    String regNumb;
    boolean isCar;

    public String getRegNumb() {
        return regNumb;
    }

    public void setRegNumb(String regNumb) {
        this.regNumb = regNumb;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }
}