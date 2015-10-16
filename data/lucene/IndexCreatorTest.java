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
public class IndexCreatorTest {
    
    public IndexCreatorTest() {
    }

    @Test
    public void testCreateAllMedlineIndex(){
        String medlineDir = "../../medline/files/";
        String textIndexDir = "./testdata/unit_test/IndexCreator/";
        IndexCreator creator = new IndexCreator();
        creator.createAllMedlineIndex(medlineDir, textIndexDir);
    }
    
}
