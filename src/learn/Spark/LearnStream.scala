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
import org.apache.spark.storage.StorageLevel
import scala.io.Source
import java.net.ServerSocket
import java.io.PrintWriter

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

   //读取套接字流;
   // 通过Socket端口监听并接收数据，然后进行相应处理。
   def  socketStream(hostname:String="local",port:Int=9999): Unit ={
      StreamingExamples.setStreamingLogLevels()
      val conf=new SparkConf().setAppName("LearnStream").setMaster("local[2]")
      val ssc=new StreamingContext(conf,Seconds(1))
      //StorageLevel.MEMORY_AND_DISK_SER是RDD的存储级别中的一个。内存不足时，存储在磁盘上
      val lines=ssc.socketTextStream(hostname,port.toInt,StorageLevel.MEMORY_AND_DISK_SER)
      val words=lines.flatMap(_.split(" "))
      val wordCount=words.map(x=>(x,1)).reduceByKey(_+_)
      wordCount.print()
      ssc.start()
      ssc.awaitTermination()
   }

   def index(length:Int):Int={
      val rdm=new java.util.Random
      rdm.nextInt(length)
   }

   //读取文件，用来产生Socket数据源
   def dataSourceSocket(filename:String,port:Int=9999): Unit ={
      val lines=Source.fromFile(filename).getLines().toList
      val rowCount=lines.length
      val lisenter=new ServerSocket(port.toInt)

      while (true){
         val socket=lisenter.accept()
         new Thread(){
            override def run(){
               val out=new PrintWriter(socket.getOutputStream,true)
               while (true){
                  Thread.sleep(3)
                  val content= lines(index(rowCount))
                  println(content)
                  out.write(content+"\n")
                  out.flush()
               }
               socket.close()
            }
         }.start()
      }
   }

}
