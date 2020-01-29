package com.nisum;

import com.datastax.spark.connector.BatchSize;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class App {
    public static void main(String[] args){
        //String fileName="D:\\Practice\\Copient_Assignments\\chat.csv";
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
        JavaRDD<Chat> dataRdd=javaSparkContext.textFile(fileName)
                                                    .filter(line->line!=null&&!line .trim().equals("")&&! line.contains("order_id"))
                                                    .map(line->line.replaceAll("\"",""))
                                                    .map(App::mapToRow);
        CassandraJavaUtil.javaFunctions(dataRdd)
                .writerBuilder("vocabulary", "chat", CassandraJavaUtil.mapToRow(Chat.class))
                .saveToCassandra();

       /* System.out.println("------------------------------------");
        System.out.println("dataRdd:"+dataRdd);
        StructType schema= new StructType(new StructField[]{
                                            new StructField("order_id", DataTypes.StringType,true, Metadata.empty()),
                                            new StructField("timeStamp", DataTypes.StringType,true, Metadata.empty()),
                                            new StructField("username", DataTypes.StringType,true, Metadata.empty()),
                                            new StructField("message", DataTypes.StringType,true, Metadata.empty())
                                        });
        System.out.println("------------------------------------");
        System.out.println("schema:"+schema);
        //Dataset<Row> dataSet=spark.createDataFrame(dataRdd,schema);
        System.out.println("------------------------------------");
       // System.out.println("collectAsList"+dataSet.collectAsList());
        //dataSet.foreach(System.out::println);*/
    }

    public static Chat mapToRow(String line){
        String timeStam="";
        String username="";
        String message="";
        String[] arr=line.split("\\,");
        System.out.println("----------------------------------------------------");
        System.out.println("line:---------"+line);
        String timeStampMessgae=((String)arr[1]).trim();
        if(timeStampMessgae.indexOf("]")>-1){
            String[] arrMsg=timeStampMessgae.split("\\]");
            if(arrMsg.length==1){
                timeStam=null;
                username=null;
                message=timeStampMessgae;
            }else {
                timeStam = arrMsg[0].split("\\[")[1];
                username = (arrMsg[1].indexOf(">") > -1 && arrMsg[1].indexOf("<") > -1) ? arrMsg[1].split(">")[0].split("\\<")[1] : null;
                message = (username == null) ? arrMsg[1] : (arrMsg[1].split(">").length > 1) ? arrMsg[1].split(">")[1] : "";
                if (arrMsg.length == 3) {
                    message = message + "]" + arrMsg[2];
                }
            }
        }else{
            timeStam=null;
            username=(timeStampMessgae.indexOf(">")>-1&&timeStampMessgae.indexOf("<")>-1)?timeStampMessgae.split(">")[0].split("\\<")[1]:null;
            message=(username==null)?timeStampMessgae:timeStampMessgae.split(">")[1];
        }

        if(arr.length==3){
            message=message+","+arr[2];
        }
       // System.out.println("id:"+arr[0]+"--timeStamp:"+timeStam+"--username:"+username+"--message"+message);
                return new Chat(arr[0],timeStam,username,message);
    }
}
