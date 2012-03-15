package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._


case class Posting(id: Long, subject: String, description: String)

object Posting {

  val postingForm = Form(
    tuple(
      "subject" -> text,
      "description" -> text
    )
  )

  val posting = {
    get[Long]("id") ~
    get[String]("subject") ~
    get[String]("description") map {
      case id~subject~description => Posting(id, subject, description)
    }
  }

  def create(subject: String, description: String) {
    DB.withConnection { implicit c =>
      SQL("insert into posting (subject, description) values ({subject}, {description})").on(
        'subject -> subject,
        'description -> description
      ).executeUpdate()
    }
  }

  def list() : List[Posting] = DB.withConnection { implicit c =>
    SQL("select * from posting").as(posting *)
  }

}
