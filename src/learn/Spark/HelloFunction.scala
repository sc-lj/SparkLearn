
package learn.Spark

/*
 * 函数可以被简单的任务是包裹了一条或几条语句的代码块，该代码体接收若干参数，经过代码体处理后返回结果，形如数学中的f(x)=x+1;
 * 在Scala中函数式一等公民，可以向变量一样被传递，被赋值，同时函数可以赋值给变量，变量也可以赋值给函数，之所以是这样，原因在于
 * 函数背后是类和对象，也就是说在运行的时候函数其实是一个变量！！！当然，背后的类是Scala语言自动帮助我们生成的，且可以天然的被
 * 序列化和反序列化，这个意义非常重要：
 * 1、可以天然的序列化和反序列化的直接好处就是函数可以在分布式系统上传递！！！
 * 2、因为函数背后其实是类和对象，所以可以和普通的变量完全一样的应用在任何普通变量可以运用的地方，包括作为参数传递，作为返回值，
 * 		被变量赋值和赋值给变量等。
 * 
 * 补充：整个IT编程技术的发展史，其实就是一部封装史：
 * 1、Function时代：在c语言中提供了函数的概念，用函数把若干条语句进行封装和复用
 * 2、Class 时代：在c++中和java等语言中提供了类和对象，把数据和处理数据业务逻辑封装起来；
 * 3、框架时代：把数据、代码和驱动引擎封装起来，是过去10年和未来10年IT技术的核心。
 * 
 * 关于函数初级入门的几个要点：
 * 1、def关键字来定义函数；
 * 2、函数会自动进行类型推断来确定函数返回值的类型，如果函数名称和函数体之间没有等于号的话，则类型推断失效，此时函数的类型为Unit
 * 3、函数的参数可以是函数
 * 4、如果在函数无法推导出函数的类型，则必须声明具体的类型，例如下面的fibonacci
 * 5、函数的参数可以有默认值，这样在调用函数的时候如果不想改变默认值的话就直接不传递参数而是直接使用默认值即可，这在实际编程中意义重大，
 *   尤其是在spark等框架中，因为框架一般都有自己默认配置和实现，此时就可以非常好的使用默认值。
 * 6、可以基于函数的参数的名称来调整函数的传递参数的顺序。其原因在于函数背后其实是类，其参数就是类的成员，所以无所谓顺序。
 * 7、函数中如果不确定传递参数的个数，传参时的一个方便语法是：_*
 * 8、可变参数中的数据其实会被收集成为Array数组，在入口方面main中其实就是可变参数，是以Array[String]的方式呈现的；
 * 
 */
object HelloFunction {
  def main(args: Array[String]): Unit = {
    hello("spark",30)
    println(hello("spark",31))//函数的参数可以是函数
//    val result=fibonacci(100)
//    println("fibonacci of 100 = "+result)
    println("Sum = " + sum(1,2,3,4,5,6,7,8,9,10))
    println("Sum = " + sum(1L to 10L:_*))
    println("sumrecursive = "+sumrecursive(1 to 100:_*))
  }
  
  def hello(name:String,age:Int=21): Int={
    println("hello,my name is "+name)
    println("hello,my age is "+age)
    age//31
  }
  
  def fibonacci(n:Long):Long={
    if(n<=1) 1 //1
    else fibonacci(n-2)+fibonacci(n-1)
  }
  
  def sum(numbers:Long*):Long= {
    var result=0L//长数型
    for(number<-numbers) result+=number
    result//返回result
  }
  
  def sumrecursive(numbers:Int*):Int={
    if (0==numbers.length) 0
    else numbers.head+sumrecursive(numbers.tail:_*);//numbers.tail的输出是Range 2 to 100，
  }
}

