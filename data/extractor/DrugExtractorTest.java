/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.extractor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import data.io.*;


/**
 *
 * @author penpen926
 */
public class DrugExtractorTest {
    
    public DrugExtractorTest() {
    }

    @Test
    public void testDrugGeneMatrix(){
        String matrixFile = "../../drug/drug_gene_matrix.txt";
        DataReader reader = new DataReader();
        ArrayList<String> drugList = reader.readIds("../../drug/drug_id.txt");
        ArrayList<String> geneList = reader.readIds("../../gene/gene_id.txt");
        float matrix[][] = reader.readMatrix(matrixFile, drugList.size(), geneList.size());
        assertEquals(1.0,matrix[drugList.indexOf("DB05271")]
                [geneList.indexOf("DRD1")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB05271")]
                [geneList.indexOf("CYP1A2")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB00957")]
                [geneList.indexOf("ABCC2")],0.00001);
        assertEquals(0.0,matrix[drugList.indexOf("DB05271")]
                [geneList.indexOf("GABRB2")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB00497")]
                [geneList.indexOf("ORM1")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB00497")]
                [geneList.indexOf("CYP3A5")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB01544")]
                [geneList.indexOf("CYP2A6")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB01544")]
                [geneList.indexOf("GABRA6")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB00916")]
                [geneList.indexOf("CYP3A4")],0.00001);
        assertEquals(1.0,matrix[drugList.indexOf("DB00916")]
                [geneList.indexOf("CYP2C9")],0.00001);
        
        System.out.println(matrix[drugList.indexOf("DB00360")][geneList.indexOf("HNMT")]);
        System.out.println(matrix[636][936]);
        
    }
    
    @Test 
    public void testDrugNameExtractor(){
        String drugbankXml = "../../drug/drugbank.xml";
        String drugIdFile = "../../drug/drug_id.txt";
        String drug1 = "DB00203";
        String drug2 = "DB00654";
        String drug3 = "DB01204";
        String name1 = "Sildenafil";
        String name2 = "Latanoprost";
        String name3 = "Mitoxantrone";
        new DrugExtractor().extractDrugNames(drugbankXml, drugIdFile, "./temp_output.txt");
        HashMap<String, HashSet<String>> drugNamesMap = new DataReader().readMap("./temp_output.txt");
        
        HashSet<String> synDrug1 = drugNamesMap.get(drug1);
        HashSet<String> synDrug2 = drugNamesMap.get(drug2);
        HashSet<String> synDrug3 = drugNamesMap.get(drug3);
        assertTrue(synDrug1.contains(name1));
        assertTrue(synDrug2.contains(name2));
        assertTrue(synDrug3.contains(name3));
        
    }
    
    @Test
    public void testDrugSynonymsExtractor(){
        String drugbankXml = "../../drug/drugbank.xml";
        String drugIdFile= "../../drug/drug_id.txt";
        String drug1 = "DB00203";
        String drug2 = "DB00654";
        String drug3 = "DB01204";
        String syn1 = "1-((3-(4,7-Dihydro-1-methyl-7-oxo-3-propyl-1H-pyrazolo(4,3-D)pyrimidin-5-yl)-4-ethoxyphenyl)sulfonyl)-4-methylpiperazine";
        String syn2 = "Xalatan";
        String syn3 = "Mitoxantrona";
        HashMap<String, HashSet<String>> synNamesMap = new DrugExtractor().extractDrugSynonyms(drugbankXml, drugIdFile);
        HashSet<String> synSet1 = synNamesMap.get(drug1);
        HashSet<String> synSet2 = synNamesMap.get(drug2);
        HashSet<String> synSet3 = synNamesMap.get(drug3);
        assertTrue(synSet1.contains(syn1));
        assertEquals(4,synSet1.size());
        assertTrue(synSet2.contains(syn2));
        assertEquals(6, synSet2.size());
        assertTrue(synSet3.contains(syn3));
        assertEquals(4, synSet3.size());
    }
    
}
