package learn.scala

/**
  * Created by lj on 2018/7/15.
  *
  *抽象类
  * 如果在父类中，在某些方法无法立即实现，而需要依赖不同的子类来覆盖，重写实现自己的不同的方法实现，此时可以将父类中的这些方法不给出具体的实现，
  * 只是方法签名，这种方法就是抽象方法。
  *
  * 而一个类中如果有一个抽象方法，那么类就必须用abstract来声明为抽象类，此时抽象类是不可以实例化的
  *
  * 在子类中覆盖抽象类的抽象方法时，不需要使用override关键字
  *
  *
  * 抽象field
  *如果在父类中，定义了field，但是没有给出初始值，则此field为抽象field
  * 抽象field意味着，Scala会根据自己的规则，为var或者val类型的field生成对应的getter和setter方法，但是父类中是没有该field的
  * 子类必须覆盖field，以定义自己的具体field，并且覆盖抽象field，不需要使用override关键字
  *
  */
object LearnAbstract {
   def main(args: Array[String]): Unit = {
      //抽象类不能实例化
      //val p=new Person

      val s=new Students("Leo")
      println(s.sayHello)
      println(s.age)
   }

   abstract class Person(val name:String){
      val age:Int
      def sayHello:String
   }


   class Students(name:String) extends Person(name){
      //返回的类型与原override类型要一样
      def sayHello:String={
         "Hello, "+name
      }

      val age:Int=25
   }


}
