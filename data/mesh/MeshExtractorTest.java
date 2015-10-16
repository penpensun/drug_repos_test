/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mesh;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.jdom2.*;
/**
 *
 * @author penpen926
 */
public class MeshExtractorTest {
    
    public MeshExtractorTest() {
    }
/*
    @Test
    public void testExtractMeshElementList() {
        String meshFile = "../../mesh/desc2015.xml";
        MeshExtractor meshEx = new MeshExtractor();
        List<Element> meshList = meshEx.extractMeshElementList(meshFile);
        assertEquals(27455,meshList.size());
    }
    
    @Test
    public void testExtractMeshId(){
        String meshFile = "../../mesh/desc2015.xml";
        MeshExtractor meshEx = new MeshExtractor();
        List<Element> meshList = meshEx.extractMeshElementList(meshFile);
        String id1 = meshEx.extractMeshId(meshList.get(0));
        assertEquals("D000001",id1);
    }
    
    @Test
    public void extractTermList(){
        String meshFile = "../../mesh/desc2015.xml";
        String targetMeshId = "D007762";
        String term1 = "Labyrinthitis";
        String term2 = "Labyrinthitides";
        String term3 = "Otitis Interna";
        MeshExtractor meshEx = new MeshExtractor();
        HashSet<String> termSet= null;
        List<Element> meshList = meshEx.extractMeshElementList(meshFile);
        for(Element mesh: meshList){
            String id = meshEx.extractMeshId(mesh);
            if(!id.equals(targetMeshId))
                continue;
            termSet = meshEx.extractTermList(mesh);
        }
        assertNotNull(termSet);
        assertEquals(3,termSet.size());
        assertTrue(termSet.contains(term1));
        assertTrue(termSet.contains(term2));
        assertTrue(termSet.contains(term3));  
    }
    
    
    @Test
    public void testExtractSuppList(){
        String suppXml = "../../mesh/reduced_supp2015.xml";
        MeshExtractor meshEx = new MeshExtractor();
        List<Element> suppList = meshEx.extractSuppElementList(suppXml);
        assertEquals(230276, suppList.size());
    }
    
    
    @Test
    public void testExtractScMeshMap(){
        String suppXml = "../../mesh/reduced_supp2015.xml";
        MeshExtractor meshEx = new MeshExtractor();
        HashSet<String> scSet = new HashSet<>();
        scSet.add("C563750");
        scSet.add("C566474");
        scSet.add("C538603");
        String output = "./testdata/unit_test/MeshExtractor/sc_mesh.mapping.txt";
        HashMap<String, HashSet<String>> map = meshEx.extractScMeshMap(scSet, suppXml, output);
        HashSet<String> map1 = map.get("C563750");
        HashSet<String> map2 = map.get("C566474");
        HashSet<String> map3 = map.get("C538603");
        assertEquals(1, map1.size());
        assertEquals(2, map2.size());
        assertEquals(1, map3.size());
        assertTrue(map1.contains("D017246"));
        assertTrue(map2.contains("D015785"));
        assertTrue(map2.contains("D009755"));
        assertTrue(map3.contains("D017490"));
    }
    
    
    @Test
    public void testExtractHeadingSynMap1(){
        HashSet<String> set = new HashSet<>();
        set.add("C567032");
        set.add("D005158");
        set.add("D006941");
        set.add("D006337");
        set.add("C536797");
        String meshXml = "../../mesh/desc2015.xml";
        HashSet<String> scSet= new HashSet<>();
        MeshExtractor meshEx = new MeshExtractor();
        List<Element> meshElementList = meshEx.extractMeshElementList(meshXml);
        HashMap<String, HashSet<String>> resMap = meshEx.extractHeadingSynMap1(set, meshElementList, scSet);
        //Check scSet.
        assertEquals(2, scSet.size());
        assertTrue(scSet.contains("C567032"));
        assertTrue(scSet.contains("C536797"));
        //Check TermSet1
        HashSet<String> termSet1 = resMap.get("D005158");
        assertEquals(27,termSet1.size());
        assertTrue(termSet1.contains("Paralyses, Facial"));
        assertTrue(termSet1.contains("Peripheral Facial Paralysis"));
        assertTrue(termSet1.contains("Central Facial Paralysis"));
        //Check TermSet2
        HashSet<String> termSet2 = resMap.get("D006941");
        assertEquals(16, termSet2.size());
        assertTrue(termSet2.contains("Oxyesthesia"));
        assertTrue(termSet2.contains("Sensation, Hyperesthetic"));
        assertTrue(termSet2.contains("Hyperesthesia, Thermal"));
        //Check TermSet3
        HashSet<String> termSet3 = resMap.get("D006337");
        assertEquals(16, termSet3.size());
        assertTrue(termSet3.contains("Heart Murmur"));
        assertTrue(termSet3.contains("Cardiac Murmurs"));
        assertTrue(termSet3.contains("Innocent Murmurs"));
        assertTrue(termSet3.contains("Diastolic Murmurs"));
    }
    
    
    @Test
    public void testExtractHeadingSynMap2(){
        String meshXml = "../../mesh/desc2015.xml";
        MeshExtractor meshEx = new MeshExtractor();
        List<Element> meshElemetList = meshEx.extractMeshElementList(meshXml);
        HashSet<String> set = new HashSet<>();
        set.add("D019150");
        set.add("D019189");
        HashMap<String, HashSet<String>> map = meshEx.extractHeadingSynMap2(set, meshElemetList);
        HashSet<String> resSet1 = map.get("D019150");
        HashSet<String> resSet2 = map.get("D019189");
        //Check the sizes.
        assertEquals(27, resSet1.size());
        assertTrue(resSet1.contains("Infantile Neuroaxonal Dystrophy"));
        assertTrue(resSet1.contains("Neuroaxonal Dystrophy, Infantile"));
        assertTrue(resSet1.contains("Seitelberger's Disease"));
        assertEquals(6, resSet2.size());
        assertTrue(resSet2.contains("Disorder, Iron Metabolism"));
        assertTrue(resSet2.contains("Metabolism Disorder, Iron"));
    }
    */
    @Test
    public void testMergeMap(){
        HashMap<String, HashSet<String>> map1 = new HashMap<>();
        HashMap<String, HashSet<String>> map2 = new HashMap<>();
        HashSet<String> set1 = new HashSet<>();
        set1.add("A1");
        set1.add("A2");
        HashSet<String> set2 = new HashSet<>();
        set2.add("B1");
        
        HashSet<String> set3 = new HashSet<>();
        set3.add("A3");
        HashSet<String> set4 = new HashSet<>();
        set4.add("C1");
        set4.add("C2");
        map1.put("A", set1);
        map1.put("B", set2);
        map2.put("A", set3);
        map2.put("C", set4);
        
        MeshExtractor meshEx = new MeshExtractor();
        HashMap<String, HashSet<String>> mergedMap = meshEx.mergeMap(map1, map2);
        HashSet<String> mergedSet1 = mergedMap.get("A");
        HashSet<String> mergedSet2 = mergedMap.get("B");
        HashSet<String> mergedSet3 = mergedMap.get("C");
        
        //Check the sets.
        assertEquals(3, mergedSet1.size());
        assertEquals(1, mergedSet2.size());
        assertEquals(2, mergedSet3.size());
        
        assertTrue(mergedSet1.contains("A1"));
        assertTrue(mergedSet1.contains("A2"));
        assertTrue(mergedSet1.contains("A3"));
        assertTrue(mergedSet2.contains("B1"));
        assertTrue(mergedSet3.contains("C1"));
        assertTrue(mergedSet3.contains("C2"));
    }
    
    
    @Test
    public void testExtractScSyn(){
        String meshXml = "../../mesh/desc2015.xml";
        String suppXml = "../../mesh/reduced_supp2015.xml";
        String output = "./testdata/unit_test/MeshExtractor/sc_mesh.mapping_1.txt";
        MeshExtractor meshEx = new MeshExtractor();
        System.out.println("Read mesh element list.");
        List<Element> meshElementList = meshEx.extractMeshElementList(meshXml);
        System.out.println("Read supp element list. ");
        List<Element> suppElementList = meshEx.extractSuppElementList(suppXml);
        
        HashSet<String> set = new HashSet<>();
        set.add("C567032");
        set.add("D005158");
        set.add("D006941");
        set.add("D006337");
        set.add("C536797");
        HashSet<String> scSet= new HashSet<>();
        HashMap<String, HashSet<String>> resMap = meshEx.extractHeadingSynMap1(set, meshElementList, scSet);
        
        scSet = new HashSet<>();
        scSet.add("Myeloperoxidase Deficiency");
        scSet.add("Congenital myasthenic syndrome with episodic apnea");
        scSet.add("3-methylcrotonyl CoA carboxylase 2 deficiency");
        System.out.println("Get the map. ");
        //HashMap<String, HashSet<String>> map = meshEx.extractScSyn(scSet, suppElementList, meshElementList, resMap, output);
    }
    
}
