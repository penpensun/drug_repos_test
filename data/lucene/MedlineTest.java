/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.lucene;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import data.io.*;


/**
 *
 * @author penpen926
 */
public class MedlineTest {
    String medlineFile = "../../medline/zip/medline15n0001.xml";
    String medlineFile2 = "../../medline/zip/medline15n0002.xml";
    public MedlineTest() {
    }

    @Test
    public void testExtractmedlineElementList(){
        MedlineParser med = new MedlineParser();
        List<Element> medlineList = med.extractMedlineElementList(medlineFile);
        assertEquals(30000,medlineList.size());
    }
    
    @Test
    public void testExtractInfo(){
        MedlineParser med = new MedlineParser();
        List<Element> medlineList = med.extractMedlineElementList(medlineFile2);
        String output = "../../medline/medline_output.txt";
        for(Element medlineElement: medlineList){
            med.extractMedlineInfo(medlineElement, output, true);
        }
    }
    
    @Test
    public void testReadMedline(){
        MedlineParser medParser = new MedlineParser();
        String output = "./testdata/unit_test/Medline/med.obj";
        medParser.parseMedlineFile(medlineFile2, output);
        ArrayList<Medline> medArray = medParser.readMedlineFile(output);
        assertEquals(30000, medArray.size());
        Medline m = medArray.get(0);
        assertEquals("Ovarian transplantation and reconstruction of fallopian tubes: with report of two cases and review of literature.",
                m.title);
        assertEquals("12336567",m.pmid);
        
        assertEquals(16, m.meshHeadings.size());
        assertTrue(m.meshHeadings.contains("Sterilization, Reproductive"));
        assertTrue(m.meshHeadings.contains("Therapeutics"));
        assertTrue(m.meshHeadings.contains("Urogenital System"));
        
        assertEquals(20, m.keywords.size());
        assertTrue(m.keywords.contains("Fallopian Tubes"));
        assertTrue(m.keywords.contains("Family Planning"));
        
        assertEquals("2 very early cases of organ transplantation, in this case of "
                + "sections of healthy ovaries from fertile women to women unintentionally "
                + "or unknowingly subjected to oophorectomy, are described; the paper concentrates "
                + "on surgical technique, for the outcome is preliminary at the time of "
                + "publication.  The surgeon involved found donors willing to lose portions "
                + "of their ovarian tissue, for various reasons, who were proven fertile.  "
                + "In 1 case of transplantation, the recipient was made ready for transplantation "
                + "concurrently with the donor, and no storage of organs was necessary; "
                + "in the other, ovarian material was stored in saline for a period of time, "
                + "and upon reintroduction to a pelvic cavity the material immediately became "
                + "reinfused and appeared healthy.  Neither of the recipients had begun menst"
                + "ruating postsurgery at the time of this article's publication (about 6 mo"
                + "nths postoperatively), but signs were hopeful.  The technique is spelled "
                + "out in a step-wise manner.", m.abstractText.trim());
    }
}
