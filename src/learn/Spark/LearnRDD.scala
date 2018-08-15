package learn.Spark

/*
* RDD创建;RDD可以通过两种方式创建：
* 第一种：读取一个外部数据集。比如，从本地文件加载数据集，或者从HDFS文件系统、HBase、Cassandra、Amazon S3等外部数据源中加载数据集。
*       Spark可以支持文本文件、SequenceFile文件（Hadoop提供的 SequenceFile是一个由二进制序列化过的key/value的字节流组成的文本存储文件）
*       和其他符合Hadoop InputFormat格式的文件。
* 第二种：调用SparkContext的parallelize方法，在Driver中一个已经存在的集合（数组）上创建。
*
* 从文件系统中加载数据创建RDD：
* Spark采用textFile()方法来从文件系统中加载数据创建RDD，该方法把文件的URI作为参数，这个URI可以是本地文件系统的地址，或者是分布式文件系统HDFS的地址，或者是Amazon S3的地址等等。
*
* 在使用Spark读取文件时，需要说明以下几点：
* 1）如果使用了本地文件系统的路径，那么，必须要保证在所有的worker节点上，也都能够采用相同的路径访问到该文件，比如，可以把该文件拷贝到每个worker节点上，或者也可以使用网络挂载共享文件系统。
* 2）textFile()方法的输入参数，可以是文件名，也可以是目录，也可以是压缩文件等。比如，textFile(“/my/directory”), textFile(“/my/directory/\*.txt”), and textFile(“/my/directory/\*.gz”).
* 3）textFile()方法也可以接受第2个输入参数（可选），用来指定分区的数目。默认情况下，Spark会为HDFS的每个block创建一个分区（HDFS中每个block默认是128MB）。你也可以提供一个比block数量更大的值作为分区数目，但是，你不能提供一个小于block数量的值作为分区数目。
*
* RDD 一般有两种操作：
* 转换（Transformation）： 基于现有的数据集创建一个新的数据集。
* 行动（Action）：在数据集上进行运算，返回计算值。
*
* 转换操作：
* 对于RDD而言，每一次转换操作都会产生不同的RDD，供给下一个“转换”使用。转换得到的RDD是惰性求值的，也就是说，整个转换过程只是记录了转换的轨迹，并不会发生真正的计算，只有遇到行动操作时，才会发生真正的计算。
* 常见的转换操作（Transformation API）：
* filter(func)：筛选出满足函数func的元素，并返回一个新的数据集
* map(func)：将每个元素传递到函数func中，并将结果返回为一个新的数据集
* flatMap(func)：与map()相似，但每个输入元素都可以映射到0或多个输出结果
* groupByKey()：应用于(K,V)键值对的数据集时，返回一个新的(K, Iterable)形式的数据集
* reduceByKey(func)：应用于(K,V)键值对的数据集时，返回一个新的(K, V)形式的数据集，其中的每个值是将每个key传递到函数func中进行聚合
*
*
* 行动操作：
* 行动操作是真正触发计算的地方。Spark程序执行到行动操作时，才会执行真正的计算，从文件中加载数据，完成一次又一次转换操作，最终，完成行动操作得到结果。
* 常见的行动操作（Action API）：
* count() 返回数据集中的元素个数
* collect() 以数组的形式返回数据集中的所有元素
* first() 返回数据集中的第一个元素
* take(n) 以数组的形式返回数据集中的前n个元素
* reduce(func) 通过函数func（输入两个参数并返回一个值）聚合数据集中的元素
* foreach(func) 将数据集中的每个元素传递到函数func中运行
*
*
* 持久化：
* 在Spark中，RDD采用惰性求值的机制，每次遇到行动操作，都会从头开始执行计算。如果整个Spark程序中只有一次行动操作，这当然不会有什么问题。
* 但是，在一些情形下，我们需要多次调用不同的行动操作，这就意味着，每次调用行动操作，都会触发一次从头开始的计算。这对于迭代计算而言，代价是很大的，
* 迭代计算经常需要多次重复使用同一组数据。
* 可以通过持久化（缓存）机制避免这种重复计算的开销。可以使用persist()方法对一个RDD标记为持久化，之所以说“标记为持久化”，
* 是因为出现persist()语句的地方，并不会马上计算生成RDD并把它持久化，而是要等到遇到第一个行动操作触发真正计算以后，才会把计算结果进行持久化，
* 持久化后的RDD将会被保留在计算节点的内存中被后面的行动操作重复使用。
* persist()的圆括号中包含的是持久化级别参数，比如，
* persist(MEMORY_ONLY)表示将RDD作为反序列化的对象存储于JVM中，如果内存不足，就要按照LRU原则替换缓存中的内容。
* persist(MEMORY_AND_DISK)表示将RDD作为反序列化的对象存储在JVM中，如果内存不足，超出的分区将会被存放在硬盘上。一般而言，使用cache()方法时，会调用persist(MEMORY_ONLY)。
*
* 最后，可以使用unpersist()方法手动地把持久化的RDD从缓存中移除。
*
*
* 分区:
* RDD是弹性分布式数据集，通常RDD很大，会被分成很多个分区，分别保存在不同的节点上。RDD分区的一个分区原则是使得分区的个数尽量等于集群中的CPU核心（core）数目。
* 对于不同的Spark部署模式而言（本地模式、Standalone模式、YARN模式、Mesos模式），都可以通过设置spark.default.parallelism这个参数的值，来配置默认的分区数目，一般而言：
* 本地模式：默认为本地机器的CPU数目，若设置了local[N],则默认为N；
* Apache Mesos：默认的分区数为8；
* Standalone或YARN：在“集群中所有CPU核心数目总和”和“2”二者中取较大值作为默认值；
*
* 对于textFile而言，如果没有在方法中指定分区数，则默认为min(defaultParallelism,2)，其中，defaultParallelism对应的就是spark.default.parallelism。
* 如果是从HDFS中读取文件，则分区数为文件分片数(比如，128MB/片)。
*
* */

import org.apache.spark.{SparkConf,SparkContext}

object LearnRDD {
   def main(args: Array[String]): Unit = {
      val conf=new SparkConf().setMaster("local").setAppName("LearnRDD")
      val sc=new SparkContext(conf)
      //从本地读取文件
      //val lines=sc.textFile("src/learn/Spark/new_sohu.txt")
      val lines=sc.textFile("file:///Users/apple/IdeaProjects/SparkLearn/src/learn/Spark/new_sohu.txt")

      //从hdfs上读取文件;/user/hadoop/是HDFS文件系统为Linux登录用户开辟的默认目录是“/user/用户名”（注意：是user，不是usr）
      val hdfslines=sc.textFile("hdfs://localhost:9000/user/hadoop/new_sohu.txt")
      val hdfslines1 = sc.textFile("/user/hadoop/new_sohu.txt")
      val hdfslines2=sc.textFile("new_sohu.txt")

      //通过并行集合（数组）创建RDD;
      //可以调用SparkContext的parallelize方法，在Driver中一个已经存在的集合（数组）上创建。
      val array=Array(1,2,3,4,5)
      val rdd=sc.parallelize(array,2)  //设置两个分区
      val list=List(1,2,3,4,5,6)
      val listrdd=sc.parallelize(list)
      listrdd.cache()
      println(listrdd.collect().mkString(","))




   }




}
