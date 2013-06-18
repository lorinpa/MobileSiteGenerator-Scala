package com.pa.process
import scala.collection.mutable._

class IndexFile(title:String, docList: ListBuffer[DocNode]) {

  def generateBody() = {
    val output =
      <ul class="nav nav-list">
        <li class="nav-header">{title}</li>
        {for (doc <- docList) yield
            <li><a href={fullLink(doc.link)}>{doc.title}</a></li>
        }
      </ul>
    output.toString()
  }

  def fullLink(link: String) = {
    link + ".html"
  }

  def toDocNode() : DocNode = {
    val docNode = new DocNode
    docNode.title = title
    docNode.link = "index.html"
    docNode.body = generateBody()
    docNode
  }
}