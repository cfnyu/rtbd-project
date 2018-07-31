// Radhika Mattoo, rm3485@nyu.edu
package rm3485;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configuration;
public class Analytic {

  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
        System.err.println("Usage: Analytic <input path> <output path> <cacheFile path>");
        System.exit(-1);
    }

    // Cache file is the third argument
    Configuration conf = new Configuration();
    DistributedCache.addCacheFile(new Path(args[2]).toUri(), conf);

    Job job = new Job(conf);
    job.setJarByClass(Analytic.class);
    job.setJobName("Analytic");
    job.setNumReduceTasks(1);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(AnalyticMapper.class);
    job.setReducerClass(AnalyticReducer.class);

    job.setMapOutputValueClass(Text.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);

    System.exit( job.waitForCompletion(true) ? 0 : 1 );
  }
}
