package learn.scala


/**
  * Created by lj on 2018/7/14.
  *
  *在Scala中， Array代表的是长度不可变的数组
  *
  * 要使用ArrayBuffer，需要导入相应的模块,ArrayBuffer是长度可变的集合类
  */

import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting.quickSort
import Array.{concat,ofDim}

object LearnArray {
   def main(args: Array[String]): Unit = {
      //println(initarray())
      //println(initArrayBuffer())
      //ArrayTransfer()
      //filternegative()
      improvementfilternegative()

      // 合并数组
      var myList1 = Array(1.9, 2.9, 3.4, 3.5)
      var myList2 = Array(8.9, 7.9, 0.4, 1.5)

      var myList3 =  concat( myList1, myList2)
      mulArray()

   }

   //数组初始化后，长度就固定下来了，而且元素全部根据其类型初始化
   def initarray():Any={
      val a=new Array[Int](10);a(1)=1//给数组第二个元素赋值
      val b=new Array[String](20);b(1)="你好"
      //也可以不给定数组类型，由其自动判断
      val c=Array("Hello","World")
      println(a.getClass.getSimpleName)
      a.length
      a(0)//取得a中的第一个元素,Array的index是从0开始的;
      b(1)//取b第二个元素
      //这种形式只返回了b，没有返回a
   }

   // 生成多维数组
   def mulArray():Any={
      var matrix= ofDim[Int](3,4)
      for(i<-0 to 2){
        for(j<-0 to 3){
          matrix(i)(j)=j
        }
      }
      val matrix1=matrix.toArray
      matrix1
   }

   def initArrayBuffer():Any={
      //创建空的arraybuffer
      val b=new ArrayBuffer[Int]()
      //向arraybuffer添加元素，通过+=操作符，可以添加一个或多个元素
      b+=1//向b中添加一个元素
      b+=(2,3,4,56,4)//向b中添加多个元素
      //通过++=操作符，可以添加其他集合中的元素
      b++=Array(31,22,34,21,54)

      //从尾部截断指定个数的元素
      b.trimEnd(3)//剔除掉尾部的三个元素，即34,21,54
      b.trimStart(3)//剔除掉头部的三个元素，即1，2，3

      //向数组中插入元素
      b.insert(3,102)//向第4个元素的位置插入102,这是因为index是从0开始的
      b.insert(4,232,233,234,235)//向第5个元素的位置连续插入232,233,234,235

      //使用remove移除指定index的元素
      var inde=b.remove(1)//移除index为1的元素，并返回其值
      b.remove(2,4)//移除index从2开始移除4个元素

      //将ArrayBuffer转换称Array
      var a=b.toArray
      println(a.getClass.getSimpleName)
      //将Array转换成ArrayBuffer
      val c=a.toBuffer

      //遍历Array和ArrayBuffer
      for(i<- b.indices)println(b(i))

      //跳跃遍历
      for(i<-0 until(b.length,2))println(b(i))

      //从尾部遍历
      for(i<-b.indices.reverse) println(b(i))

      //
      for(e<-b)println(e)

      //数组求和
      println(b.sum)
      println(a.sum)

      //数组最大
      println(b.max)

      //只能对不可变长度数组进行排序
      print(quickSort(a))

      //对数组拼接起来，一般是使用mkString，toString函数不是很好用
      println(b.mkString)//442353122
      println(a.mkString(","))//指定拼接符=>4,4,22,31,235
      println(a.mkString("<",",",">"))//指定拼接符==>  <4,4,22,31,235>

      b
   }

   //数组转换
   def ArrayTransfer():Any={
      val a=new ArrayBuffer[Int]()
      a++=(1 to 10)
      //转换成Array
      val b=a.toArray
      //对Array进行转换，还是Array
      val b2=for(e<-b)yield e*e
      println(b2)
      //对ArrayBuffer进行转换，还是ArrayBuffer
      val a2=for(e<-a) yield e*2
      println(a2)

      //结合if
      val a3=for(e<-a if e%2==0)yield e*2
      println(a3)

      //使用函数式编程转换数组;
      //filter是过滤函数，_是一个占位符，表示a中每个元素
      //map是映射函数，_是一个占位符，表示过滤后的每个元素
      a.filter(_ %2==0).map(2*_)
   }


   //移除除第一个之外的负数元素
   def filternegative():Any={
      val a=ArrayBuffer(1,2,3,4,5,-1,-2,-3,6,-5,-4)
      var foundFirstNegative=false
      var arraylength=a.length
      var index=0
      while (index<arraylength){
         if(a(index)<0){
            if(!foundFirstNegative){foundFirstNegative=true;index+=1}
            else {a.remove(index);arraylength-=1}//注意这里长度要自减1
         }
         else index+=1
      }
      println(a)
   }

   //移除除第一个之外的负数元素改良版
   //记录不需要移除的index，最后才一次性移除，这样性能较高，数组内元素移除操作只需要一次
   def improvementfilternegative():Any={
      val a=ArrayBuffer(1,2,3,4,5,-1,-2,-3,6,-5,-4)
      var foundFirstNegative=false
      //||表示或的意思；只要又一个是true就是true；只有是true的才进入到yield里面
      var keepindex=for(i<-a.indices if !foundFirstNegative||a(i)>=0)yield {
         if(a(i)<0) foundFirstNegative=true
         i
      }
      //将所有需要保留的移动前面，最后在截断，删除
      for(i<-keepindex.indices){a(i)=a(keepindex(i))}
      a.trimEnd(a.length-keepindex.length)
      println(a)
   }
}
