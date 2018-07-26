package learn.Spark

import java.io.File


object OptionFile {
    def main(args: Array[String]): Unit = {
        val path:File=new File("")
        for (d<-subdirs(path)){
            println(d)
        }
    }

    //遍历目录
    def subdirs(dir:File) :Iterator[File]={
        val children=dir.listFiles.filter(_.isDirectory())
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
