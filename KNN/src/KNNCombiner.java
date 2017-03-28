import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KNNCombiner extends Reducer<Text, Text, Text, Text> {

    protected void reduce(
            Text key,
            Iterable<Text> value,
            Reducer<Text, Text, Text, Text>.Context context)
            throws java.io.IOException, InterruptedException {

    	
    	ArrayList<String> labelAndDist = new ArrayList<String>();
    	//save the valueInput to labelAndDist arraylist
        for (Text t: value){
        	String temp = t.toString();
        	//System.out.println(temp);
        	labelAndDist.add(temp);
        }
        
        
        //System.out.println("*************************************");
        //System.out.println(String.join("/", vs));
        //Collections.sort(vs);
        
        //sort the list by ascending distance 
        Collections.sort(labelAndDist, new Comparator<String>() {
            @Override
            public int compare( String test1, String test2)
            {
            	String[] temp1 = test1.split(",");
            	String[] temp2 = test2.split(",");
            	return Double.parseDouble(temp1[1]) > Double.parseDouble(temp2[1]) ? 1 : (Double.parseDouble(temp1[1]) < Double.parseDouble(temp2[1]) ) ? -1 : 0;
                //return Double.parseDouble(temp1[0]).compareTo(Double.parseDouble(temp2[0]));
            }
        });

        int k = context.getConfiguration().getInt("k", 5);
        //System.out.println("*************************************");
        //System.out.println(String.join("/", vs));
        
        //save the fisrt k labels as valueOut
        String[] temp = new String[k];
        for (int i = 0; i< k; i++ ){
        	temp[i] = (labelAndDist.get(i).split(","))[0];
        }
        String firstFive = String.join(",", temp);
        Text valueOut = new Text();
        valueOut.set(firstFive);
        context.write(key, valueOut);
    }
}
