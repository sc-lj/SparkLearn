package learn.scala

/**
  *在 Scala 中，我们可以把定义的内联函数、方法的引用或静态方法传递给 Spark。但是所传递的函数及其引用的数据需要是可序列化的。
  *
  *
  *
  * */

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import java.io.File

object FunctionTransfer {
    def main(args: Array[String]): Unit = {
        val conf = new  SparkConf().setMaster("local").setAppName("FunctionTransfer")
        val sc = new SparkContext(conf)
        val path="file:///Users/apple/IdeaProjects/SparkLearn/src/learn/Spark/new_sohu.txt"

        val inputRDD = sc.textFile(path)
        val search=new  SearchFunction("++")
        val ref=search.getMatchesNoReference(inputRDD)
        println(ref.first())
        val nums = sc.parallelize(List(1, 2, 3, 4))
        val squared=nums.map(x=>x*x).collect()
        println(squared.mkString(","))

    }

    class SearchFunction(val query: String){
         def isMatch(s: String):Boolean={
              s.contains(query)
         }

         def getMatchesFunctionReference(rdd: RDD[String]): RDD[Boolean] = {
             // 问题:"isMatch"表示"this.isMatch"，因此我们要传递整个"this"
             rdd.map(isMatch)
         }

         def getMatchesFieldReference(rdd: RDD[String]): RDD[Array[String]] = {
           // 问题:"query"表示"this.query"，因此我们要传递整个"this"
             rdd.map(_.split(query))
         }

         def getMatchesNoReference(rdd: RDD[String]): RDD[Array[String]] = {
            // 安全:只把我们需要的字段拿出来放入局部变量中
            val query_ = this.query
            rdd.map(_.split(query_))
         }
    }
}
