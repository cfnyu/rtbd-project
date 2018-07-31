```
mvn compile
mvn package
hadoop jar analytic-1.0-SNAPSHOT-jar-with-dependencies.jar posts-clean/part-r-00000 test hdfs:///user/rm3485/county.csv
```
