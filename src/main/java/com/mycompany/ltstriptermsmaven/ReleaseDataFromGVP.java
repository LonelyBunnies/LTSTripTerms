package com.mycompany.ltstriptermsmaven;

public class ReleaseDataFromGVP {
    String equipmentID,shipDateAndTime, tripID;
    
    ReleaseDataFromGVP(String equipmentID, String shipDateAndTime, String tripID){
        this.equipmentID = equipmentID;
        this.shipDateAndTime = shipDateAndTime;
        this.tripID = tripID;
    }
    ReleaseDataFromGVP(String[] returnedValues){
        this.equipmentID = returnedValues[0];
        this.shipDateAndTime = returnedValues[1];
        this.tripID = returnedValues[2];
    }
    

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getShipDateAndTime() {
        return shipDateAndTime;
    }

    public void setShipDateAndTime(String shipDateAndTime) {
        this.shipDateAndTime = shipDateAndTime;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}
