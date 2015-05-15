
package com.mycompany.ltstriptermsmaven;


public class CurrentRailroadReturnDataFromGVP {
    String equipmentID,currentRailroad, tripID;
    
    CurrentRailroadReturnDataFromGVP(String equipmentID, String currentRailroad, String tripID){
        this.equipmentID = equipmentID;
        this.currentRailroad = currentRailroad;
        this.tripID = tripID;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getCurrentRailroad() {
        return currentRailroad;
    }

    public void setCurrentRailroad(String shipDateAndTime) {
        this.currentRailroad = shipDateAndTime;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}
