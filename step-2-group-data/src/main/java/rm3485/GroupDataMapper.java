// Radhika Mattoo, rm3485@nyu.edu
package rm3485;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;
import java.io.*;
import java.lang.StringBuilder;
import java.util.regex.Pattern;

public class GroupDataMapper extends Mapper<LongWritable, Text, Text, Text> {
  String[] dateGroups = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();
    // 0/paper.js,2011-11-26 10:55:19 UTC,c3798d9f957e0b0d93aa35337a6abd4beb8dc872,,JavaScript,Shell,Make baseItem to last optional parameter of _getBounds.
    String[] data = line.split(",");
    int length = data.length;
    if(length >= 5){
      try{
        String repo_name = data[0];
        String date = data[1].split(" ")[0];
        String commit_id = data[2];
        String langString = data[3];
        String body;

        // Fix body data
        if(length > 5){ // There's a comma in the message
          StringBuilder sb = new StringBuilder();
          for(int i = 4; i < length; i++){
            String item = data[i].replace("\n", "").replace("\r", "").replace(",", " ").replace("\t", " ").trim();
            sb.append(item + " ");
          }
          body = sb.toString().trim();
        }else{
          body = data[4].replace("\n", "").replace("\r", "").replace(",", " ").replace("\t", " ").trim();
        }

        // TODO: Remove the if statement?
        if(body.length() > 10){
          String langsReplace = langString.trim().replaceAll("bytes.{1,20}name", ",");
          String[] langs = langsReplace.split(",");
          StringBuilder lang_sb = new StringBuilder();
          for(String l: langs){
            if(l.length() > 0){
              lang_sb.append(l + " ");
            }
          }
          String languages = lang_sb.toString().trim();

          // Find group that the date belongs to: 2011-11-26
          String[] dates = date.split("-");
          int year = Integer.parseInt(dates[0].trim());
          if(year >= 2017){
            int month = Integer.parseInt(dates[1].trim());
            int month_index = month - 1;
            String group = dateGroups[month_index] + " " + Integer.toString(year);

            // Create value output string
            String output = languages + " " + body;
            context.write(new Text(group), new Text(output));
          }
        }
      }catch(Exception e){
        System.out.println("ERROR ON: " + line);
      }


    }

  }
}
