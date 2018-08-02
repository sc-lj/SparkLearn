package learn.MLlib

//需要注意的是，默认的导入scala.collection.immutable.Vector，如果要导入spark的Vector，需要写入下式
import org.apache.spark.mllib.linalg.{Vector, Vectors}

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.{Matrices, Matrix}
import org.apache.spark.mllib.linalg.distributed.{RowMatrix,BlockMatrix,IndexedRowMatrix,IndexedRow,CoordinateMatrix,MatrixEntry}

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


      // 生产RowMatrix型矩阵,先生成一个local vector
       val v0 = Vectors.dense(1.0, 0.0, 3.0)
       val v1 = Vectors.sparse(3, Array(1), Array(2.5))
       val v2 = Vectors.sparse(3, Seq((0, 1.5), (1, 1.8)))

       //将local vector转换成RDD格式
       val rows = sc.parallelize(Seq(v0, v1, v2))

      //Create a RowMatrix from an RDD[Vector].
      val mat:RowMatrix=new RowMatrix(rows)

       // Get its size.
       val m = mat.numRows()
       val n = mat.numCols()

       // QR decomposition
       val qrResult = mat.tallSkinnyQR(true)

       //计算每列之间相似度，采用抽样方法进行计算，参数为threshold；
       val simic1=mat.columnSimilarities(0.5)

       //计算每列之间相似度。
       val simic2=mat.columnSimilarities()

       //计算每列的汇总统计。
       val simic3=mat.computeColumnSummaryStatistics()
       println(simic3.max)
       println(simic3.min)
       println(simic3.mean)

       //计算每列之间的协方差，生成协方差矩阵。
        val simic4=mat.computeCovariance()

       //计算格拉姆矩阵：`A^T A`。
       //给定一个实矩阵 A，矩阵 ATA 是 A 的列向量的格拉姆矩阵，而矩阵 AAT 是 A 的行向量的格拉姆矩阵。
       val cc2=mat.computeGramianMatrix()

       //计算主成分，前K个。行为样本，列为变量。
       val cc3=mat.computePrincipalComponents(3)

       //计算矩阵的奇异值分解。
       val svd=mat.computeSVD(4,true)
       val U = svd.U
       U.rows.foreach(println)
       val s = svd.s
       val V = svd.V


       // 生成一个IndexedRowMatrix
       // 生成索引行的RDD
       val inr1=IndexedRow(1,v0)
       val inr2=IndexedRow(2,v1)
       val inr3=IndexedRow(3,v2)

       val inrows=sc.parallelize(Array(inr1,inr2,inr3))

       // Create an IndexedRowMatrix from an RDD[IndexedRow].
       val inmat: IndexedRowMatrix = new IndexedRowMatrix(inrows)

       //打印
       inmat.rows.foreach(println)

       // Get its size.
       val inm =inmat.numRows()
       val inn = inmat.numCols()

       // Drop its row indices.
       val rowMat: RowMatrix = inmat.toRowMatrix()



       //生成CoordinateMatrix(坐标矩阵)
       // 创建两个矩阵项ent1和ent2，每一个矩阵项都是由索引和值构成的三元组
       val ent1 = new MatrixEntry(0,1,0.5)
       val ent2 = new MatrixEntry(2,2,1.8)
       // 创建RDD[MatrixEntry]
       val entries : RDD[MatrixEntry] = sc.parallelize(Array(ent1,ent2))
       // 通过RDD[MatrixEntry]创建一个坐标矩阵
       val coordMat: CoordinateMatrix = new CoordinateMatrix(entries)
       //打印
       coordMat.entries.foreach(println)
       //坐标矩阵可以通过transpose()方法对矩阵进行转置操作，并可以通过自带的toIndexedRowMatrix()方法转换成索引行矩阵IndexedRowMatrix。
       // 将coordMat进行转置
       val transMat: CoordinateMatrix = coordMat.transpose()
       // 将坐标矩阵转换成一个索引行矩阵
       val indexedRowMatrix = transMat.toIndexedRowMatrix()


       //分块矩阵（Block Matrix）
       // 创建8个矩阵项，每一个矩阵项都是由索引和值构成的三元组
       val ment1 = new MatrixEntry(0,0,1)
       val ment2 = new MatrixEntry(1,1,1)
       val ment3 = new MatrixEntry(2,0,-1)
       val ment4 = new MatrixEntry(2,1,2)
       val ment5 = new MatrixEntry(2,2,1)
       val ment6 = new MatrixEntry(3,0,1)
       val ment7 = new MatrixEntry(3,1,1)
       val ment8 = new MatrixEntry(3,3,1)


       // 创建RDD[MatrixEntry]
       val Bentries : RDD[MatrixEntry] = sc.parallelize(Array(ment1,ment2,ment3,ment4,ment5,ment6,ment7,ment8))

       // 通过RDD[MatrixEntry]创建一个坐标矩阵
       val BcoordMat: CoordinateMatrix = new CoordinateMatrix(Bentries)

       // 将坐标矩阵转换成2x2的分块矩阵并存储，尺寸通过参数传入
       val matA: BlockMatrix = coordMat.toBlockMatrix(2,2).cache()

       // 可以用validate()方法判断是否分块成功
       matA.validate()

       //通过toLocalMatrix转换成本地矩阵，并查看其分块情况：
       matA.toLocalMatrix

       // 查看其分块情况
       matA.numColBlocks
       matA.numRowBlocks

       // 计算矩阵A和其转置矩阵的积矩阵
       val ata = matA.transpose.multiply(matA)
       ata.toLocalMatrix




   }


}
