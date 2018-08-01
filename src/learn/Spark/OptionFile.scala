package learn.Spark

import java.io.File


object OptionFile {
    def main(args: Array[String]): Unit = {
        val path:File=new File("/Users/apple/Downloads/")
        for (d<-subdirs(path)){
            println(d)
        }
    }

    //遍历目录
    def subdirs(dir:File) :Iterator[File]={
        val children=dir.listFiles.filter(_.isDirectory())
        //  map 函数会对每一条输入进行指定的操作，然后为每一条输入返回一个对象；
        // flatMap函数则是两个操作的集合——正是“先映射后扁平化”；先对每一条输入进行指定的操作，然后为每一条输入返回一个对象，再将所有对象合并为一个对象。
        children.toIterator++children.toIterator.flatMap(subdirs _)
    }

    def dirdel(path:File): Unit ={
        if(!path.exists()){
            return
        }
        else if (path.isFile()){
            path.delete()
            println(path+": 被删除")
        }
        val file=path.listFiles()
        for (d<-file){
            dirdel(d)
        }
        path.delete()
        println(path+": 目录被删除")
    }

}
