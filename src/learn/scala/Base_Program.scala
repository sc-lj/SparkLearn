package learn.scala

import scala.util.control.Breaks._
/**
  * Created by lj on 2018/7/12.
  *
  * scala 中支持的数据类型有：Byte，Short，Int，Long，Float（32位IEEE754单精度浮点数），Double（64位IEEE754单精度浮点数），Char，
  * String，Boolean，Unit（表示无值，用作不返回任何结果的方法的结果类型。Unit只有一个实例值，写成()。），
  * Null（	null 或空引用），Nothing（Nothing类型在Scala的类层级的最低端；它是任何其他类型的子类型。），Any（	Any是所有其他类的超类），AnyRef（AnyRef类是Scala里所有引用类(reference class)的基类）
  *
  *
  *
  *
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
      println(i)

      //手动指定变量的类型
      // 声明字符变量
      var m:String="sparker"

      //声明任意类型
      var n:Any=1

      var gg0=23433.23234//Double型
      var gg:Double=23433.23234//Double型
      // 当声明一个32位的单精度浮点数的时候，如果有小数点，后面必须跟f或者F，表示是Float类型
      var gg1:Float=13243.234F
      var gg2:Float=13243
      var gg3=13243F//Float型

      //声明整型变量
      var in=234//Int型
      var in1:Int=234//Int型
      var in2=234L//Long型
      var in3:Long=2324//Long型


      //声明多个
      var name1,name2:Any="hadoop"

      var myvar=("string",1)


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
      println(a1,b1)

      var (a,b,c):(Int,Int,Int)=(1,2,3)
      var g = if(a<=1) {b=b+1;c+1}//g的值就是c+1,即最后一个行（没有被赋予变量的）
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


     // do...while 循环，会先执行do语句快中的语句，后在判断while中条件是否满足，如果满足，继续执行do里面的语句快
      var ml=20
      do{
        println(ml)
        ml-=1
      }while(ml>0)



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


      //中断嵌套循环
      var a11 = 0
      var b11 = 0
      val numList1 = List(1,2,3,4,5)
      val numList2 = List(11,12,13)


      breakable {
         for( a11 <- numList1){
            println( "Value of a11: " + a11 )
            breakable {
               for( b11 <- numList2){
                  println( "Value of b11: " + b11 )
                  if( b11 == 12 ){
                      break
                  }
               }
            } // 内嵌循环中断
          }
      } // 外部循环中断

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


