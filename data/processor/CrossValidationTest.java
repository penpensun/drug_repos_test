/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.processor;

import biforce.graphs.NpartiteGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import data.io.*;
import data.processor.*;
import data.init.*;


/**
 *
 * @author penpen926
 */
public class CrossValidationTest {
    /**
     * Test of genCrossValid2 method, of class CrossValidation.
     */
    @Test
    public void testGenCrossValid2() {
        System.out.println("genCrossValid");
        DrugReposConfig conf = new DrugReposConfig();
        Pipeline pl = new Pipeline();
        new InitDrugReposConfig().initDrugReposConfig2(conf);
        conf.drug_id = "../../drug/drug_id.txt";
        conf.disease_id = "../../disease/disease_id.txt";
        conf.drug_disease_matrix = "../../matrix/drug_disease_matrix.txt";
        conf.drug_disease_cv_matrix = "./testdata/unit_test/CrossValidation/matrix_out.txt";
        conf.cv_result_output = "./testdata/unit_test/CrossValidation/assoc_out.txt";
        float posEw = 1.0F;
        float negEw = 0.0F;
        float prop = 0.1F;
        CrossValidation instance = new CrossValidation();
        HashMap<String, HashSet<String>> expResult = null;
        //HashMap<String, HashSet<String>> result = instance.genCrossValid2(drugListFile, diseaseListFile, drugDiseaseMatrixFile, drugDiseaseMatrixOut, outputFile, posEw, negEw, prop);
        // Check how many edges in the original matrix
        DataReader reader = new DataReader();
        ArrayList<String> drugList = reader.readIds(conf.drug_id);
        ArrayList<String> diseaseList = reader.readIds2(conf.disease_id);
        float oriMatrix[][] = reader.readMatrix(conf.drug_disease_matrix, drugList.size(), diseaseList.size());
        float outMatrix[][] = reader.readMatrix(conf.drug_disease_cv_matrix, drugList.size(), diseaseList.size());
        
        int numOri = 0;
        for(int i=0;i<oriMatrix.length;i++)
            for(int j=0;j<oriMatrix[0].length;j++)
                if(oriMatrix[i][j]>0)
                    numOri++;
        int numCv = 0;
        for(int i=0;i<outMatrix.length;i++)
            for(int j=0;j<outMatrix[0].length;j++)
                if(outMatrix[i][j]>0)
                    numCv++;
        assertEquals(numOri - (int)(numOri*0.1), numCv);
        // Check if every outputted association is removed in the cv matrix.
        HashMap<String, HashSet<String>> removedAssoc = reader.readMap(conf.cv_result_output);
        ArrayList<String> keySet = new ArrayList<>(removedAssoc.keySet());
        for(String drug: keySet){
            HashSet<String> diseaseSet = removedAssoc.get(drug);
            for(String disease: diseaseSet){
                int drugIdx = drugList.indexOf(drug);
                int diseaseIdx = diseaseList.indexOf(disease);
                // Check if drug--disease is associated in the oriMatrix, but removed in outMatrix
                assertEquals(oriMatrix[drugIdx][diseaseIdx],1.0f, 0.000001);
                assertEquals(outMatrix[drugIdx][diseaseIdx],0.0f, 0.000001);
            }
        }
    }

}
