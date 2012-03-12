package controllers

import play.api._
import play.api.mvc._
import models.Posting

object Application extends Controller {

  def listWanted = Action {
    Ok(views.html.listWanted(Posting.listWanted()))
  }

  def listOffered = Action {
    Ok(views.html.listOffered(Posting.listOffered()))
  }

  def createPosting = Action {
    Ok(views.html.createPosting())
  }

}