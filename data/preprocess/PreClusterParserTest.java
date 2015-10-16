/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.preprocess;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import data.io.*;
/**
 *
 * @author penpen926
 */
public class PreClusterParserTest {
    
    public PreClusterParserTest() {
    }

    @Test
    public void testParsePreCluster() {
        String input = "./testdata/unit_test/PreClusterParser/input.txt";
        PreClusterParser parser = new PreClusterParser();
        HashMap<String,String[]> parsedClusters = parser.parsePreCluster(input, "drug");
        // Check the cluster name.
        ArrayList<String> keys = new ArrayList<>(parsedClusters.keySet());
        assertEquals(5,keys.size());
        assertTrue(keys.contains("drug_1283"));
        assertTrue(keys.contains("drug_1287"));
        assertTrue(keys.contains("drug_956"));
        assertTrue(keys.contains("drug_1233"));
        assertTrue(keys.contains("drug_285"));
        // Check the cluster content.
        String v1[] = {"DB00166"};
        String v2[] = {"DB06704"};
        String v3[] = {"DB04841","DB00568"};
        String v4[] = {"DB00132","DB00154","DB00159"};
        String v5[] = {"DB00237","DB00241","DB00306","DB00312","DB00418","DB01353","DB01352","DB01351"};
        
        assertTrue(isStringArrayEqual(v1,parsedClusters.get("drug_1283")));
        assertTrue(isStringArrayEqual(v2,parsedClusters.get("drug_1287")));
        assertTrue(isStringArrayEqual(v3,parsedClusters.get("drug_956")));
        assertTrue(isStringArrayEqual(v4,parsedClusters.get("drug_1233")));
        assertTrue(isStringArrayEqual(v5,parsedClusters.get("drug_285")));
        
    }
    
    private boolean isStringArrayEqual(String[] s1, String[] s2){
        if(s1.length != s2.length)
            return false;
        for(String x: s1){
            boolean flag = false;
            for(String y:s2)
                if(y.equals(x))
                    flag = true;
            if(!flag)
                return false;
        }
        return true;
    }
    
    @Test
    public void testCreateMatrix(){
        // Cluster drug_620
        String drugCluster[] = {"DB00357","DB01625","DB00832","DB05246","DB04896","DB01437","DB08918"};
        String diseaseCluster[] = {"Hypersensitivity"};
        //{"Acute Chest Syndrome","Pulmonary Edema","Pulmonary Eosinophilia",
          //  "Lung Injury","Tuberculosis, Pulmonary"};
        int drugIdx[] = new int[drugCluster.length];
        int diseaseIdx[] = new int[diseaseCluster.length];
        ArrayList<String> drugIds = new DataReader().readIds("../../id/drug_id.txt");
        ArrayList<String> geneIds = new DataReader().readIds("../../id/gene_id.txt");
        ArrayList<String> diseaseIds = new DataReader().readIds2("../../id/disease_id.txt");
        
        for(int i=0;i<drugIdx.length;i++)
            drugIdx[i] = drugIds.indexOf(drugCluster[i]);
        for(int i=0;i<diseaseIdx.length;i++)
            diseaseIdx[i] = diseaseIds.indexOf(diseaseCluster[i]);

        float matrix[][] = new DataReader().readMatrix(
                "../../matrix/drug_disease_matrix.txt", 1543, 3407);
        assertEquals(0.14285715,
                new PreClusterParser().computePreClusterSim(matrix,drugIdx,diseaseIdx),
                0.00001);
        
        String drugCluster2[] = {"DB01185","DB08970","DB00687","DB00620","DB00223","DB00324","DB01234","DB00443","DB00547"};
        String geneCluster[] = {"PTGS2"};
        int drugIdx2[] = new int[drugCluster2.length];
        int geneIdx[] = new int[geneCluster.length];
        for(int i=0;i<drugIdx2.length;i++)
            drugIdx2[i] = drugIds.indexOf(drugCluster2[i]);
        for(int i=0;i<geneIdx.length;i++)
            geneIdx[i] = geneIds.indexOf(geneCluster[i]);
        
        float matrix2[][] = new DataReader().readMatrix(
                "../../drug/drug_gene_matrix.txt", 1543, 1622);
        assertEquals(0.11111111, 
                new PreClusterParser().computePreClusterSim(matrix2, drugIdx2, geneIdx),
                0.00001);
        
        String drugCluster3[] = {"DB00247","DB01253","DB00353"};
        String geneCluster2[] = {"HTR4","HTR6","HTR7","HTR1E","HTR1F","HTR1D","HTR1A","HTR1B","HTR2B",
            "HTR2C","HTR2A","TAAR1","DRD1","DRD3","DRD5","HRH2","ADRA1B","ADRA1A","ADRA2A","ADRB3",
            "ADRB2","CHRM1","ADRA1D","DRD2","ADRB1"};
        
        int drugIdx3[] = new int[drugCluster3.length];
        int geneIdx2[] = new int[geneCluster2.length];
        for(int i=0;i<drugIdx3.length;i++)
            drugIdx3[i] = drugIds.indexOf(drugCluster3[i]);
        for(int i=0;i<geneIdx2.length;i++)
            geneIdx2[i] = geneIds.indexOf(geneCluster2[i]);
        
        
        assertEquals(0.13333334, 
                new PreClusterParser().computePreClusterSim(matrix2, drugIdx3, geneIdx2),
                0.00001);
        
        String geneCluster3[] = {"HTR3A","CHRNA10","CHRNB4","CHRNB3","CHRNA3","CHRNA5","CHRNA4",
            "CHRNA7","CHRNA6","CHRNA9","CHRFAM7A","CHRNB2","CHRNA2"};
        String diseaseCluster2[] = {"Hoarseness","Paraneoplastic Syndromes"};
        int geneIdx3[] = new int[geneCluster3.length];
        int diseaseIdx2[] = new int[diseaseCluster2.length];
        for(int i=0;i<geneIdx3.length;i++)
            geneIdx3[i] = geneIds.indexOf(geneCluster3[i]);
        for(int i=0;i<diseaseIdx2.length;i++)
            diseaseIdx2[i] = diseaseIds.indexOf(diseaseCluster2[i]);
        float matrix3[][] =new DataReader().readMatrix("../../matrix/gene_disease_matrix.txt",
                1622, 3407);
        assertEquals(0.15384616,
                new PreClusterParser().computePreClusterSim(matrix3, geneIdx3, diseaseIdx2),
                0.00001);
    }
    
}
