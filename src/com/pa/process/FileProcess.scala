package com.pa.process

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import org.apache.commons.io.FileUtils


class FileProcess {

  def copySet(srcParentDir :String, destDir: String) =  {
    val srcDir = new File(srcParentDir)
    var elem: File = null
    var srcPath: String = null
    var destPath:String  = null
    if (srcDir.isDirectory()) {
      var files = srcDir.list()
      for (file <- files) {
        srcPath = srcParentDir + "/" + file;
        destPath = destDir + "/" + file;
        elem = new File(srcPath)
        if (elem.isDirectory()) {
          copyDir(srcPath, destPath)
        } else {
          copyFile(srcPath,destPath)
        }
      }
    }
  }
 
  def copyFile(src: String, dest: String ) =  {
    try {
      val srcDir = new File(src)
      val destDir = new File(dest)
      FileUtils.copyFile(srcDir, destDir)
    } catch  {
      case ex: IOException => println(ex.getMessage())
    }
  }

  def copyDir(src: String, dest: String) = {
    val srcDir  = new File(src)
    val destDir = new File(dest)
    try {
      FileUtils.copyDirectory(srcDir, destDir)
    } catch {
      case ex: IOException => println(ex.getMessage())
    }
  }

  def writeFile(fileName: String, doc: String) {
    var fos: FileOutputStream = null
    var out: OutputStreamWriter = null
    try {
      fos = new FileOutputStream(fileName)
      out = new OutputStreamWriter(fos, "UTF-8")
      out.write(doc)
    } catch {
      case ex: Exception => println(ex.getMessage())
    } finally {
      out.close()
      fos = null
    }
  }
  
  def createDir(subDir: String) =  {     
    val  file = new File(subDir)
    if (!file.exists()) {
        file.mkdir();
    }
  }
}
