package com.mycompany.ltstriptermsmaven;

import java.util.Comparator;

public class LTSTripTermsRow implements Comparator<LTSTripTermsRow> {
    String tripStatusCode,tripId,detailId,customerUploadName,application,vehicleId,
            shipDate,destination,triple,fleetId,ap,cp,
            release,currentStatusLe,dateAged,customerUploadId,movingAfterPlacement,currentStatusRailroad,route,destinationCarrier;
    
    LTSTripTermsRow(){}
    
    LTSTripTermsRow(String a,String b,String c,String d,String e,String f,
            String g,String h,String i,String j,String k,String l,
            String m,String n,String o,String p,String q,String r, String s, String t){
        
        this.tripStatusCode = a;
        this.tripId = b;
        this.detailId = c;
        this.customerUploadName = d;
        this.application = e;
        this.vehicleId = f;
        this.shipDate = g;
        this.destination = h;
        this.triple = i;
        this.fleetId = j;
        this.ap = k;
        this.cp = l;
        this.release = m;
        this.currentStatusLe = n;
        this.dateAged = o;
        this.customerUploadId = p;
        this.movingAfterPlacement = q;
        this.currentStatusRailroad = r;
        this.route = s;
        this.destinationCarrier = t;
        
    }
    
    LTSTripTermsRow(String[] inputArray){
        this.tripStatusCode = inputArray[0];
        this.tripId = inputArray[1];
        this.detailId = inputArray[2];
        this.customerUploadName = inputArray[3];
        this.application = inputArray[4];
        this.vehicleId = inputArray[5];
        this.shipDate = inputArray[6];
        this.destination = inputArray[7];
        this.triple = inputArray[8];
        this.fleetId = inputArray[9];
        this.ap = inputArray[10];
        this.cp = inputArray[11];
        this.release = inputArray[12];
        this.currentStatusLe = inputArray[13];
        this.dateAged = inputArray[14];
        this.customerUploadId = inputArray[15];
        this.movingAfterPlacement = inputArray[16];
        this.currentStatusRailroad = inputArray[17];
        this.route = inputArray[18];
        this.destinationCarrier = inputArray[19];
        
        
    }

    public String getTripStatusCode() {
        return tripStatusCode;
    }

    public String getTripId() {
        return tripId;
    }

    public String getDetailId() {
        return detailId;
    }

    public String getCustomerUploadName() {
        return customerUploadName;
    }

    public String getApplication() {
        return application;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getShipDate() {
        return shipDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getTriple() {
        return triple;
    }

    public String getFleetId() {
        return fleetId;
    }

    public String getAp() {
        return ap;
    }

    public String getCp() {
        return cp;
    }

    public String getRelease() {
        return release;
    }

    public String getCurrentStatusLe() {
        return currentStatusLe;
    }

    public String getDateAged() {
        return dateAged;
    }

    public String getCustomerUploadId() {
        return customerUploadId;
    }

    public String getMovingAfterPlacement() {
        return movingAfterPlacement;
    }

    public String getCurrentStatusRailroad() {
        return currentStatusRailroad;
    }
    
    public String getS_Column() {
        return route;
    }
    
    public String getDestinationCarrier() {
        return destinationCarrier;
    }

    

    public void setTripStatusCode(String tripStatusCode) {
        this.tripStatusCode = tripStatusCode;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public void setCustomerUploadName(String customerUploadName) {
        this.customerUploadName = customerUploadName;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTriple(String triple) {
        this.triple = triple;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setCurrentStatusLe(String currentStatusLe) {
        this.currentStatusLe = currentStatusLe;
    }

    public void setDateAged(String dateAged) {
        this.dateAged = dateAged;
    }

    public void setCustomerUploadId(String customerUploadId) {
        this.customerUploadId = customerUploadId;
    }

    public void setMovingAfterPlacement(String movingAfterPlacement) {
        this.movingAfterPlacement = movingAfterPlacement;
    }

    public void setCurrentStatusRailroad(String currentStatusRailroad) {
        this.currentStatusRailroad = currentStatusRailroad;
    }
    
    public void setS_Column(String s_Column) {
        this.route = s_Column;
    }
     
    public void setDestinationCarrier(String destinationCarrier) {
        this.destinationCarrier = destinationCarrier;
    }
    
    
    

    public String[] gvpReturnArrayContent(String timeToUseInColumnB,String statusToPrintInDColumn){
        String[] tokensOfColumnH = destination.split(",");
        String cityFromColumnH = tokensOfColumnH[0];
        String StateFromColumnH = tokensOfColumnH[1];
        
        String[] arrayOfContent;
        
        
        switch (timeToUseInColumnB) {
            case "l_Column":
                arrayOfContent = new String[] {vehicleId, cp,triple, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, destinationCarrier,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
            case "k_Column":
                arrayOfContent = new String[] {vehicleId, ap,triple, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, destinationCarrier,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
            case "m_Column":
                arrayOfContent = new String[] {vehicleId, release,triple, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, destinationCarrier,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
        }
        
        return arrayOfContent = new String[] {"Problem in gvpReturn",null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
        
    }
    
    public String[] csvReturnArrayContent(){
        String[] arrayOfContent = new String[] {tripStatusCode,tripId,detailId,
            customerUploadName,application,vehicleId,shipDate,destination,triple,fleetId,
            ap,cp,release,currentStatusLe,dateAged,customerUploadId,movingAfterPlacement,
            currentStatusRailroad,route,destinationCarrier,} ;
        
        return arrayOfContent;
        
    }
    
    public String[] mmtsReturnArrayContent(String timeToUseInColumnC,String statusToPrintInCodeColumn){
        
        String[] tokensOfColumnH = destination.split(",");
        String cityFromColumnH = tokensOfColumnH[0];
        String StateFromColumnH = tokensOfColumnH[1];
        String[] arrayOfContent;
        
        switch (timeToUseInColumnC) {
            case "l_Column":
            {
                    arrayOfContent = new String[]{vehicleId, null,cp,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,triple,
                    null,destinationCarrier,null,null,null,null,null,null,null,null,"H"};
                return arrayOfContent;
            }
            case "k_Column":
            {
                    arrayOfContent = new String[]{vehicleId, null,ap,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,triple,
                    null,destinationCarrier,null,null,null,null,null,null,null,null,"H"};
                return arrayOfContent;
            }
            case "m_Column":
            {
                    arrayOfContent = new String[]{vehicleId, null,release,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,triple,
                    null,destinationCarrier,null,null,null,null,null,null,null,null,"H"};
            return arrayOfContent;
            }
        }
        
         
        return arrayOfContent = new String[] {"Problem in mmtsReturn",null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
        
    }

    @Override
    public int compare(LTSTripTermsRow o1, LTSTripTermsRow o2) {
        
        String stringA = o1.getVehicleId();
        String stringB = o2.getVehicleId();
   
        return stringA.compareTo(stringB);
    }
}