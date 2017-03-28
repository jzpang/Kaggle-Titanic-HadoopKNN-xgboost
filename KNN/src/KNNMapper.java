import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class KNNMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	//save test data as arraylist
	private List <testPoint> testData = new ArrayList <testPoint> ();

    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
            throws java.io.IOException, InterruptedException {

        context.setStatus(key.toString());
        
        for ( testPoint testCase : testData) {
            Text keyOut = new Text();
            //set keyOut as test data
            keyOut.set(testCase.getTest());
            Text valueOut = new Text();
    		String index = context.getConfiguration().get("continues_index");
            //set valueOut as 'label from train' + ',' +'distance'
            valueOut.set(testCase.getTrainAndDistance(value.toString(), index));
            //System.out.println(testCase.getTest());
            //System.out.println(testCase.getTrainAndDistance(value.toString()));
            context.write(keyOut, valueOut);
        }
    }



    protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
            throws java.io.IOException, InterruptedException {

        // load the test data
        FileSystem fs = FileSystem.get(context.getConfiguration());
        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(new Path(context.getConfiguration().get(
                "test")))));
        String line = br.readLine();
        //int count = 0;
        while (line != null) {
        	testPoint temp = new testPoint(line);
        	testData.add(temp);
            //Vector2<String, SparseVector> v = br.readLine( line);
            //test.add(new Vector2<String, SparseVector>(v.getV1(), v.getV2()));
            line = br.readLine();
        }
        br.close();
        System.out.println("done.");
    }
}
