#!/bin/bash

set -e

javac -classpath `yarn classpath` -d . WordCountMap.java
javac -classpath `yarn classpath` -d . WordCountReduce.java
javac -classpath `yarn classpath`:. -d . WordCountMR.java

jar -cvf wordCount.jar *.class

hadoop jar wordCount.jar WordCountMR /user/rm3485/groupedData output

