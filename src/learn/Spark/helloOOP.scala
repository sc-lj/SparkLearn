package learn.Spark

/*面向对象的的核心特征：
 * 1、对象不关心消息从哪里来，不关心消息到哪里去，只关心消息的处理本身，在面向对象系统中，对象是弱耦合的，且对象是是消息驱动的或者是数据驱动的
 * 2、一个对象的行为不影响另外一个对象的行为，例一个对象挂掉了，另外一个对象也不会跟着挂掉。
 * 3、面向接口编程
 * 
 * Scala是支持面向对象的语言，而不是面向对象语言。
 * 封装、继承、多态只不过是支撑面向对象的语言级别的语法或者一些功能
 * 
 * 函数是不依赖类的，一旦依赖于类，就是方法了。
 * 
 *  
 */

/*
 * 1、在Scala中定义类是用class关键字；
 * 2、可以使用new ClassName 的方式构建出类的对象
 * 3、如果名称相同，则object中的内容都是class的静态内容，也就是说object中的内容class都可以在没有实例的时候直接去调用，
 * 		正是因为可以在没有类的实例的时候去调用object中的一切内容，所以可以使用object中的特定方法来创建类的实例，而这个特定方法
 * 		就是apply方法；
 * 4、object中的apply方式是class对象生成的工厂方法，用于控制对象的生成。
 * 5、很多框架的代码一般直接调用抽象类的object的apply方法去生成类的实例对象；
 * 		第一、其秘诀在于apply具有类的对象生成的一切生杀大权，抽象类是不可以直接实例化的，在apply方法中可以实例化抽象类的子类，
 * 				在spark中的图计算为例，Graph是抽象的class，在object Graph中的apply方法实际上是调用Graph的子类GraphOmpl来构建
 * 				Graph类型的对象实例的，当然从spark图计算的源码可以看出，GraphImpl的构造也是使用了object GraphImpl方法；
 * 		第二：这种方式神奇的效应在于更加能够应对代码版本迭代或者修改的变化，这是更高意义的面向接口编程。
 * 6、object HelloOOP是class HelloOOP的伴生对象，class HelloOOP可以直接访问object HelloOOP中的一切内容，而class HelloOOP是
 * 		object HelloOOP的伴生类，object HelloOOP可以直接访问class HelloOOP的一切内容；有一个特例就是用private[this]修饰的成员。
 * 7、在定义Scala的class的时候可以直接在类名后面()里面加入类的构造参数，此时在apply方法中也必须有该参数
 * 8、Scala中可以在object中构造很多apply方法;
 * 9、Scala中的很多集合都是使用apply的方式构造的，例如Array中：
 * 		  def apply[T: ClassTag](xs: T*): Array[T] = {
    			val array = new Array[T](xs.length)
    			var i = 0
    			for (x <- xs.iterator) { array(i) = x; i += 1 }
    			array
  			}
 * 
 */
class HelloOOP(age:Int){//class HelloOOP是object HelloOOP的伴生类，object HelloOOP是class HelloOOP的伴生对象
  var name="Spark"
  def sayHello=println("Hi,my name is "+name+",I'm "+age+" years old")
}

object HelloOOP {
  var number=0
  def main(args: Array[String]): Unit = {
    println("Hello Scala OOP!!!")
    
//    val helloOOP=new HelloOOP//helloOOP是类的实例
//     helloOOP.sayHello
    val helloOOP=HelloOOP(30)
    helloOOP.sayHello
    val helloOOP0=HelloOOP()
    helloOOP0.sayHello
    
    Array(1,2,4,5,6)
    
  }
  
  def apply():HelloOOP={
    println("My number is : "+number)
    number+=1
    new HelloOOP(10)
   }
     
  def apply(age:Int):HelloOOP={
    println("My number is : "+number)
    number+=1
    new HelloOOP(age)
  }

 }
  
  
  
