## Grouping Hive Data

### Overview
Once the data from Hive has been exported to HDFS, you can see that the output is not in the exact desired format. Below is a sample:

```
0xKione/jquery-input-grid,2016-09-16 01:02:30 UTC,3ac2752b6dbeae6515b7132629696d371493ad39,bytes17773nameCSSbytes74781nameHTMLbytes45884nameJavaScript,Adding an HTML page that lays out the functionality of the library, renaming and fixing a couple minor issues, adding gulpfile.js to the bower ignore
```

Given this data, we want to 'group' commit messages and repository languages from the same month/year into a long, comma-separated string. Here is the above example converted to our desired input, through the `GroupData` MapReduce job:

`September 2016: CSS HTML Javascript Adding an HTML page that lays out the functionality of the library renaming and fixing a couple minor issues, adding gulpfile.js to the bower ignore, .....`

where the `...` represents (many!) other message/language repository strings that were committed in September 2016.

### Build & Run
Given an HDFS input directory of `hive-cleaned` (i.e. the output directory from step 1):
```
./run.sh hive-cleaned groupedData
```

