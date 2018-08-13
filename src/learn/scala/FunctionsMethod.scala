package learn.scala

/**
  * Created by lj on 2018/7/13.
  * 在Scala中定义函数时，如果函数体直接包裹在了括号里面，而没有使用=连接，则函数的返回值类型就是Unit，
  * 这样的函数被称之为过程，过程通常用于不需要返回值的函数。
  *
  *
  * 在Scala中，提供了lazy值的特性，该特性能使一个变量只有在使用的时候，变量对应的表达式才会发生计算，
  * 这种特性对于特别耗时的计算操作特别有用，比如打开文件进行IO、进行网络IO等。
  *
  * 在Scala中方法(method)和函数(function)是有区别的：
  * 1、方法可以作为一个表达式的一部分出现（调用函数并传参），但是方法（带参方法）不能作为最终的表达式，
  * 2、参数列表对于方法是可选的，但是对于函数是强制的；即方法可以没有参数列表，参数列表也可以为空。但是函数必须有参数列表（也可以为空）
  * 3、方法名意味着方法调用，函数名只是代表函数自身。如果要强制调用一个函数，你必须在函数名后面写()。
  * 4、在scala中很多高级函数，如map(),filter()等，都是要求提供一个函数作为参数。但是我们又可以提供一个方法，这是因为如果期望出现函数的地方我们提供了一个方法的话，该方法就会自动被转换成函数。
  *
  *
  */

import java.io.IOException
import scala.io.Source._


object FunctionsMethod {
   def main(args: Array[String]): Unit = {

      var age=sayHello("lj",23)
      println(age)
      var sums=sum(10)
      println(sums)
      var fabs=fab(20)
      println(fabs)
      println(sayhello(age=28))
      println(varparam(1,2,3,4))
      //如果想要将一个已有的序列直接调用变长参数函数，是不对的。比如val s=varparam(1 to 5)
      //此时需要使用Scala特殊的语法将参数定义为序列，
      println(varparam(1 to 5:_*))

      println(varparam1())
      println(varparam1(1 to 5:_*))

      //定义带参数的方法
      def m1(x:Int) = x+3//其类型是Int型
      //定义一个函数
      val f=(x:Int)=>x*3
      //带参数的方法不能作为最终表达式出现，函数可以作为最终表达式出现

      //定一个参数列表为空的方法
      def m2()="spark"
      //定义一个没有参数列表的方法
      def m3="spark"

      //定义一个参数列表为空的函数
      val f1=()=>"spark"
      //无法定义一个没有参数列表的函数
      //val f2= =>"spark"

      val myList = List(3,56,1,4,72)
      //给map传递进一个函数
      myList.map(x=>x*2)
      //给map()提供一个方法作为参数
      val fun=(x:Int)=>x*2
      myList.map(fun)

      //将方法强制转换成函数
      val f3=m1 _
      println(f3(3))//输出：6


      //三个过程函数
      var a=sayhello1("lj")
      println("a",a)
      var b=sayhello2("lj")
      println("b",b)
      println(sayhello3("lj"))
//      exception()
//      exception1()
      exception2()

      // 定义匿名函数
      var inc = (x:Int) => x+1
      var x = inc(7)-1//调用匿名函数

     //在匿名函数中定义多个参数：
      var mul=(x:Int,y:Int)=>x+y
      println(mul(3, 4))

      //不给匿名函数设置参数
      var userDir=()=>{ System.getProperty("user.dir") }
      println( userDir() )


      //定义匿名函数、Lambda表达式与闭包
      //“Lambda表达式”的形式如下：(参数) => 表达式 //如果参数只有一个，参数的圆括号可以省略
      //val myNumFunc: Int=>Int = (num: Int) => num * 2 //这行是我们输入的命令，把匿名函数定义为一个值，赋值给myNumFunc变量
      //也可以如下声明
      val myNumFunc = (num: Int) => num * 2
      //val myNumFunc: Int=>Int = (num) => num * 2
      println(myNumFunc(3)) //myNumFunc函数调用的时候，需要给出参数的值，这里传入3，得到乘法结果是6

      //闭包函数
      //在下面示例中，step是一个自由变量，它的值只有在运行的时候才能确定，num的类型是确定的，num的值只有在调用的时候才被赋值。这样的函数，被称为“闭包”，它反映了一个从开放到封闭的过程。
      def plusStep(step: Int) = (num: Int) => num + step
      //给step赋值
      val myFunc = plusStep(3)
      //调用myFunc函数
      println(myFunc(10))

      var more = 1
      val addMore = (x: Int) => x + more
      println(addMore(10))

   }

   def exception():Any={
      try {
         //主动抛出异常
         throw new IllegalArgumentException("x should not be negative")
      }catch {
         //定义想捕获的异常,
         case _:IllegalArgumentException=>print("sorry,error")
      }finally {//finally 最终都会执行的
         print("\nrelease io resource!!!")
      }
   }
   def exception1():Any={
      try {
         throw new IOException("user defined exception")
      }catch {
         case e1:IllegalArgumentException=>print("illegal argument")
         case e2:IOException=>print("io exception")
      }
   }


   def exception2():Any={
      try {
         throw new IOException("user defined exception")
      }catch {
         case _:Exception=>print("illegal argument")
      }
   }


   //即使文件不存在，现在也不好报错，只会在使用该变量的时候，才报错
   //变量lines的类型是lazy
   lazy val lines:String=fromFile("/Users/lj/a.txt").mkString

//   print(lines)//调用lines变量，如果没有整个文件，则报错

   def linees:String=fromFile("/Users/lj/a.txt").mkString
   /*
   * Scala要求必须给出所有的参数的类型,scala的函数如果用Unit声明的话，那么就是没有返回值
   * */
   def sayHello(name:String,age:Int):Int={
      if (age>19){
         printf("hi %s,you are a big boy\n",name);age//返回age
      }
      else {
         printf("hi %s,you are a little boy\n",name)
         age
      }
   }

   def sum(n:Int):Int={
      var result=0
      for(i<-1 to 10)
         result+=i
      result
   }

   //递归函数，如果在函数自身调用递归函数本身，必须手动给出函数的返回类型
   def fab(n:Int):Int={
      if (n<=1) 1
      else fab(n-1)+fab(n-2)
   }

   //给予函数的默认参数
   def sayhello(name:String="lj",age:Int):String={
      if(age>20){
//         var str="hello,%s, welcome adult new world".format(name)
//         str
         "hello,"+name+"welcome adult new world"
      }
      else {
         var str="hello,%s, welcome young new world".format(name)
         str
      }

   }

   // 给予函数定义参数个数可变的
   def varparam(nums:Int*):Int={
      var res=0
      for(num<-nums){
         res+=num
      }
      res
   }

   def varparam1(nums:Int*):Int={
      if(nums.isEmpty)0//判断nums的长度
      else nums.head+varparam1(nums.tail:_*)//nums.head 调用nums的第一个参数
   }

   //返回了"hello,"+name
   def sayhello1(name:String):String="hello,"+name
   //定义过程
   //没有返回"hello"+name
   def sayhello2(name:String){print("hello,"+name);"hello"+name}

   //没有返回"hello,"+name
   def sayhello3(name:String):Unit="hello,"+name


}
