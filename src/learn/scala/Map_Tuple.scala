package learn.scala

/**
  * Created by lj on 2018/7/14.
  * Map 类似与python的字典,一般情况下，Map是无序的
  * Tuple 类似与python的tuple,不能新增元素
  */


object Map_Tuple {
   def main(args: Array[String]): Unit = {
      genMap()
   }

   def genMap():Any={
      //创建一个不可变的Map
      val ages=Map("Leo"->20,"Jen"->30,"File"->26)
      val ages1=Map(("Leo",20),("Jen",30),("File",26))
      //修改对应的key的value
      //ages("Leo")=24 //这种形式会报错

      //对不可变的Map进行添加，只是将其赋值给新的Map，原来的Map还是不变的
      val ages2=ages+("Jack"->35,"Tome"->26)
      //移除不可变的Map中的元素
      val ages3=ages-"Jack"


      //创建可变的Map
      var agess=scala.collection.mutable.Map("Leo"->20,"Jen"->30,"File"->26)
      val agess1=scala.collection.mutable.Map(("Leo",20),("Jen",30),("File",26))
      //修改Map
      agess("Leo")=24
      //增加Map
      agess+=("Jack"->35,"Tome"->26)
      //移除Map
      agess-="Tome"


      //创建空的Map
      val age=new scala.collection.mutable.HashMap[String,Int]
      age("Leo")=24

      //获得Map的value,如果没有该key，会报错
      val leoage=ages("Leo")

      //检查Map中是否有key
      val jenage=if(ages.contains("Jen"))ages("Jen") else 0
      val fileage=ages.getOrElse("File",0)//如果存在就返回，如果不存在就返回0

      //遍历Map
      for((key,value)<-ages)println(key,value)

      //遍历Map中keys
      for(key<-ages.keySet)println(key)

      //遍历Map的values
      for(value<-ages.values)println(value)

      //生成新的Map，反转key和value，其类型跟原来的Map一样，mutable的反转后还是mutable
      for((key,value)<-ages)yield (value,key)

      //创建自动排序的Map，针对key
      val agesss=scala.collection.immutable.SortedMap("Leo"->20,"Jen"->30,"File"->26,"Jack"->35,"Tome"->26)

      //让Map记住插入的顺序
      //LinkedHashMap会按照插入的顺序呈现
      val sortages=new scala.collection.mutable.LinkedHashMap[String,Int]
      sortages("Leo")=23
      sortages("Jen")=24
      sortages("Leos")=24

   }

   //Tuple是一种数据类型,类似python的tuple
   def gentuple():Any={
      val t=("elo",23,"jsf","elo")
      //访问tuple,tuple的index是从1开始的
      println(t._1)


      //zip操作,类似python的zip
      val names=Array("Leo","Jack","Jen")
      val ages=Array(23,25,16)
      val name_age=names.zip(ages)

      for((name,age)<-name_age)println(name,age)

   }

}
