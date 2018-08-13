package learn.scala

/**
  * Created by lj on 2018/7/15.
  *
  * object ,相当于class的单个实例，通常在里面放一些静态的field或者method，
  *
  * 第一次调用object的方法时，就会执行object的constructor,也就是object内部不在method中代码；
  * 但是object不能定义接受参数的constructor
  *
  * 注意：object的constructor只会在其第一次被调用时执行一次，以后再次调用就不会再次执行constructor了
  *
  * object通常用于作为单例模式的实现，或者放class的静态成员，比如工具方法
  *
  *
  * 伴生对象
  * 如果有一个class，还有一个与class同名的object，那么就称这个object是class的伴生对象，class是object的伴生类
  *
  * 伴生类和伴生对象必须放在一个.scala文件中
  * 伴生类和伴生对象，最大的特点就在于，互相可以访问private field；一般是通过object.field 形式访问的。
  *
  * object继承抽象类
  * object的功能其实和class类似，除了不能定义接受参数的constructor之外，object也可以继承抽象类，并覆盖抽象类中的方法。
  *
  *
  * apply方法
  * 通常在伴生对象中实现apply方法，并在其中实现构造伴生类的对象的功能；
  * 而创建伴生类的对象时，通常不会使用new Class的方式，而是使用Class()的方式，隐式地调用伴生对象的apply方法，
  * 这样会让对象创建更加简洁。
  *
  * 在Scala中，apply方法和update方法都会遵循相关的约定被调用，约定如下：
  * 用括号传递给变量(对象)一个或多个参数时，Scala 会把它转换成对apply方法的调用；
  * 与此相似的，当对带有括号并包括一到若干参数的对象进行赋值时，编译器将调用对象的update方法，
  * 在调用时，是把括号里的参数和等号右边的对象一起作为update方法的输入参数来执行调用
  *
  * 比如，Array类的伴生对象的apply方法就实现了接受可变数量的参数，并创建一个Array对象的功能。
  *
  *
  * Scala要实现枚举功能，则需要用object继承Enumeration类，并且调用Value方法来初始化枚举值
  *
  */

//创建一个伴生对象；单例对象的定义和类的定义很相似，明显的区分是，用object关键字，而不是用class关键字
object LearnObject {
   private var lastId = 0  //一个人的身份编号
   private def newPersonId() = {
      lastId +=1
      lastId
   }
   def apply(name:String,age:Int): Any = {
      println("Hello！my name is " + name + ", i'm age olds is " + age)
   }

   def main(args: Array[String]){
      val person1 = new LearnObject("Ziyu")
      val person2 = new LearnObject("Minxing")
      person1.info()
      person2.info()//第二次调用的时候，数字变为2。

      //在这里可用new声明，也可不用new声明
      //val person3 = LearnObject
      val person3 =new LearnObject
      person3("Jack",12)//调用的是伴生类中apply方法

      val person4=LearnObject("Mike",35)//调用的是伴生对象中的apply方法

   }
}


//当单例对象与某个类具有相同的名称时，它被称为这个类的“伴生对象”。类和它的伴生对象必须存在于同一个文件中，而且可以相互访问私有成员（字段和方法）。
//构建伴生类
class LearnObject {
   private val id = LearnObject.newPersonId() //调用了伴生对象中的方法
   private var name = ""
   def this(name: String) {
      this()
      this.name = name
   }
   def info() { printf("The id of %s is %d.\n",name,id)}

   def apply(name:String,age:Int): Any ={
      println("this is "+name+", his/her age is "+age)
   }
}








abstract class Hello(var message:String){
   def sayHello(name:String):Unit
}

object Hellolmpl extends Hello("hello"){
   override def sayHello(name: String):Unit= {
      println(message+","+name)
   }
}

//Hellolmpl.sayHello("Jack")


//实现枚举类
object Season extends Enumeration{

   var SPRING,SUMMER,AUTUMN,WINTER=Value
}

//Season.SPRING

object Season1 extends Enumeration{
   //传入了index和value
   var SPRING=Value(0,"spring")
   var SUMMER=Value(1,"su")
   var AUTUMN=Value(2,"autumn")
   var WINTER=Value(3,"winter")
}
//查找index
//Season1.SPRING.id

//查找value
//Season1.SPRING.toString

//通过index查找

//Season1(1)
//Season2(2)

//通过value查找
//Season1.withName("winter")

//for(w<-Season1.values)println(w)

