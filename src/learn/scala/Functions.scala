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
  *
  */

import java.io.IOException

import scala.io.Source._

object Functions {
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


      //三个过程函数
      var a=sayhello1("lj")
      println("a",a)
      var b=sayhello2("lj")
      println("b",b)
      println(sayhello3("lj"))
//      exception()
//      exception1()
      exception2()

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
   lazy val lines=fromFile("/Users/lj/a.txt").mkString

//   print(lines)//调用lines变量，如果没有整个文件，则报错

   def linees=fromFile("/Users/lj/a.txt").mkString
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
      if(nums.length==0)0//判断nums的长度
      else nums.head+varparam1(nums.tail:_*)//nums.head 调用nums的第一个参数
   }

   //返回了"hello,"+name
   def sayhello1(name:String)="hello,"+name
   //定义过程
   //没有返回"hello"+name
   def sayhello2(name:String){print("hello,"+name);"hello"+name}

   //没有返回"hello,"+name
   def sayhello3(name:String):Unit="hello,"+name



}
