/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataprocessing;

import data.processor.TanimotoCoeffFormatConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author penpen926
 */
public class TanimotoCoeffFormatConverterTest {
    String inputFile = "../../drug_drug_sim/similarityscore/yuan/db_similarity.txt";
    String nodesOutputFile = "../../drug_drug_sim/test_sim_score/test_converter_nodes_output.txt";
    String matrixOutputFile="../../drug_drug_sim/test_sim_score/test_converter_matrix_output.txt";
    public TanimotoCoeffFormatConverterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convertYuanForm method, of class TanimotoCoeffFormatConverter.
     */
    //@Test
    public void testConvertYuanForm() {
        System.out.println("(dataprocessing.convertYuanForm) Test starts.");
        try{
        TanimotoCoeffFormatConverter tani = new TanimotoCoeffFormatConverter();
        tani.convertYuanForm(inputFile, nodesOutputFile, matrixOutputFile);
        /* Check the nodes.*/
        FileReader nodesfr = new FileReader(nodesOutputFile);
        BufferedReader nodesbr = new BufferedReader(nodesfr);
        String line = null;
        line = nodesbr.readLine();
        /* 1.Check if all nodes are inputted.*/
        String[] nodesNames = line.split("\t");
        ArrayList<String> nodesNamesList = new ArrayList<>(Arrays.asList(nodesNames));
        assertEquals(6829,nodesNames.length);
        /* Check 6 node names. */
        assertTrue(nodesNamesList.contains("DB00014"));
        assertTrue(nodesNamesList.contains("DB00035"));
        assertTrue(nodesNamesList.contains("DB00115"));
        assertTrue(nodesNamesList.contains("DB00120"));
        assertTrue(nodesNamesList.contains("DB00125"));
        assertTrue(nodesNamesList.contains("DB00180"));
        
        /* 2. Check the matrix.*/
        FileReader matrixfr = new FileReader(matrixOutputFile);
        BufferedReader matrixbr = new BufferedReader(matrixfr);
        double[][] matrix = new double[nodesNamesList.size()][nodesNamesList.size()];
        int rowIdx = 0;
        while((line = matrixbr.readLine())!= null){
            if(line.trim().isEmpty())
                    continue;
            String[] weights = line.split("\t");
            /* First check the length of the weights.*/
            assertEquals(matrix.length,weights.length); /* Check the length of the column.*/
            assertFalse(rowIdx>=matrix.length); /* Check the length of the row.*/
            /* Assign the matrix.*/
            for(int i=0;i<weights.length;i++){
                matrix[rowIdx][i] = Double.parseDouble(weights[i]);
            }
            rowIdx++;
        }
        
        /* Check some value of the matrix.*/
        int idx1, idx2;
        idx1 = nodesNamesList.indexOf("DB00175");
        idx2 = nodesNamesList.indexOf("DB00014");
        assertEquals(0.20000000298,matrix[idx1][idx2],0.00001);
        assertEquals(0.20000000298,matrix[idx2][idx1],0.00001);
        
        idx1 = nodesNamesList.indexOf("DB00159");
        idx2 = nodesNamesList.indexOf("DB04784");
        assertEquals(0.153846159577,matrix[idx1][idx2],0.00001);
        assertEquals(0.153846159577,matrix[idx2][idx1],0.00001);
        
        idx1 = nodesNamesList.indexOf("DB00152");
        idx2 = nodesNamesList.indexOf("DB02576");
        assertEquals(0.102040819824,matrix[idx1][idx2],0.00001);
        assertEquals(0.102040819824,matrix[idx2][idx1],0.00001);
        
        idx1 = nodesNamesList.indexOf("DB00014");
        idx2 = nodesNamesList.indexOf("DB08927");
        assertEquals(0.290780127048,matrix[idx1][idx2],0.00001);
        assertEquals(0.290780127048,matrix[idx2][idx1],0.00001);
        
        idx1 = nodesNamesList.indexOf("DB00188");
        idx2 = nodesNamesList.indexOf("DB08927");
        assertEquals(0.301724135876,matrix[idx1][idx2],0.00001);
        assertEquals(0.301724135876,matrix[idx2][idx1],0.00001);
        
         /* Check all NaN.*/
        int i=0,j=0;
        try{
        for(i=0;i<matrix.length;i++)
            for(j=0;j<matrix[0].length;j++){
                if(i==j)
                    assertTrue(Double.isNaN(matrix[i][j]));
                else
                    assertFalse(Double.isNaN(matrix[i][j]));
            }
        }catch(AssertionError e){
            System.out.println("Assertion error by NaN. i:  "+i+" j:  "+j);
        }
        System.out.println("(dataprocessing.convertYuanForm) Test ends");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void tempTestTanimotoConversion(){
        System.out.println("(dataprocessing.tempTestTanimotoConversion) Test starts.");
        try{
            FileReader nodesfr = new FileReader(nodesOutputFile);
            BufferedReader nodesbr = new BufferedReader(nodesfr);
            String line = null;
            line = nodesbr.readLine();
            /* 1.Check if all nodes are inputted.*/
            String[] nodesNames = line.split("\t");
            ArrayList<String> nodesNamesList = new ArrayList<>(Arrays.asList(nodesNames));
            assertEquals(6829,nodesNames.length);
            /* Check 6 node names. */
            assertTrue(nodesNamesList.contains("DB00014"));
            assertTrue(nodesNamesList.contains("DB00035"));
            assertTrue(nodesNamesList.contains("DB00115"));
            assertTrue(nodesNamesList.contains("DB00120"));
            assertTrue(nodesNamesList.contains("DB00125"));
            assertTrue(nodesNamesList.contains("DB00180"));
            
            /* 2. Check the matrix.*/
            FileReader matrixfr = new FileReader(matrixOutputFile);
            BufferedReader matrixbr = new BufferedReader(matrixfr);
            double[][] matrix = new double[nodesNamesList.size()][nodesNamesList.size()];
            int rowIdx = 0;
            while((line = matrixbr.readLine())!= null){
                if(line.trim().isEmpty())
                    continue;
                String[] weights = line.split("\t");
                /* First check the length of the weights.*/
                assertEquals(matrix[0].length,weights.length);
                
                /* Check the length of the column.*/
                assertFalse(rowIdx>=matrix.length); /* Check the length of the row.*/
                /* Assign the matrix.*/
                for(int i=0;i<weights.length;i++){
                    matrix[rowIdx][i] = Double.parseDouble(weights[i]);
                }
                rowIdx++;
            }
            /* Check the rowIdx. */
            assertEquals(6829,rowIdx);
            /* Check some value of the matrix.*/
            int idx1, idx2;
            idx1 = nodesNamesList.indexOf("DB00175");
            idx2 = nodesNamesList.indexOf("DB00014");
            assertEquals(0.20000000298,matrix[idx1][idx2],0.00001);
            assertEquals(0.20000000298,matrix[idx2][idx1],0.00001);

            idx1 = nodesNamesList.indexOf("DB00159");
            idx2 = nodesNamesList.indexOf("DB04784");
            assertEquals(0.153846159577,matrix[idx1][idx2],0.00001);
            assertEquals(0.153846159577,matrix[idx2][idx1],0.00001);

            idx1 = nodesNamesList.indexOf("DB00152");
            idx2 = nodesNamesList.indexOf("DB02576");
            assertEquals(0.102040819824,matrix[idx1][idx2],0.00001);
            assertEquals(0.102040819824,matrix[idx2][idx1],0.00001);

            idx1 = nodesNamesList.indexOf("DB00014");
            idx2 = nodesNamesList.indexOf("DB08927");
            assertEquals(0.290780127048,matrix[idx1][idx2],0.00001);
            assertEquals(0.290780127048,matrix[idx2][idx1],0.00001);

            idx1 = nodesNamesList.indexOf("DB00188");
            idx2 = nodesNamesList.indexOf("DB08927");
            assertEquals(0.301724135876,matrix[idx1][idx2],0.00001);
            assertEquals(0.301724135876,matrix[idx2][idx1],0.00001);
            
            /* Check all NaN.*/
            int i=0,j=0;
            try{
            for(i=0;i<matrix.length;i++)
                for(j=0;j<matrix[0].length;j++){
                    if(i==j)
                        assertTrue(Double.isNaN(matrix[i][j]));
                    else
                        assertFalse(Double.isNaN(matrix[i][j]));
                }
            }catch(AssertionError e){
                System.out.println("Assertion error by NaN. i:  "+i+" j:  "+j);
            }
                    
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("(dataprocessing.tempTestTanimotoConversion) Test ends.");
    }
    
    
    

    
}
