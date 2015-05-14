
package com.mycompany.ltstriptermsmaven;

public class DataFromGVP {
    String equipmentID,shipDateAndTime, tripID;
    
    DataFromGVP(String equipmentID, String shipDateAndTime, String tripID){
        this.equipmentID = equipmentID;
        this.shipDateAndTime = shipDateAndTime;
        this.tripID = tripID;
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
