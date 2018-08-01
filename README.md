# Stack Overflow and GitHub Data Analysis
> Authors: Christian Fernandez & Radhika Mattoo

## Directory Structure

## Build & Run

## Data Locations

Step 1 - Hive Import
    - Input: From google, stored /user/cf86/bigQuery
    - Output: From hive, stored in /user/cf86/hive_output

Step 2 - Group Data
    - Input: From hive /user/cf86/hive_output
    - Output: From MapReduce, /user/rm3485/groupedData

Step 3 - Word Count
    - Input: From step 2, /user/rm3485/groupedData
    - Output: From MapReduce, /user/cf86/output/topic_data

Step 4 - Process Topics
    - Input: Stack Overflow Posts (Questions), /user/rm3485/posts-clean
    - Output: From MapReduce, /user/rm3485/finalTest
    - Cached File: From Word Count (Step 3), /user/cf86/output/topic_data

Final output file can also be found in this repository named `output-data.txt`
