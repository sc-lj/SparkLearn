package learn.Spark

/*
 * 1、默认情况下，map构造的是不可变的集合，里面的内容不可修改，一旦修改就变成了新的map。原有map内容保持不变；
 * 2、map的实例是调用工厂方法模式apply来构造Map实例，而需要注意的是Map是接口，在apply中使用了具体的实现
 * 3、如果想直接new出Map实例，则需要使用HashMap等具体的Map子类；
 * 4、查询一个map中的值一定是采用getOrElse的语法的，一方面是在key不存在的情况下不报告异常，另外还有一个神奇的作用就是提供默认值
 * 		而关于默认值的提供在实际开发中至关重要，在spark中很多默认的配置都是通过getOrElse的方式来实现的。
 * 5、使用SortedMap可以得到排序的Map集合
 * 6、LinkedHashMap可以记住插入的数据的顺序，这在实际开发中非常有用
 * 7、tuple中可以有很多不同类型的数据，例如("wangjialin","male",30,"i'm love python so much!!")
 * 8、在企业级实际开发大数据的时候一定会反复的使用Tuple来表达数据结构，以及使用Tuple来处理业务逻辑
 * 9、Tuple的另外一个非常重要的使用是作为函数的返回值，在Tuple中返回若干值，以SparkContext源码为例来说明
 * 		// Create and start the scheduler
    val (sched, ts) = SparkContext.createTaskScheduler(this, master, deployMode)
    _schedulerBackend = sched
    _taskScheduler = ts
    _dagScheduler = new DAGScheduler(this)
    _heartbeatReceiver.ask[Boolean](TaskSchedulerIsSet)
 * 
 */
object HelloMapTuple {
  def main(args: Array[String]): Unit = {
    val bigData=Map("Spark"->6,"Hadoop"->11)//调用工厂方法模式apply来构造Map实例，而需要注意的是Map是接口，在apply中使用了具体的实现
    val person=scala.collection.immutable.SortedMap(("china",5000),("Htspark",10),("htspark",10),("htspark",1),("hadoop",34))
    // 是按照key排序的,大写的排在前面,key相同的，只输出相同中最后一个
    
    val programmingLanguage=scala.collection.mutable.Map("Scala"->13,"Java"->23)
    programmingLanguage("Scala")=15
    
    for ((name,age)<-programmingLanguage) println(name +":"+ age)
    println(programmingLanguage.getOrElse("python", "lujun"))//lujun是默认值
    
    
    val persionInformation=new scala.collection.mutable.LinkedHashMap[String,Int]
    persionInformation+=("Scala"->13,"Java"->23,"python"->10)
//    persionInformation-=("Java")
    for ((name,age)<-persionInformation) println(name+" : "+age )
    for (key<-persionInformation.keySet) println(key)
    for (value<-persionInformation.values) println(value)
    
//    val result=for((name,age)<-persionInformation) yield (age,name)
//    for ((name,age)<-result) println(age+" : "+name)
    
    for ((name,age)<-person) println(name+" : "+age )
    
    val information=("wangjialin","male",30,"i'm love python so much!!")
    println(information._3)
    
    
  }
}