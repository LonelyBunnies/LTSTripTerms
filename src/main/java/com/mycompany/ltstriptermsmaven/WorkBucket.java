package com.mycompany.ltstriptermsmaven;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
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

//import java.util.regex.Pattern;
//import java.util.concurrent.TimeUnit;
//import org.junit.*;
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class WorkBucket {
    
    public ArrayList<CSVRow> csvSheet;
    public String repeatClms;
    
    public WorkBucket(){
        csvSheet = new ArrayList<>();
    }
    
    public void readCSV(String csvFilePath) throws FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ',' , '"' , 1);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                csvSheet.add(new CSVRow(nextLine));
            }
        }
    }
    
    public void filter_DuplicateVehicleID(){
        // Hashsets prevent duplicates from being added and so are used for 
        // filtering duplicates
        HashSet hs = new HashSet();
        // The contents of csvSheet are copied into the hashset
        hs.addAll(csvSheet);
        // The csvSheet is cleared.
        csvSheet.clear();
        // The duplicate free contents of the hashset are added to the csvSheet.
        csvSheet.addAll(hs);
    }
    
    public void filter_CurrentTripStatus(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(currentRow.getA_Column().equals("Current")){
                csvSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromAP(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(currentRow.getK_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromCP(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(currentRow.getL_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_BlanksFromRelease(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(currentRow.getM_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRelease(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(!currentRow.getM_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromCP(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(!currentRow.getL_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_EverythingButBlanksFromAP(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(!currentRow.getK_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        } 
    }
    public void filter_BlanksFromRailroad(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(currentRow.getT_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        }
    }
    public void filter_EverythingButBlanksFromRailroad(){
        for(int i = 0; i < csvSheet.size(); i++){
            CSVRow currentRow = csvSheet.get(i);
            if(!currentRow.getT_Column().equals("")){
                csvSheet.remove(i);
                --i;
            }
        } 
    }
    public String deriveFilePathOfYesterdaysversion(String todaysFilePath){
        
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);
        DateFormat timeFormatter = new SimpleDateFormat("MM-dd-yyyy");
        String yesterdayString = (timeFormatter.format(yesterday));
        
        
        Path todaysPath = FileSystems.getDefault().getPath(todaysFilePath);
        
        
        String beginingOfPath = (todaysPath.subpath(0,todaysPath.getNameCount()-2)).toString();
        String parentOfFile = (todaysPath.subpath(0,todaysPath.getNameCount()-1)).toString();
        String filename = (todaysPath.getFileName()).toString();
        
        String newParentFile = (parentOfFile.substring(0,30)).concat(yesterdayString);
        
        String yesterdayPath = beginingOfPath + System.getProperty("file.seperator") + parentOfFile + System.getProperty("file.seperator") + filename;
        return yesterdayPath;
    }
    
    
    public void mmtsOutputCSV (String bucketSpecificMmtsOutputFilepath,String timeToUseInBColumn , String statusToPrintInColumnD) throws IOException{
        
        String filePathOfYesterdaysversion = deriveFilePathOfYesterdaysversion(bucketSpecificMmtsOutputFilepath);
        
        WorkBucket yesterdayBucket = new WorkBucket();
        yesterdayBucket.readCSV(filePathOfYesterdaysversion);
        
        CSVWriter yesterdayWriter = new CSVWriter(new FileWriter(filePathOfYesterdaysversion,true));
        CSVWriter todayWriter = new CSVWriter(new FileWriter(bucketSpecificMmtsOutputFilepath,true));
        for (CSVRow currentIterationRow : csvSheet) {
            if(currentIterationRow.getE_Column().equals("MMTS"))
                for(CSVRow yesterdayCurrentIterationRow : yesterdayBucket.csvSheet){
                    
                }
                
                todayWriter.writeNext(currentIterationRow.mmtsReturnArrayContent(timeToUseInBColumn , statusToPrintInColumnD));
        }
        yesterdayWriter.close();
        todayWriter.close();
    }
    public void gvpOutputCSV(String bucketSpecificGvpOutputFilepath ,String timeToUseInBColumn , String statusToPrintInColumnD)throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(bucketSpecificGvpOutputFilepath));
        for (CSVRow currentIterationRow : csvSheet) {
            if(currentIterationRow.getE_Column().equals("GVP"))
                writer.writeNext(currentIterationRow.gvpReturnArrayContent( timeToUseInBColumn, statusToPrintInColumnD));
        }
        writer.close();
    }
    public void outputCSV(String outputCSVFilePath) throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(outputCSVFilePath));
        for (CSVRow currentIterationRow : csvSheet) {
            writer.writeNext(currentIterationRow.csvReturnArrayContent());
        }
        writer.close();
    }
    
    public void sortByVehicleID(){
        Collections.sort(csvSheet, new CSVRow());
    }
    
    public void deriveCpTimeFromApTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CSVRow currentRow : csvSheet) {
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
        for (CSVRow currentRow : csvSheet) {
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
        for (CSVRow currentRow : csvSheet) {
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
    public void retrieveReleaseTime(String username, String password){
        // Following 3 variable update by Work All Car Loop
        int numberOfCarsToBeWorked = csvSheet.size();
        int numberOfCarsAlreadyWorked = 0;
        int carsToWorkThisIter = 0;
        
        // Holds data scraped from GVP Website
        ArrayList<DataFromGVP> gVPReturnData = new ArrayList<>();

        // Opens browser window, navigagates to GVP, Logs in.
        WebDriver driver;
        String baseUrl;

        driver = new FirefoxDriver();
        baseUrl = "https://gvp.transcore.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl + "/gvp/Public/login.aspx");
        driver.findElement(By.id("tbxLgnId")).clear();
        driver.findElement(By.id("tbxLgnId")).sendKeys(username);
        driver.findElement(By.id("tbxPwd")).clear();
        driver.findElement(By.id("tbxPwd")).sendKeys(password);
        driver.findElement(By.id("btnLogin")).click();


        // Loop to Work All Cars 
        while(numberOfCarsToBeWorked > numberOfCarsAlreadyWorked){

            // 180 is the max number that can be loaded into the GVP search
            // following determines how many of the maximum possible 180 cars 
            // will be searches for in this iteration. 
            if((numberOfCarsToBeWorked - numberOfCarsAlreadyWorked) >= 180)
                carsToWorkThisIter = 180;
            else
                carsToWorkThisIter = numberOfCarsToBeWorked - numberOfCarsAlreadyWorked;
            
            
            StringBuilder VehiclesToSearchFor = new StringBuilder();
            
            // loop appends the next X vehicle IDs to VehiclesToSearchFor. X is carsToWorkThisIter.
            for(int i = 0; i < carsToWorkThisIter; i++){
                CSVRow currentRow = csvSheet.get(i+numberOfCarsAlreadyWorked);
                VehiclesToSearchFor.append(currentRow.getF_Column());
                VehiclesToSearchFor.append(",");
            }
            VehiclesToSearchFor.setLength(VehiclesToSearchFor.length() -1 );
            
           
            // clears then fills the equipment search field of the GVP window.
            // Clicks the search button. Changes the template to "Empty W".
            driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).clear();
            driver.findElement(By.id("ctl00_ph1_EquipmentListDetails_EquipmentListDetails")).sendKeys(VehiclesToSearchFor.toString());
            driver.findElement(By.id("ctl00_ph1_btnSearchShipment")).click();
            new Select(driver.findElement(By.id("ctl00_ph1_ResultTemplate"))).selectByVisibleText("Empty W");
            
            
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
                xPathToShipDateAndTime.append("]/td[7]");
                
                // Builds XPath to the TripID of the current row.
                StringBuilder xPathToTripId = new StringBuilder("//table/tbody/tr[2]/td/"
                        + "table/tbody/tr/td"
                        + "/table/tbody/tr[2]/td"
                        + "/table[2]/tbody/tr/td"
                        + "/table/tbody/tr[4]/td"
                        + "/table[1]/tbody/tr[2]/td"
                        + "/table/tbody/tr[");
                xPathToTripId.append(indexOfResultsRow);
                xPathToTripId.append("]/td[15]");
                
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
                gVPReturnData.add(new DataFromGVP(equipmentID, shipDateAndTime, tripId));
            }
            // number of newly completed cars is added to the total;
            numberOfCarsAlreadyWorked += carsToWorkThisIter;
        }
        // each row missing a release compares its trip-id and vehicle-id to the
        // data structures scraped from the websites. Rows retrieve the relase 
        //date and time from the data structure that has matching IDs
        for(CSVRow currentRow : csvSheet){
            for(DataFromGVP currentDownload : gVPReturnData){
                if(currentRow.f_Column.equals(currentDownload.getEquipmentID()) || currentRow.b_Column.equals(currentDownload.getTripID())){
                    currentRow.m_Column = currentDownload.getShipDateAndTime();
                    break;
                }   
            }
        }
        // The browser closes.
        driver.quit();
    }
    public void rmPasses_SequentialShipdateCpApReleaseDateAged(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < csvSheet.size(); i++){
            CSVRow currentCSVRow= csvSheet.get(i);
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
                            csvSheet.remove(i);
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
        for (int i = 0; i < csvSheet.size(); i++){
            CSVRow currentCSVRow= csvSheet.get(i);
            try{
                Date shipDate = formatter.parse(currentCSVRow.getG_Column());
                Date cpDate = formatter.parse(currentCSVRow.getL_Column());
                Date apDate = formatter.parse(currentCSVRow.getK_Column());
                Date releaseDate = formatter.parse(currentCSVRow.getM_Column());
                Date dateAged = formatter.parse(currentCSVRow.getO_Column());
                
                if (!shipDate.before(cpDate)){ 
                    csvSheet.remove(i);
                    --i;
                }
                else if (!cpDate.before(apDate)){ 
                    csvSheet.remove(i);
                    --i;
                }    
                else if (!apDate.before(releaseDate)){ 
                    csvSheet.remove(i);
                    --i;
                }    
                else if (!releaseDate.before(dateAged)){ 
                    csvSheet.remove(i); 
                    --i;
                }    
            }catch (ParseException ex) {
                Logger.getLogger(WorkBucket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

   
}
