package learn.scala

/**
  * Created by lj on 2018/7/15.
  *
  * 继承代表子类可以拥有父类的field和method；然后子类可以在自己内部放入父类所没有的，子类特有的field和method；
  * 使用继承可以有效复用代码；
  *
  * 子类可以覆盖父类的field和method；但是如果父类用final修饰，field和method用final修饰，则该类是无法被继承的，
  * field和method是无法被覆盖的
  *
  *
  * Scala如果子类覆盖一个父类中的非抽象方法，则必须使用override关键字；
  * 在子类覆盖父类方法后，如果想在子类中调用父类被覆盖的方法，需要用到super关键字，可以显示地指定要调用父类的方法。
  *
  * 子类可以覆盖父类的val field，而且子类的val field还可以覆盖父类的val field的getter方法；只要在子类中使用override关键字
  *
  *如果创建了子类的对象，但是又将其赋予了父类类型的变量，则在后续的程序中，又需要将父类类型的变量转换为子类类型的变量，
  * 那么我们需要先使用islnstanceOf判断对象是否是指定类的对象，如果是的话，则可以使用aslnstanceOf将对象转换为指定类型
  * 注意：如果对象是null，则islnstanceOf一定返回false，aslnstanceOf一定返回null
  * 注意：如果没有用islnstanceOf先判断对象是否为指定类的实例，就直接用aslnstanceOf转换，则可能会抛出异常
  *
  *islnstanceOf只能判读出对象是否是指定类以及其子类的对象，而不能精确判断出，对象就是指定类的对象，而不是其子类的对象
  * 如果要求精确地判断对象就是指定类的对象，那么就只能使用getClass和classOf了
  *对象.getClass 可以精确获取对象的类，classOf[类]可以精确获取类，然后使用==操作符即可判断
  *
  *使用模式匹配进行判断
  * 在实际开发中，比如spark源码中，大量的地方都是使用了模式匹配的方式来进行类型的判断，这种方式更加地简洁明了，
  * 而且代码的可维护性和可扩展性也非常高
  *
  * 使用模式匹配，功能性上来说，与islnstanceOf一样，也是判断主要是该类以及该类的子类的对象即可，不是精准判断的
  *
  *
  * protected 关键字
  * scala 中的关键字protected关键字来修饰field和method，这样在子类中就不需要super关键字了，直接就可以访问field和method
  * 还可以使用protected[this]，则只能在当前子类对象中访问父类的field和method，无法通过其他子类对象访问父类的field和method
  *
  *
  * 调用父类的constructor
  * Scala中，每个类可以有一个主constructor和任意多个辅助constructor，而每个辅助constructor的第一行都必须是调用其他辅助constructor或者主constructor；
  * 因此子类的辅助constructor是一定不可能直接调用父类的constructor的。
  * 只能在子类的主constructor中调用父类的constructor。
  * 注意：如果是父类中接收的参数，比如name和age，子类中接收时，就不要用任何val或者var来修饰了，否则会认为是子类要覆盖父类的field，就要加override关键字
  *
  *
  */

object ClassInherit {
   def main(args: Array[String]): Unit = {
      val s=new Students
      println(s.getScore)
      println(s.getName)
      println(s.age)
      println(s.sex)

      //p：父类的变量引用了子类的对象
      val p:Person=new Students

      //声明一个空的子类的变量,这里要用var声明
      var st:Students=null
      //println(st.getName)//这里就报错了，没有实例化
      //将p强制转换为Students类型，赋值给st
      if(p.isInstanceOf[Students])st=p.asInstanceOf[Students]

      println(st.getName)

      println(p.isInstanceOf[Students])//true
      println(p.isInstanceOf[Person])//true

      println(p.getClass==classOf[Students])//true
      println(p.getClass==classOf[Person])//false

      p match {
         case er:Person=>println("it's Person's Object!!")
         case _=>println("unknown type!!")
      }

   }


   //使用关键字abstract定义一个抽象类,不能直接被实例化
   abstract class Biological{
      val attributes:String//字段没有初始化值，就是一个抽象字段;抽象类中定义的字段，只要没有给出初始化值，就表示是一个抽象字段，但是，抽象字段必须要声明类型
      def info() //抽象方法，不需要使用abstract关键字
      def greeting(){println("welcome to my new world")}
   }


   class Person extends Biological {
      //重写超类字段，需要使用override关键字，否则编译会报错
      override val attributes= "pepole"
      //重写超类的抽象方法时，不需要使用override关键字，不过，如果加上override编译也不错报错
      def info(): Unit ="this is about pepole"
      //重写超类的非抽象方法，必须使用override关键字
      override def greeting(): Unit = {println("welcome to my new world")}
      //成为私有字段，外界无法访问，只有在类内部可以访问该字段。
      private var name="Leo"
      val age:Int=24
      protected val height:Int=124
      protected[this] val weight:Int=68
      //定义一个getter方法，让子类可以获取父类的私有字段
      def getName:String=name

      def sex="M"
   }

   class Students extends Person{
      private var score:String="A"

      def getScore:String=score

      //不能用override覆盖父类的name
      override val age: Int = 30

      override def sex: String = "this person is "+super.sex+", age is "+age

      def getweight:String="this person "+getName+"'s weight is "+weight

      //这种情况下，不能使用s.weight，因为s已经是另外一个类Students的实例了，被this修饰过的，只能在其子类中使用，不被另外一个子类使用
      //def getheight(s:Students)="this person "+s.getName+"'s height is "+weight+", his/her weight is "+s.weight

   }

}
