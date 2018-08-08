package learn.scala

object ScalaDataStruct {
   def main(args: Array[String]): Unit = {
      genIterator()

   }

   def genTuple(): Unit ={
      //元组是不同类型的值的聚集。元组和列表不同，列表中各个元素必须是相同类型，而元组可以包含不同类型的元素。
      val tuple = ("BigData",2015,45.0)
      println(tuple._1)//获得第一个元素，BigData
      println(tuple._2)//获得第二个元素，2015

   }

   def genList(): Unit ={
      //声明一个列表
      val intList=List(5,1,2,3,4)
      //获取列表的头部，5
      println(intList.head)
      //获取列表的尾部,List(1,2,3,4)，尾部是一个列表
      println(intList.tail)
      //获取第二个元素
      println(intList(1))

      //取列表第二个元素
      println(intList.tail.head)

      //取除最后一个元素外的元素，返回的是列表
      println(intList.init)

      //取列表最后一个元素，返回元素值
      println(intList.last)

      //列表元素倒置，返回新列表
      val newIntList=intList.reverse.tail

      //在列表的头部增加一个元素，0
      //该操作不会改变intList列表
      val intListOther = 0::intList

      //在列表的头部增加一个元素，5
      //该操作不会改变intList列表
      val intList1=5+:intList

      //在列表的头部增加一个元素，List(5,4)
      val intList2=List(5,4) +: intList

      //不能写成如下形式
      //val intList11=intList+:5

      //在列表尾部加个元素，5
      val intList3=intList:+5

      //drop丢弃前n个元素,返回新列表; dropRight(n: Int): List[A] 丢弃最后n个元素，并返回新列表
      val newintList3=intList3.drop(2)

      //获取前n个元素;takeRight(n: Int): List[A] 丢弃最后n个元素，并返回新列表
      val newIntList2=intList2.take(3)

      //去除列表的重复元素，并返回新列表
      val newIntList1=intList1.distinct

      //判断是否为空
      println(intList3.isEmpty)

      //::操作符是右结合的，因此，如果要构建一个列表List(1,2,3)，实际上也可以采用下面的方式,其中Nil表示空列表。
      val initList = 1::2::3::Nil

      //:::操作符对不同的列表进行连接得到新的列表,List(1, 2, 3, 3, 4, 5, 6)
      val list1=List(1,2,3)
      val list2=List(3,4,5,6)
      val newlist=list1:::list2//list连接
      val newlist1=List.concat(list1,list2) //list连接
      println(newlist.sum)//求和
      println(newlist.contains(2))//检测列表中是否包含指定的元素

      val newList=newlist.sorted//排序

      //dropWhile(p: (A) => Boolean): List[A]   从左向右丢弃元素，直到条件p不成立
      //endsWith[B](that: Seq[B]): Boolea   检测列表是否以指定序列结尾
      //startsWith[B](that: Seq[B], offset: Int): Boolear   检测列表在指定位置是否包含指定序列
      //indexOf(elem: A, from: Int): Int 从指定位置 from 开始查找元素第一次出现的位置
      // intersect(that: Seq[A]): List[A] 计算多个集合的交集

      var b=List("woe","wow","adabosting","bostong","randormforest")
      println(b.filter(_.length>=4))
      //等价于
      println(b.filter(x=>x.length>=4))
      println(b.indexOf("bost",3))


   }

   def genSet(): Unit ={
      //集(set)是不重复元素的集合。列表中的元素是按照插入的先后顺序来组织的，但是，”集”中的元素并不会记录元素的插入顺序，而是以“哈希”方法对元素的值进行组织，所以，它允许你快速地找到某个元素。
      //集包括可变集和不可变集，缺省情况下创建的是不可变集，通常我们使用不可变集。
      //虽然可变集和不可变集都有添加或删除元素的操作，但是，二者有很大的区别。对不可变集进行操作，会产生一个新的集，原来的集并不会发生变化。 而对可变集进行操作，改变的是该集本身，

      //声明不可变集
      var mySet=Set("Hadoop","Spark")//这里声明变量，需要var，不然后面无法添加新的元素
      mySet +="Scala" //向mySet中增加新的元素
      println(mySet.contains("Scala"))
      println(mySet)

      //声明可变集,需要导入包
      import scala.collection.mutable.Set
      val myMutableSet=Set("Hadoop","Spark")//这里声明变量可以使用val，因为这是声明的是可变集
      myMutableSet+="Scala"
      println(myMutableSet.contains("Scala"))
      myMutableSet-="Spark"//删除元素
      println(myMutableSet)

   }


   def genDict(): Unit ={
      //字典在Scala中又叫映射
      //在Scala中，映射(Map)是一系列键值对的集合，也就是，建立了键和值之间的对应关系。在映射中，所有的值，都可以通过键来获取。
      //映射包括可变和不可变两种，默认情况下创建的是不可变映射，如果需要创建可变映射，需要引入scala.collection.mutable.Map包。

      //创建不可变映射
      val university = Map("XMU" -> "Xiamen University", "THU" -> "Tsinghua University","PKU"->"Peking University")
      println(university("XMU"))
      val xmu=if(university.contains("XMU")) university("XMU") else 0
      println(xmu)


      //定义可变映射
      import scala.collection.mutable.Map
      val university1=Map("XMU" -> "Ximen University", "THU" -> "Tsinghua University","PKU"->"Peking University")
      university1("XMU")="Xiamen University" //更新已有元素
      university1("FZU")="Fuzhou University" //添加新元素

      university1 += ("Swufe"->"southwest of financial university")//添加一个新元素
      university1 += ("SDU"->"Shandong University","WHU"->"Wuhan University")//同时添加两个新元素

      //循环遍历映射
      for ((k,v)<-university1){
         printf("Code is : %s and name is: %s\n",k,v)
      }

      //遍历键
      for(k<-university1.keys){
         println(k)
      }

      //遍历映射的值
      for (v<-university1.values)println(v)
   }


   def genIterator(): Unit ={
      //在Scala中，迭代器（Iterator）不是一个集合，但是，提供了访问集合的一种方法。当构建一个集合需要很大的开销时（比如把一个文件的所有行都读取内存），迭代器就可以发挥很好的作用。
      //迭代器包含两个基本操作：next和hasNext。next可以返回迭代器的下一个元素，hasNext用于检测是否还有下一个元素。
      val iter=Iterator("Hadoop","Spark","Scala")
      while (iter.hasNext){
         println(iter.next())
      }
      val iter1=Iterator("Hadoop","Spark","Scala")
      for (elem<-iter1){
         println(elem)
      }

      val iter2=Iterator(List("Hadoop","Spark"),List("Scala"))
      for (elem<-iter2){
         println(elem)
      }
      println("\n")
      val university1=Map("XMU" -> "Ximen University", "THU" -> "Tsinghua University")
      val university2=Map("PKU"->"Peking University")
      val iter3=Iterator(university1,university2,List("Hadoop","Spark"),"Scala")
      for (elem<-iter3){
         if(elem.isInstanceOf[List[String]]){
            for(j<- elem.asInstanceOf[List[String]]){
               println(j)
            }
         } else if(elem.isInstanceOf[Map[String,String]]){
            elem.asInstanceOf[Map[String,String]].foreach{case (key, value) => println(value)}
         } else {
            println(elem)
         }
      }

      println("\n")
      val iter4=Iterator(university1,university2,List("Hadoop","Spark"),"Scala")

      iter4.foreach{
         iter =>
            iter match {
               case ls: List[String] =>
                  ls.foreach(println)
               case map: Map[String, String] =>
                  map.foreach{case (key,value)=>println(value)}
               case s: String =>
                  println(s)
            }
      }

   }


}
