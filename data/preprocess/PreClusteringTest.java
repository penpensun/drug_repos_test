/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.preprocess;

import org.junit.Test;
import static org.junit.Assert.*;
import biforce.io.Main3;
import data.io.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author penpen926
 */
public class PreClusteringTest {
    
    public PreClusteringTest() {
    }

    @Test
    public void testAgainstTransClust(){
        String input = "./testdata/unit_test/PreCluster/inputxml.txt";
        float threshold = 0;
        String clusterOut = "./testdata/unit_test/PreCluster/cluster_out.txt";
        String paramFile = "/Users/penpen926/work/projects/BiForce/application/BiForce/parameters.ini";
        String indexMappingOut = "./testdata/unit_test/PreCluster/indexMappingOut.txt";
        String vertexMappingOut = "./testdata/unit_test/PreCluster/vertexMapping.txt";
        String clusterPrefix = "trans";
        
        Main3.runGraphDrugReposPreClust(threshold, null,
                input, clusterOut, paramFile,
                1, indexMappingOut, vertexMappingOut, clusterPrefix,false);
                
    }
    
    
    private void adjustExampleMatrix(){
        String exampleFile = "./testdata/unit_test/PreCluster/adjusted_matrix.txt";
        String out = "./testdata/unit_test/PreCluster/matrix_out.txt";
        ArrayList<String> entityList = new DataReader().readIds("./testdata/unit_test/PreCluster/entity.txt");
        float[][] exampleMatrix = new DataReader().readMatrix(exampleFile, 
                entityList.size(), entityList.size());
        for(int i=0;i<exampleMatrix.length;i++)
            for(int j=i;j<exampleMatrix[0].length;j++){
                if(i == j)
                    exampleMatrix[i][j] = Float.NaN;
                if(exampleMatrix[i][j] != exampleMatrix[j][i])
                    if(exampleMatrix[i][j] == 0)
                        exampleMatrix[i][j] = exampleMatrix[j][i];
                    else if(exampleMatrix[j][i] == 0)
                        exampleMatrix[j][i] = exampleMatrix[i][j];
            }
        
        //Write the matrix
        new DataWriter().writeMatrix(exampleMatrix, out);
                
    }
    
    /**
     * The example matrix in transclust in a triangle matrix, I need to adjust it into a complete matrix.
     */
    private void adjustMatrix2(){
        String mFile = "./testdata/unit_test/PreCluster/matrix.txt";
        String out = "./testdata/unit_test/PreCluster/complete_matrx.txt";
        String diagOut = "./testdata/unit_test/PreCluster/rev_diag.txt";
        FileReader fr = null;
        BufferedReader br = null;
        float[][] outM = new float[232][232];
        try{
            fr = new FileReader(mFile);
            br = new BufferedReader(fr);
            String line= null;
            int lineIdx = 0;
            while((line = br.readLine())!= null){
                String[] splits = line.split("\t");
                for(int i=0;i<splits.length;i++)
                    outM[lineIdx][i] = Float.parseFloat(splits[i]);
                for(int j=splits.length;j<232;j++)
                    outM[lineIdx][j] = 0;
                lineIdx++;
            }
            fr.close();
            br.close();
            // Output the outM
            new DataWriter().writeMatrix(outM, out);
            // Create the diag matrix
            float revDiag[][] = new float[232][232];
            for(int i=0;i<232;i++)
                for(int j=0;j<232;j++)
                    if(i+j== 231)
                        revDiag[i][j] = 1;
                    else revDiag[i][j] =0;
            // Output the rev diag matrix
            new DataWriter().writeMatrix(revDiag,diagOut);
            
        }catch(IOException e){
            System.err.println("File reading error.");
        }
        
    }
    
    /**
     * This method analyses the average similarities within the cluster and between the cluster.
     */
    public void analyseSimilarity(){
        ArrayList<String[]> clusters = parseCluster();
        ArrayList<String> entityList = new DataReader().readIds("./testdata/unit_test/PreCluster/entity.txt");
        float sim[][] = new DataReader().readMatrix("./testdata/unit_test/PreCluster/matrix_out.txt",
                entityList.size(), entityList.size());
        float matrix[][] = new float[clusters.size()][clusters.size()];
        for(int i=0;i<matrix.length;i++)
            for(int j=i;j<matrix[0].length;j++){
                if(i == j)
                    matrix[i][j] = computeIntraAveSim(clusters.get(i),entityList, sim);
                else{
                    float interSim = computeInterAveSim(clusters.get(i), clusters.get(j),entityList,sim);
                    matrix[i][j] = interSim;
                    matrix[j][i] = interSim;
                }  
            }
        String output = "./testdata/unit_test/PreCluster/analysis_cluster.txt";
        new DataWriter().writeMatrix(matrix, output);
    }
    
    private float computeInterAveSim(String[] c1, String[] c2, ArrayList<String> entityList, float[][] matrix){
        float totalSim = 0;
        for(int i=0;i<c1.length;i++)
            for(int j=0;j<c2.length;j++)
                totalSim += matrix[entityList.indexOf(c1[i])]
                        [entityList.indexOf(c2[j])];
        return totalSim/(c1.length*c2.length);
    }
    
    private float computeIntraAveSim(String[] c, ArrayList<String> entityList, float[][] matrix){
        if(c.length ==1)
            return Float.NaN;
        float totalSim = 0;
        for(int i=0;i<c.length;i++)
            for(int j=0;j<c.length;j++)
                if(i == j){}
                else totalSim += matrix[entityList.indexOf(c[i])][entityList.indexOf(c[j])];
        return totalSim/(c.length*c.length - c.length);
    }
    
    private ArrayList<String[]> parseCluster(){
        String clusterOutFile = "./testdata/unit_test/PreCluster/cluster_out.txt";
        ArrayList<String[]> clusters = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(clusterOutFile);
            br = new BufferedReader(fr);
            
            String line = null;
            while((line = br.readLine())!= null){
                if(!line.startsWith("<cluster")){
                    System.err.println("Wrong cluster start:  "+line);
                    return null;
                }
                line = br.readLine();
                String[] splits = line.split("\t");
                clusters.add(splits);
                line = br.readLine();
                if(!line.startsWith("</cluster")){
                    System.err.println("Wrong cluster end:  "+line);
                    return null;
                }
            }
            fr.close();
            br.close();
        }catch(IOException e){
            System.err.println("File reading error.");
        }
        return clusters;
    }
    
    @Test
    public void testOutput(){
       //adjustExampleMatrix();
        analyseSimilarity();
    }
    
}
