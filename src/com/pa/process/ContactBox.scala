package com.pa.process
/** ContactBox is simple (String) search and replace. 
 *  token - Defines a pattern located at the footer of each article on my website.
 *  replace - Text which replaces "token"
 *
 *  @return returns a new version of the body string. The pattern represented 
 *  by "token" is replaced by "replace".
 */
class ContactBox {
  def findAndReplace(body: String) : String  = {
    val token = " - I'm an experienced developer. My main interest is in new technology. Please use our contact box <a href=\"http://public-action.org/contact\" target=\"_blank\">here</a> if you are interested in hiring me. Please no recruiters :)"
    val replace = "is an experienced developer focused on  new technology. Open for work (contract or hire), <a id=\"contact\" href=\"http://public-action.org/mob/contact\" target=\"_blank\">Drop Lorin  a note. </a> <span class=\"muted\">  Please no recruiters :)</span>"
    body.replace(token,replace)
  }
}
