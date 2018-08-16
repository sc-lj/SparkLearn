package learn.Spark


/*
* 键值对RDD（Pair RDD）
*
* 常用的键值对转换操作包括reduceByKey()、groupByKey()、sortByKey()、join()、cogroup()等。
* reduceByKey(func)的功能是，使用func函数合并具有相同键的值。
* groupByKey()的功能是，对具有相同键的值进行分组。比如，对四个键值对(“spark”,1)、(“spark”,2)、(“hadoop”,3)和(“hadoop”,5)，采用groupByKey()后得到的结果是：(“spark”,(1,2))和(“hadoop”,(3,5))。
* keys只会把键值对RDD中的key返回形成一个新的RDD。
* values只会把键值对RDD中的value返回形成一个新的RDD。
* sortByKey()的功能是返回一个根据键排序的RDD。
* 只想对键值对RDD的value部分进行处理，而不是同时对key和value进行处理。对于这种情形，Spark提供了mapValues(func)，它的功能是，对键值对RDD中的每个value都应用一个函数，但是，key不会发生变化。
*
* join包括内连接(join)、左外连接(leftOuterJoin)、右外连接(rightOuterJoin)等。最常用的情形是内连接，所以，join就表示内连接。
* 对于内连接，对于给定的两个输入数据集(K,V1)和(K,V2)，只有在两个数据集中都存在的key才会被输出，最终得到一个(K,(V1,V2))类型的数据集。
*
*
* 共享变量==>广播变量（broadcast variables）和累加器（accumulators）
* 在默认情况下，当Spark在集群的多个不同节点的多个任务上并行运行一个函数时，它会把函数中涉及到的每个变量，在每个任务上都生成一个副本。
* 但是，有时候，需要在多个任务之间共享变量，或者在任务（Task）和任务控制节点（Driver Program）之间共享变量。为了满足这种需求，
* Spark提供了两种类型的变量：广播变量（broadcast variables）和累加器（accumulators）。广播变量用来把变量在所有节点的内存之间进行共享。
* 累加器则支持在所有不同节点之间进行累加计算（比如计数或者求和）。
*
* 广播变量（broadcast variables）允许程序开发人员在每个机器上缓存一个只读的变量，而不是为机器上的每个任务都生成一个副本。
* 通过这种方式，就可以非常高效地给每个节点（机器）提供一个大的输入数据集的副本。Spark的“动作”操作会跨越多个阶段（stage），
* 对于每个阶段内的所有任务所需要的公共数据，Spark都会自动进行广播。通过广播方式进行传播的变量，会经过序列化，然后在被任务使用时再进行反序列化。
* 这就意味着，显式地创建广播变量只有在下面的情形中是有用的：当跨越多个阶段的那些任务需要相同的数据，或者当以反序列化方式对数据进行缓存是非常重要的。
*
* 可以通过调用SparkContext.broadcast(v)来从一个普通变量v中创建一个广播变量。这个广播变量就是对普通变量v的一个包装器，通过调用value方法就可以获得这个广播变量的值。
* 广播变量被创建以后，那么在集群中的任何函数中，都应该使用广播变量broadcastVar的值，而不是使用v的值，这样就不会把v重复分发到这些节点上。
* 此外，一旦广播变量创建后，普通变量v的值就不能再发生修改，从而确保所有节点都获得这个广播变量的相同的值。
*
*
* 累加器是仅仅被相关操作累加的变量，通常可以被用来实现计数器（counter）和求和（sum）。Spark原生地支持数值型（numeric）的累加器，
* 程序开发人员可以编写对新类型的支持。如果创建累加器时指定了名字，则可以在Spark UI界面看到，这有利于理解每个执行阶段的进程。
*
* 一个数值型的累加器，可以通过调用SparkContext.longAccumulator()或者SparkContext.doubleAccumulator()来创建。
* 运行在集群中的任务，就可以使用add方法来把数值累加到累加器上，但是，这些任务只能做累加操作，不能读取累加器的值，
* 只有任务控制节点（Driver Program）可以使用value方法来读取累加器的值。
*
*
* */


import org.apache.spark.{SparkContext,SparkConf}
object LearnPairRDD {
   val conf=new SparkConf().setMaster("local").setAppName("LearnPairRDD")
   val sc=new SparkContext(conf)

   def main(args: Array[String]): Unit = {
      wordCount()
      //创建广播变量
      val broadcastVar = sc.broadcast(Array(1, 2, 3))
      //获得广播变量的值
      println(broadcastVar.value)

      //创建累加器
      val accum=sc.longAccumulator("My Accumulator")
      sc.parallelize(Array(1,2,3,4,5,6)).foreach(x=>accum.add(x))

      //在任务节点获取累加器的值
      accum.value

   }

   def wordCount(){
      //val lines=sc.textFile("src/learn/Spark/new_sohu.txt")
      //val parRdd=lines.flatMap(line=>line.split("+")).flatMap(line=>line.split("")).map(word=>(word,1))

      val parRdd=sc.parallelize(Array(("spark",1),("spark",2),("hadoop",3),("hadoop",5)))
      println("reduceByKey")
      val parReduce=parRdd.reduceByKey((a,b)=>a+b)
      parReduce.foreach(println)

      println("groupByKey")
      val parGroup=parRdd.groupByKey()
      parGroup.foreach(println)

      println("sortByKey")
      val parSort=parReduce.sortByKey()
      parSort.foreach(println)

      println("mapValues")
      val parValues=parRdd.mapValues(value=>value+1)
      parValues.foreach(println)

      println("join")
      val parRdd1=sc.parallelize(Array(("spark",2),("hadoop",6),("hadoop",4),("spark",6)))
      val parJoin=parRdd.join(parRdd1)
      parJoin.foreach(println)






   }



}
