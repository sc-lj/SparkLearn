package learn.Spark

/*object 作为Scala中的一个关键字，相当于java中的puplic static class这一类修饰符，
*也就是说object中的成员都是静态的，所以我们在这个例子中main方法是静态的，不需要类的实例
* 就可以直接被虚拟机调用，而这正是作为JVM平台上程序入口的必备条件。
* 疑问，object是不是一个对象。object是Scala中的静态类，不是对象。
* 从spark的master和worker的源码中都发现类其入口的main方法是在object中的
*/

object Hello {
/*1、main是方法，因为被def定义且不具备函数特征
 *2、main是Scala语言中的规定的Scala的应用程序入口，一个运行的Scala应用程序只有一个main入口
 *3、args:Array[String] 其中args是参数名称，Array[String]表明应用程序运行时传入的参数集合,String是参数类型
 *4、:Unit 表明main入口方面的类型是unit，也就是说执行main方法后，返回的是unit类型
 *5、unit是什么类型？相当于java中的void类型。
 *6、=是什么？是表明main方法执行的结果是由谁来赋值的，或者main方法的方法体在哪里？在“=”的右面
 *7、方法体一般用{}来封装，里面可以有很多语句
 *8、{}默认情况下最后一条语句的结果类型就是{}的返回类型
 *9、跟踪println的源代码的一个额外的收获是发现Scala的println的IO操作是借助了JAVA的IO操作，也就是说Scala调用了JAVA
 *10、如果方法或者函数的类型或者返回类型是unit的话，就可以直接把“:Unit=”直接去掉，其他非unit类型则不可以去掉
 *11、关于println打印出内容到控制台，底层借助了java IO的功能，一个事实是Scala在做很多比较底层的实现的时候，
 * 		经常会使用java的实现来缩短开发时间，例如说操作数据源（DB、NoSQL）的JDBC；再例如关于线程thread的操作，Scala往往
 * 		也会直接调用java的thread。
 *12、按照当今OS的原理，main入口方法都是运行在主线程中的，OS的运行分为kernel space和user space，应用程序是运行在user space中，
 * 		一般都是应用程序Scala fork出来，被fork出来的应用程序进程默认会有主线程，而我们的main方法就是默认在主线程中的。
 * 
 */
 def main(args:Array[String]):Unit={
   println("Hello Scala!!!")//在console上打印出“hello Scala!!!”这个字符串并换行
   println(args.length)
 }
 def mai0n(args:Array[String]):Unit={
   println("Hello Scala00!!!")//在console上打印出“hello Scala!!!”这个字符串并换行
//   println(args.length) 
 }
}
 
