package com.mycompany.ltstriptermsmaven;

import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.joda.time.LocalDate;

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
        
        String repeatCLMs = folderToHoldResultsPath + File.separator + "repeatCLMs";


        WorkBucket workBucketApCpAndReleaseNeeded = new WorkBucket(repeatCLMs);
        workBucketApCpAndReleaseNeeded.readCSV(tripTermsFilePath);
        workBucketApCpAndReleaseNeeded.filter_DuplicateVehicleID();
        workBucketApCpAndReleaseNeeded.filter_BlanksFromRailroad();
        workBucketApCpAndReleaseNeeded.filter_CurrentTripStatus();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromAP();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromCP();
        workBucketApCpAndReleaseNeeded.filter_EverythingButBlanksFromRelease();
        workBucketApCpAndReleaseNeeded.sortByVehicleID();
        workBucketApCpAndReleaseNeeded.outputCSV(workBucketApCpAndReleaseNeededFilepath);
        
        WorkBucket workBucketRailroadNeeded = new WorkBucket(repeatCLMs);
        workBucketRailroadNeeded.readCSV(tripTermsFilePath);
        workBucketRailroadNeeded.filter_DuplicateVehicleID();
        workBucketRailroadNeeded.filter_EverythingButBlanksFromRailroad();
        workBucketRailroadNeeded.filter_CurrentTripStatus();
        workBucketRailroadNeeded.sortByVehicleID();
        workBucketRailroadNeeded.outputCSV(workBucketRailroadNeededFilepath);
        
        WorkBucket workBucketCpAndAPNeededFailSequentialTest = new WorkBucket(repeatCLMs);
        workBucketCpAndAPNeededFailSequentialTest.readCSV(tripTermsFilePath);
        workBucketCpAndAPNeededFailSequentialTest.filter_DuplicateVehicleID();
        workBucketCpAndAPNeededFailSequentialTest.filter_BlanksFromRailroad();
        workBucketCpAndAPNeededFailSequentialTest.filter_CurrentTripStatus();
        workBucketCpAndAPNeededFailSequentialTest.filter_BlanksFromRelease();
        workBucketCpAndAPNeededFailSequentialTest.filter_EverythingButBlanksFromAP();
        workBucketCpAndAPNeededFailSequentialTest.filter_EverythingButBlanksFromCP();
        workBucketCpAndAPNeededFailSequentialTest.sortByVehicleID();
        workBucketCpAndAPNeededFailSequentialTest.deriveApTimeFromReleaseTime();
        workBucketCpAndAPNeededFailSequentialTest.deriveCpTimeFromApTime();
        workBucketCpAndAPNeededFailSequentialTest.rmPasses_SequentialShipdateCpApReleaseDateAged();
        workBucketCpAndAPNeededFailSequentialTest.outputCSV(workBucketFailedSequentialTestCpAndAPNeededFilepath);
        
        
        WorkBucket workBucketCPNeeded = new WorkBucket(repeatCLMs);   // when output: time in "Current date and time should be cp time"
        workBucketCPNeeded.readCSV(tripTermsFilePath);
        workBucketCPNeeded.filter_DuplicateVehicleID();
        workBucketCPNeeded.filter_BlanksFromRailroad();
        workBucketCPNeeded.filter_CurrentTripStatus();
        workBucketCPNeeded.filter_BlanksFromAP();
        workBucketCPNeeded.filter_EverythingButBlanksFromCP();
        workBucketCPNeeded.filter_BlanksFromRelease();
        workBucketCPNeeded.sortByVehicleID();
        workBucketCPNeeded.deriveCpTimeFromApTime();
        workBucketCPNeeded.mmtsOutputCSV(workBucketCpNeededMmtsFilepath, "l_Column","Y");
        workBucketCPNeeded.gvpOutputCSV(workBucketCpNeededGvpFilepath, "l_Column","Y");

        WorkBucket workBucketAPNeeded = new WorkBucket(repeatCLMs);   // when output: time in "Current date and time should be ap time"
        workBucketAPNeeded.readCSV(tripTermsFilePath);
        workBucketAPNeeded.filter_DuplicateVehicleID();
        workBucketAPNeeded.filter_BlanksFromRailroad();
        workBucketAPNeeded.filter_CurrentTripStatus();
        workBucketAPNeeded.filter_EverythingButBlanksFromAP();
        workBucketAPNeeded.filter_BlanksFromCP();
        workBucketAPNeeded.filter_BlanksFromRelease();
        workBucketAPNeeded.sortByVehicleID();
        workBucketAPNeeded.deriveApTimeFromCpTime();
        workBucketAPNeeded.mmtsOutputCSV(workBucketAPNeededMmtsFilepath,"k_Column","Z");
        workBucketAPNeeded.gvpOutputCSV(workBucketAPNeededGvpFilepath, "k_Column","Z");


        WorkBucket workBucketCpAndAPNeededPassSequentialTest = new WorkBucket(repeatCLMs);
        workBucketCpAndAPNeededPassSequentialTest.readCSV(tripTermsFilePath);
        workBucketCpAndAPNeededPassSequentialTest.filter_DuplicateVehicleID();
        workBucketCpAndAPNeededPassSequentialTest.filter_BlanksFromRailroad();
        workBucketCpAndAPNeededPassSequentialTest.filter_CurrentTripStatus();
        workBucketCpAndAPNeededPassSequentialTest.filter_BlanksFromRelease();
        workBucketCpAndAPNeededPassSequentialTest.filter_EverythingButBlanksFromAP();
        workBucketCpAndAPNeededPassSequentialTest.filter_EverythingButBlanksFromCP();
        workBucketCpAndAPNeededPassSequentialTest.sortByVehicleID();
        workBucketCpAndAPNeededPassSequentialTest.deriveApTimeFromReleaseTime();
        workBucketCpAndAPNeededPassSequentialTest.deriveCpTimeFromApTime();
        workBucketCpAndAPNeededPassSequentialTest.rmFails_SequentialShipdateCpApReleaseDateAged();
        workBucketCpAndAPNeededPassSequentialTest.mmtsOutputCSV(workBucketPassedSequentialTestCpAndAPNeededMmtsCpFilepath,"l_Column", "Y");
        workBucketCpAndAPNeededPassSequentialTest.mmtsOutputCSV(workBucketPassedSequentialTestCpAndAPNeededMmtsApFilepath,"k_Column", "Z");
        workBucketCpAndAPNeededPassSequentialTest.gvpOutputCSV(workBucketPassedSequentialTestCpAndAPNeededGvpCpFilepath,"l_Column", "Y");
        workBucketCpAndAPNeededPassSequentialTest.gvpOutputCSV(workBucketPassedSequentialTestCpAndAPNeededGvpApFilepath,"k_Column", "Z");
        
        WorkBucket workBucketReleaseNeeded = new WorkBucket(repeatCLMs);
        workBucketReleaseNeeded.readCSV(tripTermsFilePath);// Every workbucket stars with this
        workBucketReleaseNeeded.filter_DuplicateVehicleID();
        workBucketReleaseNeeded.filter_BlanksFromRailroad();
        workBucketReleaseNeeded.filter_CurrentTripStatus();// End every workbucket should start with this
        workBucketReleaseNeeded.filter_BlanksFromAP();
        workBucketReleaseNeeded.filter_BlanksFromCP();
        workBucketReleaseNeeded.filter_EverythingButBlanksFromRelease();
        workBucketReleaseNeeded.sortByVehicleID();
        workBucketReleaseNeeded.retrieveReleaseTime(usernameGVP, userPasswordGVP);
        workBucketReleaseNeeded.filter_BlanksFromRelease();
        workBucketReleaseNeeded.mmtsOutputCSV(workBucketReleaseNeededMmtsFilepath,"m_Column", "W");
        workBucketReleaseNeeded.gvpOutputCSV(workBucketReleaseNeededGvpFilepath, "m_Column", "W");
   
        
        
        WorkBucket workBucketCpAndReleaseNeeded = new WorkBucket(repeatCLMs); 
        workBucketCpAndReleaseNeeded.readCSV(tripTermsFilePath);
        workBucketCpAndReleaseNeeded.filter_DuplicateVehicleID();
        workBucketCpAndReleaseNeeded.filter_BlanksFromRailroad();
        workBucketCpAndReleaseNeeded.filter_CurrentTripStatus();
        workBucketCpAndReleaseNeeded.filter_BlanksFromAP();
        workBucketCpAndReleaseNeeded.filter_EverythingButBlanksFromRelease();
        workBucketCpAndReleaseNeeded.filter_EverythingButBlanksFromCP();
        workBucketCpAndReleaseNeeded.sortByVehicleID();
        workBucketCpAndReleaseNeeded.deriveCpTimeFromApTime();
        workBucketCpAndReleaseNeeded.retrieveReleaseTime(usernameGVP, userPasswordGVP);
        workBucketCpAndReleaseNeeded.filter_BlanksFromRelease();
        workBucketCpAndReleaseNeeded.mmtsOutputCSV(workBucketCpAndReleaseNeededMmtsCpFilepath, "l_Column", "Y");
        workBucketCpAndReleaseNeeded.gvpOutputCSV(workBucketCpAndReleaseNeededGvpReleaseFilepath, "m_Column", "W");
        workBucketCpAndReleaseNeeded.mmtsOutputCSV(workBucketCpAndReleaseNeededMmtsReleaseFilepath, "m_Column", "W");
        workBucketCpAndReleaseNeeded.gvpOutputCSV(workBucketCpAndReleaseNeededGvpCpFilepath, "l_Column", "Y");



        WorkBucket workBucketApAndReleaseNeeded = new WorkBucket(repeatCLMs);
        workBucketApAndReleaseNeeded.readCSV(tripTermsFilePath);
        workBucketApAndReleaseNeeded.filter_DuplicateVehicleID();
        workBucketApAndReleaseNeeded.filter_BlanksFromRailroad();
        workBucketApAndReleaseNeeded.filter_CurrentTripStatus();
        workBucketApAndReleaseNeeded.filter_BlanksFromCP();
        workBucketApAndReleaseNeeded.filter_EverythingButBlanksFromRelease();
        workBucketApAndReleaseNeeded.filter_EverythingButBlanksFromAP();
        workBucketApAndReleaseNeeded.sortByVehicleID();
        workBucketApAndReleaseNeeded.deriveApTimeFromCpTime();
        workBucketApAndReleaseNeeded.retrieveReleaseTime(usernameGVP, userPasswordGVP);
        workBucketApAndReleaseNeeded.filter_BlanksFromRelease();
        workBucketApAndReleaseNeeded.mmtsOutputCSV(workBucketApAndReleaseNeededMmtsApFilepath,"k_Column", "Z");
        workBucketApAndReleaseNeeded.mmtsOutputCSV(workBucketApAndReleaseNeededMmtsReleaseFilepath,"m_Column", "W");
        workBucketApAndReleaseNeeded.gvpOutputCSV(workBucketApAndReleaseNeededGvpApFilepath,"k_Column", "Z");
        workBucketApAndReleaseNeeded.gvpOutputCSV(workBucketApAndReleaseNeededGvpReleaseFilepath, "m_Column", "W");
        
        

    }
}
    
 