package com.mycompany.ltstriptermsmaven;

public class MmtsClmsRow {
    String vehicleID, statusDate,code,city,state,le,rrabrv,triptype;
    
    MmtsClmsRow(){}
    
    MmtsClmsRow(String[] inputArray){
        
        this.vehicleID = inputArray[0];
        this.statusDate = inputArray[2];
        this.code = inputArray[3];
        this.city = inputArray[4];
        this.state = inputArray[5];
        this.le = inputArray[7];
        this.rrabrv = inputArray[9];
        this.triptype = inputArray[18];
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLe() {
        return le;
    }

    public void setLe(String le) {
        this.le = le;
    }

    public String getRrabrv() {
        return rrabrv;
    }

    public void setRrabrv(String rrabrv) {
        this.rrabrv = rrabrv;
    }

    public String getTriptype() {
        return triptype;
    }

    public void setTriptype(String triptype) {
        this.triptype = triptype;
    }
    
}
