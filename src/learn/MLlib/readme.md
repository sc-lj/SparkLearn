该部分主要是学习spark的MLlib模块。

MLlib支持本地向量和存在单机上的矩阵计算，以及存在多台机器上的弹性分布数据集矩阵计算。

MLlib支持两种类型的本地向量：密集的和稀疏的。
密集的向量是由double类型的数组表示其全部值；
稀疏向量是由两种方式并行表示的：指针和值。
例如，一个向量(1.0, 0.0, 3.0)可以有密集向量[1.0, 0.0, 3.0]表示，或者由稀疏向量格式(3, [0, 2], [1.0, 3.0])表示, 其中3是向量的大小。

如果训练的数据集中，有大量的稀疏数据。MLlib支持使用LIBSVM格式存储和读取数据。它用每一行表示一个稀疏向量，其格式如下：
   
      label index1:value1 index2:value2 ...

MLlib也支持两种类型的本地矩阵，
密集矩阵就是将矩阵存储成一维的数组，
比如：

      1.0 2.0
      3.0 4.0
      5.0 6.0
 
表示为：[1.0, 3.0, 5.0, 2.0, 4.0, 6.0] with the matrix size (3, 2).


MLlib 支持的分布式矩阵是一种保存有长类型的行和列指针以及double类型值的矩阵，这些矩阵存放在一个或者多个RDDs上。选择合适的格式存储大规模的矩阵至关重要的。
毕竟在分布式机器上转换矩阵格式是非常耗费时间和内存的。MLlib目前支持四种格式的分布式矩阵。

-  RowMatrix(行矩阵) ：

   行矩阵RowMatrix是最基础的分布式矩阵类型。每行是一个本地向量，行索引无实际意义（即无法直接使用）。数据存储在一个由行组成的RDD中，其中每一行都使用一个本地向量来进行存储。由于行是通过本地向量来实现的，故列数（即行的维度）被限制在普通整型（integer）的范围内。在实际使用时，由于单机处理本地向量的存储和通信代价，行维度更是需要被控制在一个更小的范围之内。RowMatrix可通过一个RDD[Vector]的实例来创建
   
-  IndexedRowMatrix(索引行矩阵)：

   索引行矩阵IndexedRowMatrix与RowMatrix相似，但它的每一行都带有一个有意义的行索引值，这个索引值可以被用来识别不同行，或是进行诸如join之类的操作。其数据存储在一个由IndexedRow组成的RDD里，即每一行都是一个带长整型索引的本地向量。

-  CoordinateMatrix(坐标矩阵)：

   CoordinateMatrix是由其条目的RDD支持的分布式矩阵。 每一个矩阵项MatrixEntry都是（i：Long，j：Long，value：Double）的元组，其中i是行索引，j是列索引，value是该位置的值。 只有当矩阵的两个维度都很大且矩阵非常稀疏时，才应使用CoordinateMatrix。


-  BlockMatrix(分块矩阵):
   
   分块矩阵是基于矩阵块MatrixBlock构成的RDD的分布式矩阵，其中每一个矩阵块MatrixBlock都是一个元组((Int, Int), Matrix)，其中(Int, Int)是块的索引，而Matrix则是在对应位置的子矩阵（sub-matrix），其尺寸由rowsPerBlock和colsPerBlock决定，默认值均为1024。分块矩阵支持和另一个分块矩阵进行加法操作和乘法操作，并提供了一个支持方法validate()来确认分块矩阵是否创建成功。

   分块矩阵可由索引行矩阵IndexedRowMatrix或坐标矩阵CoordinateMatrix调用toBlockMatrix()方法来进行转换，该方法将矩阵划分成尺寸默认为1024×1024的分块，可以在调用toBlockMatrix(rowsPerBlock, colsPerBlock)方法时传入参数来调整分块的尺寸。
    

# Basic Statistics

## Summary statistics
MLlib是对列数据进行基本统计的。

## Correlations
MLlib目前支持Pearson和Spearman相关系数的计算。而且可以对多列进行两两列的相关系数进行计算。

## Stratified sampling
MLlib支持两种形式的分层抽样：sampleByKey和sampleByKeyExact。处理的数据以健值对的形式存在，如：(1, 'a')。键就是label，值就是特定的属性。

sampleByKey方法通过提供的参数，即每个键需要的被采样的比例，来对数据进行随机抽样。

sampleByKeyExact比sampleByKey中使用的每层简单随机抽样需要更多的资源，但是会提供99.99％置信度的精确抽样大小。 python目前不支持sampleByKeyExact。

## Hypothesis testing
MLlib支持对数据的独立性和拟合优度的Pearson’s chi-squared ( χ2)检验 。输入数据类型决定了是进行独立性假设检验还是拟合优度的检验。
进行独立性检验需要输入的是 Matrix类型，进行拟合优度检验需要输入的是 Vector类型。

此外，spark.mllib提供了Kolmogorov-Smirnov（KS）检验的单样本，双侧实现，用于概率分布的相等性。 通过提供理论分布的名称（目前仅支持正态分布）及其参数，或根据给定的理论分布计算累积分布的函数，用户可以测试其假设，即他们的样本是从 分配。 在用户针对正态分布（distName =“norm”）进行测试但不提供分发参数的情况下，测试初始化为标准正态分布并记录适当的消息。

## Streaming Significance Testing

