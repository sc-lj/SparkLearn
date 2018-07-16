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
  * 这样会让对象创建更加简洁
  *
  * 比如，Array类的伴生对象的apply方法就实现了接受可变数量的参数，并创建一个Array对象的功能。
  *
  *
  * Scala要实现枚举功能，则需要用object继承Enumeration类，并且调用Value方法来初始化枚举值
  *
  */
object LearnObject {
   private var eyeNum=2
   print("this is object")

   def geteyeNum=eyeNum

   def apply(name:String,age:Int)=new LearnObject(name,age)
}


class LearnObject(val name:String,val age:Int){
   def sayHello=println("Hi,"+name+", Iguess you are "+age+"years old!"+", and usually you must have "+LearnObject.eyeNum+"eyes")
}

//这两者类似，都能实现同样的功能，但是下面一种比较简洁
//var l=new LearnObject("Jack",23)
//var l=LearnObject("Jack",23)






abstract class Hello(var message:String){
   def sayHello(name:String):Unit
}

object Hellolmpl extends Hello("hello"){
   override def sayHello(name: String)= {
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

