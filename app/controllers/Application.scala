package controllers

import play.api._
import play.api.mvc._
import java.util.UUID
import models.{Category, Posting}

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.listOffered())
  }

  def listWanted = Action {
    Ok(views.html.listWanted(Posting.list(Category.WANTED)))
  }

  def listOffered = Action {
    Ok(views.html.listOffered(Posting.list(Category.OFFERED)))
  }

  def createPosting = Action {
    Ok(views.html.createPosting(Posting.postingForm))
  }

  def newPosting = Action {
    implicit request =>
      Posting.postingForm.bindFromRequest.fold(
        errors => BadRequest,
        data => {
          val posting = Posting.create(data)
          Ok(views.html.requirePostingVerification(posting.id))
        }
      )
  }

  def verifyPosting(id: String) = Action {
    Posting.verify(id)
    Ok(views.html.confirmPostingVerification());
  }

}