package learn.scala

/**
  * Created by lj on 2018/7/15.
  *
  *匿名内部类
  *匿名子类可以定义一个类的没有名称的子类，并直接创建其对象，然后将对像的引用赋予一个变量。之后甚至可以将该匿名子类的对象传递给其他函数。
  *
  *
  *
  */
object Anonymous {
   def main(args: Array[String]): Unit = {

      val s=new Students("Jack",30,100)

      //定义匿名类,该子类没有名字，只是继承于Person
      var p=new Person("Leo",25){
         override def sayHello():String= {
            "Hi,i'm name "+name
         }
      }

      greeting(p)

   }
   //函数greeting中定义的sayHello返回的类型要与匿名类重写的函数返回的类型一样。
   def greeting(p: Person{def sayHello():String})={
      println(p.age)
      println(p.sayHello)
   }

   class Person(protected val name: String,var age:Int){
      def sayHello()={
         "Hello ,"+name
      }
   }

   //不用加val或者var
   class Students(name:String,age:Int,val Score:Double) extends Person(name,age){
      //子类的辅助constructor
      def this(name:String){
         this(name,0,0)
      }

      def this(age:Int){
         this("leo",age,0)
      }
   }
}
