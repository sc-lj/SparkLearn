package learn.scala
import java.io._
import javafx.concurrent.Worker

/**
  * Created by lj on 2018/8/11.
  *
  * 模式匹配
  * Scala中模式匹配除了可以对值进行匹配之外，还可以进行类型匹配，对Array和List的元素情况进行匹配、
  * 对case class进行匹配、甚至对有值或没值(Option)进行匹配。
  *
  * 模式匹配的语法：
  * 变量 match {case 值=>代码} 。如果值为下划线，则代表了不满足以上所有情况下的默认情况如何处理的。
  * 此外，match case中，只要有一个case分支满足并处理了，就不会继续判断下一个case分支。
  * 在case后的条件判断中，不仅仅只是提供一个值，而是可以在值后面再加一个if守卫，进行双重过滤。
  *
  *
  * scala的模式匹配语法，可以将模式匹配的默认情况_(下划线)，替换为一个变量名，此时模型匹配语法就会将要匹配的值赋值给这个变量，
  * 从而可以在后面的处理语句中使用要匹配的值。
  *
  *
  * 对Array进行模式匹配，分别可以匹配带有指定元素的数组，带有指定个数元素的数组、以某元素打头的数组
  * 对List进行模式匹配、与Array类似，但是需要使用List特有的::操作符。
  *
  *
  * Scala中提供了一种特殊的类，用case class进行声明，中文也可以称作样例类。case class只定义field，并且由Scala编译时自动提供getter和setter方法，
  * 但是没有method。
  * case class的主构造函数接收的参数通常不需要使用var或val修饰，Scala自动就会使用val修饰（如果使用了var修饰，那么还是会按照var来）
  * Scala自动为case class定义了伴生对象，也就是object，并且定义了apply()方法，该方法接收主构造函数中相同的参数，并返回case class对象
  *
  *
  *Option 有两种值，一种是Some，表示有值，一种None，表示没有值
  * Option通常会用于模式匹配中，用于判断某个变量是有值还是没有值
  *
  *
  */
object PatternMatch {
   def main(args: Array[String]): Unit = {
//      judgeGrade("Leo","F")
      greeting(Array("Leo"))
      greeting(Array("jen","marry","lory"))
      greeting(Array("Leo","Jack"))
      greeting(Array("Jack"))

      val tom:Person=Teacher("tom","math")
      val leo:Person=Student("leo","2")
      val jack:Person=Worker("jack")
      judgeIdentifly(tom)
      judgeIdentifly(leo)
      judgeIdentifly(jack)
   }

   def judgeGrade(name:String,grade:String){
      grade match {
         case "A"=>println(name+",you are Excellent")
         case "B"=>println(name+",you are Good")
         case "C" if name=="Jack"=>println(name+",you are a good boy,come on.")
         case "C"=>println(name+",you are just so so")
         case _grade if name=="Leo"=>println(name+",you are a good boy,come on. your grade is "+_grade)
         case _grade=>println("you need word harder. your grade is "+_grade)
      }
   }


   def processExceptions(e:Exception){
      e match {
         case e1:IllegalArgumentException=>println("you have illegal arguments! exception is "+e1)
         case e2:FileNotFoundException=>println("cannot find the file you need read or write! exception is "+e2)
         case e3:IOException=>println("you get an error while you were doing IO operation! exception is "+e3)
         case _:Exception=>println("cannot know which exception you have!")
      }
   }


   def greeting(arr:Array[String]){
      arr match {
         case Array("Leo")=>println("Hi,Leo!")
         case Array(girl1,girl2,girl3)=>println("Hi,girls,nice to meet you."+girl1+" and "+girl2+" and "+girl3)
         case Array("Leo",_*)=>println("Hi,Leo,please introduce your friends to me")
         case _=>println("hey,who are you?")
      }
   }


   def greeting1(arr:List[String]){
      arr match {
         case "Leo"::Nil=>println("Hi,Leo!")
         case girl1::girl2::girl3::Nil=>println("Hi,girls,nice to meet you."+girl1+" and "+girl2+" and "+girl3)
         //注意这里要用tail。
         case "Leo"::tail=>println("Hi,Leo,please introduce your friends to me")
         case _=>println("hey,who are you?")
      }
   }


   class Person
   case class Teacher(name:String,subject:String)extends Person
   case class Student(name:String,classroom:String) extends Person
   case class Worker(name: String) extends Person

   def judgeIdentifly(p:Person): Unit ={
      p match {
         case Teacher(name,subject)=>println("Teacher,name is "+name+", subject is "+subject)
         case Student(name,classroom)=>println("Student,your name is "+name+", your classroom is "+classroom)
         case _=>println("Illegal access,please go out of school!")
      }
   }


   val grades=Map("Leo"->"A","Jack"->"B","Jen"->"C")
   def getGrade(name:String): Unit ={
      val grade=grades.get(name)
      grade match {
         case Some(grade)=>println("your grade is "+grade)
         case None=>println("Sorry,your grade information is not in the system")
      }
   }

}
