package learn.scala


/**
  * Created by lj on 2018/8/12.
  *
  * Actor 代表的是多线程编程，但是其模式与多线程有所不同。Scala的actor尽可能地避免锁和共享状态。从而避免多线程并发时出现资源争用的情况，
  * 进而提升多线程编程的性能。这种模式还可以避免死锁等一系列多线程编程的问题。
  * spark中使用的分布式多线程框架--Akka。其核心概念也是Actor。
  *
  * scala 提供了actor trait来让我们更方便地进行actor多线程编程，就actor trait是基础的多线程基类和接口。只需要重写actor trait的act方法，
  * 即可实现自己的线程执行体。
  * 此外，使用start()方法启动actor；使用!(感叹号)向actor发送消息；actor内部使用receive和模式匹配接收消息。
  *
  */

object ScalaActor {
   def main(args: Array[String]): Unit = {

   }

//   class HelloActor extends Actor

}















