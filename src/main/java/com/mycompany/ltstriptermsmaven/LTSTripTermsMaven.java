package com.mycompany.ltstriptermsmaven;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LTSTripTermsMaven {
    
    public static void main(String[] args) throws IOException {

        NewOkCancelDialog welcomeScreen = new NewOkCancelDialog(null, true);
        welcomeScreen.setVisible(true);
        String usernameGVP = welcomeScreen.usernameField.getText();
        String userPasswordGVP = welcomeScreen.passwordField.getText();
        welcomeScreen.dispose();
       
        
        String tripTermsFilePath = "";
        String tripTermsFileParentPath = "";
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .CSV", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            tripTermsFilePath = chooser.getSelectedFile().getPath();
            tripTermsFileParentPath = chooser.getSelectedFile().getParentFile().getPath();
        }
        String todaysDate = new LocalDate().toString();
        File folderToHoldResultsPath = new File (tripTermsFileParentPath + File.separator + "Results_Of_Running_TripTerms" + todaysDate);
        folderToHoldResultsPath.mkdir();
        
        
        

        String workBucketReleaseNeededGvpFilepath = folderToHoldResultsPath + File.separator + "ReleaseNeededGvp.csv";
        String workBucketReleaseNeededMmtsFilepath = folderToHoldResultsPath + File.separator +"ReleaseNeededMmts.csv";

        String workBucketCpNeededGvpFilepath = folderToHoldResultsPath + File.separator +"CPNeededGvp.csv";
        String workBucketCpNeededMmtsFilepath = folderToHoldResultsPath + File.separator + "CPNeededMmts.csv";

        String workBucketAPNeededGvpFilepath = folderToHoldResultsPath + File.separator + "APNeededGvp.csv";
        String workBucketAPNeededMmtsFilepath = folderToHoldResultsPath + File.separator + "APNeededMmts.csv";

        String workBucketCpAndReleaseNeededGvpCpFilepath = folderToHoldResultsPath + File.separator + "CpAndReleaseNeededCpGvp.csv";
        String workBucketCpAndReleaseNeededGvpReleaseFilepath = folderToHoldResultsPath + File.separator + "CpAndReleaseNeededReleaseGvp.csv";
        String workBucketCpAndReleaseNeededMmtsCpFilepath = folderToHoldResultsPath + File.separator + "CpAndReleaseNeededCpMmts.csv";
        String workBucketCpAndReleaseNeededMmtsReleaseFilepath = folderToHoldResultsPath + File.separator + "CpAndReleaseNeededReleaseMmts.csv";

        String workBucketFailedSequentialTestCpAndAPNeededFilepath = folderToHoldResultsPath + File.separator + "FailedSequentialTestCpAndAPNeeded.csv";
        
        String workBucketPassedSequentialTestCpAndAPNeededGvpCpFilepath = folderToHoldResultsPath + File.separator + "PassedSequentialTestCpAndAPNeededCpGvp.csv";
        String workBucketPassedSequentialTestCpAndAPNeededGvpApFilepath = folderToHoldResultsPath + File.separator + "PassedSequentialTestCpAndAPNeededApGvp.csv";
        String workBucketPassedSequentialTestCpAndAPNeededMmtsCpFilepath = folderToHoldResultsPath + File.separator + "PasseddSequentialTestCpAndAPNeededMmtsCp.csv";
        String workBucketPassedSequentialTestCpAndAPNeededMmtsApFilepath = folderToHoldResultsPath + File.separator + "PasseddSequentialTestCpAndAPNeededMmtsAp.csv";

        String workBucketApAndReleaseNeededGvpApFilepath = folderToHoldResultsPath + File.separator + "ApAndReleaseNeededApGvp.csv";
        String workBucketApAndReleaseNeededGvpReleaseFilepath = folderToHoldResultsPath + File.separator + "ApAndReleaseNeededReleaseGvp.csv";
        String workBucketApAndReleaseNeededMmtsApFilepath = folderToHoldResultsPath + File.separator + "ApAndReleaseNeededApMmts.csv";
        String workBucketApAndReleaseNeededMmtsReleaseFilepath = folderToHoldResultsPath + File.separator + "ApAndReleaseNeededReleaseMmts.csv";
       
        String workBucketApCpAndReleaseNeededFilepath = folderToHoldResultsPath + File.separator + "ApCpAndReleaseNeeded.csv";
        
        String workBucketRailroadNeededFilepath = folderToHoldResultsPath + File.separator + "RailroadNeeded.csv";
        
        String repeatCLMs = folderToHoldResultsPath + File.separator + "repeatCLMs.csv";


        WorkBucket workBucketApCpAndReleaseNeeded = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketApCpAndReleaseNeeded.filter_BlanksFromDestinationCarrier();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromAP();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromCP();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromRelease();
        workBucketApCpAndReleaseNeeded.outputCSV(workBucketApCpAndReleaseNeededFilepath);
        
        WorkBucket workBucketRailroadNeeded = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketRailroadNeeded.filter_EverythingButBlanksFromRailroad();
        workBucketRailroadNeeded.outputCSV(workBucketRailroadNeededFilepath);
        
        WorkBucket workBucketCpAndAPNeededFailSequentialTest = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketCpAndAPNeededFailSequentialTest.filter_BlanksFromDestinationCarrier();
        workBucketCpAndAPNeededFailSequentialTest.filter_BlanksFromRelease();
        workBucketCpAndAPNeededFailSequentialTest.filter_EverythingButBlanksFromAP();
        workBucketCpAndAPNeededFailSequentialTest.filter_EverythingButBlanksFromCP();
        workBucketCpAndAPNeededFailSequentialTest.deriveApTimeFromReleaseTime();
        workBucketCpAndAPNeededFailSequentialTest.deriveCpTimeFromApTime();
        workBucketCpAndAPNeededFailSequentialTest.rmPasses_SequentialShipdateCpApReleaseDateAged();
        workBucketCpAndAPNeededFailSequentialTest.outputCSV(workBucketFailedSequentialTestCpAndAPNeededFilepath);
        
        
        // webBrowser instance and URL
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "https://gvp.transcore.com/gvp/Public/login.aspx";
        
        // web browser will wait 3 minutes before time-out
        driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

        // Opens browser window, navigagates to GVP, Logs in.
        driver.get(baseUrl);
        driver.findElement(By.id("tbxLgnId")).clear();
        driver.findElement(By.id("tbxLgnId")).sendKeys(usernameGVP);
        driver.findElement(By.id("tbxPwd")).clear();
        driver.findElement(By.id("tbxPwd")).sendKeys(userPasswordGVP);
        driver.findElement(By.id("btnLogin")).click();
        
        // changes cutomer to intellitrans
        
        driver.findElement(By.linkText("Change Customer")).click();
        
        int customerRowIndex = 2;
        while(true){
            
            StringBuilder stringBuilder = new StringBuilder("//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/tbody/tr[");   
            stringBuilder.append(Integer.toString(customerRowIndex));
            stringBuilder.append("]/td[2]");
            
            
            String str = driver.findElement(By.xpath(stringBuilder.toString())).getText();
            
            if(str.equals("IntelliTrans")){
                StringBuilder intellitransButtonXPath = new StringBuilder("//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/tbody/tr[");
                intellitransButtonXPath.append(Integer.toString(customerRowIndex));
                intellitransButtonXPath.append("]/td[1]/input");
                
                driver.findElement(By.xpath(intellitransButtonXPath.toString())).click();
                break;
            }
            customerRowIndex++;
        }
        
        
        driver.findElement(By.id("ctl00_ph1_btnApplySettings")).click();
        
        /*
        WorkBucket workBucketCPNeededGvp = new WorkBucket(tripTermsFilePath, repeatCLMs);   // when output: time in "Current date and time should be cp time"
        workBucketCPNeededGvp.filter_Mmts();
        workBucketCPNeededGvp.filter_BlanksFromDestinationCarrier();
        workBucketCPNeededGvp.filter_BlanksFromAP();
        workBucketCPNeededGvp.filter_EverythingButBlanksFromCP();
        workBucketCPNeededGvp.filter_BlanksFromRelease();
        workBucketCPNeededGvp.deriveCpTimeFromApTime();
        workBucketCPNeededGvp.retrieveCurrentCarrier(driver);
        workBucketCPNeededGvp.gvpOutputCSV(workBucketCpNeededGvpFilepath, "l_Column","Y");
        
        
        WorkBucket workBucketCPNeededMmts = new WorkBucket(tripTermsFilePath, repeatCLMs); 
        workBucketCPNeededGvp.filter_Gvp();
        workBucketCPNeededGvp.filter_BlanksFromDestinationCarrier();
        workBucketCPNeededGvp.filter_BlanksFromAP();
        workBucketCPNeededGvp.filter_EverythingButBlanksFromCP();
        workBucketCPNeededGvp.filter_BlanksFromRelease();
        workBucketCPNeededGvp.deriveCpTimeFromApTime();
        workBucketCPNeededMmts.mmtsOutputCSV(workBucketCpNeededMmtsFilepath, "l_Column","Y");
        
        
        WorkBucket workBucketAPNeededGvp = new WorkBucket(tripTermsFilePath, repeatCLMs);   // when output: time in "Current date and time should be ap time"
        workBucketAPNeededGvp.filter_Mmts();
        workBucketAPNeededGvp.filter_BlanksFromDestinationCarrier();
        workBucketAPNeededGvp.filter_EverythingButBlanksFromAP();
        workBucketAPNeededGvp.filter_BlanksFromCP();
        workBucketAPNeededGvp.filter_BlanksFromRelease();
        workBucketAPNeededGvp.deriveApTimeFromCpTime();
        workBucketAPNeededGvp.retrieveCurrentCarrier(driver);
        workBucketAPNeededGvp.gvpOutputCSV(workBucketAPNeededGvpFilepath, "k_Column","Z");
        
        
        
        

        WorkBucket workBucketAPNeededMmts = new WorkBucket(tripTermsFilePath, repeatCLMs);   // when output: time in "Current date and time should be ap time"
        workBucketAPNeededMmts.filter_Gvp();
        workBucketAPNeededMmts.filter_BlanksFromDestinationCarrier();
        workBucketAPNeededMmts.filter_EverythingButBlanksFromAP();
        workBucketAPNeededMmts.filter_BlanksFromCP();
        workBucketAPNeededMmts.filter_BlanksFromRelease();
        workBucketAPNeededMmts.deriveApTimeFromCpTime();
        workBucketAPNeededMmts.mmtsOutputCSV(workBucketAPNeededMmtsFilepath,"k_Column","Z");
        
        
       
        
        
        

        WorkBucket workBucketCpAndAPNeededPassSequentialTestGvp = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketCpAndAPNeededPassSequentialTestGvp.filter_Mmts();
        workBucketCpAndAPNeededPassSequentialTestGvp.filter_BlanksFromDestinationCarrier();
        workBucketCpAndAPNeededPassSequentialTestGvp.filter_BlanksFromRelease();
        workBucketCpAndAPNeededPassSequentialTestGvp.filter_EverythingButBlanksFromAP();
        workBucketCpAndAPNeededPassSequentialTestGvp.filter_EverythingButBlanksFromCP();
        workBucketCpAndAPNeededPassSequentialTestGvp.deriveApTimeFromReleaseTime();
        workBucketCpAndAPNeededPassSequentialTestGvp.deriveCpTimeFromApTime();
        workBucketCpAndAPNeededPassSequentialTestGvp.rmFails_SequentialShipdateCpApReleaseDateAged();
        workBucketCpAndAPNeededPassSequentialTestGvp.retrieveCurrentCarrier(driver);
        workBucketCpAndAPNeededPassSequentialTestGvp.gvpOutputCSV(workBucketPassedSequentialTestCpAndAPNeededGvpCpFilepath,"l_Column", "Y");
        workBucketCpAndAPNeededPassSequentialTestGvp.gvpOutputCSV(workBucketPassedSequentialTestCpAndAPNeededGvpApFilepath,"k_Column", "Z");
        
        
        
        WorkBucket workBucketCpAndAPNeededPassSequentialTestMmts = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketCpAndAPNeededPassSequentialTestMmts.filter_Gvp();
        workBucketCpAndAPNeededPassSequentialTestMmts.filter_BlanksFromDestinationCarrier();
        workBucketCpAndAPNeededPassSequentialTestMmts.filter_BlanksFromRelease();
        workBucketCpAndAPNeededPassSequentialTestMmts.filter_EverythingButBlanksFromAP();
        workBucketCpAndAPNeededPassSequentialTestMmts.filter_EverythingButBlanksFromCP();
        workBucketCpAndAPNeededPassSequentialTestMmts.deriveApTimeFromReleaseTime();
        workBucketCpAndAPNeededPassSequentialTestMmts.deriveCpTimeFromApTime();
        workBucketCpAndAPNeededPassSequentialTestMmts.rmFails_SequentialShipdateCpApReleaseDateAged();
        workBucketCpAndAPNeededPassSequentialTestMmts.mmtsOutputCSV(workBucketPassedSequentialTestCpAndAPNeededMmtsCpFilepath,"l_Column", "Y");
        workBucketCpAndAPNeededPassSequentialTestMmts.mmtsOutputCSV(workBucketPassedSequentialTestCpAndAPNeededMmtsApFilepath,"k_Column", "Z");
        
        
        
        WorkBucket workBucketReleaseNeededGvp = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketReleaseNeededGvp.filter_Mmts();
        workBucketReleaseNeededGvp.filter_BlanksFromDestinationCarrier();
        workBucketReleaseNeededGvp.filter_BlanksFromAP();
        workBucketReleaseNeededGvp.filter_BlanksFromCP();
        workBucketReleaseNeededGvp.filter_EverythingButBlanksFromRelease();
        workBucketReleaseNeededGvp.retrieveReleaseTime(driver);
        workBucketReleaseNeededGvp.filter_BlanksFromRelease();
        workBucketReleaseNeededGvp.gvpOutputCSV(workBucketReleaseNeededGvpFilepath, "m_Column", "W");
        
        
        WorkBucket workBucketReleaseNeededMmts = new WorkBucket(tripTermsFilePath, repeatCLMs);
        workBucketReleaseNeededMmts.filter_Gvp();
        workBucketReleaseNeededMmts.filter_BlanksFromDestinationCarrier();
        workBucketReleaseNeededMmts.filter_BlanksFromAP();
        workBucketReleaseNeededMmts.filter_BlanksFromCP();
        workBucketReleaseNeededMmts.filter_EverythingButBlanksFromRelease();
        workBucketReleaseNeededMmts.filter_BlanksFromRelease();
        workBucketReleaseNeededMmts.outputCSV(workBucketReleaseNeededMmtsFilepath);
   

        WorkBucket workBucketCpAndReleaseNeededGvpCp = new WorkBucket(tripTermsFilePath, repeatCLMs); 
        workBucketCpAndReleaseNeededGvpCp.filter_Mmts();
        workBucketCpAndReleaseNeededGvpCp.filter_BlanksFromDestinationCarrier();
        workBucketCpAndReleaseNeededGvpCp.filter_BlanksFromAP();
        workBucketCpAndReleaseNeededGvpCp.filter_EverythingButBlanksFromRelease();
        workBucketCpAndReleaseNeededGvpCp.filter_EverythingButBlanksFromCP();
        workBucketCpAndReleaseNeededGvpCp.deriveCpTimeFromApTime();
        //workBucketCpAndReleaseNeededGvpCp.retrieveReleaseTime(driver);
        //workBucketCpAndReleaseNeededGvpCp.filter_BlanksFromRelease();
        workBucketCpAndReleaseNeededGvpCp.retrieveCurrentCarrier(driver);
        workBucketCpAndReleaseNeededGvpCp.gvpOutputCSV(workBucketCpAndReleaseNeededGvpCpFilepath, "l_Column", "Y");
        

        WorkBucket workBucketCpAndReleaseNeededGvpRelease = new WorkBucket(tripTermsFilePath, repeatCLMs); 
        workBucketCpAndReleaseNeededGvpRelease.filter_Mmts();
        workBucketCpAndReleaseNeededGvpRelease.filter_BlanksFromDestinationCarrier();
        workBucketCpAndReleaseNeededGvpRelease.filter_BlanksFromAP();
        workBucketCpAndReleaseNeededGvpRelease.filter_EverythingButBlanksFromRelease();
        workBucketCpAndReleaseNeededGvpRelease.filter_EverythingButBlanksFromCP();
        workBucketCpAndReleaseNeededGvpRelease.retrieveReleaseTime(driver);
        workBucketCpAndReleaseNeededGvpRelease.filter_BlanksFromRelease();
        workBucketCpAndReleaseNeededGvpRelease.gvpOutputCSV(workBucketCpAndReleaseNeededGvpReleaseFilepath, "m_Column", "W");
        
        WorkBucket workBucketCpAndReleaseNeededMmtsCp = new WorkBucket(tripTermsFilePath, repeatCLMs); 
        workBucketCpAndReleaseNeededMmtsCp.filter_Gvp();
        workBucketCpAndReleaseNeededMmtsCp.filter_BlanksFromDestinationCarrier();
        workBucketCpAndReleaseNeededMmtsCp.filter_BlanksFromAP();
        workBucketCpAndReleaseNeededMmtsCp.filter_EverythingButBlanksFromRelease();
        workBucketCpAndReleaseNeededMmtsCp.filter_EverythingButBlanksFromCP();
        workBucketCpAndReleaseNeededMmtsCp.deriveCpTimeFromApTime();
        workBucketCpAndReleaseNeededMmtsCp.mmtsOutputCSV(workBucketCpAndReleaseNeededMmtsCpFilepath, "l_Column", "Y");
        
        WorkBucket workBucketCpAndReleaseNeededMmtsRelease = new WorkBucket(tripTermsFilePath, repeatCLMs); 
        workBucketCpAndReleaseNeededMmtsRelease.filter_Gvp();
        workBucketCpAndReleaseNeededMmtsRelease.filter_BlanksFromDestinationCarrier();
        workBucketCpAndReleaseNeededMmtsRelease.filter_BlanksFromAP();
        workBucketCpAndReleaseNeededMmtsRelease.filter_EverythingButBlanksFromRelease();
        workBucketCpAndReleaseNeededMmtsRelease.filter_EverythingButBlanksFromCP();
        workBucketCpAndReleaseNeededMmtsRelease.deriveCpTimeFromApTime();
        workBucketCpAndReleaseNeededMmtsRelease.outputCSV(workBucketCpAndReleaseNeededMmtsReleaseFilepath);
        */
        
        WorkBucket workBucketApAndReleaseNeededGvpAp = new WorkBucket(tripTermsFilePath,repeatCLMs);
        workBucketApAndReleaseNeededGvpAp.filter_Mmts();
        workBucketApAndReleaseNeededGvpAp.filter_BlanksFromDestinationCarrier();
        workBucketApAndReleaseNeededGvpAp.filter_BlanksFromCP();
        workBucketApAndReleaseNeededGvpAp.filter_EverythingButBlanksFromRelease();
        workBucketApAndReleaseNeededGvpAp.filter_EverythingButBlanksFromAP();
        workBucketApAndReleaseNeededGvpAp.deriveApTimeFromCpTime();
        workBucketApAndReleaseNeededGvpAp.retrieveCurrentCarrier(driver);
        workBucketApAndReleaseNeededGvpAp.gvpOutputCSV(workBucketApAndReleaseNeededGvpReleaseFilepath, "m_Column", "W");
        
        WorkBucket workBucketApAndReleaseNeededGvpRelease = new WorkBucket(tripTermsFilePath,repeatCLMs);
        workBucketApAndReleaseNeededGvpRelease.filter_Mmts();
        workBucketApAndReleaseNeededGvpRelease.filter_BlanksFromDestinationCarrier();
        workBucketApAndReleaseNeededGvpRelease.filter_BlanksFromCP();
        workBucketApAndReleaseNeededGvpRelease.filter_EverythingButBlanksFromRelease();
        workBucketApAndReleaseNeededGvpRelease.filter_EverythingButBlanksFromAP();
        workBucketApAndReleaseNeededGvpRelease.retrieveReleaseTime(driver);
        workBucketApAndReleaseNeededGvpRelease.filter_BlanksFromRelease();
        workBucketApAndReleaseNeededGvpRelease.gvpOutputCSV(workBucketApAndReleaseNeededGvpApFilepath,"k_Column", "Z");
        
        /*
        WorkBucket workBucketApAndReleaseNeededMmtsAp = new WorkBucket(tripTermsFilePath,repeatCLMs);
        workBucketApAndReleaseNeededMmtsAp.filter_Gvp();
        workBucketApAndReleaseNeededMmtsAp.filter_BlanksFromDestinationCarrier();
        workBucketApAndReleaseNeededMmtsAp.filter_BlanksFromCP();
        workBucketApAndReleaseNeededMmtsAp.filter_EverythingButBlanksFromRelease();
        workBucketApAndReleaseNeededMmtsAp.filter_EverythingButBlanksFromAP();
        workBucketApAndReleaseNeededMmtsAp.deriveApTimeFromCpTime();
        workBucketApAndReleaseNeededMmtsAp.mmtsOutputCSV(workBucketApAndReleaseNeededMmtsApFilepath,"k_Column", "Z");
                
        WorkBucket workBucketApAndReleaseNeededMmtsRelease = new WorkBucket(tripTermsFilePath,repeatCLMs);
        workBucketApAndReleaseNeededMmtsRelease.filter_Gvp();
        workBucketApAndReleaseNeededMmtsRelease.filter_BlanksFromDestinationCarrier();
        workBucketApAndReleaseNeededMmtsRelease.filter_BlanksFromCP();
        workBucketApAndReleaseNeededMmtsRelease.filter_EverythingButBlanksFromRelease();
        workBucketApAndReleaseNeededMmtsRelease.filter_EverythingButBlanksFromAP();
        workBucketApAndReleaseNeededMmtsRelease.deriveApTimeFromCpTime();
        workBucketApAndReleaseNeededMmtsRelease.outputCSV(workBucketApAndReleaseNeededMmtsReleaseFilepath);
        */

        // The browser closes.
        driver.quit();
    }
}
    
 