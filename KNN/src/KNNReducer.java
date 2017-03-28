import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KNNReducer extends Reducer<Text, Text, Text, Text> {
	protected void reduce(
			Text key,
			Iterable<Text> value,
			Reducer<Text, Text, Text, Text>.Context context)
			throws java.io.IOException, InterruptedException {
		//ArrayList<Vector2SF> vs = new ArrayList<Vector2SF>();

		Map<String, Integer> count = new HashMap<String, Integer>();
		
		//count the occurrance for each label, save to HashMap count
		for (Text v : value) {
			for (String label: v.toString().split(",")){
				if (count.containsKey(label)){
					count.put(label, count.get(label) + 1);
				} else{
					count.put(label, 1);
				}
			}
		}
		
		//find the label with max occurance as classLabel
		int max = 0;
		String classLabel ="";
		for (String label: count.keySet()){
			if (count.get(label) > max){
				max = count.get(label);
				classLabel = label;
			}
		}
		context.write(key, new Text(classLabel));
		
	}
}