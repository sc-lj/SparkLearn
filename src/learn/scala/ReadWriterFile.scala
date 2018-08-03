package learn.scala

//参考网站：http://dblab.xmu.edu.cn/blog/spark/

/*
* 读写文件
* Scala需要使用java.io.PrintWriter实现把数据写入到文本文件
*
* 使用Scala.io.Source的getLines方法实现对文件中所有行的读取。
*
* */

import java.io.{PrintWriter,FileWriter}
import scala.io.Source
import java.io.RandomAccessFile
import java.io.{File,FileInputStream}

import org.apache.spark.{SparkConf,SparkContext}

object ReadWriterFile {
    def main(args: Array[String]): Unit = {


    }

    def writeFile(): Unit ={
        //文件保存包 SparkLearn 文件夹下的，而不是保存在该代码所在文件夹，而是该项目所在的文件夹下的，SparkLearn
        val out=new PrintWriter("out.txt")
        for (i<-0 to 10) out.println(i)
        out.close()

        //true是在文件末尾追加写入，默认为false
        val out1=new FileWriter("out1.txt",true)
        for (i<-1 to 6) out1.write(i.toString)
        out1.close()
    }

    def writeRandomAccessFile(): Unit ={
        //必须要指定的该对象的操作模式，r rw等
        //该对象在实例化时，如果要操作的文件不存在，会自动建立。
        //如果要操作的文件存在，则不会建立。
        //如果存在的文件有数据，那么在没有指定指针位置的情况下，写入数据，会将文件开头的数据覆盖。
        val randomFile=new RandomAccessFile("random.txt","r")
        val fileLength=randomFile.length()//得到文件长度

        randomFile.seek(fileLength)//指针指向文件末尾
        for(i<- 'a' to 'g') randomFile.writeBytes(i.toString)
        randomFile.close()

    }


    def readFile(): Unit ={
        //实现对文件中所有行的读取。
        val input=Source.fromFile("out.txt","utf-8")
        val lines = input.getLines //返回的结果是一个迭代器
        for ( line <-lines) println(line)
        //或者也可以对迭代器应用toArray或toBuffer方法，将这些行放到数组或数组缓冲中
        val linesArray = input.getLines.toArray
        val contents = input.mkString
        input.close()//关闭文件
    }

    //读取二进制文件
    def readBytes(filename:String): Unit ={
        val file=new File(filename)
        val in = new FileInputStream(file)
        val bytes=new Array[Byte](file.length.toInt)
        in.read(bytes)
        in.close()
    }

    def readOtherSource(): Unit ={
        val source1=Source.fromURL("https://blog.csdn.net/power0405hf/article/details/50441836","utf-8")
        val source2 = Source.fromString("Hello, World!")//从给定的字符串读取——对调试很有用
        val source3 = Source.stdin//从标准输入读取
    }


    //scala读取csv文件，
    //需要先定义一个类来读取csv文件的header
    class SimpleCsvHeader(header:Array[String]) extends Serializable{
        val index=header.zipWithIndex.toMap

        def apply(array: Array[String],key:String):String=array(index(key))
    }

    def readCsvFile(filename:String): Unit ={
        val conf=new SparkConf().setMaster("local").setAppName("ReadWriterFile")
        val sc=new SparkContext(conf)
        val csv=sc.textFile("out.csv")
        val data=csv.map(line=>line.split(",").map(elem=>elem.trim))
        val header=new SimpleCsvHeader(data.take(1)(0))// 取出第一行来创建header
        val rows=data.filter(line=>header(line,"user")!="user")// 去掉header
        val users=rows.map(row=>header(row,"user"))
        val usersByHits=rows.map(row=>header(row,"user")->header(row,"hits").toInt)
    }


}
