package com.nisum;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class AppContactList {

        public static void main(String[] args){
          String fileName=args[0];
        System.out.println("------------------------------------");
        System.out.println("file name"+fileName);

        SparkConf conf=new SparkConf().setAppName("ReadCsv");
        JavaSparkContext javaSparkContext=new JavaSparkContext(conf);
        SparkSession spark= SparkSession
                .builder()
                .config(conf)
                .config("spark.sql.warehouse.dir","/file:D:/tmp")
                .config("spark.cassandra.connection.host","127.0.0.1")
                .config("spark.cassandra.connection.port","9042")
                .getOrCreate();
        JavaRDD<ContactList> dataRdd=javaSparkContext.textFile(fileName)
                .filter(line->line!=null&&!line .trim().equals("")&&! line.contains("First Name"))
                .map(line->line.replaceAll("\"",""))
                .map(line->line.replaceAll(",",",~"))
                .map(AppContactList::mapToRow);
        CassandraJavaUtil.javaFunctions(dataRdd)
                .writerBuilder("vocabulary", "contactlist", CassandraJavaUtil.mapToRow(ContactList.class))
                .saveToCassandra();

    }

        public static  ContactList mapToRow(String line){
            line=line.replaceAll("~"," ");
            String[] arr=line.split("\\,");
            System.out.println("----------------------------------------------------");
            String email=(String)arr[4];
            System.out.println("line:---------"+email);
            return new  ContactList(((String)arr[0]).trim(),
                    ((String)arr[1]).trim(),
                    ((String)arr[2]).trim(),
                    ((String)arr[3]).trim(),
                    (email!=null&&!email.trim().equals("")?email.trim():"def@gmail.com"),
                    ((String)arr[5]).trim(),
                    ((String)arr[6]).trim());
       }

}
