package SEAS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Data {
	private int nFeatures;
	private int nObjects;
	private int nValues;
	private int[] firstValueIndex;
	private int[] valueFrequency;
	private int[][] coOccurrence;
	private double[][] conditionalPossibility;
	private double[][] normalizedConditionalPossibility;
	private int[] coOccurenceWithLabel;
	private double[] conditionalPossibilityWithLabel;
    private List<String> listOfCalss = new ArrayList<>();
    private Instances instances;
    private String outlierClassName;
    
    /**
	 * record 
	 * nFeatures, nObjects, firstValueIndex, nValues
	 * valueFrequency, coOccurrence, listOfClass
	 * 
	 * @throws IOException
	 */
	public void dataPrepareFromArff(String filePath) throws IOException{
				
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(filePath));
		
		Instances instances = loader.getDataSet();
		this.instances = instances;
		instances.setClassIndex(instances.numAttributes()-1);
		nFeatures = instances.numAttributes() - 1;
		nObjects = instances.numInstances();
		
		//record value number of each feature and calculate the sum of all possible values of all features
		firstValueIndex = new int[nFeatures + 1];
		firstValueIndex[0] = 0;
		for(int i = 1; i < nFeatures; i++) {
			firstValueIndex[i] = firstValueIndex[i-1] + instances.attribute(i-1).numValues();
		}
		firstValueIndex[nFeatures] = firstValueIndex[nFeatures - 1] + instances.attribute(nFeatures-1).numValues();
		nValues = firstValueIndex[nFeatures];
		
		valueFrequency = new int[nValues];
		coOccurrence = new int[nValues][nValues];
		
		
		
		
		for(int i = 0; i < nObjects; i++) {
			Instance instance = instances.instance(i);

			//calculate value frequency
			for(int j = 0; j < nFeatures; j++) {
				int localValueIndex = (int) instance.value(j);
				int globalValueIndex = firstValueIndex[j] + localValueIndex;
				valueFrequency[globalValueIndex]++;			
			}
				
			//record class of each objects
			if(instance.value(nFeatures) == 0.0) {
				listOfCalss.add("outlier");
			}else {
				listOfCalss.add("normal");
			}
			
			//calculate co-occurrence
			for(int a = 0; a < nFeatures; a++) {
				for(int b = 0; b < nFeatures; b++) {
					int valueLocalIndex1 = (int) instance.value(a);
					int valueLocalIndex2 = (int) instance.value(b);
					int valueGlobalIndex1 = valueLocalIndex1 + firstValueIndex[a];
					int valueGlobalIndex2 = valueLocalIndex2 + firstValueIndex[b];
					coOccurrence[valueGlobalIndex1][valueGlobalIndex2]++;
				}
			}
				
		}
		
		//calculate conditional possibility matrix
		conditionalPossibility = new double[nValues][nValues];
		for(int i = 0; i < nValues; i++) {
			for(int j = 0; j < nValues; j++) {
				if(valueFrequency[i] != 0) {
					conditionalPossibility[i][j] = (double) coOccurrence[i][j] / (double) valueFrequency[i];
				}else {
					conditionalPossibility[i][j] = 0;
				}
					
			}
		}
		normalizedConditionalPossibility = normalizedMatrix(conditionalPossibility);
		
	}
	
	
	public void calCPWithLabel() {
		
		coOccurenceWithLabel = new int[nValues];
		conditionalPossibilityWithLabel = new double[nValues];
		
		for(int i = 0; i < nObjects; i++) {
			Instance instance = instances.instance(i);
			//calculate co-occurrence
			for(int a = 0; a < nFeatures; a++) {				
				int valueLocalIndex = (int) instance.value(a);
				int valueGlobalIndex1 =  valueLocalIndex + firstValueIndex[a];
				if(instance.value(nFeatures) == 0.0) {
					coOccurenceWithLabel[valueGlobalIndex1]++;
				}				
			}
		}
				
		for(int i = 0; i < nValues; i++) {
			conditionalPossibilityWithLabel[i] = (double)coOccurenceWithLabel[i] / (double)valueFrequency[i];
		}
	}
	
	
	
	public static double[][] normalizedMatrix(double[][] matrix) {
		int nValue = matrix.length;
		
		//column-normalized 
		double[] columnSum = new double[nValue];
		for(int i = 0; i < nValue; i++) {
			double sum = 0.0;
			for(int j = 0; j < nValue; j++) {
				sum += matrix[j][i];
			}
			columnSum[i] = sum;
		}	
		double[][] normalizedMatrix = new double[nValue][nValue];
		for(int i = 0; i < nValue; i++) {
			for(int j = 0; j < nValue; j++) {
				normalizedMatrix[i][j] = matrix[i][j] / columnSum[j];
			}
		}
		return normalizedMatrix;
	}
	
	
	
	
	public int getNFeatures() {
		return nFeatures;
	}
	
	public int getNObjects() {
		return nObjects;
	}
	
	public int getNValues() {
		return nValues;
	}
	
	public int[] getFirstValueIndex() {
		return firstValueIndex;
	}
	
	public int[] getValueFrequency() {
		return valueFrequency;
	}

	public int[][] getCoOccurrence() {
		return coOccurrence;
	}

	public double[][] getConditionalPossibility() {
		return conditionalPossibility;
	}
	
	public double[][] getNormalizedConditionalPossibility() {
		return normalizedConditionalPossibility;
	}
	
	public double[] getConditionalPossibilityWithLabel() {
		return conditionalPossibilityWithLabel;
	}
	
	public List<String> getListOfClass() {
		return listOfCalss;
	}
	
	public Instances getInstances() {
		return instances;
	}
	
	
	
}
