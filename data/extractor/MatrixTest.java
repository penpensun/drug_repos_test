/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.extractor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import data.io.DataReader;
import data.io.DataWriter;
/**
 *
 * @author penpen926
 */
public class MatrixTest {
    String drug_id = "../../id/drug_id.txt";
    String disease_id = "../../id/disease_id.txt";
    String drug_matrix = "../../matrix/drug_matrix.txt";
    String gene_id = "../../id/gene_id.txt";
    String gene_matrix = "../../matrix/gene_matrix.txt";
    
    @Test
    public void checkDrugMatrix(){
        DataReader reader = new DataReader();
        ArrayList<String> drugIds = reader.readIds(drug_id);
        float[][] drugMatrix = null;
        try{
            drugMatrix = reader.readMatrix(drug_matrix, drugIds.size(), drugIds.size());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        //Check the matrix results
        assertEquals(0.260869, drugMatrix[drugIds.indexOf("DB08928")]
                [drugIds.indexOf("DB00014")], 0.0001);
        assertEquals(0.260869, drugMatrix[drugIds.indexOf("DB00014")]
                [drugIds.indexOf("DB08928")], 0.0001);
        
        assertEquals(0.28225806, drugMatrix[drugIds.indexOf("DB00035")]
                [drugIds.indexOf("DB08928")], 0.0001);
        assertEquals(0.28225806, drugMatrix[drugIds.indexOf("DB08928")]
                [drugIds.indexOf("DB00035")], 0.0001);
        
        assertEquals(0.2481203, drugMatrix[drugIds.indexOf("DB08928")]
                [drugIds.indexOf("DB00050")],0.0001);
        assertEquals(0.2481203, drugMatrix[drugIds.indexOf("DB00050")]
                [drugIds.indexOf("DB08928")],0.0001);
        
    }
    
    @Test
    public void checkGeneMatrix(){
        DataReader reader = new DataReader();
        ArrayList<String> geneIds = reader.readIds(gene_id);
        float[][] matrix = reader.readMatrix(gene_matrix, geneIds.size(), geneIds.size());
        
        assertEquals(0f, matrix[geneIds.indexOf("FXYD2")]
                [geneIds.indexOf("FUMC")],0.0001);
        assertEquals(0f, matrix[geneIds.indexOf("FUMC")]
                [geneIds.indexOf("FXYD2")],0.0001);
        
        assertEquals(18.9081f, matrix[geneIds.indexOf("ABCA1")]
                [geneIds.indexOf("ABCB4")],0.0001);
        assertEquals(18.9081f, matrix[geneIds.indexOf("ABCB4")]
                [geneIds.indexOf("ABCA1")],0.0001);
        
        assertEquals(30.897f, matrix[geneIds.indexOf("ABCC9")]
                [geneIds.indexOf("ABCB4")],0.0001);
        assertEquals(30.897f, matrix[geneIds.indexOf("ABCB4")]
                [geneIds.indexOf("ABCC9")],0.0001);
        
        assertEquals(146.3148, matrix[geneIds.indexOf("MTNR1A")]
                [geneIds.indexOf("MTNR1B")],0.0002);
        assertEquals(146.3148f, matrix[geneIds.indexOf("MTNR1B")]
                [geneIds.indexOf("MTNR1A")],0.0002);
        
    }

    public void testGeneMatrix() {
        DataReader reader = new DataReader();
        ArrayList<String> geneList = reader.readIds("../../gene/gene_id.txt");
        ArrayList<String> diseaseList = reader.readIds2("../../disease/disease_id.txt");
        float[][] matrix = reader.readMatrix("../../gene/gene_disease_matrix.txt", geneList.size(), diseaseList.size());
        System.out.println(matrix[geneList.indexOf("HTR4")][diseaseList.indexOf("Abortion, Spontaneous")]);
        System.out.println(matrix[geneList.indexOf("HTR4")][diseaseList.indexOf("Leukemoid Reaction")]);
        System.out.println(matrix[geneList.indexOf("HTR4")][diseaseList.indexOf("Pancreatic Pseudocyst")]);
        System.out.println();
        System.out.println(matrix[geneList.indexOf("HPRT1")][diseaseList.indexOf("Hepatitis, Autoimmune")]);
        System.out.println(matrix[geneList.indexOf("HPRT1")][diseaseList.indexOf("Pancreatic Pseudocyst")]);
        System.out.println(matrix[geneList.indexOf("HPRT1")][diseaseList.indexOf("Leukemoid Reaction")]);
        System.out.println();
        System.out.println(matrix[geneList.indexOf("PDE7A")][diseaseList.indexOf("Wolff-Parkinson-White Syndrome")]);
        System.out.println(matrix[geneList.indexOf("PDE7A")][diseaseList.indexOf("Pancreatic Pseudocyst")]);
    }

    public void testGeneMatrixSymmetry() {
        String matrixFile = "../../gene/gene_matrix.txt";
        String geneList = "../../gene/gene_id.txt";
        DataReader reader = new DataReader();
        ArrayList<String> genes = reader.readIds(geneList);
        float[][] matrix = reader.readMatrix(matrixFile, genes.size(), genes.size());
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    System.out.println(i + "  " + j);
                    if (matrix[i][j] > matrix[j][i]) {
                        matrix[i][j] = matrix[j][i];
                    } else {
                        matrix[j][i] = matrix[i][j];
                    }
                }
            }
        }
        new DataWriter().writeMatrix(matrix, "../../gene_matrix_1.txt");
    }
    
}
