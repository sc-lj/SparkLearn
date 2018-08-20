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
* Spark SQL可以支持Parquet、JSON、Hive等数据源，并且可以通过JDBC连接外部数据源。
* Parquet是一种流行的列式存储格式，可以高效地存储具有嵌套字段的记录。Parquet是语言无关的，而且不与任何一种数据处理框架绑定在一起，
* 适配多种语言和组件，能够与Parquet配合的组件有:
* - 查询引擎: Hive, Impala, Pig, Presto, Drill, Tajo, HAWQ, IBM Big SQL；
* - 计算框架: MapReduce, Spark, Cascading, Crunch, Scalding, Kite；
* - 数据模型: Avro, Thrift, Protocol Buffers, POJOs。
*
*
* 通过JDBC连接数据库：
* Spark支持通过JDBC方式连接到其他数据库获取数据生成DataFrame。
* 下面以连接MySQL为例。
* 需要下载一个MySQL的JDBC驱动（http://dev.mysql.com/downloads/connector/j/）
* 需要将该驱动解压，并把该驱动程序拷贝到spark的安装目录
*
*
*
*
* */

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkContext,SparkConf}

import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.{Encoder,Row}

import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import java.util.Properties

object LearnSQL {

   def main(args: Array[String]): Unit = {
      val conf=new SparkConf().setMaster("local").setAppName("LearnSQL")
      val sc=new SparkContext(conf)


      //dataFrame()
      //rdd2DataFrame1()
      //readWriteParquet()
      linkMySQl()

   }

   // spark 连接MySQL
   def linkMySQl(): Unit ={
      val spark=SparkSession.builder().getOrCreate()
      import spark.implicits._
      val jdbcDF = spark.read.format("jdbc").option("url", "jdbc:mysql://127.0.0.1:3306/spark").option("driver","com.mysql.jdbc.Driver").option("dbtable", "student").option("user", "root").option("password", "lj123").load()
      jdbcDF.show()

      //插入数据
      val studentRDD=spark.sparkContext.parallelize(Array("3 Rongcheng M 26","4 Guanhua M 27")).map(_.split(" "))
      //下面要设置模式信息
      val schema=StructType(List(StructField("id",IntegerType,true),StructField("name",StringType,true),StructField("gender",StringType,true),StructField("age",IntegerType,true)))
      //下面创建Row对象，每个Row对象都是rowRDD中的一行
      val rowRDD=studentRDD.map(p=>Row(p(0).toInt,p(1).trim,p(2).trim,p(3).toInt))
      //建立起Row对象和模式之间的对应关系，也就是把数据和模式对应起来
      val studentDF=spark.createDataFrame(rowRDD,schema)
      //下面创建一个prop变量用来保存JDBC连接参数
      val prop=new Properties()
      prop.put("user","root")
      prop.put("password","lj123")
      prop.put("driver","com.mysql.jdbc.Driver")//表示驱动程序是com.mysql.jdbc.Driver
      //下面就可以连接数据库，采用append模式，表示追加记录到数据库spark的student表中
      studentDF.write.mode("append").jdbc("jdbc:mysql://127.0.0.1:3306/spark","spark.student",prop)

   }

   //读写Parquet
   def readWriteParquet(): Unit ={
      val spark=SparkSession.builder().getOrCreate()
      import spark.implicits._
      val sparkRDD=spark.read.parquet("./src/learn/Spark/users.parquet")
      sparkRDD.createOrReplaceTempView("people")
      val nameDF=spark.sql("SELECT * FROM people")
      nameDF.foreach(line=>println("Name: "+line(0)+" favorite: "+line(1)))

      //将DataFrame保存成parquet文件。
      val peopleDF=spark.read.json("./src/learn/Spark/people.json")
      peopleDF.write.parquet("./src/learn/Spark/people.parquet")

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
