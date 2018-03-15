package SEAS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class ObjectScorer {

	
	

	/**
	 * 
	 * @param length
	 * @param data
	 * @param valueNum
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static Hashtable<Integer, Double> calObjectOutierness(List<Double> length, List<List<String>> data, int valueNum) throws IOException{
		Hashtable<Integer, Double> outlierScoresInFormat = new Hashtable<>();
        //计算每个feature的weight，代码针对有相同数量的feature
        List<Double> relevance = new ArrayList<>();
        Double relevenceSum = 0.0;
        for (int i = 0; i < length.size(); i++) {
        	if(i % valueNum==0){
        		double sum = 0.0;
        		for(int j = 0; j < valueNum; j++){
        			sum += length.get(i+j);
        		}
	            relevance.add(sum);  
	            relevenceSum = relevenceSum + sum;
        	}
        }
           
        int index = 0;
        for(int i = 0; i < data.size(); i++){
        	List<String> object = data.get(i);        	
        	double score = 0.0;
        	for(int j = 0; j < object.size()-1; j++){
        		if(object.get(j).equals("0")){
        			score += relevance.get(j)/relevenceSum * length.get(j*2);
        		}
        		else {
					score += relevance.get(j)/relevenceSum * length.get(j*2+1);
				}
        	}       	
        	//objectScores.put(score, object.get(object.size()-1));

        	outlierScoresInFormat.put(index, score);
        	index++;
        }
        return outlierScoresInFormat;
	}
	
	
	
	/**
	 * 计算方式simply add
	 * @param length
	 * @param dataFilePath csv文件
	 * @return
	 * @throws IOException
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public static Hashtable<Integer, Double> calObjectOutierness3(List<Double> length, List<List<String>> data,  double selectedRate, int valueNum) throws IOException{
		Hashtable<Integer, Double> outlierScoresInFormat = new Hashtable<>();
        //计算每个feature的weight，代码针对有相同数量的feature

           
        int index = 0;
        for(int i = 0; i < data.size(); i++){
        	List<String> object = data.get(i);        	
        	double score = 0.0;
        	for(int j = 0; j < object.size()-1; j++){
        		if(object.get(j).equals("0")){
        			score += length.get(j*2);
        		}
        		else {
					score += length.get(j*2+1);
				}
        	}       	

        	outlierScoresInFormat.put(index, score);
        	index++;
        }
        return outlierScoresInFormat;
	}
	
	

	
	/**
	 * 只用subset of value计算object score, 计算方式，simply add
	 * @param length
	 * @param data
	 * @param selectedRate
	 * @param valueNum
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static Hashtable<Integer, Double> calObjectOutierness2(Map<String, Double> subsetValueScore, Set<String> selectedFeatures, List<List<String>> data,  double selectedRate, int valueNum) throws IOException{
		Hashtable<Integer, Double> outlierScoresInFormat = new Hashtable<>();
        int count = 0;
        int index = 0;
        for(int i = 0; i < data.size(); i++){
        	List<String> object = data.get(i);        	
        	double score = 0.0;
            Iterator<String> selectedFeaturesIt = selectedFeatures.iterator();
            while(selectedFeaturesIt.hasNext()){
            	String featureName = selectedFeaturesIt.next();
            	int featureIndex = Integer.parseInt(featureName.substring(1))-1;
        		String valueName = "A" + String.valueOf(featureIndex+1) + "_" + object.get(featureIndex);
        		if(subsetValueScore.containsKey(valueName)){
        			score += subsetValueScore.get(valueName);
        			count++;
        		}    

            }    
        	score = score / count;       	
        	outlierScoresInFormat.put(index, score);
        	index++;
        }
        
        return outlierScoresInFormat;
	}
}
