package learn.scala

import scala.util.control.Breaks._
/**
  * Created by lj on 2018/7/12.
  */
object Base_Program {
   def main(args: Array[String]): Unit = {
      //声明var，var变量的值可以改变的，类似与变量
      var i=1+1

      //声明val变量，后续该变量可以使用，但是val变量的值是不可以改变，类似与常量
      val j=1
      //j=3 //报错，j不可改变
      i=1
      //在实际中，建议使用val声明变量，毕竟在大量的网络传输过程中，如果使用var，会担心值被错误的改动


      //手动指定变量的类型
      // 声明字符变量
      var m:String="sparker"

      //声明任意类型
      var n:Any=1

      //声明多个
      var name1,name2:Any="hadoop"

      println(i)

      /*
      * sacla的数据类型有Byte、Char、Short、Int、Long、Float、Double、Boolean。
      * */

      //var name3:Char="rg"

      //将1转化成字符
      println(1.toString)

      //Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      println(1.to(10))
      println(1 to 10)

      //取Hello和world的交集
      println("Hello".intersect("world"))

      //控制、if语句
      var age=30
      val adult=if (age>10) "adult" else 0
      println(adult)

      //要给变量声明个数据类型
      var ab:Any=null
      if (age<18)
         ab="children"
      else ab="adult"
      println(ab)

      var a1,b1,c1:Any=null
      if (age<18) {
         a1 = "children"
      }
      else b1="adult"
      println(ab)

      var (a,b,c):(Int,Int,Int)=(1,2,3)
      var g = if(a<=1) {b=b+1;c+1}//g的值就是c+1
      printf("g's number is %s",g)
      //printf 用于格式化；println打印时h会加一个换行符；print打印时不会加换行符

//      val name=scala.io.StdIn.readLine("welcome to new world,please tell me your name:")
//      print("tell me your age:")
//      val yourage=scala.io.StdIn.readInt()

//      if (yourage>18)
//         printf("%s Hi,play happy",name)
//      else
//         printf("%s ,your age is %d,sorry,you don't get in!",name,yourage)

      var m1=10
      while(m1>0){
         println(m1)
         m1-=1
      }
      //scala 没有for循环，只能使用while循环,scala中有简易的for循环
      var mn=10
      println("循环")
      for (i<- 1 to mn) println(i)
      for (i<- 1 until mn) println(i)//忽略了上确界（10），10没有输出

      for (j<- "hello world") println(j)

      //跳出循环语句，使用booleanl类型变量，returnh或者Breaks的break函数来代替使用

      breakable{
         var nb=10
         for(c<-"hello world"){
            if(nb==5)break
            print(c)
            nb-=1
         }
      }

      //多重for循环
      for(i<-1 to 9;j<-1 to 9){
         if(j==9){
            print(i*j+" ")
         }
         else{
            print(i*j+" ")
         }
      }

      //取偶数
      for(i<-1 to 100 if i%2==0) println(i)

      //for推导式：构造集合
      var list=for(i<-1 to 10 if i%2==0) yield i
      println(list)//Vector(2, 4, 6, 8, 10)


   }
}


