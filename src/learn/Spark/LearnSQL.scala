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

import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

object LearnSQL {

   def main(args: Array[String]): Unit = {
      val conf=new SparkConf().setMaster("local").setAppName("LearnSQL")
      val sc=new SparkContext(conf)


      //dataFrame()
      rdd2DataFrame1()

   }

   //把RDD保存成文件
   def rdd2File(): Unit ={
      val spark =SparkSession.builder().getOrCreate()
      import spark.implicits._

      //第一种将rdd保存为文件的方法
      val peopleDF = spark.read.format("json").load("./src/learn/Spark/people.json")
      //write.format()支持输出 json,parquet, jdbc, orc, libsvm, csv, text等格式文件，如果要输出文本文件，可以采用write.format(“text”)，
      // 但是，需要注意，只有select()中只存在一个列时，才允许保存成文本文件，如果存在两个列，比如select(“name”, “age”)，就不能保存成文本文件。
      //在./src/learn/Spark/ 目录下存在newpeople.csv文件夹（注意不是文件）。
      peopleDF.select("name","age").write.format("csv").save("./src/learn/Spark/newpeople.csv")

      //第二种将rdd保存为文件的方法
      peopleDF.rdd.saveAsTextFile("./src/learn/Spark/newpeople.txt")


      //又将newpeople.csv文件夹中的数据加载到rdd中。
      val conf=new SparkConf().setMaster("local").setAppName("LearnSQL")
      val sc=new SparkContext(conf)
      val textFile = sc.textFile("file:///usr/local/spark/mycode/newpeople.csv")


   }

   //使用编程方式定义RDD模式;当无法提前定义case class时，就需要采用编程方式定义RDD模式。
   def rdd2DataFrame1(): Unit ={
      val spark =SparkSession.builder().getOrCreate()
      import spark.implicits._
      val peopleRDD=spark.sparkContext.textFile("./src/learn/Spark/people.txt")
      //定义一个模式字符串
      val schemaString="name age"
      //根据模式字符串生成模式
      val fields=schemaString.split(" ").map(fieldName=>StructField(fieldName,StringType,nullable = true))
      val schema=StructType(fields)
      //从上面信息可以看出，schema描述了模式信息，模式中包含name age两个字段
      val rowRdd=peopleRDD.map(_.split(",")).map(attributes=>Row(attributes(0),attributes(1).trim))
      val peopleDF=spark.createDataFrame(rowRdd,schema)
      //必须注册为临时表才能供下面查询使用
      peopleDF.createOrReplaceTempView("people")
      val results=spark.sql("SELECT name,age FROM people")
      results.map(attributes=>"name: "+attributes(0)+","+"age: "+attributes(1)).show()



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
