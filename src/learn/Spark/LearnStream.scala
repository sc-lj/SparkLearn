package learn.Spark


/*
* 编写Spark Streaming程序的基本步骤是：
* 1.通过创建输入DStream来定义输入源
* 2.通过对DStream应用转换操作和输出操作来定义流计算。
* 3.用streamingContext.start()来开始接收数据和处理流程。
* 4.通过streamingContext.awaitTermination()方法来等待处理结束（手动结束或因为错误而结束）。
* 5.可以通过streamingContext.stop()来手动结束流计算进程。
*
*
*
*
*/

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.streaming._

object LearnStream {
   def main(args: Array[String]): Unit = {


   }

   //读取文件流
   def fileStream(): Unit ={
      //创建StreamingContext对象
      //设置为本地运行模式，2个线程，一个监听，另一个处理数据
      val conf=new SparkConf().setAppName("LearnStream").setMaster("local[2]")
      //Seconds(1))表示间隔1秒钟就监听一次
      val src=new StreamingContext(conf,Seconds(1))
      //监听文件流,这是监听文件夹。只有当文件夹里新增文件时，才会进行统计
      val lines=src.textFileStream("./src/learn/File")
      val words=lines.map(line=>line.split(" "))
      val wordCounts=words.map(x=>(x,1)).reduceByKey(_ + _)
      wordCounts.print()
      src.start()
      src.awaitTermination()
   }




}
