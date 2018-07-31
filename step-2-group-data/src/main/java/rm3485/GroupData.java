// Radhika Mattoo, rm3485@nyu.edu
package rm3485;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class GroupData {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
        System.err.println("Usage: Stackoverflow <input path> <output path>");
        System.exit(-1);
    }
    Job job = new Job();
    job.setJarByClass(GroupData.class);
    job.setJobName("GroupData");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(GroupDataMapper.class);
    job.setReducerClass(GroupDataReducer.class);

    job.setMapOutputValueClass(Text.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);

    System.exit( job.waitForCompletion(true) ? 0 : 1 );
  }
}
