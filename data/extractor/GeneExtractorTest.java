/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.extractor;

import data.io.DataReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author penpen926
 */
public class GeneExtractorTest {

    public void testExtractGene() {
        String geneFile = "../../gene/gene_id.txt";
        ArrayList<String> geneList = new DataReader().readIds(geneFile);
        float[][] matrix = new float[geneList.size()][geneList.size()];
        matrix = new DataReader().readMatrix("../../gene/gene_matrix.txt", geneList.size(), geneList.size());
        System.out.println(matrix[geneList.indexOf("CA10")][geneList.indexOf("GABRA5")]);
        System.out.println(matrix[geneList.indexOf("FXYD2")][geneList.indexOf("FUMC")]);
        System.out.println(matrix[geneList.indexOf("MTNR1A")][geneList.indexOf("MTNR1B")]);
        System.out.println(matrix[geneList.indexOf("ABCA1")][geneList.indexOf("ABCB4")]);
        System.out.println(matrix[geneList.indexOf("ABCB4")][geneList.indexOf("ABCA1")]);
        System.out.println(matrix[geneList.indexOf("ABCC9")][geneList.indexOf("ABCB4")]);
        System.out.println(matrix[geneList.indexOf("GSTA1")][geneList.indexOf("GSTM3")]);
    }

    public void check() {
        String geneNameFile = "../../gene/gene_id.txt";
        String folder = "../../gene/seq/";
        HashSet<String> set = new HashSet<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(geneNameFile);
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                set.add(line);
                File f = new File(folder + line + ".fasta");
                if (!f.exists()) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(set.size());
        File seq = new File(folder);
        String[] files = seq.list();
        System.out.println(files.length);
        for (String file : files) {
            set.remove(file.substring(0, file.lastIndexOf(".")));
        }
        System.out.println(set.size());
        Iterator<String> iter = set.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
    
}
