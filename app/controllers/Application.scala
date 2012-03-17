package controllers

import play.api._
import play.api.mvc._
import models.Posting

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.listOffered())
  }

  def listWanted = Action {
    Ok(views.html.listWanted(Posting.list()))
  }

  def listOffered = Action {
    Ok(views.html.listOffered(Posting.list()))
  }

  def createPosting = Action {
    Ok(views.html.createPosting(Posting.postingForm))
  }

  def verifyPosting = Action {
    Ok(views.html.verifyPosting())
  }

  def newPosting = Action { implicit request =>
    Posting.postingForm.bindFromRequest.fold(
      errors => BadRequest(views.html.listWanted(Posting.list())),
      data => {
        Posting.create(data)
        Redirect(routes.Application.verifyPosting())
      }
    )
  }

}