import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.NullWritable;

public class WordCountMR {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: WordCountMR <input path> <output path>");
            System.exit(-1);
        }
		
        @SuppressWarnings("deprecation")
        Job job = new Job();
        job.setJarByClass(WordCountMR.class);
        job.setJobName("Word Count");
		
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);
		
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
		
        job.setMapOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}


