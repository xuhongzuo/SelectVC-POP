package SEAS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ODUtils {
	public static void main(String[] args) throws IOException {
		//String path = args[0];
		//String rateString = args[1];
		//double rate = Double.parseDouble(rateString);
		String path = "X:\\data\\";	
		double rate = 0.3;
    	
		if(path.endsWith(".arff")) {
	    	String name = getDatasetName(path);
	    	System.out.print(name + ",");
	    	
			long beginTime = System.currentTimeMillis(); 		
			Data data = new Data();
			data.dataPrepareFromArff(path);
			data.calCPWithLabel();
			POP.runPOP(data, rate);			
			long endTime = System.currentTimeMillis(); 
			System.out.format("%.4fs%n", (endTime - beginTime) / 1000.0);   		
			
		}else {
			List<String> datasetList = buildDataSetsPathList(path);
			for(int i = 0; i <datasetList.size(); i++) {
		    	String name = getDatasetName(datasetList.get(i));
		    	System.out.print(name + ",");
				
				
				long beginTime = System.currentTimeMillis(); 
				Data data = new Data();
				data.dataPrepareFromArff(datasetList.get(i));
				data.calCPWithLabel();
				POP.runPOP(data, rate);
				long endTime = System.currentTimeMillis(); 
				System.out.format("%.4fs%n", (endTime - beginTime) / 1000.0);   		
			}
		}

	}
	
	
	
	
	public static String getDatasetName(String path) {
		String name = "";
		
		//windows
		if(path.contains("\\")) {
			String[] splitedString = path.split("\\\\");
			name = splitedString[splitedString.length-1].split("\\.")[0];
		}else {
			//linux
			String[] splitedString = path.split("/");
			name = splitedString[splitedString.length-1].split("\\.")[0];
		}
		
		return name;
	}
	
	
	
    /**
     * to store the file names contained in a folder
     * @param dataSetFilesPath the path of the folder
     */
    public static List<String> buildDataSetsPathList(String dataSetFilesPath)
    {
        File filePath = new File(dataSetFilesPath);
        String[] fileNameList =  filePath.list();
        int dataSetFileCount = 0;
        for (int count=0;count < fileNameList.length;count++)
        {
            // System.out.println(fileNameList[count]);
            if (fileNameList[count].toLowerCase().endsWith(".arff"))
            {
                dataSetFileCount = dataSetFileCount +1;
            }
        }
        List<String> dataSetFullNameList = new ArrayList<>();

        dataSetFileCount = 0;
        for (int count =0; count < fileNameList.length; count++)
        {
            if (fileNameList[count].toLowerCase().endsWith(".arff"))
            {
                if(dataSetFilesPath.contains("\\")) {
                    dataSetFullNameList.add(dataSetFilesPath+"\\"+fileNameList[count]);
                }else {
                    dataSetFullNameList.add(dataSetFilesPath+"/"+fileNameList[count]);

				}

                dataSetFileCount = dataSetFileCount +1;
            }
        }
        
        return dataSetFullNameList;
    }
	
	
}
