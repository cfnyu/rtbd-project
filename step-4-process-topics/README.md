## Grouping Hive Data

### Overview
The final step of the project is associating 'topics' found from the word count and associating them with Stack Overflow questions via a weighted search.

Since we need to iterate over 2 datasets at once, Hadoop's [Distributed Cache](https://hadoop.apache.org/docs/r2.6.3/api/org/apache/hadoop/filecache/DistributedCache.html) was used to hold the topic word count.

Inside the map code, we take a single Stack Overflow question and calculate its weight for every topic. The weights are valued as such:

```
Topic in question body: 0.3
Topic in question title: 0.3
Topic equals one of the question's tags: 0.4
```
Since we want to maintain our date grouping, we must keep the date as the key. However, to make our lives easier, we attach the topic name to the key so the reducer can sort questions (grouped by date and topic) by their given weights.

In the reducer, we split the topic from the date in the key, and sort the corresponding question list by their weights. We then output  `date:topic:qId1,qId2,....qIdn` to HDFS for a file that contains all the data we need, but is still small enough to download and use in our UI.


### Build & Run
Given an input HDFS directory of `posts-clean` and the output file from step 3 stored as `output/topic_data`:

```
./run.sh posts-clean finalTest output/topic_data
```
aka
```
./run.sh <inputDir> <outputDir> <cachedFile>
```

The final part file was then downloaded locally as `output-data.txt`.
