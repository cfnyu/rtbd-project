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
        String stop_expr = "^([0-9]|in|the|at|a|with|or|have|then|if|while|for|do|try|end|import|has|update|updated|updating|skipevent|field|icons|load|same|since|tag|pull|push|back|also|context|content|current|title|version|adding|add|added|delete|deleting|deleted|being|help|key|value|output|keys|values|outputs|than|branch|move|via|as|to|merge|from|and|fix|on|master|commit|emacs|code|fixed|use|not|fixed|by|see|be|time|changes|that|now|fixes|de|readme|it|-|of|new|is|when|some|more|support|up|change|all|latest|you|using|user|unused|text|run|return|project|prepare|other|only|many|line|links|link|linked|language|issues|issue|instead|initial|info|dev|developer|development|remove|docs|doc|documentation|things|try|install|handling|after|loading|right|function|tweak|trying|null|undefined|int|float|double|string|additional|which|number|dependencies|dependency|setup|logic|handle|handled|layout|modified|longer|problem|data|name|client|again|my|mine|removed|app|but|fixing|works|work|worked|working|broke|broken|screen|just|implement|implemented|implementing|we|us|our|folder|directory|page|font|there|multiple|getting|get|got|gotten|forgotten|better|best|full|always|section|april|renamed|because|one|own|proper|renamed|requirements|custom|base|lib|match|pass|through|ignore|game|games|adds|add|added|small|crash|feat|specific|simple|still|old|message|will|without|conflict|created|check|model|login|clean|allow|parameter|profile|theme|avoid|checks|errors|error|missing|installation|classes|way|minor|examples|improve|digital|different|site|tweaks)*$";

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
