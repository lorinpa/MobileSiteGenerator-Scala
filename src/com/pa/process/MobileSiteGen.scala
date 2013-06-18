package com.pa.process

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.InputSource
import org.xml.sax.XMLReader
import scala.collection.mutable._

import scala.xml.XML

object MobileSiteGen {

  /** MobileSiteGen is a simple command line utility which converts a website's rss xml feed in
   *  to a mobilzed version of the website.
   *
   * @param args the command line arguments.. Required: -in INFILE -out OUTPUTDIR 
   * INFILE represents the rss xml feed saved to disk as a file.
   * OUTPUTDIR is a directory name which this program creates. The output directory location
   * is created relative to the current directory.
   * 
   */
  def main(args: Array[String]): Unit = {
    var srcFile: String = null
    var outputDir: String = null
    var valid = false
    args.size match {
      case 4 => {
         if ((args(0)== "-in") && (args(2) =="-out")) {
          valid = true
          srcFile = args(1)
          outputDir = args(3)
         } else {
           println("Usage: -in INFILE -out OUTPUTDIR")
         }
      }
      case _ => println("Usage: -in INFILE -out OUTPUTDIR")
    }
    if (valid) {
      val xmlProcess = new XMLProcess
      val file  = new File(srcFile)
      val inputStream = new FileInputStream(file);
      val  reader = new InputStreamReader(inputStream, "UTF-8");
      val  is = new InputSource(reader);
      is.setEncoding("UTF-8");
      val  list = xmlProcess.parseObject(is);
      val cwd = System.getProperty("user.dir");
      // set the output directory relative to the current directory
      val targetDir = cwd + "/" + outputDir
      val fileProcess = new FileProcess()
      fileProcess.createDir(targetDir)
      val indexFileTitle = "A-side"
      val indexFileHref= "index.html"
      val contactBox = new ContactBox
        // write out each article as a html file
      for (doc <- list) {
        doc.body = contactBox.findAndReplace(doc.body)
        fileProcess.writeFile(targetDir+ "/"+ doc.link+".html",  new DocFormatter(doc,indexFileTitle,indexFileHref).format(true))
      }
      // write out the index.html (website table of contents)
      // Note! we have some hard-coded values that are specific to my site. 
      val indexDoc = new IndexFile("Public Action Articles", list).toDocNode()
      fileProcess.writeFile(targetDir+ "/"+ indexDoc.link,  new DocFormatter(indexDoc,"AA-side","a-side.html").format(false))
      // write out the search engine site map file
      val siteMap = new SiteMap
      siteMap.writeOutputFile(targetDir+ "/siteMap.xml", list)
      // add static (pre-composed) content
      val srcDir = cwd + "/static-content";
      fileProcess.copySet(srcDir, outputDir);

      println("Process Complete.")
    }
  }
}






