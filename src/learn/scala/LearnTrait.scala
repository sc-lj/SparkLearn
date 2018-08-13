package learn.scala

/**
  * Created by lj on 2018/7/28.
  *
  * 在 Scala中Trait 为重用代码的一个基本单位。一个 Traits 封装了方法和变量，和 Interface（接口）相比，它的方法可以有实现，
  * 这一点有点和抽象类定义类似。但和类继承不同的是，Scala 中类继承为单一继承，也就是说子类只能有一个父类。但一个类可以和多个 Trait 混合，
  * 这些 Trait 定义的成员变量和方法也就变成了该类的成员变量和方法，由此可以看出 Trait 集合了 Interface 和抽象类的优点，
  * 同时又没有破坏单一继承的原则。
  *
  *
  * 在trait中可以定义抽象方法，就与抽象类中的抽象方法一样，只要不给出方法的具体实现即可。类可以使用extends关键字继承trait。
  * 类继承trait后，必须实现其中的抽象方法，实现时不需要使用override关键字。Scala不支持对类进行多继承，但是支持多重继承trait，使用with关键字即可。
  *
  *
  *scala中的trait可以不是只定义抽象方法，还可以定义具体方法，此时trait更像包含了通用工具方法的东西。
  * 举例来说，trait中可以包含一些很多类都通用的功能方法，比如打印日志等等。
  *
  *
  * 在trait中可以定义具体的field，此时继承trait的类就自动获得了trait中定义的field，
  * 但是这种获取field的方式与继承class是不同的：如果是继承class获取的field，实际是定义在父类中的；而继承trait获取的field，就直接被添加到类中。
  *
  *
  * 在trait中可以定义抽象的field，而trait中的具体方法则可以基于抽象field来编写。但是继承trait的类，则必须覆盖抽象field，提供具体的值。
  *
  *
  * 为实例混入trait
  * 有时我们可以创建类的对象时，指定该对象混入某个trait，这样只有这个对象混入该trait的方法，而类的其他对象则没有。
  *
  *
  * trait调用链
  * Scala中支持让类继承多个trait后，在这些trait中都定义同一个方法，只是内容不一样，依次调用多个trait中的同一个方法，
  * 只要让多个trait的同一个方法中，在最后都执行super方法即可。
  *在类中调用多个trait中都有的这个方法时，首先会从最右边的trait的方法开始执行，然后依次往左执行，形成一个调用链条。
  *
  * 在trait链中调用父类的抽象方法，在子类中重写抽象方法时，不能只用override，要用abstract override。
  * trait中，子trait中不用全部重写父trait中的抽象方法，即部分实现父trait中的方法。
  *
  *
  * trait的构造机制
  * trait的构造函数与class的构造函数的区别在于：trait是没有接收参数的构造函数，只能接收有默认参数的trait函数。
  *
  *
  * trait field的初始化
  * 由于trait是没有接收参数的构造函数的，所以当需要trait初始化Field时，我们就需要提前定义。
  *
  *
  * trait 也可以继承class
  * 此时这个class就会成为所有继承该trait的类的父类。
  *
  */
object LearnTrait {
   def main(args: Array[String]): Unit = {
      val p1=new Student("Jack")
      val p2=new Student("Per")
      val p3=new Student("Mike")
      p2.sayHello("Jack")
      p1.makeFriends(p2)
      p1.makeNewFriends(p3)
      p1.womanEmotion("Lucy",23)

      println("*"*12)
      val g1=new Grade("Dev")
      g1.sayHello
      //在定义实例的时候，动态的给实例混入了某个trait，这样该实例就可以调用该trait中的方法。
      val g2=new Grade("Jack") with MyLogger
      g2.sayHello

      println("*-"*20)
      val c1=new Class("Jun")
      c1.sayHello
      c1.printOut("Mike")

      println("*-"*20)
      println("trait 构造机制")
      val s1=new Students

      println("*-"*20)
      println("trait field 的初始化")
      println(pw)

      println("*-"*20)
      println("trait  继承class")
      val pm=new PersonM("Div")
      pm.sayHello


   }

   trait Logger{
      def log(message:String)=println("log:"+message)
   }

   trait HelloTrait{
      //定义了一个抽象方法，trait中的方法没有具体实现；
      def sayHello(name:String)
   }

   trait MakeFriendsTrait{
      def makeFriends(p:Student)
   }

   trait Person{
      val eyeNum:Int=2
   }

   trait Woman{
      val emotion:String
      def womanEmotion(name:String)=println(name+" have a "+emotion+" emotion")
   }

   //继承第一个trait时，要使用extends，后面的trait使用with，
   class Student(val name:String) extends Person with Woman with HelloTrait with MakeFriendsTrait with Logger with Cloneable with Serializable{
      val emotion:String="better"

      //重写继承的trait的抽象方法,重写的方法可以调用继承其他trait的field或者具体方法
      def sayHello(othername:String)=println("Hello,"+othername,"i have "+eyeNum+"eyes。")

      def makeFriends(p:Student) {
         println("Hello,my name is " + name + ",your name is " + p.name)
         log("makeFriends method is invoked with parameter Person[name="+p.name+"]")
      }

      def makeNewFriends(p:Student): Unit ={
         println("Hello,my name is " + name + ",your name is " + p.name)
         log("makeNewFriends method is invoked with parameter Person[name="+p.name+"]")
         womanEmotion(p.name)
      }

      //如果需要重写具体的方法，必须作出必要的改变
      def womanEmotion(name:String,age:Int){
         println(name+" have a "+emotion+" emotion, she has "+age+" ages old!!")
      }
   }

   trait logged{
      //后面必须跟{}，不然这个定义的就是一个抽象方法。
      def log(msg:String){}
   }

   //这个要在实例中混入的trait，必须要继承与实例一样的trait
   //trait继承trait，重写方法时，要加override关键字
   trait MyLogger extends logged{
      override def log(msg: String): Unit ={
         println("log: "+msg)
      }
   }

   class Grade(val name:String) extends logged with Woman{
      val emotion:String="good"
      def sayHello={
         println("Hi,i'm "+name);log("sayHello is invoked!")
      }
   }

   trait Handler{
      println("check Handler")
      //具体方法
      def handlerData(data:String){println("Handler "+data)}
      //抽象方法
      def handler(data:String)
   }

   //定义trait调用链，这里要用super方法，这个super方法是要调用下一个trait，即SignatureValidHandler
   trait DataValidHandler extends Handler{
      println("check DataValidHandler")
      override def handlerData(data: String): Unit = {
         println("check DataValidHandler, "+data)
         super.handlerData(data)
      }

      //如果该trait要被其他类继承，那么该trait中不用重写父trait的抽象方法
      // 直接在继承的class中重写父类的trait。
      //abstract override def handler(data:String){
      //   super.handler(data)
      //}
   }

   trait SignatureValidHandler extends Handler{
      println("check SignatureValidHandler")
      override def handlerData(data: String){
         println("check SignatureValidHandler: "+data)
         super.handlerData(data)
      }
   }

   trait ValidHandler extends Handler{
      //重写父trait中抽象方法，要用abstract。
      abstract override def handler(data: String){
         super.handler(data)
         println("this is override father's abstract method "+data)

      }
   }

   //先调用共同的父trait，然后从左往右执行，依次是Handler、SignatureValidHandler、DataValidHandler
   //至于方法的调用是先执行本类下方法，然后从右往左执行，依次是DataValidHandler、SignatureValidHandler、Handler
   class Class(val name: String) extends SignatureValidHandler with DataValidHandler{
      println("check Class ")
      def sayHello={println("Hello, "+name);handlerData(name)}

      //当重写了继承的trait方法，那么在该类下调用该方法就不会再调用继承的父trait。只有在没有重写的时候，才会链式调用父trait中的方法。
//      override def handlerData(data: String): Unit = {
//         println("override handledata "+name)
//      }

      //可加可不加override关键字
      override def handler(newname:String){
         println("your newname is "+newname+", your old name is "+name)
      }

      def printOut(nename:String): Unit ={
         handler("this "+nename)
      }
   }


   //trait 的构造函数
   class Personman{println("Personman's constructor!")}
   trait Loggers{println("Loggers's constructor!")}
   trait MyLoggers extends Loggers{println("MyLoggers's constructor!")}
   trait TimeLogger extends Loggers{println("TimeLoggers's constructor!")}
   //初始化的顺序是先Personman，然后是共同的父trait即Loggers,然后是MyLoggers，TimeLogger，最后是执行本类
   class Students extends Personman with MyLoggers with TimeLogger{
      println("Students's constructor!!")
   }


   //trait field的初始化
   trait sayHello{
      val msg:String
      println(msg.toString)
   }
   //像这种，虽然定义没有问题，但是在初始化的时候，会先初始化sayHello，由于sayHello中的msg是没有值的，print会出错
//   class PersonWo extends sayHello{
//      val msg:String="init"
//   }


   //第一种初始化方法
   class PersonWoman
   //使用动态混入trait。在初始化sayHello之前，重写了msg。
   val pw=new {val msg:String="init"} with PersonWoman with sayHello

   //第二种初始化方法,这在初始化sayHello之前，重写了msg。
   class PersonWoman1 extends {val msg:String="init"} with sayHello {}

   //第三种初始化方法,将field 先定义成lazy模式，然后继承
   trait sayHello1{
      lazy val msg:String=null
      println(msg.toString)
   }
   class PersonWoman2 extends sayHello{
      override lazy val msg: String = "init"
   }

   //trait继承class
   class MyUtil{
      def printMessage(msg:String)=println(msg)
   }
   trait AllLogger extends MyUtil{
      def log(msg:String)=printMessage("log: "+msg)
   }

   class PersonM(val name:String) extends AllLogger{
      def sayHello: Unit ={
         log("Hi, i'm "+name)
         printMessage("this message from "+name)
      }
   }
}
