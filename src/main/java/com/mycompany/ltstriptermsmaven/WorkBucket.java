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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
            if(currentRow.getA_Column().equals("Current")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromAP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getK_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromCP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getL_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromRelease(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getM_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRelease(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getM_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromCP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getL_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromAP(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getK_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_BlanksFromRailroad(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getT_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRailroad(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(!currentRow.getT_Column().equals("")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_Mmts(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getE_Column().equals("MMTS")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_Gvp(){
        for(int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i);
            if(currentRow.getE_Column().equals("GVP")){
                LTSTripTermsSheet.remove(i);
                --i;
            }
        } 
    }
    
    
    public void sortByVehicleID(){
        Collections.sort(LTSTripTermsSheet, new LTSTripTermsRow());
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
    public void deriveCpTimeFromApTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getK_Column();
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
            currentRow.setL_Column(outputDate);
        }
    }
    public void deriveApTimeFromCpTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getL_Column();
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
            currentRow.setK_Column(outputDate);
        }
    }
    public void deriveApTimeFromReleaseTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LTSTripTermsRow currentRow : LTSTripTermsSheet) {
            String dateInCSVRow = currentRow.getM_Column();
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
            currentRow.setK_Column(outputDate);
        }
    }
    
    public int calCarsToWorkThisIter(int numberOfCarsToBeWorked, int numberOfCarsAlreadyWorked){
        // 180 is the max number that can be loaded into the GVP search
        // following determines how many of the maximum possible 180 cars 
        // will be searches for in this iteration. 
        if((numberOfCarsToBeWorked - numberOfCarsAlreadyWorked) >= 180)
            return 180;
        else
            return (numberOfCarsToBeWorked - numberOfCarsAlreadyWorked);
    }
    
    public void retrieveReleaseTime(String username, String password){
        // Following 3 variable update by Work All Car Loop
        int numberOfCarsToBeWorked = LTSTripTermsSheet.size();
        int numberOfCarsAlreadyWorked = 0;
        int carsToWorkThisIter = 0;
        
        // Holds data scraped from GVP Website
        ArrayList<ReleaseDataFromGVP> gVPReturnData = new ArrayList<>();

        // Opens browser window, navigagates to GVP, Logs in.
        WebDriver driver;
        String baseUrl;

        driver = new FirefoxDriver();
        baseUrl = "https://gvp.transcore.com";
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);

        driver.get(baseUrl + "/gvp/Public/login.aspx");
        driver.findElement(By.id("tbxLgnId")).clear();
        driver.findElement(By.id("tbxLgnId")).sendKeys(username);
        driver.findElement(By.id("tbxPwd")).clear();
        driver.findElement(By.id("tbxPwd")).sendKeys(password);
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.cssSelector("nobr")).click();


        // Loop to Work All Cars 
        while(numberOfCarsToBeWorked > numberOfCarsAlreadyWorked){

            carsToWorkThisIter = calCarsToWorkThisIter(numberOfCarsToBeWorked, numberOfCarsAlreadyWorked);
            
            
            StringBuilder VehiclesToSearchFor = new StringBuilder();
            
            // loop appends the next X vehicle IDs to VehiclesToSearchFor. X is carsToWorkThisIter.
            for(int i = 0; i < carsToWorkThisIter; i++){
                LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i+numberOfCarsAlreadyWorked);
                VehiclesToSearchFor.append(currentRow.getF_Column());
                VehiclesToSearchFor.append(",");
            }
            VehiclesToSearchFor.setLength(VehiclesToSearchFor.length() -1 );
            
           
            // clears then fills the equipment search field of the GVP window.
            // Clicks the search button. Changes the template to "Empty W".
            driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).clear();
            driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).sendKeys(VehiclesToSearchFor.toString());
            driver.findElement(By.id("ctl00_ph1_btnSearchShipment")).click();
            new Select(driver.findElement(By.id("ctl00_ph1_ResultTemplate"))).selectByVisibleText("LTSTripTermsTemplate(DoNotModify)");
    
            
            
            // Loop scrapes data from the tables of the GVP Results 
            // Results are stored in even numbered rows.
            int indexOfResultsRow = 0;
            while(true){
                // iterates the row index.
                indexOfResultsRow += 2;
                // Builds xPath to the equipmentID of the current row.
                StringBuilder xPathToEquipmentId = new StringBuilder("//table/tbody/tr[2]/td"
                        + "/table/tbody/tr/td"
                        + "/table/tbody/tr[2]/td"
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td"
                        + "/table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathToEquipmentId.append(indexOfResultsRow);
                xPathToEquipmentId.append("]/td[3]/a");
                
                // Builds XPath to the ShipDateAndTime of the current row.
                StringBuilder xPathToShipDateAndTime = new StringBuilder("//table/tbody/tr[2]/td"
                        + "/table/tbody/tr/td" 
                        + "/table/tbody/tr[2]/td" 
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td"
                        + "/table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathToShipDateAndTime.append(indexOfResultsRow);
                xPathToShipDateAndTime.append("]/td[4]");
                
                // Builds XPath to the TripID of the current row.
                StringBuilder xPathToTripId = new StringBuilder("//table/tbody/tr[2]/td"
                        + "/table/tbody/tr/td"
                        + "/table/tbody/tr[2]/td"
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td/"
                        + "table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathToTripId.append(indexOfResultsRow);
                xPathToTripId.append("]/td[5]");
                
                // If the current row to iterate over is empty(Non-Existent),
                // All cars in work-all-cars iteration have been scraped.
                // Navigates back to the search screen to begin next iteration of work-all-cars loop
                if((driver.findElements(By.xpath(xPathToEquipmentId.toString()))).size() ==0){
                    driver.get(baseUrl + "/GVP/Secure/Search/SearchResults.aspx?fid=8&SCHEDULE=Y&UpdatePermitted=True");
                    driver.findElement(By.id("ctl00_ph1_lnkReviseSearch")).click();
                    break;
                }
                
                // New object is created that represent the data of scraped row.
                String equipmentID = driver.findElement(By.xpath(xPathToEquipmentId.toString())).getText();
                String shipDateAndTime = driver.findElement(By.xpath(xPathToShipDateAndTime.toString())).getText();
                String tripId = driver.findElement(By.xpath(xPathToTripId.toString())).getText();
                gVPReturnData.add(new ReleaseDataFromGVP(equipmentID, shipDateAndTime, tripId));
            }
            // number of newly completed cars is added to the total;
            numberOfCarsAlreadyWorked += carsToWorkThisIter;
        }
        // each row missing a release compares its trip-id and vehicle-id to the
        // data structures scraped from the websites. Rows retrieve the relase 
        //date and time from the data structure that has matching IDs
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            for(ReleaseDataFromGVP currentDownload : gVPReturnData){
                if(currentRow.f_Column.equals(currentDownload.getEquipmentID()) && currentRow.b_Column.equals(currentDownload.getTripID())){
                    currentRow.setM_Column(currentDownload.getShipDateAndTime());
                    break;
                }   
            }
        }
        // The browser closes.
        driver.quit();
    }
    public void retrieveCurrentCarrier(String username, String password){
        // Following 3 variable update by Work All Car Loop
        int numberOfCarsToBeWorked = LTSTripTermsSheet.size();
        int numberOfCarsAlreadyWorked = 0;
        int carsToWorkThisIter = 0;
        
        // Holds data scraped from GVP Website
        ArrayList<CurrentRailroadReturnDataFromGVP> gVPReturnData = new ArrayList<>();

        // Opens browser window, navigagates to GVP, Logs in.
        WebDriver driver;
        String baseUrl;

        driver = new FirefoxDriver();
        baseUrl = "https://gvp.transcore.com";
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);

        driver.get(baseUrl + "/gvp/Public/login.aspx");
        driver.findElement(By.id("tbxLgnId")).clear();
        driver.findElement(By.id("tbxLgnId")).sendKeys(username);
        driver.findElement(By.id("tbxPwd")).clear();
        driver.findElement(By.id("tbxPwd")).sendKeys(password);
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.cssSelector("nobr")).click();


        // Loop to Work All Cars 
        while(numberOfCarsToBeWorked > numberOfCarsAlreadyWorked){

            carsToWorkThisIter = calCarsToWorkThisIter(numberOfCarsToBeWorked, numberOfCarsAlreadyWorked);
            
            
            StringBuilder VehiclesToSearchFor = new StringBuilder();
            
            // loop appends the next X Trip IDs to VehiclesToSearchFor. X is carsToWorkThisIter.
            for(int i = 0; i < carsToWorkThisIter; i++){
                LTSTripTermsRow currentRow = LTSTripTermsSheet.get(i+numberOfCarsAlreadyWorked);
                VehiclesToSearchFor.append(currentRow.getB_Column());
                VehiclesToSearchFor.append(",");
            }
            VehiclesToSearchFor.setLength(VehiclesToSearchFor.length() -1 );
            
           
            // clears then fills the trip information search field of the GVP window.
            // Clicks the search button.
            driver.findElement(By.id("ctl00_ph1_Historical")).click();
            driver.findElement(By.id("ctl00_ph1_Current")).click();
            driver.findElement(By.id("ctl00_ph1_SnapSearch")).click();
            new Select(driver.findElement(By.id("ctl00_ph1_ResultTemplate"))).selectByVisibleText("Empty W(DoNotEdit)");
            driver.findElement(By.id("ctl00_ph1_TRIPID81")).clear();
            driver.findElement(By.id("ctl00_ph1_TRIPID81")).sendKeys(VehiclesToSearchFor.toString());
            driver.findElement(By.id("ctl00_ph1_btnSearchShipment")).click();
            
            // Loop scrapes data from the tables of the GVP Results 
            // Results are stored in even numbered rows.
            int indexOfResultsRow = 0;
            while(true){
                // iterates the row index.
                indexOfResultsRow += 2;
                // Builds xPath to the equipmentID of the current row.
                StringBuilder xPathToEquipmentId = new StringBuilder("//table/tbody/tr[2]/td"
                        +"/table/tbody/tr/td"
                        +"/table/tbody/tr[2]/td"
                        +"/table[2]/tbody/tr/td"
                        +"/table/tbody/tr[4]/td"
                        +"/table[1]/tbody/tr[2]/td"
                        +"/table/tbody/tr[");
                xPathToEquipmentId.append(indexOfResultsRow);
                xPathToEquipmentId.append("]/td[3]/a");
                
                // Builds XPath to the TripId of the current row.
                StringBuilder xPathToTripId = new StringBuilder("//table/tbody/tr[2]/td"
                        + "/table/tbody/tr/td" 
                        + "/table/tbody/tr[2]/td" 
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td"
                        + "/table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathToTripId.append(indexOfResultsRow);
                xPathToTripId.append("]/td[4]");
                
                // Builds XPath to the Current Carrier of the current row.
                StringBuilder xPathTocurrentCarrier = new StringBuilder("//table/tbody/tr[2]/td"
                        + "/table/tbody/tr/td"
                        + "/table/tbody/tr[2]/td"
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td/"
                        + "table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathTocurrentCarrier.append(indexOfResultsRow);
                xPathTocurrentCarrier.append("]/td[12]");
                
                // If the current row to iterate over is empty(Non-Existent),
                // All cars in work-all-cars iteration have been scraped.
                // Navigates back to the search screen to begin next iteration of work-all-cars loop
                if((driver.findElements(By.xpath(xPathToEquipmentId.toString()))).size() ==0){
                    driver.get(baseUrl + "/GVP/Secure/Search/SearchResults.aspx?fid=8&SCHEDULE=Y&UpdatePermitted=True");
                    driver.findElement(By.id("ctl00_ph1_lnkReviseSearch")).click();
                    break;
                }
                
                // New object is created that represent the data of scraped row.
                String equipmentID = driver.findElement(By.xpath(xPathToEquipmentId.toString())).getText();
                String tripId = driver.findElement(By.xpath(xPathToTripId.toString())).getText();
                String currentCarrier = driver.findElement(By.xpath(xPathTocurrentCarrier.toString())).getText();
                gVPReturnData.add(new CurrentRailroadReturnDataFromGVP(equipmentID, tripId, currentCarrier));
            }
            // number of newly completed cars is added to the total;
            numberOfCarsAlreadyWorked += carsToWorkThisIter;
        }
        // each row missing a release compares its trip-id and vehicle-id to the
        // data structures scraped from the websites. Rows retrieve the relase 
        //date and time from the data structure that has matching IDs
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            for(CurrentRailroadReturnDataFromGVP currentDownload : gVPReturnData){
                if(currentRow.f_Column.equals(currentDownload.getEquipmentID()) && currentRow.b_Column.equals(currentDownload.getTripID())){
                    currentRow.setT_Column(currentDownload.getCurrentRailroad());
                    break;
                }   
            }
        }
        // The browser closes.
        driver.quit();
    }
    
    public void rmPasses_SequentialShipdateCpApReleaseDateAged(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < LTSTripTermsSheet.size(); i++){
            LTSTripTermsRow currentCSVRow= LTSTripTermsSheet.get(i);
            try{
                Date shipDate = formatter.parse(currentCSVRow.getG_Column());
                Date cpDate = formatter.parse(currentCSVRow.getL_Column());
                Date apDate = formatter.parse(currentCSVRow.getK_Column());
                Date releaseDate = formatter.parse(currentCSVRow.getM_Column());
                Date dateAged = formatter.parse(currentCSVRow.getO_Column());
            
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
                Date shipDate = formatter.parse(currentCSVRow.getG_Column());
                Date cpDate = formatter.parse(currentCSVRow.getL_Column());
                Date apDate = formatter.parse(currentCSVRow.getK_Column());
                Date releaseDate = formatter.parse(currentCSVRow.getM_Column());
                Date dateAged = formatter.parse(currentCSVRow.getO_Column());
                
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
    
    public void stripFirstDigitFromTripId(){
        for(LTSTripTermsRow currentRow : LTSTripTermsSheet){
            String newTripID = currentRow.getB_Column().substring(1);
            currentRow.setB_Column(newTripID);
        }
    }
    
    public boolean checkFileExists(String filepath){
          File f = new File(filepath);
 
	  if(f.exists()){
		  return true;
	  }else{
		  return false;
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
            if(currentIterationRow.getE_Column().equals("MMTS")){
                String[] tripTermRow = currentIterationRow.mmtsReturnArrayContent(timeToUseInBColumn , statusToPrintInColumnD);
                
                for(MmtsClmsRow currentmmtsClmsRow : yesterdaySheet.getmmtsClmsSheet()){
                    if(tripTermRow[0].equals(currentmmtsClmsRow.getVehicleID()) &&
                            tripTermRow[2].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[3].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[4].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[5].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[7].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[9].equals(currentmmtsClmsRow.getStatusDate()) &&
                            tripTermRow[18].equals(currentmmtsClmsRow.getStatusDate()))
                        {
                            if(yesterdayFileExists){
                                duplicateClmsWriter.writeNext(tripTermRow);
                                break;
                            }
                            else{
                                todayWriter.writeNext(tripTermRow);
                                break;
                            }
                        }
                    else{
                        todayWriter.writeNext(tripTermRow);
                        break;
                    }
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
            if(currentIterationRow.getE_Column().equals("GVP")){
                String[] tripTermRow = currentIterationRow.gvpReturnArrayContent(timeToUseInBColumn , statusToPrintInColumnD);
                
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
                            if(yesterdayFileExists){
                               duplicateClmsWriter.writeNext(tripTermRow);
                               break;
                            }
                            else{
                                todayWriter.writeNext(tripTermRow);
                                break;
                            }
                        }
                    else{
                        todayWriter.writeNext(tripTermRow);
                        break;
                    }
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
    
}
