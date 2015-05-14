
package com.mycompany.ltstriptermsmaven;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MmtsClmsSheet {
    public ArrayList<MmtsClmsRow> mmtsClmsSheet;

    
    public MmtsClmsSheet(){
        mmtsClmsSheet = new ArrayList<>();
    }
    
    public void readCSV(String clmsFilePath) throws FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new FileReader(clmsFilePath), ',' , '"' , 1);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                mmtsClmsSheet.add(new MmtsClmsRow(nextLine));
            }
        }
    }
    public ArrayList<MmtsClmsRow> getmmtsClmsSheet (){
        return mmtsClmsSheet;
    }
}
