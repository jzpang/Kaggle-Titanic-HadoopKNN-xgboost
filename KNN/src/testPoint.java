import java.util.Arrays;
import java.util.HashSet;

public class testPoint {
	protected String line;
	protected String[] test;
	protected double[] value_test;
	
	
	

	
	public testPoint(){
	}

	public testPoint(String s) {
		this.line = s;
		this.test = line.split(",");
		this.value_test = Arrays.stream(test).mapToDouble(Double::parseDouble).toArray();

	}
	
	protected String getTest(){
		return line;
	}
	
		
	protected String getTrainAndDistance(String s, String index) {
		String[] sample = s.split(",");
		String label = sample[sample.length-1];
		String[] train = Arrays.copyOf(sample, sample.length-1);
		
		HashSet<Integer> continous_featureIndex = new HashSet<Integer>();
		
		if (index.equals("NULL")){
		}
		else{	
			String[] indexes = index.split(",");
			for(String tmp: indexes){
				continous_featureIndex.add(Integer.parseInt(tmp));
			}
		}
		
		double[] value_train =  Arrays.stream(train).mapToDouble(Double::parseDouble).toArray();
		double temp = 0d;
		
		for (int i = 1; i< value_train.length; i++){
			if (continous_featureIndex.contains(i)){
			//if (i==3 || i==7){//If feature is continous, 3 is index of 'Age', 7 is index of 'Fare'
				temp += (value_train[i] - value_test[i]) * (value_train[i] - value_test[i]);
			} else{
				if (Double.compare(value_train[i], value_test[i]) !=0 ){//discrete value, if not equal, dist+1
					temp += 1;
				}
			}
		}
		double distance = Math.sqrt(temp);
		return label + "," + Double.toString(distance);
	}
	
	
}
