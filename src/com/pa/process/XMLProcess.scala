package com.pa.process

import javax.xml.parsers.SAXParserFactory
import org.xml.sax.InputSource
import org.xml.sax.XMLReader
import scala.collection.mutable._

/** XMLProcess configures a SAX parser and connects an SAX 
 *  event handler (DocNodeListHandler ) .
 *
 */

class XMLProcess {
  val SAX_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
  val SAX_NAMESPACES = "http://xml.org/sax/features/namespaces";

  def  initializeReader() :  XMLReader = {
    val factory = SAXParserFactory.newInstance()
    factory.setFeature(SAX_NAMESPACE_PREFIXES, true)
    factory.setFeature(SAX_NAMESPACES, false)
    val parser = factory.newSAXParser()
    val xmlreader = parser.getXMLReader()
    xmlreader
  }

  /** Connects the SAX event handler (DocNodeListHandler)
   *  and starts the parse process.
   * 
   *  @param is A generice input source. Our default use case 
   *  is a file stored on disk.
   *
   *  @return A collection of DocNodes. Each DocNode stores the 
   *  pertinent information parsed from out input soure 
   *  (E.G. article title, body, publish date, url)
   */
  def parseObject( is:InputSource) : ListBuffer[DocNode] = {

    try {
      val xmlreader = initializeReader();
      val handler = new DocNodeListHandler()
      xmlreader.setContentHandler(handler);
      xmlreader.parse(is);
      handler.get()
    } catch  {
      case ex: Exception => throw ex
    }
  }
}
