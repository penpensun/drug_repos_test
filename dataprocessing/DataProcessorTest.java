/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataprocessing;

import data.processor.DataProcessor;
import data.io.DataReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author penpen926
 */
public class DataProcessorTest {
    
    public DataProcessorTest() {
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
     * Test of jaccardIndex method, of class DataProcessor.
     */
    @Test
    public void testJaccardIndex() {
        System.out.println("jaccardIndex");
        String[] array1 = {"a1","a2","a3","a4"};
        String[] array2 = {"a1","a2","a3","a5","a6"};
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(array1));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(array2));
        
        DataProcessor instance = new DataProcessor();
        float expResult = 0.75F;
        float result = instance.jaccardIndex(list1, list2);
        assertEquals(expResult, result, 0.00001);
    }
    
    public void testPathwaySim(){
        System.out.println("testPathwaySim");
        String[] array1 = {"P11168","P52789","Q96C23","P06744","P08237","P04075","P60174","P35575",
            "P04406","P07738","Q8TE04","P18669","P15259","P06733","P30613"};
        String[] array2 = {"P11168","P52789","P06744","P17858","P05062","P04406","P00558","P15259",
            "P06733","P30613","Q9Y5U8","P11498","P08559","P11177","P10515","P09622","O75390","Q6ZMR3",
            "P53985","Q99798","P50213","O43837","P51553","Q02218","P36957","P53597","Q96I99","P31040",	
            "P21912","Q99643","O14521","P07954","P40925","P00367","Q9UI32","Q15758","P37837","P29401",
            "P49247","O95336","P11413","P52209","Q6LAP8","P21399","O75874","P14618"};
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(array1));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(array2));
        DataProcessor instance = new DataProcessor();
        float expResult = 7.0F/15;
        assertEquals(expResult,instance.jaccardIndex(list1, list2),0.0001);
        
        /* Read the pathway ids. */
        ArrayList<String> pathwayIds = new DataReader().readPathwayIds("../../pathway/pathway_id_fasta.txt");
        /* Read the matrix. */
        int idx1 = pathwayIds.indexOf("SMP00531");
        int idx2 = pathwayIds.indexOf("SMP00654");
        float[][] matrix = new DataReader().readMatrix("../../pathway/pathway_matrix_yuan.txt", 691, 691);
        assertEquals(expResult, matrix[idx1][idx2],0.0001);
        assertEquals(expResult, matrix[idx2][idx1],0.0001);
    }
    
}
