#!/bin/bash

set -e

POST_FILES=$1
OUTPUT_DIR=$2
TOPIC_DATA=$3

check_requirements() {
    if ! type "hdfs" > /dev/null; 
    then 
        echo "hdfs command not found.";
        exit 1
    fi
}

setup_env() {
    if [ -d "./target" ]; then
        echo "Removing target directory"
        rm -rf target/
    fi
}

setup_maven() { 
    if ! type "mvn" > /dev/null; then 
        echo "Loading Maven Module"
        module load maven/3.3.3
    fi
}

setup() {
    setup_env
    setup_maven
    check_requirements
}

if [ "$#" -ne 3 ]; then
    echo "USAGE: run.sh <post directory> <output directory> <topic data>"
    exit 1
fi

setup

echo "Compiling code using maven"
mvn compile && mvn package

echo "Executing analytic jar file using Hadoop"
hadoop jar target/analytic-1.0-SNAPSHOT-jar-with-dependencies.jar $POST_FILES $OUTPUT_DIR $TOPIC_DATA

