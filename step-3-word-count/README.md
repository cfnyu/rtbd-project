## Word Count

### Overview
Now that we have the data grouped by date, we want to find the distribution of words for a given date, and output that to HDFS. Thus, we use the classic Word Count MapReduce job. The output from step 2 should look something like this:

`September 2016: CSS HTML Javascript Adding an HTML page that lays out the functionality of the library renaming and fixing a couple minor issues, adding gulpfile.js to the bower ignore, .....`

And we would like the output from this MR job to look like:

```
September 2016:CSS:<count>
September 2016:HTML:<count>
September 2016:Javascript:<count>
....
```
and so on. We filter out stop words in the reducer like `an` and `that`, which would have a huge word count no matter the date.

### Build and Run
Given an HDFS input directory of groupedData (i.e. the output from step 2):

```
./run.sh groupedData output
```

Once the MR job has run, we need to merge the part files into a single file for easier ingestion (into the Distributed Cache) for step 4
```
hdfs dfs -getmerge output/* topic_data
hdfs dfs -put topic_data output/
```
