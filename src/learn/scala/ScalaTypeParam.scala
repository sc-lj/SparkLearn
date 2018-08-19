package learn.scala

/**
  * Created by lj on 2018/8/12.
  *
  * scala 类型参数
  * 使用Scala类型参数可以保证使用该类型参数的地方，就肯定也只能是这种类型。从而实现程序更好的健壮性。
  *
  *
  * 泛型类
  * 泛型类，其实就是在类的声明中，定义一些泛型类型。然后在类内部，比如field或者method，就可以使用这些泛型类型。
  * 使用泛型类，通常是需要对类中的某些成员，比如某些field和method中的参数或变量，进行统一的类型限制，这样可以保证程序更好的健壮性和稳定性。
  * 如果不使用泛型类进行统一的类型限制，那么在后期程序运行过程中，难免会出现问题，比如传入了不希望的类型，导致程序出问题。
  * 在使用类的时候，比如创建类的对象，将类型参数替换为实际的类型，即可。
  * Scala自动推断泛型类型特性；直接给使用了泛型类型的field赋值时，Scala会自动进行类型推断。
  *
  *
  * 泛型函数
  * 泛型函数与泛型类类似，可以给某个函数在声明时指定泛型类型，然后在函数体内，多个变量或者返回值之间，就可以使用泛型类型进行声明，从而对某个特殊的变量，
  * 或者多个变量，进行强制性的类型限制。
  * 与泛型类一样，可以通过给使用了泛型类型的变量传递值来让Scala自动推断泛型的实际类型，也可以在调用函数时，手动指定泛型类型。
  *
  *
  * 上边界Bounds
  * 在指定泛型类型的时候，有时，我们需要对泛型类型的范围进行界定，而不是可以是任意的类型。比如，我们可能要求某个泛型类型，它就必须是某个类的子类，
  * 这样在程序中就可以放心地调用泛型类型继承的父类的方法，程序才能正常的使用和运行。此时就可以使用上下边界Bounds的特性。
  * Scala的上下边界特性允许泛型类型必须是某个类的子类，或者必须是某个类的父类。
  *
  *
  * 下边界Bounds
  * 下边界Bounds指定泛型类型必须是某个类的父类。
  *
  *
  * View Bounds
  * 上下边界Bounds，虽然可以让一种泛型类型，支持有父子关系的多种类型。但是，在某个类与上下边界Bounds指定的父子类型范围内的类都没有任何关系，则默认是肯定不能接受的。
  * View Bounds作为一种上下边界Bounds的加强版，支持可以对类型进行隐式转换，将指定的类型进行隐式转换后，再判断是否在边界指定的类型范围内。
  *
  *
  * Context Bounds
  * Context Bounds是一种特殊的Bounds，它会根据泛型类型的声明，比如"T:类型"要求必须存在一个类型为"类型[T]"的隐式值。Context Bounds之所以
  * 叫Context，是因为它基于的是一种全局的上下文，需要使用到上下文中的隐式值以及注入。
  *
  *
  * Manifest Context Bounds
  * 在Scala中，如果要实例化一个泛型数组，就必须使用Manifest Context Bounds。也就是说，如果数组元素类型为T的话，需要为类或者函数定义[T:Manifest]泛型类型，
  * 这样才能实例化Array[T]这种泛型数组。
  *
  *
  * 协变和逆变
  *
  *
  * Existential Type
  * 在Scala里，有一种特殊的类型参数，就是Existential Type，存在性类型
  *
  *
  */
object ScalaTypeParam {
   def main(args: Array[String]): Unit = {
      val leo=new Student[Int](111)
      getCard[String]("hello world!!!")

      //上边界Bounds
      val marry=new Student1("Marry")
      val jack=new Worker("Jack")
      val mike=new Student1("Mike")
      //下面这行编译错误，这是因为输入的参数必须是Person类或者Person的子类
      //val party=new Party(marry,jack)
      val party=new Party(marry,mike)


      //下边界Bounds
      val father=new Father("jack")
      val child=new Child("Mike")
      getIDCard(jack)
      getIDCard(father)
      getIDCard(child)


      //View Bounds
      val dogg=new Dog("dogg")
      val party1=new Party1(mike,dogg)
      val party2=new Party1(jack,dogg)


      //Context Bounds
      val cal=new Calculator(2,3)
      cal.max


      //Manifest Context Bounds
      val yuxiangrousi=new Meat("yuxiangrousi")
      val gongbaojiding=new Meat("gongbaojiding")
      val shousimaocai=new Vegetable("shousimaocai")
      val meatpackage=packageFood(yuxiangrousi,gongbaojiding)
      println(meatpackage)

      //协变
      val jack1=new Card[Master]("jack1")
      enterMeet(jack1)
      //逆变
      val jack2=new Card1[Professional]("jack2")
      enterMeet(jack2)




   }



   //泛型类
   //T就是泛型类型，代表的是一种类型。可以定义多个泛型类型
   class Student[T](val localid:T){
      def getSchoolld(hukouid:T)="S-"+hukouid+"-"+localid
   }

   //泛型函数
   def getCard[T](content:T)={
      if(content.isInstanceOf[Int])"card:001, "+content
      else if(content.isInstanceOf[String])"card :this is your card, "+content
      else "card: "+content
   }

   //上边界Bounds
   class Person(val name:String){
      def sayHello=println("Hello,I'm "+name)
      def makeFriends(p:Person): Unit ={
         sayHello
         p.sayHello
      }
   }

   class Student1(name:String)extends Person(name)
   class Worker(name:String)
   //T<:Person表示输入的类型必须是Person类型或者其子类
   class Party[T<:Person](p1:T,p2:T){
      def play=p1.makeFriends(p2)
   }

   //下边界Bounds
   class Father(val name:String)
   class Child(name:String) extends Father(name)

   def getIDCard[R>:Child](person:R): Unit = {
      if (person.getClass == classOf[Child]) println("please tell us your parents' name")
      else if (person.getClass == classOf[Father]) println("sign your name for your child's id card")
      else println("sorry,you are not allowed to get id card.")
   }

   //View Bounds
   class Dog(val name:String){def sayHello=println("Wang,Wang,I'm "+name)}
   implicit def dog2person(dog:Object):Person={
      if(dog.isInstanceOf[Dog]){
         val _dog=dog.asInstanceOf[Dog]
         new Person(_dog.name)
      }
      else Nil
   }

   //T<%Person 表示输入的类型可以是Person类型、Person类型的子类、对实例进行隐式转换后是Person类型或者Person子类
   class Party1[T<%Person](p1:T,p2:T)


   //Context Bounds
   class Calculator[T:Ordering](val number1:T,val number2:T){
      //定义了T:Ordering，隐式值的类型为Ordering[T]
      def max(implicit order:Ordering[T])=if(order.compare(number1,number2)>0)number1 else number2
   }


   //Manifest Context Bounds;打包饭菜
   class Meat(val name:String)
   class Vegetable(val name:String)

   def packageFood[T:Manifest](food:T*)={
      val foodPackage=new Array[T](food.length)
      for(i<-0 until food.length)foodPackage(i)=food(i)
      foodPackage
   }


   //协变和逆变==》实例：进入会场
   class Master
   class Professional extends Master

   //大师以及大师级别以下的名片都可以进入会场
   //+T：协变；Master及其子类都可以进来。
   //Card[Professional]是Card[Master]的子类
   class Card[+T](val name:String)
   def enterMeet(card:Card[Master]): Unit ={
      println("welcome to have this meeting!")
   }

   //只要专家级别的名片就可以进入会场，如果大师级别的过来了，当然可以
   //-T：逆变
   class Card1[-T](val name:String)

   //Professional及其父类都可以进来
   def enterMeet(card:Card1[Professional]): Unit ={
      println("welcome to have this meeting!")
   }


}
