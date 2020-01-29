package com.nisum;


import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.collection.immutable.List;

public class ReadCassendraFromSpark {
    public static void main(String ...vr){
        SparkConf conf=new SparkConf().setAppName("ReadCassandraData");
        JavaSparkContext javaSparkContext=new JavaSparkContext(conf);
        SparkSession spark= SparkSession
                .builder()
                .config(conf)
                .config("spark.sql.warehouse.dir","/file:D:/tmp")
                .config("spark.cassandra.connection.host","127.0.0.1")
                .config("spark.cassandra.connection.port","9042")
                .getOrCreate();
        String keySpaceName = "vocabulary";
        String tableName = "contactlist";

        CassandraJavaRDD<CassandraRow> cassandraRDD = CassandraJavaUtil.javaFunctions(javaSparkContext).cassandraTable(keySpaceName, tableName);
        JavaRDD<ContactList> contactListRDD =  cassandraRDD.map(row->{
            return new ContactList(row.getString("department"),
                                   row.getString("firstname"),
                                   row.getString("lastname"),
                                   row.getString("primarycontact"),
                                   row.getString("emailid"),
                                   row.getString("phno"),
                                   row.getString("role"));
        });
        System.out.println("---------------------------");
        System.out.println("---------list-------------"+contactListRDD.collect().size());
        contactListRDD.map(obj->obj.toString()).collect().stream().forEach(System.out::println);
                }
}
