package com.mycompany.ltstriptermsmaven;


public class GvpClmsRow {
    String equipID,currentDateAndTime,le,status,eventStationCity,EventStationState,rr,sightingtype,tripId;
    
    GvpClmsRow(){}
    
    GvpClmsRow(String[] inputArray){
        this.equipID = inputArray[0];
        this.currentDateAndTime = inputArray[1];
        this.le = inputArray[2];
        this.status = inputArray[3];
        this.eventStationCity = inputArray[4];
        this.EventStationState = inputArray[5];
        this.rr = inputArray[6];
        this.sightingtype = inputArray[21];
        this.tripId = inputArray[37];
    }

    public String getEquipID() {
        return equipID;
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID;
    }

    public String getCurrentDateAndTime() {
        return currentDateAndTime;
    }

    public void setCurrentDateAndTime(String currentDateAndTime) {
        this.currentDateAndTime = currentDateAndTime;
    }

    public String getLe() {
        return le;
    }

    public void setLe(String le) {
        this.le = le;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventStationCity() {
        return eventStationCity;
    }

    public void setEventStationCity(String eventStationCity) {
        this.eventStationCity = eventStationCity;
    }

    public String getEventStationState() {
        return EventStationState;
    }

    public void setEventStationState(String EventStationState) {
        this.EventStationState = EventStationState;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getSightingtype() {
        return sightingtype;
    }

    public void setSightingtype(String sightingtype) {
        this.sightingtype = sightingtype;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
    
    
}
