package learn.Spark

import org.apache.spark.internal.Logging
import org.apache.log4j.{Logger,Level}

//主要是用来对输出的日志信息进行格式设置
object StreamingExamples extends Logging {
      def setStreamingLogLevels(): Unit ={
         val log4jInitialized =Logger.getRootLogger.getAllAppenders.hasMoreElements
         if (!log4jInitialized){
            logInfo("Setting log level to [WARN] for streaming example." +
              " To override add a custom log4j.properties to the classpath.")
            Logger.getRootLogger.setLevel(Level.WARN)
         }
      }
}
