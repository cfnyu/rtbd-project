# Stack Overflow and GitHub Data Analysis

## Authors
Christian Fernandez, cf86@nyu.edu

Radhika Mattoo, rm3485@nyu.edu

## Overview

Programmers alike do not inherently have all the answers when it comes to software and engineering. Thus, popular websites such as Stack Overflow and GitHub provide a platform for engineers to initiate discussions, raise questions, and crowd source answers. This project aims to analyze GitHub commit messages via a traditional word count to create a dated distribution of popular commit topics. We will then use this distribution to apply a weighted search of related Stack Overflow questions. Finally, these dated topics and their corresponding questions will be collated into a simple UI to assist programmers in accelerating their own development process. 

## Requirements

This code was run on a Hadoop Cluster running hadoop version `2.6.0-cdh5.11.1`. In order to compile this project you will need the following: 

- Hadoop version: 2.6.0-cdh5.11.1
- Maven version: 3.3.3
- [Jar Dependencies](step-1-hive-import/README.md#dependencies) for Hive, more information in the Step 1 hive directory
- Java: java version "1.8.0_152"
        Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
        Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)

## Directory Structure
###### More details, for each step, can be found in the individual step folders README files

- [step-1-hive-import](step-1-hive-import/README.md): This directory contains a README file with the steps taken to import the GitHub data into HDFS using Hive.
- [step-2-group-data](step-2-group-data/README.md): This directory contains the code used to process the output of step 1. 
- [step-3-word-count](step-3-word-count/README.md): This directory contains the code used to count all words for each GitHub comment that was grouped in step 2.
- [step-4-process-topics](step-4-process-topics/README.md): This last directory contains the final code to process the GitHub data and find the associations with the Stack Overflow data.

## Build & Run

All step directories, with the exception of step 1, have individual `run.sh` shell scripts which include all commands needed to build and execute each step.

## Data Locations

- [Step 1 - Hive Import](step-1-hive-import/README.md)
  - Input: From Google, stored /user/cf86/bigQuery
  - Output: From hive, stored in /user/cf86/hive-cleaned

- [Step 2 - Group Data](step-2-group-data/README.md)
  - Input: From hive /user/cf86/hive-cleaned
  - Output: From MapReduce, /user/rm3485/groupedData

- [Step 3 - Word Count](step-3-word-count/README.md)
  - Input: From step 2, /user/rm3485/groupedData
  - Output: From MapReduce, /user/cf86/output/topic_data

- [Step 4 - Process Topics](step-4-process-topics/README.md)
  - Input: Stack Overflow Posts (Questions), /user/rm3485/posts-clean
  - Output: From MapReduce, /user/rm3485/finalTest
  - Cached File: From Word Count (Step 3), /user/cf86/output/topic_data

Final [output file](output-data.txt) can also be found in this repository named `output-data.txt`
