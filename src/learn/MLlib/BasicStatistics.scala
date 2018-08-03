package learn.MLlib

/**
  * Created by lj on 2018/8/2.
  */


import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary,Statistics}
import org.apache.spark.rdd.RDD

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.stat.test.ChiSqTestResult


object BasicStatistics {
   def main(args: Array[String]): Unit = {
//      SummaryStatistics()
//       StratifiedSampling()
       HypothesisTesting()
   }

   val conf=new SparkConf().setMaster("local").setAppName("BasicStatistics")
   val sc=new SparkContext(conf)

   def SummaryStatistics(): Unit ={
      val observation=sc.parallelize(
         Seq(
            Vectors.dense(1.0,2.0,4.0,8.0,10.0),
            Vectors.dense(2.0,4.0,8.0,16.0,20.0),
            Vectors.dense(4.0,8.0,16.0,32.0,40.0)
         )
      )

      //colStats 返回的实例包括列的最大值、最小值、均值、方差、非零的数字的数量、以及
      val summary:MultivariateStatisticalSummary=Statistics.colStats(observation)
      println(summary.mean)// a dense vector containing the mean value for each column
      println(summary.numNonzeros)// number of nonzeros in each column
      println(summary.variance)// column-wise variance
      println(summary.count)

      sc.stop()
   }

   //计算两列数据的相关系数
   def Correlations(): Unit ={
      val seriesX:RDD[Double]=sc.parallelize(Array(1,2,3,4,5))
      // must have the same number of partitions and cardinality as seriesX
      val seriesY:RDD[Double]=sc.parallelize(Array(11, 22, 33, 33, 555))

      // compute the correlation using Pearson's method. Enter "spearman" for Spearman's method. If a
      // method is not specified, Pearson's method will be used by default.
      //目前提供了两种相关系数计算方法：Spearman和Pearson
      val correlation:Double=Statistics.corr(seriesX,seriesY,"pearson")

      println(s"Correlation is: $correlation")


      // note that each Vector is a row and not a column
      val data:RDD[Vector]=sc.parallelize(
         Seq(
            Vectors.dense(1.0, 10.0, 100.0),
            Vectors.dense(2.0, 20.0, 200.0),
            Vectors.dense(5.0, 33.0, 366.0)
         )
      )

      val corrMatrix:Matrix=Statistics.corr(data,"pearson")
      println(corrMatrix)
   }


   //分层抽样
   def StratifiedSampling(): Unit ={
      val data=sc.parallelize(Seq((1, 'a'), (1, 'b'), (2, 'c'), (2, 'd'), (2, 'e'), (3, 'f')))
      // specify the exact fraction desired from each key
      val fraction=Map(1->0.1,2->0.6,3->0.3)

      // Get an approximate sample from each stratum
      val approxSample=data.sampleByKey(withReplacement = false,fractions = fraction)

      // Get an exact sample from each stratum
      val exactSample=data.sampleByKeyExact(withReplacement = false,fractions = fraction)

   }

    def HypothesisTesting(){
        val vec:Vector=Vectors.dense(1.0,2.0,3.0,4.0)
        val goodnessOfFitTestResult=Statistics.chiSqTest(vec)
        // summary of the test including the p-value, degrees of freedom, test statistic, the method
        // used, and the null hypothesis.
        println(s"$goodnessOfFitTestResult\n")

        // a contingency matrix. Create a dense matrix ((1.0, 2.0), (3.0, 4.0), (5.0, 6.0))
        val mat: Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))
        // conduct Pearson's independence test on the input contingency matrix
        val independenceTestResult=Statistics.chiSqTest(mat)
        // summary of the test including the p-value, degrees of freedom
        println(s"$independenceTestResult\n")

        val obs: RDD[LabeledPoint] =
            sc.parallelize(
                Seq(
                    LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0)),
                    LabeledPoint(1.0, Vectors.dense(1.0, 2.0, 0.0)),
                    LabeledPoint(-1.0, Vectors.dense(-1.0, 0.0, -0.5)
                    )
                )
            ) // (feature, label) pairs.

        // The contingency table is constructed from the raw (feature, label) pairs and used to conduct
        // the independence test. Returns an array containing the ChiSquaredTestResult for every feature
        // against the label.
        val featureTestResults:Array[ChiSqTestResult]=Statistics.chiSqTest(obs)
        featureTestResults.zipWithIndex.foreach{
            case (k,v)=>
                println(s"columns${(v+1)}:")
                println(k)
        }// summary of the test


        val data: RDD[Double] = sc.parallelize(Seq(0.1, 0.15, 0.2, 0.3, 0.25))  // an RDD of sample data

        // run a KS test for the sample versus a standard normal distribution
        val testResult = Statistics.kolmogorovSmirnovTest(data, "norm", 0, 1)
        // summary of the test including the p-value, test statistic, and null hypothesis if our p-value
        // indicates significance, we can reject the null hypothesis.
        println(testResult)
        println()

        // perform a KS test using a cumulative distribution function of our making
        val myCDF = Map(0.1 -> 0.2, 0.15 -> 0.6, 0.2 -> 0.05, 0.3 -> 0.05, 0.25 -> 0.1)
        val testResult2 = Statistics.kolmogorovSmirnovTest(data, myCDF)
        println(testResult2)
    }

    def StreamingSignificanceTesting(): Unit ={

    }

}
