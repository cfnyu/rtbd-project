#!/bin/bash

set -e

HIVE_OUTPUT=$1
GROUP_DATA=$2

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

if [ "$#" -ne 2 ]; then
    echo "USAGE: run.sh <output hive data path> <output directory>"
    exit 1
fi

setup

echo "Compiling code using maven"
mvn compile && mvn package

echo "Executing analytic jar file using Hadoop"
hadoop jar target/group-data-1.0-SNAPSHOT-jar-with-dependencies.jar $HIVE_OUTPUT $GROUP_DATA

