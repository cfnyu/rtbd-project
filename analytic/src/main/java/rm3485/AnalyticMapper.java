// Radhika Mattoo, rm3485@nyu.edu
package rm3485;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;
import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import java.lang.StringBuilder;

public class AnalyticMapper extends Mapper<LongWritable, Text, Text, Text> {
  private ArrayList<String> topics = new ArrayList<String>();
  private double topicEqualsTag = 0.3;
  private double topicInBody = 0.2;
  private double topicInTitle = 0.2;

  // Score range optimal in 7 - 20
  private double scoreInRange = 0.3;
  private double scoreBelowRange = 0.1;
  private double scoreAboveRange = 0.2;

  @Override
  protected void setup(Context context) throws IOException,InterruptedException{
    try{
      Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());
      if(files.length > 0){
        for(Path file : files){
          BufferedReader bf = new BufferedReader(new FileReader(file.toString()));
          String line;
          while((line = bf.readLine()) != null){
            topics.add(line.trim());
          }
        }
      }
    }catch(IOException e){
      System.err.println("Exception while reading cache file " + e.getMessage());
    }
  }

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    // Get StackOverflow line data
    String line = value.toString();
    String[] split = line.split(",");
    if(split.length >= 5){
      String questionId = split[0];
      int score = Integer.parseInt(split[1]);
      String title = split[2];
      String[] tagList = split[3].trim().split(" ");

      // Rebuild body after "," split
      StringBuilder bodyBuilder = new StringBuilder();
      if(split.length > 5){
        for(int i = 4; i < split.length; i++){
          bodyBuilder.append(split[i]);
        }
      }else{
        bodyBuilder.append(split[4]);
      }
      String body = bodyBuilder.toString();

      // Split tags by space and add to arraylist
      ArrayList<String> tags = new ArrayList<String>();
      if(tagList.length > 1){ // several tags
        for(String itm : tagList){
          tags.add(itm);
        }
      }else{ //only one tag
        tags.add(tagList[0]);
      }

      // For each topic, generate a weight for the SO question
      Iterator<String> it = topics.iterator();
      while(it.hasNext()){
        String[] data = it.next().split(":"); //date:topic
        String date = data[0].trim();
        String topic = data[1].trim();
        String count = data[2].trim();
        double weight = 0.0;

        if(body.contains(topic)){
          weight += topicInBody;
        }
        if(title.contains(topic)){
          weight += topicInTitle;
        }
        Iterator<String> tags_it = tags.iterator();
        while(tags_it.hasNext()){
          String tag = tags_it.next();
          if(topic.equals(tag)){
            weight += topicEqualsTag;
          }
        }
        if(score >= 7 && score <= 20){
          weight += scoreInRange;
        }else if(score < 7){
          weight += scoreBelowRange;
        }else{
          weight += scoreAboveRange;
        }

        String weightString = Double.toString(weight);
        if(weight > 0.0 && weightString.length() > 0  && questionId.length() > 0){
          String output_value =  weightString + ":" + questionId;
          String output_key = date + ":" + topic;
          context.write(new Text(output_key), new Text(output_value));
        }
      }
    }
  }
}
