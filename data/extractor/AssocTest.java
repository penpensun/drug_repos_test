/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.extractor;

import org.junit.Test;
import static org.junit.Assert.*;
import data.io.DataReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 *
 * @author penpen926
 */
public class AssocTest {
    String drug_id = "../../id/drug_id.txt";
    String cas_id = "../../id/cas_number.txt";
    String drug_drug_name_assoc = "../../assoc/drug_drug_names_assoc.txt";
    String drug_gene_assoc= "../../assoc/drug_gene_assoc.txt";
    
    
    @Test
    public void checkDrugCasAssoc(){
        DataReader reader = new DataReader();
        ArrayList<String> drug = reader.readIds(drug_id);
        ArrayList<String> cas = reader.readIds(cas_id);
        
        // Check
        String drug1 = "DB00121";
        int idx1 = drug.indexOf(drug1);
        assertTrue(idx1!=-1);
        assertEquals("58-85-5",cas.get(idx1));
        
        String drug2 = "DB00268";
        int idx2 = drug.indexOf(drug2);
        assertTrue(idx2!=-1);
        assertEquals("91374-21-9",cas.get(idx2));
        
        String drug3 = "DB00639";
        int idx3 = drug.indexOf(drug3);
        assertTrue(idx2!=-1);
        assertEquals("64872-77-1",cas.get(idx3));
        
        String drug4 = "DB00960";
        int idx4 = drug.indexOf(drug4);
        assertTrue(idx4!=-1);
        assertEquals("13523-86-9",cas.get(idx4));
        
        String drug5 = "DB00465";
        int idx5 = drug.indexOf(drug5);
        assertTrue(idx5!=-1);
        assertEquals("66635-83-4",cas.get(idx5));
    }
    
    @Test
    public void checkDrugDrugNameAssoc(){
        DataReader reader = new DataReader();
        ArrayList<String> drugIds = reader.readIds(drug_id);
        HashMap<String, String> drugNameMap = reader.readMap2(drug_drug_name_assoc);
        
        String drug1 = "DB00398";
        String name1 = "Sorafenib";
        assertEquals(name1,drugNameMap.get(drug1));
        
        String drug2 = "DB01206";
        String name2 = "Lomustine";
        assertEquals(name2,drugNameMap.get(drug2));
        
        String drug3 = "DB00458";
        String name3 = "Imipramine";
        assertEquals(name3, drugNameMap.get(drug3));
    }
    
    @Test
    public void checkDrugGeneAssoc(){
        DataReader reader = new DataReader();
        HashMap<String, HashSet<String>> drugGeneMap = reader.readMap(drug_gene_assoc);
        String drug1 = "DB05271";
        String gene11 = "DRD1";
        String gene12 = "CYP1A2";
        assertTrue(drugGeneMap.get(drug1).contains(gene11));
        assertTrue(drugGeneMap.get(drug1).contains(gene12));
        
        String drug2 = "DB00957";
        String gene21 = "ABCC2";
        assertTrue(drugGeneMap.get(drug2).contains(gene21));
        
        String drug3 = "DB00497";
        String gene31 = "ORM1";
        String gene32 = "CYP3A5";
        assertTrue(drugGeneMap.get(drug3).contains(gene31));
        assertTrue(drugGeneMap.get(drug3).contains(gene32));
        
        String drug4 = "DB01544";
        String gene41 = "GABRA6";
        String gene42 = "CYP2A6";
        assertTrue(drugGeneMap.get(drug4).contains(gene41));
        assertTrue(drugGeneMap.get(drug4).contains(gene42));
        
        String drug5 = "DB00916";
        String gene51 = "CYP3A4";
        String gene52 = "CYP2C9";
        assertTrue(drugGeneMap.get(drug5).contains(gene51));
        assertTrue(drugGeneMap.get(drug5).contains(gene52));
    }
 
}
