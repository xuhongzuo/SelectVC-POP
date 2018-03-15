package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Evaluation {
	
    private List rankedList = null;
    private String outlierClassName = "";

    
    
	public Evaluation(String outlierClassName){
		this.outlierClassName = outlierClassName;
	}
	
	
	
    /**
     * to rank instances based on their outlier scores
     * @param outlierScores a hashtable with instances IDs as keys and outlier scores as values
     * @return the ranked instances according to the outlier scores
     */   
    public LinkedHashMap<Integer,Integer> rankInstancesBasedOutlierScores(Hashtable<Integer,Double> outlierScores) {
        
        List list = new ArrayList(outlierScores.entrySet());
        Collections.sort(list, new Comparator<Map.Entry>() {     
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                //return ((Double) o2.getValue()).compareTo((Double) o1.getValue()); //descending ranking
                return ((Double) o1.getValue()).compareTo((Double) o2.getValue()); // asending ranking
            }
            
        });
        
        rankedList = list;
        StringBuilder sb = new StringBuilder();
        LinkedHashMap<Integer,Integer> rankList = new LinkedHashMap();
        
        for(int i = 0; i < list.size(); i++) {
            Map.Entry object = (Map.Entry<Integer, Double>)list.get(i);
            int index = (Integer) object.getKey();
            double score = (Double) object.getValue();
            rankList.put(index, i+1);
        }
        return rankList;
    }
    
    
    
    
    
    /**
     * to calculate the AUC score based on the ranking of outlier scores
     * @param classes the class labels of instances
     * @param rankList the ranking list of instances w.r.t outlier scores
     * @return the AUC score
     */
    public double computeAUCAccordingtoOutlierRanking(List listOfclass, LinkedHashMap<Integer,Integer> rankList) {
        long totalRank = 0;
        long positiveNum = 0;
        
        for(int i = 0; i < listOfclass.size(); i++){
            if(listOfclass.get(i).equals(outlierClassName)){
               totalRank += rankList.get(i);
               positiveNum++;
            }
        }
        
        double auc = (totalRank - (Math.pow(positiveNum, 2.0)+positiveNum) / 2) / (positiveNum * (listOfclass.size() - positiveNum));
        return auc;
    }
    
    
    
    
//    /**
//     * 测试计算AUC方法
//     * @param args
//     */
//    public static void main(String[] args){
//    	List<String> listOfclass = new ArrayList<>();
//    	listOfclass.add("1");
//    	listOfclass.add("1");
//    	listOfclass.add("2");
//    	listOfclass.add("2");
//    	Hashtable<Integer, Double> hashtable = new Hashtable<>();
//    	hashtable.put(0, 0.1);
//    	hashtable.put(1, 0.4);
//    	hashtable.put(2, 0.35);
//    	hashtable.put(3, 0.8);
//    	Evaluation evaluation = new Evaluation("2");
//    	double auc = evaluation.computeAUCAccordingtoOutlierRanking(listOfclass, evaluation.rankInstancesBasedOutlierScores(hashtable));
//    	System.out.println(auc);
//    	
//    }
}
