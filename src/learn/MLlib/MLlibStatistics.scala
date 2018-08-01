package learn.MLlib

//需要注意的是，默认的导入scala.collection.immutable.Vector，如果要导入spark的Vector，需要写入下式
import org.apache.spark.ml.linalg.SparseMatrix
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.{Matrices, Matrix}

/**
  * Created by lj on 2018/8/1.
  */
object MLlibStatistics {
   def main(args: Array[String]): Unit = {
      val conf=new SparkConf().setMaster("local").setAppName("MLlibStatistics")
      val sc=new SparkContext(conf)

      //产生一个密集向量(1.0,2.0,0.0,3.0)
      val dv:Vector=Vectors.dense(1.0,2.0,0.0,3.0)

      //产生一个稀疏向量(1.0,2.0,0.0,3.0),Array(0,1,3)是定义向量中非零数字的位置。Array(1.0,2.0,3.0)向量中非零的数字。
      val sv1:Vector=Vectors.sparse(4,Array(0,1,3),Array(1.0,2.0,3.0))

      //产生一个稀疏向量(1.0,2.0,0.0,3.0)，这里是定义非零的位置。
      val sv2:Vector=Vectors.sparse(4,Seq((0,1.0),(1,2.0),(3,3.0)))

      //数据标签的表示方式，类别的表示方式也有稀疏和密集的两种表示方式。
      val pos=LabeledPoint(1.0,dv)
      val neg=LabeledPoint(0.0,sv1)//LabeledPoint(0.0,Vectors.sparse(4,Array(0,2),Array(1.0,2.0,3.0)))

      //读取 LIBSVM格式存储的文件
      //默认的路径是/Users/lj/IdeaProjects/SparkLearn
      val example:RDD[LabeledPoint]=MLUtils.loadLibSVMFile(sc,"./src/learn/MLlib/libsvm_data.txt")

      //生成3X2的 密集类型矩阵((1.0, 2.0), (3.0, 4.0), (5.0, 6.0))
      val dm:Matrix=Matrices.dense(3,2,Array(1,2,3,4,5,6))

      //生成稀疏类型的矩阵，((9.0, 0.0,3.0), (0.0, 8.0,4.0), (0.0, 6.0,5.0))，
      // Array(0, 1,3, 6)中0表示第一个数字在0列，1表示第一列有一个数字，3表示第二列有两个数字，3-1=2，最后一个数字6表示该矩阵总共有多少个数字。
      // Array(0, 2,2, 0,1,2)表示非零元素所在的行号，
      // Array(9, 8, 6,3,4,5)表示矩阵中的非零值,这些非零值是按照矩阵的列排序的
      val sm: Matrix =Matrices.sparse(3,3,Array(0, 1,3, 6), Array(0, 1,2, 0,1,2), Array(9, 8, 6,3,4,5))
      println(sm)

      //生成稀疏类型的矩阵，((0.0, 6.0,0.0), (1.0, 8.0,4.0), (0.0, 0.0,5.0))
      //Array(0,1, 3, 5)中的0表示第一个数字在0列，1示第一列有一个数字，3表示第二列有两个数字，3-1=2，最后一个数字5表示该矩阵总共有多少个数字。
      val sm1: Matrix =Matrices.sparse(3,3,Array(0,1, 3, 5), Array(1, 0,1,1,2), Array(1, 6, 8,4,5))
      println(sm1)

   }


}
