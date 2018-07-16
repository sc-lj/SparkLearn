package learn.Spark

/**
 * scala 中的基本控制结果有顺序、条件和循环三种方式，这个其他的JVM语言是一致的，但是Scala也有一些高级的流程控制结果类模式匹配；
 * 在这里主要if、for、while等三种控制结果及其企业级最佳实践，并且在最后用spark源码说明
 * 
 */
object ControlStructures {
  def main(args: Array[String]): Unit = {
    println("scala")
/*if条件表达式会更会if会面 括号里面的boolean值来决定整个if表达式的值
 * 1、Scala中的if条件表达式是有值的，整个java是不一样的
 * 2、if 条件表达式中可以进行类型推导，类型推导的一般过程就是根据变量的值的类型来推导确定变量的类型，这在很多复杂算法的实现的时候可以让我们
 * 		省略掉变量的类型的书写，为复杂算法的实现提供来非常大的便利；
 * 3、如果if后面没有else部分，默认的实现是if（...）... else (...)
 * 		
 * 		下面的例子节选自spark的核心类SparkContext，在else部分虽然不返回有意义的结果，但是依旧没有省略else，而是用了else {None}这种方式，
 * 		其目的是为了确保if条件表达式的类型为处理逻辑需要的类型option，而不是Any类型，为下一步的处理打下基础
 * 		例：if (isEventLogEngland) {Some(logger)} else {None}
 *    
 * 4、if 表达式中如果有多条语句可以使用{}包裹起来，但是{}中的多条语句，哪一条是计算结果？--是{}中的最后一条语句
 * 5、补充说明，{...} 代表了一个语句块，语句块是有值的，值就是最后一条语句，其类型是最后一条语句值的类型
 * 6、if 表达式可以用在for循环等其他控制结构中用于限制结果
 * 
 */
    var age=2//var定义可改变的变量
    val result=if (age > 20)  "Worker" else "student"//此时因为"Worker" 和 "student"都是字符串，所以result也是字符串类型
    println(result)
    
    val result2=if (age > 18)  "Worker" else 1 //此时因为if表达式中的else两侧内容一个是字符类型，一个是整数类型，所以result2的类型是两种的公共父类Any类型
    
    val result3=if (age > 18)  "Adult"
    println(result3)
    
    var x,y=0
    val result4= if (age <18){
      x=x+1;
      y=y+1;//;可以省略
      x+y
    }else 0
    println(result4)
    
    for (i<-0 to 5 if i==2){//<-是提取符，从0 to 5中提取，i%2==0，取偶数
      println(i)
    }
    
/*
 * for循环是不断的循环一个集合，然后根据for循环后面的{...}代码块部分会根据for循环(...)里面提取的集合的item来作为{...}代码块的输入进行流程控制。    
 * 1、for 循环中加入的if叫做条件守卫，用于限制for循环（优化for循环，去掉不必要的执行步骤，或者说用于跳出for循环）
 * 2、最后再次强调，在for循环中能够提取出什么内容取决于后面的集合的类型
 * 3、想跳出for循环的话，除了加入if守卫以外，还可以使用return关键字
 * 
 * */
    var flag=true
    var sum=0
    for (i<-0 to 6 if flag){
      sum=sum+i
      if (5 == i) flag=false
    }
    println("sum="+sum)
    
    for (item<-"hello spark".split(" ")) println(item)
    

//    for (i<-0 to 9 ){
//      sum=sum+i
//      if (5 == i) return //return 是返回的是方法级别的，这在实际开发中非常常用
//    }
//    println("sum with return="+sum)
    
/*
 * while循环，也是循环集合来作为{...}的输入，进而完成流程的控制的，while循环在实际server和framwork开发中至关重要的，例如让一个线程一直循环下去，一般都会使用while；
 *     
 */
    import scala.util.control.Breaks._
    flag=true
    breakable{//breakable是Breaks下面的一种方法
      while(flag){
        for (item<-"spark"){
          println(item)
          if (item=='r') {
            flag=false
            break//break与breakable是配对的，要同时使用
          }
        }
      }
    }

  
  }
}