# Hive Import

## Overview

The [GitHub Dataset](https://bigquery.cloud.google.com/dataset/bigquery-public-data:github_repos), downloaded from [Google BigQuery](https://bigquery.cloud.google.com), provided us with almost a terabyte of data. In order to manipulate that data we chose to use Hive.

## Dependencies

Our original dataset was downloaded as several large JSON files (approx. 700 MB to 1GB in size). In order to load that data into Hive we used a tool created by [Roberto Congiu](https://github.com/rcongiu). Before getting started download both jar files from the [hive-json-serde](http://www.congiu.net/hive-json-serde/1.3.8/cdh5/) website.

> [json-serde-1.3.8-jar-with-dependencies.jar](http://www.congiu.net/hive-json-serde/1.3.8/cdh5/json-serde-1.3.8-jar-with-dependencies.jar)

> [json-udf-1.3.8-jar-with-dependencies.jar](http://www.congiu.net/hive-json-serde/1.3.8/cdh5/json-udf-1.3.8-jar-with-dependencies.jar)

Once in `beeline` and connected to the server, use the following commands to load the jar files:

```java
ADD JAR hdfs:///user/cf86/json-udf-1.3.8-jar-with-dependencies.jar;
ADD JAR hdfs:///user/cf86/json-serde-1.3.6-SNAPSHOT-jar-with-dependencies.jar;
```
## Commands used to load the data into Hive

Start by selecting a database to load the data

```sql
use YOUR_DATABASE;
```

Next step is to create the table in preparation for the data import
```sql
CREATE TABLE new_table (commit string, tree string,parent array<string>, author map<string,string>, committer map<string,string>, subject string, message string, trailer array<struct<string:string>>,difference array<string>, repo_name array<string>) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;
```

Finally load all the data files into the new table

```sql
LOAD DATA INPATH 'hdfs://<PATH TO FILE>' INTO TABLE new_table;
LOAD DATA INPATH 'hdfs://<PATH TO FILE>' INTO TABLE new_table;
LOAD DATA INPATH 'hdfs://<PATH TO FILE>' INTO TABLE new_table;
```

Once the data has been imported into Hive we can extract the data in a usable form

```sql
INSERT OVERWRITE DIRECTORY '/PATH/IN/HDFS' ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' SELECT lan.repo_name, author["date"], commit, language FROM new_table cmt INNER JOIN languages lan ON cmt.repo_name[0] = lan.repo_name WHERE size(language) > 0;
```
