// Radhika Mattoo, rm3485@nyu.edu
package rm3485;
import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.*;
import java.io.*;
import java.lang.NumberFormatException;
import java.util.*;
import java.lang.StringBuilder;

public class AnalyticReducer extends Reducer<Text, Text, Text, NullWritable> {

  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
    String data_string = key.toString();
    String[] dataArr = data_string.split(":");
    if(dataArr.length == 2){
      String date = dataArr[0];
      String topic = dataArr[1];
      int max = 50;
      // Date:Topic, ["weight:id", "weight:id"]
      ArrayList<String> data = new ArrayList<String>();
      for(Text value: values){
        data.add(value.toString().trim());
      }
      // Sort by weight
      Collections.sort(data, (o1, o2) -> Double.compare(Double.parseDouble(o1.split(":")[0]), Double.parseDouble(o2.split(":")[0]) ) );
      Collections.reverse(data);

      int size = data.size() > max ? max : data.size();
      StringBuilder sb = new StringBuilder(date + ":" + topic + ":");
      for(int i = 0; i < size; i++){
        String id = data.get(i).split(":")[1];
        sb.append(id + ",");
      }
      String output = sb.toString();
      String final_output = output.substring(0, output.length() - 1);
      context.write(new Text(final_output), NullWritable.get());
    }
  }
}
