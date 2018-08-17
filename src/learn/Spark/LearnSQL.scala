package learn.Spark


/*
* Spark SQL采用的是DataFrame结构来进行SQL语句。
* 使用SparkSession来创建DataFrame。
*
* 将RDD转换得到DataFrame
* Spark提供了两种方法来实现从RDD转换得到DataFrame：
* 第一种方法是，利用反射来推断包含特定类型对象的RDD的schema，适用对已知数据结构的RDD转换；
*             在利用反射机制推断RDD模式时，需要首先定义一个case class，因为，只有case class才能被Spark隐式地转换为DataFrame。
* 第二种方法是，使用编程接口，构造一个schema并将其应用在已知的RDD上。
*
*
* */

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkContext,SparkConf}

import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.{Encoder,Row}

object LearnSQL {

   def main(args: Array[String]): Unit = {
      val conf=new SparkConf().setMaster("local").setAppName("LearnSQL")
      val sc=new SparkContext(conf)


      //dataFrame()
      rdd2DataFrame()

   }

   case class Person(name:String,age:Int)

   //利用反射机制推断RDD模式
   def rdd2DataFrame(): Unit ={
      val spark=SparkSession.builder().getOrCreate()
      //使支持RDDs转换为DataFrames及后续sql操作
      import spark.implicits._ //导入包，支持把一个RDD隐式转换为一个DataFrame
      val peopleDF=spark.sparkContext.textFile("./src/learn/Spark/people.txt").map(_.split(","))
      val peopleDf=peopleDF.map(attribute=>Person(attribute(0).trim,attribute(1).trim.toInt)).toDF()
      //必须注册为临时表才能供下面的查询使用
      peopleDf.createOrReplaceTempView("people")
      val personsRDD=spark.sql("select name,age from people where age>20")
      personsRDD.map(t=>"Name:"+t(0)+","+"Age:"+t(1)).show()

   }

   def dataFrame(): Unit ={
      val spark=SparkSession.builder().getOrCreate()
      //使支持RDDs转换为DataFrames及后续sql操作
      import spark.implicits._ //导入包，支持把一个RDD隐式转换为一个DataFrame
      val df=spark.read.json("./src/learn/Spark/people.json")
      df.show()
      // 打印模式信息
      df.printSchema()
      //选择多列
      df.select(df("name"),df("age")+1).show()

      //条件过滤
      df.filter(df("age")>20).show()

      //分组聚合
      df.groupBy("age").count().show()

      //排序
      df.sort(df("age").desc).show()

      //多列排序
      df.sort(df("age").desc,df("name").asc).show()

      //队列进行重命名
      df.select(df("name").as("username"),df("age")).show()
   }
}
