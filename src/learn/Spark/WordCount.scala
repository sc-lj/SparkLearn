package learn.Spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {


      // 创建一个 spark context,传递进两个参数，一个是集群url，这里写的是local，这个特殊值可以让 Spark 运行在单机单线程上而无需连接到集群；
      // 一个是应用名，这里是wordcount，当连接到一个集群时，这个值可以帮助你在集群管理器的用户界面中找到你的应用。
      val conf=new SparkConf().setMaster("local").setAppName("wordcount")
      val sc =new SparkContext(conf)
      val inputfile = args(0)  "/Users/apple/IdeaProjects/SparkLearn/src/learn/Spark/new_sohu.txt"
      val outputFile = args(1)

      //读取传人的文件
      val input=sc.textFile(inputfile)

      // 把它切分成一个个单词
      val words=input.flatMap(line=>line.split("++"))

      //转换成键值对
      val counts=words.map(word=>(word,1)).reduceByKey{case (x,y)=>x+y}

      //将统计出来的单词总数存入一个文本文件，引发求值
      counts.saveAsTextFile(outputFile)
  }


}
