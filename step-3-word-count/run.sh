#!/bin/bash

set -e

if [ "$#" -ne 2 ]; then
    echo "USAGE: run.sh <input directory> <output directory>"
    exit 1
fi

javac -classpath `yarn classpath` -d . WordCountMap.java
javac -classpath `yarn classpath` -d . WordCountReduce.java
javac -classpath `yarn classpath`:. -d . WordCountMR.java

jar -cvf wordCount.jar *.class

hadoop jar wordCount.jar WordCountMR $1 $2

