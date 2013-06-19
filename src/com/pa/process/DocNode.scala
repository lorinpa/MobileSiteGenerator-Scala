package com.pa.process

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import scala.collection.mutable.ListBuffer

/** DocNode represents a single website article.
  *
  * All properties are mutable and contain getter/setters.
  *
  * @constructor Constructor does not require any parameters.
  *
  */
class DocNode {
  var title: String = _
  var body: String = _
  var link: String = _
  var pubDate: String = _
}

/** DocNodeListHandler represents a SAX event handler which populates
 *  a collection of DocNodes (website articles)
 */
class DocNodeListHandler extends DefaultHandler  {
  val docNodeList = ListBuffer[DocNode]()
  var start = false 
  var node: DocNode = null
  val buffer:StringBuilder = new StringBuilder()
    
  override def startElement(namespaceURI: String, localName: String ,  qName:String , atts: Attributes ) = {
    buffer.setLength(0);
  
    if (qName.equals("language")) {
      start = true;
    } else if (qName.equals("item") && start) {
      node = new DocNode()
    }
  }

  override def endElement( uri: String,  localName: String,  qName: String)  = {
    if (start) {
      if (qName.equals("title")) {
        node.title = buffer.toString()
      } else if (qName.equals("description")) {
        node.body= buffer.toString()   
      } else if (qName.equals("pubDate")) {
        node.pubDate= buffer.toString()    
      } else if (qName.equals("link")) {
        node.link = buffer.toString()
        node.link= node.link.split("/")(node.link.count(_=='/') )
      } else if (qName.equals("item")) {     
        docNodeList.append(node)
        node = new DocNode();
      }
    }
  }

  override def  characters(ch: Array[Char],start:  Int ,  length: Int)  = {
    buffer.append(new String(ch, start, length))
  }
  /**
   * @return A collection of DocNodes (website articles) 
   */
  def  get() : ListBuffer[DocNode] = {
    docNodeList
  }
  
}
