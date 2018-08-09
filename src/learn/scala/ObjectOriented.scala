package learn.scala

/**
  * Created by lj on 2018/7/14.
  *
  * 如果定义不带private的var field，此时Scala生成的面向JVM的类时，会定义private的name字段，
  * 并提供public的getter和setter方法
  *
  * 如果使用private修饰field，则生成getter和setter也是private的
  *
  * 如果定义val field，则只会生成getter方法
  *
  * 如果不希望生成getter和setter方法，则将field声明为private[this]，只能在该类中使用，
  * 其他类即使继承了，也不能使用
  *
  *
  * Scala中，同样可以在类中定义内部类；每个外部类的对象的内部类，都是不同的类
  *
  */


import scala.collection.mutable.ArrayBuffer
object ObjectOriented {
   def main(args: Array[String]): Unit = {
      //创建类的对象，并调用其方法
      //new 类名：就可以创建类的对象
      var hello=new  HelloWorld
      hello.sayhello()
      println(hello.getName)

      val hel=new Hell0
      println(hel.name)//leo
      hel.name="Jack"
      println(hel.name)//Jack

      val s=new Students
      println(s.name)//调用的是name方法
      s.name="Leo"//调用的是name_方法

      val s1=new Students1
      s1.updateName("Jack2")
      println(s1.getName)

      val a1=new Students2
      val a2=new Students2
      a1.age=20
      a2.age=30
      println(a1.older(a2))


      val st1=new Students3
      val st2=new Students3
      st1.age=20
      st2.age=30
      //println(st1.older(st2))//这里出错了，这是因为在Students3中定义了private[this]，
      // 而s2传入了其他类的实例


      //注册一个班级
      val c1=new Class
      val s11=c1.getStudent("Jack")
      c1.students+=s11

      //有注册一个班级
      val c2=new Class
      val s21=c2.getStudent("Steve")
      //c1.students+=s21//不能放入到c1中的内部类中，c1.students和c2.students是不同外部实例的不同的类
   }


   //定义一个类
   class HelloWorld{
      //私有化，使得实例方法无法调用，只有通过专门的类方法给外部一个接口
      private var name="leo"//name有getter和setter方法，只不过是private
      val age=24//age有getter方法，是public的
      //定义方法
      def sayhello(){println("Hello,"+name)}
      //
      def getName:String=name
   }


   class Hell0{
      //没有私有化，外部就能访问
      var name="leo"//有getter和setter方法，是public的
      private val age=23//age只有getter方法，是private的
      //定义方法
      def sayhello(){println("Hello,"+name)}

   }

   /*
   *自定义getter和setter方法,这时候一定要注意Scala的语法限制，签名、=、参数间不能有空格
    */

   class Students{
      private var myName="Jack"
      //定义getter方法
      def name:String="your name is "+myName
      //定义setter方法，在名称下面加个_，注意不能有空格
      def name_=(newValue:String){
         println("you cannot edit your name!!!")
      }
   }

   /*
   *如果不想field有setter方法，则可以定义val，这时，就在也不能更改field的值
   * 但是如果希望能够仅仅暴露出一个getter方法，并且还能通过某些方法更改field的值，
   * 那么需要综合使用private以及自定义getter方法；此时，由于field是private的，
   * 所以setter和getter都是private，对外界没有暴露，自己可以实现修改field值的方法，
   * 自己可以覆盖getter方法
    */
   class Students1{
      private var name="Jack"
      //定义的是setter方法
      def updateName(newName:String){
         if(newName=="Jack1")name=newName
         else println("not accept this new name!!!")
      }

      //定义的是getter方法
      def getName:String="My name is "+name

   }

   /*
   * 如果将field使用private来修饰，那么代表这个field是类私有的，在类的方法中，
   * 可以直接访问类的其他对象的private field;这种情况下，如果不希望field被其他对象访问到，
   * 那么可以使用private[this],意味着对象私有的field，只有本对象内可以访问到
   */

   class Students2{
      private var Myage=0

      //注意age_和下面的age的名字要一样，只差一个_
      def age_=(newValue:Int){
         if(newValue>0) Myage=newValue
         else println("illegal age!")
      }

      def age=Myage
      def older(s:Students2)={
         Myage>s.Myage
      }
   }

   class Students3{
      private[this] var Myage=0

      //注意age_和下面的age的名字要一样，只差一个_
      def age_=(newValue:Int){
         if(newValue>0) Myage=newValue
         else println("illegal age!")
      }

      def age=Myage
      //def older(s:Students2)={
        // Myage>s.Myage//报错了
      }


   //定义类中类
   //定义班级类
   class Class{
      //定义学生类
      class Student(val name:String){}

      //存储班级中的学生
      val students=new ArrayBuffer[Student]
      //得到一个新的学生
      def getStudent(name:String)={
         //返回的是当时调用的时候的内部类，就是它的实例
         new Student(name)
      }
   }




}
