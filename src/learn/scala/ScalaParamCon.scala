package learn.scala

/**
  * Created by lj on 2018/8/12.
  *
  * Scala提供的隐式转换和隐式参数功能，可以允许你手动指定，将某种类型的对象转换称其他类型的对象。
  * Scala的隐式转换，其核心的就是定义隐式转换函数，即implicit conversion function。
  * 定义的隐式转换函数，只要在编写的程序内引入，就会被Scala自动使用。Scala会根据隐式转换的签名，
  * 在程序中使用到隐式转换函数接收的参数类型定义的对象时，会自动将其传入隐式转换函数，转换为另外一种类型的对象并返回。这就是隐式转换。
  * 隐式转换函数通常不会由用户手动调用，而是用Scala进行调用，所以取什么名字无所谓的。
  * 但是如果要使用隐式转换，则需要对隐式转换函数进行导入。因此通常建议将隐式转换函数的名称命名为"name2one"类似的形式。
  *
  * 要实现隐式转换，只要程序可见的范围内定义隐式转换函数即可。Scala会自动使用隐式转换函数。隐式转换函数与普通函数唯一的语法区别就是：
  * 要以implicit 开头，而且最好要定义函数返回类型
  *
  *
  * 隐式转换可以在不知不觉中加强现有类型的功能。也就是说，可以为某个类定义一个加强版的类，并定义互相之间的隐式转换，从而让源类在使用加强版的方法时，
  * 有Scala自动进行隐式转换为加强类，然后在调用该方法。
  *
  *
  * scala 默认会使用两种隐式转换，一种是源类型，或者目标类型的伴生对象内的隐式转换函数；一种是当前程序作用域内的可以用唯一标示符表示的隐式转换函数。
  * 如果隐式转换函数不在上述两种情况下的话，那么就必须手动使用import语法引入某个包下的隐式转换函数，比如import test._
  * 通常建议，仅仅在需要进行隐式转换的地方，比如某个函数或者方法内，用import导入隐式转换函数，这样可以缩小隐式转换函数的作用域，避免不需要的隐式转换。
  *
  *
  *
  * 隐式转换的发生时机：
  * 1、调用某个函数，但是给函数传入的参数类型，与函数定义的接收参数类型匹配
  * 2、使用某个类型的对象，调用某个方法，而这个方法并不存在于该类型时
  * 3、使用某个类型的对象，调用某个方法，虽然该类型有这个方法，但是给方法传入的参数类型，与方法定义的接收参数的类型不匹配
  *
  *
  * 隐式参数：
  * 隐式参数指的是在函数或者方法中，定义一个用implicit修饰的参数，此时Scala会尝试找到一个指定类型的，用implicit修饰的对象，即隐式值，并注入参数。
  * Scala会在两个范围内查找：一种是当前作用域内可见的val或者var定义的隐式变量；一种是隐式参数类型的伴生对象内的隐式值
  *
  */
object ScalaParamCon {
   def main(args: Array[String]): Unit = {

      val tickerhouse=new TicketHouse
      val leo=new Student("leo")
      //传入的是Student，但是需要传入的是SpecialPerson类型，所以有隐式转换
      tickerhouse.buySpecialTicket(leo)


      //考试签到
      signForExam("Leo")
   }

   //特殊售票窗口
   class SpecialPerson(val name:String)
   class Student(val name:String)
   class Older(val name:String)

   implicit def object2SpecialPerson(obj:Object):SpecialPerson={
      if(obj.getClass==classOf[Student]){val stu=obj.asInstanceOf[Student];new SpecialPerson(stu.name)}
      else if (obj.getClass==classOf[Older]){val older=obj.asInstanceOf[Older];new SpecialPerson(older.name)}
      else Nil
   }

   var ticketNumber=0
   def buySpecialTicket(p:SpecialPerson)={
      ticketNumber +=1
      "T-"+ticketNumber
   }


   //案例:超人变身
   class Man(val name:String)
   class SuperMan(val name:String){
      def emitLaser=println("emit a laster")
   }

   //将输入Man类隐式转换成SuperMan类型
   implicit def man2superman(man:Man):SuperMan=new SuperMan(man.name)

   val leo=new Man("Leo")
   //调用emitLaser方法时，发现Man类中没有该方法，会自动在作用域内查找是否有隐式转换
   leo.emitLaser


   //特殊售票窗口加强版
   class TicketHouse{
      var ticketNumber=0
      def buySpecialTicket(p:SpecialPerson)={
         ticketNumber+=1
         "T-"+ticketNumber
      }
   }


   //考试签到;隐式参数
   class SignPen{
      def writer(content:String)=println(content)
   }

   implicit val signPen=new SignPen

   def signForExam(name:String)(implicit signPen: SignPen){
      signPen.writer(name+"come to exam in time")
   }
}
