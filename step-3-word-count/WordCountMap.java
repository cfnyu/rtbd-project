import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMap
	extends Mapper<LongWritable, Text, Text, Text> {
 
    @Override
    public void map(LongWritable key, Text value, Context context) 
           throws IOException, InterruptedException {

        String line = value.toString();
        String[] values = line.split(":");
        String dateRange = values[0];
		
        for (int v = 1; v < values.length; v++) {
            String[] messages = values[v].split(",");

	    for (int m = 0; m < messages.length; m++) {
	        String[] tokens = messages[m].split(" ");

                for (int t = 0; t < tokens.length; t++) {
                    String token = tokens[t];
		    context.write(new Text(dateRange + ":" + token), new Text("1"));
                }
            }
        }
    }
}

