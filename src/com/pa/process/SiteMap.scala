package com.pa.process
import scala.xml.XML
import scala.collection.mutable._

/** SiteMap constructs a xml document used by search engines like
 *  Google to index our website artivles.
 *
 */
class SiteMap {

  val baseAddr = "http://public-action.org/mob"

  def writeOutputFile(fileName: String, items: ListBuffer[DocNode]) = {
    val locationPrefix = "http://public-action.org/mob/doc-"

    val index = -1
    val priority = 0.5
    val changefreq = "daily"
    // Note! On my site the rss feed is published in descending order by publish data
    // thus the first articles in the rss feed (for my site) has the most recent date

    try {
      val output =
        <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9
          http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
          <url>
            <loc>{baseAddr}</loc>
            <changefreq>daily</changefreq>
            <priority>1.0</priority>
            <lastmod>{convertDate(items(0).pubDate)}</lastmod>
          </url>
          {for (item <- items) yield
            <url>
                <loc>{fullLink(item.link)}</loc>
                <lastmod>{convertDate(item.pubDate)}</lastmod>
            </url>
          }
        </urlset>
      scala.xml.XML.save(fileName,output,"UTF-8",true)
    } catch {
      case ex: Exception => ("An error occured " + ex.getMessage() )
    }
  }
  /** Generates full link as required by the Site Map xml schema.
   *  Note we need to add our base address, we can not use relative
   *  URLs here. We need a full url.
   *
   */
  def fullLink(link: String) = {
    baseAddr + "/"+ link + ".html"
  }

  /** Reformats the article published date from the RSS format
   *  to a yyyy-mm-dd
   *  @return a reformatted date
   */
  def convertDate(inDate: String) : String = {
    val split = inDate.split(" ")
    split(3) + "-" + convertMonth(split(2)) + "-" + split(1)
  }
  /** Converts a text representation of a month to a 
   *  numberic representation.
   *
   */
  def convertMonth(monthStr: String) : String = {
    monthStr match {
      case "Jan" => "01"
      case "Feb" => "02"
      case "Mar" => "03"
      case "Apr" => "04"
      case "May" => "05"
      case "Jun" => "06"
      case "Jul" => "07"
      case "Aug" => "08"
      case "Sep" => "09"
      case "Oct" => "10"
      case "Nov" => "11"
      case "Dec" => "12"
      case _ => ""
    }
  }
}
