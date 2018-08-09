package learn.scala


/**
  *Scala中可以给类定义多个辅助构造函数(constructor)；辅助constructor之间可以相互调用，
  * 而且必须第一行调用主constructor。
  * 主constructor是与类名放在一起的，而且类中，没有定义在任何方法或者代码块中的代码，
  * 就是主constructor的代码。
  * 如果主constructor传入的参数什么修饰都没有，比如name:String，那么如果类内部的方法使用到了，
  * 则会声明为private[this] name；否则没有该field，就只能被constructor代码使用而已
  *
  * Scala构造器包含1个主构造器和若干个（0个或多个）辅助构造器。
  * 辅助构造器的名称为this，每个辅助构造器都必须调用一个此前已经定义的辅助构造器或主构造器。
  *
  * */
object ScalaConstructor {
   def main(args: Array[String]): Unit = {
      val con=new mainConStudents("lj",age = 27)


      val con1=new Constudents //主构造器
      val con2=new Constudents("lj")//第一个辅助构造器
      val con3=new Constudents(name = "jack",age = 34)//第二个辅助构造器
      con1.getName
      con1.increment(1)
      println(con1.current)

      con2.getName
      con2.increment(2)
      println(con2.current)

      con3.getName
      con3.increment(3)
      println(con3.current)
   }

   //辅助constructor函数
   class Constudents{
      private var name=""
      private var age=0
      //第一个辅助constructor，接受一个参数
      def this(name:String){
         this()//调用主的constructor
         this.name=name//初始化name
      }

      //第二个辅助constructor，这个辅助constructor接受两个参数
      def this(name:String,age:Int){
         //由于上一辅助constructor是接受name的，所以这里可以直接调用上一个辅助constructor
         this(name)//调用上一个辅助constructor
         this.age=age//初始化age
      }

      def getName=println("this is "+name+", age is "+age)
      def increment(step: Int): Unit = { age += step}
      def current: Int = {age}
   }

   //上面是通过辅助构造器来传递参数，下面这个是通过主构造器来传递参数。
   //构建主constructor函数
   class mainConStudents(val name:String,val age:Int){
      println("your name is "+name+", your age is "+age)
   }

   //主constructor函数还可以使用默认参数，来给参数默认的值
   class mainConStudents1(val name:String="Jack",val age:Int=23){
      println("your name is "+name+", your age is "+age)
   }
}
