/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.extractor;

import data.io.DataReader;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author mac-97-41
 */
public class DiseaseExtractorTest {
    
    public DiseaseExtractorTest() {
    }

    @Test
    public void testDiseaseSyn() {
        String diseaseId = "../../disease/disease_id.txt";
        String ctdDiseaseFile= "../../ctd/CTD_Diseases.tsv";
        HashMap<String, HashSet<String>> map = new SynExtractor().extractCtdDiseaseSynonyms(diseaseId, ctdDiseaseFile);
        String disease1 = "Diverticulitis, Colonic";
        String syn1 = "Colonic Diverticulitis";
        HashSet<String> synSet1 = map.get(disease1);
        assertTrue(synSet1.contains(syn1));
        
        String disease2 = "Autistic Disorder";
        String syn2 = "ASD, INCLUDED";
        String syn3 = "Autism";
        String syn4 = "Autism, Early Infantile";
        String syn5 = "Autism, Infantile";
        HashSet<String> synSet2 = map.get(disease2);
        assertTrue(synSet2.contains(syn2));
        assertTrue(synSet2.contains(syn3));
        assertTrue(synSet2.contains(syn4));
        assertTrue(synSet2.contains(syn5));
        
    }
    
    /**
     * This method is used for checking the correctness of disease matrix. 
     * 5 pairs of data are put into the test.
     */
    public void checkDiseaseMatrix(){
        String diseaseFile = "../../disease/disease_id.txt";
        String diseaseMatrix = "../../disease/disease_matrix.txt";
        DataReader reader = new DataReader();
        ArrayList<String> diseaseList = reader.readIds2(diseaseFile);
        float matrix[][] = reader.readMatrix(diseaseMatrix, diseaseList.size(), diseaseList.size());
        System.out.println(matrix[diseaseList.indexOf("Retinitis")]
                [diseaseList.indexOf("Kidney Cortex Necrosis")]);
        System.out.println(matrix[diseaseList.indexOf("Diabetes Mellitus, Transient Neonatal, 3")]
                [diseaseList.indexOf("Diabetes Mellitus, Transient Neonatal, 2")]);
        System.out.println(matrix[diseaseList.indexOf("Macrostomia")]
                [diseaseList.indexOf("Hemolysis")]);
        System.out.println(matrix[diseaseList.indexOf("Hirschsprung Disease")]
                [diseaseList.indexOf("Retinal Degeneration")]);
        System.out.println(matrix[diseaseList.indexOf("Hemorrhage")]
                [diseaseList.indexOf("Fibrosis")]);
        
    }
    
    
    /**
     * This method checks how many diseases are non similar to any other diseases, i.e. sim = 0
     */
    public void check0SimDiseases(){
        String diseaseFile = "../../disease/disease_id.txt";
        String diseaseMatrixFile = "../../disease/disease_matrix.txt";
        DataReader reader = new DataReader();
        ArrayList<String> diseaseList = reader.readIds2(diseaseFile);
        float matrix[][] = reader.readMatrix(diseaseMatrixFile, diseaseList.size(), diseaseList.size());
        for(int i=0;i<diseaseList.size();i++){
            boolean flag = true;
            for(int j =0;j<diseaseList.size();j++){
                if(i ==j)
                    continue;
                if(matrix[i][j] !=0)
                    flag = false;
            }
            if(flag)
                System.out.println(diseaseList.get(i));
        }
    }
    
    
    /**
     * Check how many connections to other diseases the sim0 disease have got.
     */
    public void checkSim0Connection(){
        ArrayList<String> sim0List = new DataReader().readIds2("../../disease/disease_sim0.txt");
        ArrayList<String> diseaseList = new DataReader().readIds2("../../disease/disease_id.txt");
        ArrayList<String> drugList = new DataReader().readIds("../../drug/drug_id.txt");
        float matrix[][] = new DataReader().readMatrix("../../drug/drug_disease_matrix.txt", drugList.size(), diseaseList.size());
        for(String sim0d: sim0List){
            int idx = diseaseList.indexOf(sim0d);
            if(idx ==-1)
                throw new IllegalStateException("index:-1\t"+sim0d);
            int numConn= 0;
            for(int i=0;i<matrix.length;i++)
                if(matrix[i][idx] >0)
                    numConn++;
            System.out.println(sim0d+"\t"+numConn);
        }
    }
    
    
}
