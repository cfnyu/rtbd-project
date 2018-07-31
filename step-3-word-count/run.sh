#!/bin/bash

set -e

if [ "$#" -ne 2 ]; then
    echo "USAGE: run.sh <input directory> <output directory>"
    exit 1
fi


INPUT_PATH=$1
OUTPUT_PATH=$2

check_requirements() {
    if ! type "hdfs" > /dev/null;
    then
        echo "hdfs command not found.";
        exit 1
    fi
}

setup_maven() {
    if ! type "mvn" > /dev/null; then
        echo "Loading Maven Module"
        module load maven/3.3.3
    fi
}

setup() {
    setup_maven
    check_requirements
}

if [ "$#" -ne 2 ]; then
    echo "USAGE: run.sh <input path> <output path>"
    exit 1
fi

setup

echo "Executing analytic jar file using Hadoop"
hadoop jar target/group-data-1.0-SNAPSHOT-jar-with-dependencies.jar $POST_FILES $OUTPUT_DIR $TOPIC_DATA








javac -classpath `yarn classpath` -d . WordCountMap.java
javac -classpath `yarn classpath` -d . WordCountReduce.java
javac -classpath `yarn classpath`:. -d . WordCountMR.java

jar -cvf wordCount.jar *.class

hadoop jar wordCount.jar WordCountMR $1 $2

