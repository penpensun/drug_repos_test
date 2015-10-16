/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.lucene;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author penpen926
 */
public class SearcherTest {
    String testInput = "./testdata/unit_test/lucene/test_input.txt";
    String indexDir = "./testdata/unit_test/IndexCreator/";
    String output = "./testdata/unit_test/lucene/test_output.txt";
    String drugSynFile = "../../drug/drug_syn.txt";
    String diseaseSynFile = "../../disease/disease_syn_map.txt";
    
    
    @Test
    public void testSearcher(){
        Searcher s = new Searcher();
        //s.validateParsedRes(testInput, indexDir, output, drugSynFile, diseaseSynFile);
    }
}
