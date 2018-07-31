// Radhika Mattoo, rm3485@nyu.edu
package rm3485;
import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.*;
import java.lang.StringBuilder;
import java.io.*;
public class GroupDataReducer extends Reducer<Text, Text, Text, NullWritable> {

  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
    StringBuilder sb = new StringBuilder(key.toString() + ":");
    for(Text t : values){
      String val = t.toString();
      sb.append(val + ",");
    }
    String output = sb.toString();
    output = output.substring(0, output.length() - 1);
    context.write(new Text(output), NullWritable.get());
  }
}
