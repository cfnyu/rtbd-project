import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.NullWritable;

public class WordCountReduce
	extends Reducer<Text, Text, Text, NullWritable> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
        int minValue = 100;
        String valid_expr = "^[a-zA-Z0-9_-]*$";
        String stop_expr = "^([0-9]|in|the|at|a|with|or|have|then|if|while|for|dotry|end|import|has|update|updated|updating|skipevent|field|icons|load|same|since|tag|pull|push|back|also|context|content|current|title|version|adding|add|added|delete|deleting|deleted|being|help|key|value|output|keys|values|outputs|than|branch|move|via|as|to|merge|from|and|fix|on|master|commit|emacs|code|fixed|use|not|fixed|by|see|be|time|changes|that|now|fixes|de|readme|it|-|of|new|is|when|some|more|support|up|change|all|latest|you|using|user|unused|text|run|return|project|prepare|other|only|many|line|links|link|linked|language|issues|issue|instead|initial|info)*$";

        int sumValue = 0;
        String[] keyData = key.toString().split(":");

        if (keyData.length >= 2) {
           String dateRange = keyData[0];
           String token = keyData[1].toLowerCase();

           for (Text value : values) {
              sumValue += Integer.parseInt(value.toString());
           }

	   if (sumValue > 100 && token.matches(valid_expr) && !token.matches(stop_expr)) {
              context.write(new Text(dateRange + ":" + token + ":" + String.valueOf(sumValue)), NullWritable.get());
           }
        }
    }
}
