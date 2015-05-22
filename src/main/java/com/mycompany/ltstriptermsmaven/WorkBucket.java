package com.mycompany.ltstriptermsmaven;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.LocalDate;

//import java.util.regex.Pattern;
//import java.util.concurrent.TimeUnit;
//import org.junit.*;
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class WorkBucket {
    
    public ArrayList<LTSTripTermsRow> LTSTripTermsSheet;
    public String repeatClmsFilepath;
    
    public WorkBucket(String tripTermsFilePath, String repeatClmsFilepath)throws FileNotFoundException, IOException{
        this.repeatClmsFilepath = repeatClmsFilepath;
        LTSTripTermsSheet = new ArrayList<>();
        readCSV(tripTermsFilePath);
        filter_DuplicateVehicleID();
        filter_CurrentTripStatus();
        stripFirstDigitFromTripId();
        sortByVehicleID();
        
    }
    
    public void readCSV(String csvFilePath) throws FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ',' , '"' , 1);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                LTSTripTermsSheet.add(new LTSTripTermsRow(nextLine));
            }
        }
    }
    
    public void deriveCpTimeFromApTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getAp();
            Date date = null;
            try {
                date = formatter.parse(dateInCSVRow);
            } catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calenderOfTimeInCSVRow = Calendar.getInstance();
            calenderOfTimeInCSVRow.setTime(date);
            calenderOfTimeInCSVRow.add(Calendar.MINUTE, -1);
            Date dateToReturn = calenderOfTimeInCSVRow.getTime();
            String outputDate = formatter.format(dateToReturn);
            currentRow.setCp(outputDate);
        }
    }
    public void deriveApTimeFromCpTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getCp();
            Date date = null;
            try {
                date = formatter.parse(dateInCSVRow);
            } catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calenderOfTimeInCSVRow = Calendar.getInstance();
            calenderOfTimeInCSVRow.setTime(date);
            calenderOfTimeInCSVRow.add(Calendar.MINUTE, 1);
            Date dateToReturn = calenderOfTimeInCSVRow.getTime();
            String outputDate = formatter.format(dateToReturn);
            currentRow.setAp(outputDate);
        }
    }
    public void deriveApTimeFromReleaseTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getRelease();
            Date date = null;
            try {
                date = formatter.parse(dateInCSVRow);
            } catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calenderOfTimeInCSVRow = Calendar.getInstance();
            calenderOfTimeInCSVRow.setTime(date);
            calenderOfTimeInCSVRow.add(Calendar.MINUTE, -5);
            Date dateToReturn = calenderOfTimeInCSVRow.getTime();
            String outputDate = formatter.format(dateToReturn);
            currentRow.setAp(outputDate);
        }
    }
    
    public void filter_DuplicateVehicleID(){
        // Hashsets prevent duplicates from being added and so are used for 
        // filtering duplicates
        HashSet hs = new HashSet();
        // The contents of LTSTripTermsSheet are copied into the hashset
        hs.addAll(LTSTripTermsSheet);
        // The LTSTripTermsSheet is cleared.
        LTSTripTermsSheet.clear();
        // The duplicate free contents of the hashset are added to the LTSTripTermsSheet.
        LTSTripTermsSheet.addAll(hs);
    }
    public void filter_CurrentTripStatus(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getTripStatusCode().equals("Current")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromAP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getAp().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromCP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getCp().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromRelease(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getRelease().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRelease(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getRelease().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromCP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getCp().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromAP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getAp().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_BlanksFromDestinationCarrier(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getDestinationCarrier().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRailroad(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getDestinationCarrier().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_Mmts(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getApplication().equals("MMTS")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_Gvp(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getApplication().equals("GVP")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    
    public void stripFirstDigitFromTripId(){
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            String newTripID = currentRow.getTripId().substring(1);
            currentRow.setTripId(newTripID);
        }
    }
    
    public void rmPasses_SequentialShipdateCpApReleaseDateAged(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentCSVRow= LTSTripTermsSheet.get(i);
            try{
                Date shipDate = formatter.parse(currentCSVRow.getShipDate());
                Date cpDate = formatter.parse(currentCSVRow.getCp());
                Date apDate = formatter.parse(currentCSVRow.getAp());
                Date releaseDate = formatter.parse(currentCSVRow.getRelease());
                Date dateAged = formatter.parse(currentCSVRow.getDateAged());
            
            if (shipDate.before(cpDate)) {
                if (cpDate.before(apDate)) {
                    if (apDate.before(releaseDate)) {
                        if (releaseDate.before(dateAged)) {
                            LTSTripTermsSheet.remove(i);
                            --i;
                        }
                    }
                }
            }
            }catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void rmFails_SequentialShipdateCpApReleaseDateAged(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentCSVRow= LTSTripTermsSheet.get(i);
            try{
                Date shipDate = formatter.parse(currentCSVRow.getShipDate());
                Date cpDate = formatter.parse(currentCSVRow.getCp());
                Date apDate = formatter.parse(currentCSVRow.getAp());
                Date releaseDate = formatter.parse(currentCSVRow.getRelease());
                Date dateAged = formatter.parse(currentCSVRow.getDateAged());
                
                if (!shipDate.before(cpDate)){ 
                    LTSTripTermsSheet.remove(i);
                    --i;
                }
                else if (!cpDate.before(apDate)){ 
                    LTSTripTermsSheet.remove(i);
                    --i;
                }    
                else if (!apDate.before(releaseDate)){ 
                    LTSTripTermsSheet.remove(i);
                    --i;
                }    
                else if (!releaseDate.before(dateAged)){ 
                    LTSTripTermsSheet.remove(i); 
                    --i;
                }    
            }catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }  
      
    
    public void sortByVehicleID(){
        Collections.sort(LTSTripTermsSheet, new LTSTripTermsRow());
    }
    
    public void setAllDestinationCarrierToBlank(){
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            currentRow.setDestinationCarrier("");
        }
    }
    
    public String deriveFilePathOfYesterdaysversion(String todaysFilePath){
        
        LocalDate today = new LocalDate();
        LocalDate yesterday = today.minusDays(1);
        String yesterdayString = yesterday.toString();
        
        Path todaysPath = FileSystems.getDefault().getPath(todaysFilePath);
        
        
        String filename = (todaysPath.getFileName()).toString();
        String parentOfFile = ((todaysPath.getParent()).getFileName()).toString();
        String beginingOfPath = ((todaysPath.getParent()).getParent()).toString();
        
        
        
        String newParentFile = (parentOfFile.substring(0,28)).concat(yesterdayString);
        
        String yesterdayPath = beginingOfPath + File.separator + newParentFile + File.separator + filename;
        return yesterdayPath;
    }
    

    public boolean checkFileExists(String filepath){
          File f = new File(filepath);
 
	  if(f.exists()){
		  return true;
	  }else{
		  return false;
	  }
    }
    public int calculateCarsToWorkThisIter(int numberOfCarsToBeWorked, int numberOfCarsAlreadyWorked){
        // 180 is the max number that can be loaded into the GVP search
        // following determines how many of the maximum possible 180 cars 
        // will be searches for in this iteration. 
        if((numberOfCarsToBeWorked - numberOfCarsAlreadyWorked) >= 180)
            return 180;
        else
            return (numberOfCarsToBeWorked - numberOfCarsAlreadyWorked);
    }
    public String releaseTimesVehiclesToSearchFor(int carsToWorkThisIter, int numberOfCarsAlreadyWorked){
        StringBuilder vehiclesToSearchFor = new StringBuilder();
        for(int i = 0; i < carsToWorkThisIter; i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i+numberOfCarsAlreadyWorked);
            vehiclesToSearchFor.append(currentRow.getVehicleId());
            vehiclesToSearchFor.append(",");
        }
        vehiclesToSearchFor.setLength(vehiclesToSearchFor.length() -1 );
        return vehiclesToSearchFor.toString();
    }
    public String currentCarrierVehiclesToSearchFor(int carsToWorkThisIter, int numberOfCarsAlreadyWorked){
        // loop appends the next X Trip IDs to vehiclesToSearchFor. X is carsToWorkThisIter.
        StringBuilder vehiclesToSearchFor = new StringBuilder();
        for(int i = 0; i < carsToWorkThisIter; i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i+numberOfCarsAlreadyWorked);
            vehiclesToSearchFor.append(currentRow.getTripId());
            vehiclesToSearchFor.append(",");
        }
        vehiclesToSearchFor.setLength(vehiclesToSearchFor.length() -1 );
        return vehiclesToSearchFor.toString();
    }
    public String[] retrieveReleaseTimesXPaths(int indexOfResultsRow){
        String[] xPaths = new String[3];
        StringBuilder xPathToEquipmentId, xPathToShipDateAndTime, xPathToTripId;
        
        
        // Builds xPath to the equipmentID of the current row.
        xPathToEquipmentId = new StringBuilder("//table/tbody/tr[2]/td"
                + "/table/tbody/tr/td"
                + "/table/tbody/tr[2]/td"
                + "/table[2]/tbody/tr/td"
                + "/table/tbody/tr[4]/td"
                + "/table[1]/tbody/tr[2]/td"
                + "/table/tbody/tr[");
        xPathToEquipmentId.append(indexOfResultsRow);
        xPathToEquipmentId.append("]/td[3]/a");

        // Builds XPath to the ShipDateAndTime of the current row.
        xPathToShipDateAndTime = new StringBuilder("//table/tbody/tr[2]/td"
                + "/table/tbody/tr/td" 
                + "/table/tbody/tr[2]/td" 
                + "/table[2]/tbody/tr/td"
                + "/table/tbody/tr[4]/td"
                + "/table[1]/tbody/tr[2]/td"
                + "/table/tbody/tr[");
        xPathToShipDateAndTime.append(indexOfResultsRow);
        xPathToShipDateAndTime.append("]/td[4]");

        // Builds XPath to the TripID of the current row.
        xPathToTripId = new StringBuilder("//table/tbody/tr[2]/td"
                + "/table/tbody/tr/td"
                + "/table/tbody/tr[2]/td"
                + "/table[2]/tbody/tr/td"
                + "/table/tbody/tr[4]/td/"
                + "table[1]/tbody/tr[2]/td"
                + "/table/tbody/tr[");
        xPathToTripId.append(indexOfResultsRow);
        xPathToTripId.append("]/td[5]");
        
        
        xPaths[0] = xPathToEquipmentId.toString();
        xPaths[1] = xPathToShipDateAndTime.toString();
        xPaths[2] = xPathToTripId.toString();
        
        return xPaths;
    }
    public String[] retrieveCurrentCarrierXPaths(int indexOfResultsRow){
        String[] xPaths = new String[3];
        StringBuilder xPathToEquipmentId, xPathToCurrentCarrier, xPathToTripId;
        
        // Builds xPath to the equipmentID of the current row.
        xPathToEquipmentId = new StringBuilder("//table/tbody/tr[2]/td"
            +"/table/tbody/tr/td"
            +"/table/tbody/tr[2]/td"
            +"/table[2]/tbody/tr/td"
            +"/table/tbody/tr[4]/td"
            +"/table[1]/tbody/tr[2]/td"
            +"/table/tbody/tr[");
        xPathToEquipmentId.append(indexOfResultsRow);
        xPathToEquipmentId.append("]/td[3]/a");


            

        // Builds XPath to the TripId of the current row.
        xPathToTripId = new StringBuilder("//table/tbody/tr[2]/td"
            + "/table/tbody/tr/td" 
            + "/table/tbody/tr[2]/td" 
            + "/table[2]/tbody/tr/td"
            + "/table/tbody/tr[4]/td"
            + "/table[1]/tbody/tr[2]/td"
            + "/table/tbody/tr[");
        xPathToTripId.append(indexOfResultsRow);
        xPathToTripId.append("]/td[4]");

        // Builds XPath to the Current Carrier of the current row.
        xPathToCurrentCarrier = new StringBuilder("//table/tbody/tr[2]/td"
            + "/table/tbody/tr/td"
            + "/table/tbody/tr[2]/td"
            + "/table[2]/tbody/tr/td"
            + "/table/tbody/tr[4]/td/"
            + "table[1]/tbody/tr[2]/td"
            + "/table/tbody/tr[");
        xPathToCurrentCarrier.append(indexOfResultsRow);
        xPathToCurrentCarrier.append("]/td[12]");
        
            
        xPaths[0] = xPathToEquipmentId.toString();
        xPaths[1] = xPathToTripId.toString();
        xPaths[2] = xPathToCurrentCarrier.toString();
        
        return xPaths;
    }
    
    public String formatGvpDate(String inputDate){
        StringBuilder stringBuilder = new StringBuilder();
        String year,month, day, time;
        
        month = inputDate.substring(0,2);
        day = inputDate.substring(3,5);
        year = inputDate.substring(6,10);
        time = inputDate.substring(11);
        
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);
        stringBuilder.append(" ");
        stringBuilder.append(time);
        
        return stringBuilder.toString();
    }
                            
    public void navigateToSearchForm(WebDriver driver){
        driver.get("https://gvp.transcore.com/GVP/Secure/Search/Search.aspx?fid=7&nw=t");
    }
    public void clearAndFillEquipmentSearchField(WebDriver driver, String VehiclesToSearchFor){
        driver.findElement(By.id("ctl00_ph1_EquipmentId")).click();
        driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).clear();
        driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).sendKeys(VehiclesToSearchFor);
        new Select(driver.findElement(By.id("ctl00_ph1_ResultTemplate"))).selectByVisibleText("LTSTripTermsTemplate(DoNotModify)");
    }
    public void clearAndFillTripInformationField(WebDriver driver, String vehiclesToSearchFor){
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            driver.findElement(By.id("ctl00_ph1_Historical")).click();
            driver.findElement(By.id("ctl00_ph1_Current")).click();
            driver.findElement(By.id("ctl00_ph1_SnapSearch")).click();
            new Select(driver.findElement(By.id("ctl00_ph1_ResultTemplate"))).selectByVisibleText("Empty W(DoNotEdit)");
            driver.findElement(By.id("ctl00_ph1_TRIPID81")).clear();
            driver.findElement(By.id("ctl00_ph1_TRIPID81")).sendKeys(vehiclesToSearchFor);
    }
    public void clickSearchButton(WebDriver driver){
        driver.findElement(By.id("ctl00_ph1_btnSearchShipment")).click();
    }
    public void retrieveReleaseTime(WebDriver driver){
        // Following 3 variable update by Work All Car Loop
        int numberOfCarsToBeWorked = LTSTripTermsSheet.size();
        int numberOfCarsAlreadyWorked = 0;
        int carsToWorkThisIter = 0;
        
        // Holds data scraped from GVP Website
        ArrayList<ReleaseDataFromGVP> gVPReturnData = new ArrayList<>();

        // Loop to Work All Cars 
        while(numberOfCarsToBeWorked > numberOfCarsAlreadyWorked){
            
            // calculate how many cars will be worked this iteration
            carsToWorkThisIter = calculateCarsToWorkThisIter(numberOfCarsToBeWorked, numberOfCarsAlreadyWorked);
            if (carsToWorkThisIter <= 0)
                break;
            
            // comma deliniated String of vehicle IDs. 
            String VehiclesToSearchFor = releaseTimesVehiclesToSearchFor(carsToWorkThisIter, numberOfCarsAlreadyWorked);
        
           // navigates to search form
           navigateToSearchForm(driver);
           
            
           // clears then fills the equipment search field of the GVP window.
           // Clicks the search button. 
           // Changes the template to "LTSTripTermsTemplate(DoNotModify)".
           clearAndFillEquipmentSearchField(driver, VehiclesToSearchFor);
           clickSearchButton(driver);
           
            // Scrape the number of results row
            int numberOfResults = scrapeNumberOfSearchResults(driver);
            
            // Loop scrapes data from the tables of the GVP Results 
            // Results are stored in even numbered rows.
            
            for(int indexOfResultsRow = 2; indexOfResultsRow <= (numberOfResults * 2); indexOfResultsRow += 2){
                // iterates the row index.
                
                
                // build Xpaths to data tables holds three values
                String[] xPaths = retrieveReleaseTimesXPaths(indexOfResultsRow);
                
                String xPathToEquipmentId = xPaths[0];
                String xPathToShipDateAndTime = xPaths[1];
                String xPathToTripId = xPaths[2];
                
                
                // If the current row to iterate over is empty(Non-Existent),
                // All cars in work-all-cars iteration have been scraped.
                if((driver.findElements(By.xpath(xPathToEquipmentId))).size() ==0){
                    break;
                }
                
                String equipmentId = driver.findElement(By.xpath(xPathToEquipmentId)).getText();
                String shipDateAndTime = formatGvpDate(driver.findElement(By.xpath(xPathToShipDateAndTime)).getText());
                String tripId = driver.findElement(By.xpath(xPathToTripId)).getText();
                
                
                
                // New object is created that represent the data of scraped row.
                gVPReturnData.add(new ReleaseDataFromGVP(equipmentId, shipDateAndTime, tripId));
            }
            // number of newly completed cars is added to the total;
            numberOfCarsAlreadyWorked += carsToWorkThisIter;
        }
        // each row missing a release compares its trip-id and vehicle-id to the
        // data structures scraped from the websites. Rows retrieve the relase 
        //date and time from the data structure that has matching IDs
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            for(ReleaseDataFromGVP currentDownload : gVPReturnData){
                if(currentRow.vehicleId.equals(currentDownload.getEquipmentID())){
                    currentRow.setRelease(currentDownload.getShipDateAndTime());
                    break;
                }   
            }
        }
    }
    public void retrieveCurrentCarrier(WebDriver driver){

        // Following 3 variable update by Work All Car Loop
        int numberOfCarsToBeWorked = LTSTripTermsSheet.size();
        int numberOfCarsAlreadyWorked = 0;
        int carsToWorkThisIter = 0;
        
        setAllDestinationCarrierToBlank();
        
        // Holds data scraped from GVP Website
        ArrayList<CurrentRailroadReturnDataFromGVP> gVPReturnData = new ArrayList<>();
        
        // Loop to Work All Cars 
        while(numberOfCarsToBeWorked > numberOfCarsAlreadyWorked){

            // calculate how many cars will be worked this iteration
            carsToWorkThisIter = calculateCarsToWorkThisIter(numberOfCarsToBeWorked, numberOfCarsAlreadyWorked);
            if (carsToWorkThisIter <= 0)
                break;
            
            // comma deliniated String of Trip Ids. 
            String vehiclesToSearchFor = currentCarrierVehiclesToSearchFor(carsToWorkThisIter,  numberOfCarsAlreadyWorked);
            
            
            
            // navigates to search form
           navigateToSearchForm(driver);
           
            // selects templates and options
            // clears then fills the trip information search field of the GVP window.
            // Clicks the search button.
            clearAndFillTripInformationField(driver, vehiclesToSearchFor);
            clickSearchButton(driver);
            
            // Scrape the number of results row
            int numberOfResults = scrapeNumberOfSearchResults(driver);
            
            // Loop scrapes data from the tables of the GVP Results 
            // Results are stored in even numbered rows.
            
            for(int indexOfResultsRow = 2; indexOfResultsRow <= (numberOfResults * 2); indexOfResultsRow += 2){
                // iterates the row index.
                
                
                
                // build Xpaths to data tables holds three values
                String[] xPaths = retrieveCurrentCarrierXPaths(indexOfResultsRow);
                
                String xPathToEquipmentId = xPaths[0];
                String xPathToTripId = xPaths[1];
                String xPathToCurrentCarrier = xPaths[2];
                
                // If the current row to iterate over is empty(Non-Existent),
                // All cars in work-all-cars iteration have been scraped.
                if((driver.findElements(By.xpath(xPathToEquipmentId.toString()))).size() ==0){
                    break;
                }
                
                
                
                // trip info is scraped
                String equipmentID = driver.findElement(By.xpath(xPathToEquipmentId)).getText();
                String tripId = driver.findElement(By.xpath(xPathToTripId)).getText();
                String currentCarrier = driver.findElement(By.xpath(xPathToCurrentCarrier)).getText();
                
                // New object is created that represent the data of scraped row.
                gVPReturnData.add(new CurrentRailroadReturnDataFromGVP(equipmentID, currentCarrier,tripId));
            }
            // number of newly completed cars is added to the total;
            numberOfCarsAlreadyWorked += carsToWorkThisIter;
        }
        // each row missing a release compares its trip-id and vehicle-id to the
        // data structures scraped from the websites. Rows retrieve the relase 
        //date and time from the data structure that has matching IDs
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            for(CurrentRailroadReturnDataFromGVP currentDownload : gVPReturnData){
                if(currentRow.vehicleId.equals(currentDownload.getEquipmentID()) && currentRow.tripId.equals(currentDownload.getTripID())){
                    currentRow.setDestinationCarrier(currentDownload.getCurrentRailroad());
                    break;
                }   
            }
        }
        filter_BlanksFromDestinationCarrier();
    }
    public int scrapeNumberOfSearchResults(WebDriver driver){
        // xpath of number of results string.
        String xPathToSearchResults = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td/table[2]/tbody/tr/td/table/tbody/tr[2]/td[1]/span[2]";

        String str = driver.findElement(By.xpath(xPathToSearchResults)).getText();
        String[] splited = str.split("\\s+");
        if (splited[0].equals("No")){
            return 0;
        }
        else{
            return Integer.parseInt(splited[0]);
        }
    }

    public void mmtsOutputCSV (String bucketSpecificMmtsOutputFilepath,String timeToUseInBColumn , String statusToPrintInColumnD) throws IOException{
        boolean yesterdayFileExists;
        
        
        String filePathOfYesterdaysversion = deriveFilePathOfYesterdaysversion(bucketSpecificMmtsOutputFilepath);
        
        yesterdayFileExists = checkFileExists(filePathOfYesterdaysversion);
        
        MmtsClmsSheet yesterdaySheet = new MmtsClmsSheet();
        
        if(yesterdayFileExists){
            yesterdaySheet.readCSV(filePathOfYesterdaysversion);
        }
        CSVWriter duplicateClmsWriter = new CSVWriter(new FileWriter(repeatClmsFilepath,true));
        
        CSVWriter todayWriter = new CSVWriter(new FileWriter(bucketSpecificMmtsOutputFilepath));
        
        for (LTSTripTermsRow currentIterationRow : LTSTripTermsSheet) {
            if(currentIterationRow.getApplication().equals("MMTS")){
                String[] tripTermRow = currentIterationRow.mmtsReturnArrayContent(timeToUseInBColumn , statusToPrintInColumnD);
                
                if(yesterdayFileExists){
                    for(MmtsClmsRow currentmmtsClmsRow : yesterdaySheet.getmmtsClmsSheet()){
                        if(tripTermRow[0].equals(currentmmtsClmsRow.getVehicleID()) &&
                                tripTermRow[2].equals(currentmmtsClmsRow.getStatusDate()) &&
                                tripTermRow[3].equals(currentmmtsClmsRow.getCode()) &&
                                tripTermRow[4].equals(currentmmtsClmsRow.getCity()) &&
                                tripTermRow[5].equals(currentmmtsClmsRow.getState()) &&
                                tripTermRow[7].equals(currentmmtsClmsRow.getLe()) &&
                                tripTermRow[9].equals(currentmmtsClmsRow.getRrabrv()) &&
                                tripTermRow[18].equals(currentmmtsClmsRow.getStatusDate()))
                            {
                                duplicateClmsWriter.writeNext(tripTermRow);
                                break;
                            }
                        else{
                            todayWriter.writeNext(tripTermRow);
                            break;
                        }
                    } 
                }
                else{
                    todayWriter.writeNext(tripTermRow);
                }
            }
        }
        duplicateClmsWriter.close();
        todayWriter.close();
    }
    public void gvpOutputCSV(String bucketSpecificGvpOutputFilepath ,String timeToUseInBColumn , String statusToPrintInColumnD)throws IOException{
        boolean yesterdayFileExists;
        
        String filePathOfYesterdaysversion = deriveFilePathOfYesterdaysversion(bucketSpecificGvpOutputFilepath);
        yesterdayFileExists = checkFileExists(filePathOfYesterdaysversion);
        
        GvpClmsSheet yesterdaySheet = new GvpClmsSheet();
        
        if(yesterdayFileExists){
            yesterdaySheet.readCSV(filePathOfYesterdaysversion);
        }
        CSVWriter duplicateClmsWriter = new CSVWriter(new FileWriter(repeatClmsFilepath,true));
        CSVWriter todayWriter = new CSVWriter(new FileWriter(bucketSpecificGvpOutputFilepath));
        
        
        for (LTSTripTermsRow currentIterationRow : LTSTripTermsSheet) {
            if(currentIterationRow.getApplication().equals("GVP")){
                String[] tripTermRow = currentIterationRow.gvpReturnArrayContent(timeToUseInBColumn , statusToPrintInColumnD);
                
                if(yesterdayFileExists){
                    for(GvpClmsRow currentgvpClmsRow : yesterdaySheet.getgvpClmsSheet()){
                        if(tripTermRow[0].equals(currentgvpClmsRow.getEquipID()) &&
                                tripTermRow[1].equals(currentgvpClmsRow.getCurrentDateAndTime()) &&
                                tripTermRow[2].equals(currentgvpClmsRow.getLe()) &&
                                tripTermRow[3].equals(currentgvpClmsRow.getStatus()) &&
                                tripTermRow[4].equals(currentgvpClmsRow.getEventStationCity()) &&
                                tripTermRow[5].equals(currentgvpClmsRow.getEventStationState()) &&
                                tripTermRow[6].equals(currentgvpClmsRow.getRr()) &&
                                tripTermRow[21].equals(currentgvpClmsRow.getSightingtype()) &&
                                tripTermRow[37].equals(currentgvpClmsRow.getTripId()))
                            {
                                duplicateClmsWriter.writeNext(tripTermRow);
                                break;
                            }
                        else{
                            todayWriter.writeNext(tripTermRow);
                            break;   
                        }
                    } 
                }
                else{
                    todayWriter.writeNext(tripTermRow);
                }
            }
        }
        duplicateClmsWriter.close();
        todayWriter.close();
    }
    public void outputCSV(String outputCSVFilePath) throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(outputCSVFilePath));
        for (LTSTripTermsRow currentIterationRow : LTSTripTermsSheet) {
            writer.writeNext(currentIterationRow.csvReturnArrayContent());
        }
        writer.close();
    }
    public void screenOutput(){
        for (LTSTripTermsRow currentIterationRow : LTSTripTermsSheet) {
            System.out.println(Arrays.toString(currentIterationRow.csvReturnArrayContent()));
        }
    }
}
