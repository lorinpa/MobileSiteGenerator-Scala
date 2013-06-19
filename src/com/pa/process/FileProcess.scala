package com.pa.process

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import org.apache.commons.io.FileUtils

/** FileProcess is a thin Scala wrapper around the Apache Commons IO Java Library.
 *
 */
class FileProcess {
  /** Copies a set of files, subdirectories and subdirectory contents 
   *  from the soure locattion represented by srcParentDir, to the location
   *  represented by destDir.
   *
   *  @param srcParentDir represents the location we are copying from.
   *  @param destDir represnets the location we are copyiny to.
   *
   */
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
  /** Copies an individual file.
   *   
   *   @param src represents the file being copied.
   *   @param dest represents the detination location.
   */
  def copyFile(src: String, dest: String ) =  {
    try {
      val srcDir = new File(src)
      val destDir = new File(dest)
      FileUtils.copyFile(srcDir, destDir)
    } catch  {
      case ex: IOException => println(ex.getMessage())
    }
  }

  /** Copies an individual directory and all of it's child contents.
   *
   *  @param src represents the location of the directory we are copying.
   *  @param dest represents the location we are copying to.
   */
  def copyDir(src: String, dest: String) = {
    val srcDir  = new File(src)
    val destDir = new File(dest)
    try {
      FileUtils.copyDirectory(srcDir, destDir)
    } catch {
      case ex: IOException => println(ex.getMessage())
    }
  }
  /** Creates a new file and writes content to the new file.
   *  @param fileName the location and name of the file we are writing.
   *  @param doc represents the content we are writing to the new file.
   *
   */
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
  /** Creates a new directory if it doesn't already exist.
   *  @param subDir represents the name and location of the directory we are either 
   *  creating or checking it's existance.
   */
  def createDir(subDir: String) =  {     
    val  file = new File(subDir)
    if (!file.exists()) {
        file.mkdir();
    }
  }
}
