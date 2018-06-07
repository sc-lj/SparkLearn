package learn.Spark

/*
 * 大数据技术是数据的集合以及对数据结合的操作技术的统称，具体来说：
 * 1、数据集合：会涉及数据的搜集、存储等，搜集会有很多技术，存储现在比较经典的是使用Hadoop，也有很多情况使用Kafka
 * 2、对数据集合的操作技术，目前全球最火的是spark
 * 
 * spark的框架语言是Scala，首选的应用程序开发语言也是Scala，所以Scala对集合以及集合操作的支持就至关重要且必须异常强大；
 * 一个补充说明是：可能是巧合，spark中对很多数据的操作算子和Scala中对集合的操作算子是一样的，也就是说你掌握了Scala中
 * 的集合的操作，基本上就可以直接去开发spark代码了。
 * 
 * 关于scala中数据创建和操作：
 * 1、最原始的创建数据的方式是形如 val array=new Array[Int](5),指定数组的类型是Int且其固定长度为5个元素
 * 2、对数组元素访问的时候下标的范围在0到length-1的长度，超过length-1的话会出现java.lang.ArrayIndexOutOfBoundException
 * 3、最常用和经典的创建数组的方式是形如val array=Array[Int](1,2,3,4,5),直接通过Array类名并传入参数的方式来创建数组实例，
 * 		在背后的实现是调用Array的工厂方法模式apply来构造数组及数组的内容的。
 * 4、关于array本身在底层的实现是借助来jvm平台上的java语言的数组的实现，是不可变的！
 * 5、如果我们想使用可变数组的话，首先需要导入 import scala.collection.mutable.ArrayBuffer，然后使用ArrayBuffer这个可变数组
 * 6、关于ArrayBuffer增加元素默认情况下都是在ArrayBuffer末尾增加元素的，效率非常高
 * 7、当需要多线程并发操作时，把ArrayBuffer转换成为Array就非常重要，其实，即使是Array，其本身虽然不可变动（元素不可删减，），
 * 		但是我们可以修改Array中每个元素的内容，所以多线程操作的时候，还是必须考虑并发写的问题。
 * 8、如果想在已经有的数组的基础上通过作用于每个元素来生成新的元素构成的新数组，则可以通过yield语法来完成，这在大数据中意义重大，
 * 		第一点：它是在不修改已经有的Array的内容的基础上完成的，非常适合大数据的处理；
 * 		第二点；在大数据处理中，例如在spark中业务操场的核心思想就类似于yield，来通过使用function对每个元素操作获得新的元素的集合，其实就是新的RDD，例如MaPartitionsRDD
 * 9、集合的操作往往可以通过丰富的操作算子，例如filter来过滤需要条件的元素，例如map来进行每一个元素的加工；
 */
object HelloArray {
  def main(args: Array[String]): Unit = {
//    val array=new Array[Int](5)
		  val array=Array[Int](1,2,3,4,5)//在这里可以去掉[Int]这个泛类型，是因为Scala有类型推导的能力，而我们已经传进了当前数组array的值，所以可以根据值来推导出类型；
//    val array=Array.apply(1,2,3,4,5)
    array(0)=10//数据的索引下标是从0开始的
//    array(5)=1//数组下标越界，会出现java.lang.ArrayIndexOutOfBoundException
  for (item<-array) println(item)  
  
  val names=Array("scala",2,"spark")
  for (item<-names) println(item) 
  
  import scala.collection.mutable.ArrayBuffer
  val arrayBuffer=ArrayBuffer[Int]()
  arrayBuffer+=1
  arrayBuffer+=2
  arrayBuffer+=(3,4,5,6,7)
  arrayBuffer++=Array(1,2,3)
  arrayBuffer.insert(arrayBuffer.length-3,100,1000)//在指定位置加入元素
  arrayBuffer.remove(arrayBuffer.length-3)//删除倒数第三个元素
  arrayBuffer.toArray//当需要多线程并发操作时，把ArrayBuffer转换成为Array就非常重要
  for (item<-arrayBuffer) println(item) 
  
  for(i <-0 until array.length) print(array(i)+" ")//print和println的区别是print是不换行打印，println是换行打印
  println
  for(i <-0 until (array.length,2)) print(array(i)+" ")
  println
  for(i <-(0 until array.length).reverse) print(array(i)+" ")
  println
  println("Max = "+array.max)
	println("Min = "+array.min)//array.sum
	scala.util.Sorting.quickSort(array)//对数组进行升叙排序，内容变成来2，3，4，5，10
	println("quitsort = "+array(0))
	println(array.mkString(","))
  println(array.mkString("*****",",","*****"))
  
  val arrayAddedOne= for (item<-array) yield item+1
  println(arrayAddedOne.mkString(" "))
	
	val arrayEven= for (item<-array if item%2==0) yield item+1 //提取偶数
	
	println(array.filter{x=>x% 2 == 0}.mkString("  "))//提取偶数，filter是函数
	println(array.filter( _ % 2 == 0).mkString("  "))//提取偶数，filter,省略掉x=>x，用下划线“_”代替，这是对每个元素的操作
	println(array.filter( _ % 2 == 0).map(_ *10).mkString("  "))
	
	
  }
}