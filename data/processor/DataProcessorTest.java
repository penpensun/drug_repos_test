/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.processor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author mac-97-41
 */
public class DataProcessorTest {
    
    public DataProcessorTest() {
    }

    @Test
    public void testSummarizeRes() {
        String drugFile = "./testdata/unit_test/DataProcessor/drug.txt";
        String diseaseFile = "./testdata/unit_test/DataProcessor/disease.txt";
        HashMap<String, HashSet<String>> posMap = new HashMap<>();
        HashMap<String, HashSet<String>> resMap= new HashMap<>();
        String negFile = "./testdata/unit_test/DataProcessor/neg.txt";
        //Insert into posMap
        posMap.put("drug1", new HashSet<>());
        posMap.get("drug1").add("disease1");
        posMap.get("drug1").add("disease2");
        posMap.get("drug1").add("disease3");
        
        posMap.put("drug2", new HashSet<>());
        posMap.get("drug2").add("disease2");
        posMap.get("drug2").add("disease4");
        
        posMap.put("drug3",new HashSet<>());
        posMap.get("drug3").add("disease1");
        posMap.get("drug3").add("disease3");
        
        posMap.put("drug4",new HashSet<>());
        posMap.get("drug4").add("disease2");
        
        posMap.put("drug5",new HashSet<>());
        posMap.get("drug5").add("disease1");
        posMap.get("drug5").add("disease4");
        
        //Insert into resMap
        resMap.put("drug1",new HashSet<>());
        resMap.get("drug1").add("disease2");
        resMap.get("drug1").add("disease3");
        
        resMap.put("drug2",new HashSet<>());
        resMap.get("drug2").add("disease1");
        resMap.get("drug2").add("disease4");
        
        resMap.put("drug3", new HashSet<>());
        resMap.get("drug3").add("disease2");
        resMap.get("drug3").add("disease3");
        
        resMap.put("drug4", new HashSet<>());
        resMap.get("drug4").add("disease1");
        resMap.get("drug4").add("disease2");
        resMap.get("drug4").add("disease3");
        
        resMap.put("drug5", new HashSet<>());
        resMap.get("drug5").add("disease1");
        
        String outputName = "test";
        String outputFile = "./testdata/unit_test/DataProcessor/output.txt";
        
        //new DataProcessor().summarizeRes3(drugFile, diseaseFile, resMap, posMap, negFile, outputName, outputFile, false);
        
        //Read the outputfile
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(outputFile);
            br = new BufferedReader(fr);
            String line = null;
            line = br.readLine();
            String[] splits = line.split("\t");
            int tp = Integer.parseInt(splits[1]);
            int fp = Integer.parseInt(splits[2]);
            int tn = Integer.parseInt(splits[3]);
            int fn = Integer.parseInt(splits[4]);
            assertEquals(6,tp);
            assertEquals(4,fp);
            assertEquals(6,tn);
            assertEquals(4, fn);
            
            float sen = Float.parseFloat(splits[5]);
            float spec = Float.parseFloat(splits[6]);
            assertEquals(0.6,sen,0.00001);
            assertEquals(0.6,spec,0.00001);
        }catch(IOException e){
            System.err.println("Reading error.");
            return;
        }
        
    }
    
}
