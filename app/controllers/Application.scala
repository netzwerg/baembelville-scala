package controllers

import play.api.mvc._
import models.{Category, Posting}
import play.api.Play.current

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
        errors => BadRequest(views.html.createPosting(errors)),
        data => {
          val posting = Posting.create(data)
          sendMail(posting)
          // TODO: Mail error handling
          Ok(views.html.requirePostingVerification(posting.id))
        }
      )
  }

  def sendMail(posting: Posting) {
    import com.typesafe.plugin._
    val mail = use[MailerPlugin].email
    // TODO: Configuration including Links for Activation & Deletion
    mail.setSubject("New posting, yo!")
    mail.addRecipient(posting.eMail)
    mail.addFrom("BliBlaBlo <noreply@email.com>")
    mail.sendHtml("<html>Hello <b>dear</b></html>")
  }

  def verifyPosting(id: String) = Action {
    Posting.verify(id)
    Ok(views.html.verifyPosting())
  }

  def deletePosting(id: String) = Action {
    Ok(views.html.deletePosting(id))
  }

  def deletePostingConfirmed(id: String) = Action {
    implicit request =>
      Posting.delete(id)
      Redirect(routes.Application.listOffered())
  }

}