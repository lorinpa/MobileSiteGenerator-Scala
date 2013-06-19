package com.pa.process
import scala.collection.mutable._

/** IndexFile represent our web site table of contents document.
 *  E.G. index.html . 
  *
  * @param sets a content header for the table of contents 
  * E.G. Our Sites Articles
  *
  * @param docList is a collection of DocNode class instances.
  * Each docNode represents a html document (article/webpage).
  */
class IndexFile(title:String, docList: ListBuffer[DocNode]) {
  /** Generates a html list of website articles. 
   *  
   *  @return A string containing an html list of website articles,
   *  titles and links.
   */
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
  /** Adds a ".html" suffix to an article's
   *  file name/link.
   *  
   *  @param the article's link. Drupal generates
   *  link/paths which do not contain a html suffix.
   *  Therefore, we add the suffix here.
   *
   *  @return the link/file name with a ".html" suffix added.
   */
  def fullLink(link: String) = {
    link + ".html"
  }
  /** Constructs a new docNode which represents 
   *  our mobile website's table of contents. 
   *
   *  @return a new docNode which represents the mobile
   *  website's table of contents (index.html)
   */
  def toDocNode() : DocNode = {
    val docNode = new DocNode
    docNode.title = title
    docNode.link = "index.html"
    docNode.body = generateBody()
    docNode
  }
}
