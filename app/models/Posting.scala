package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._


case class Posting(id: Long, subject: String, description: String, userName: String, eMail: String, phone: String)

object Posting {

  val postingForm = Form(
    tuple(
      "subject" -> text,
      "description" -> text,
      "userName" -> text,
      "eMail" -> text,
      "phone" -> text
    )
  )

  val posting = {
    get[Long]("id") ~
    get[String]("subject") ~
    get[String]("description") ~
    get[String]("userName") ~
    get[String]("eMail") ~
    get[String]("phone") map {
      case id~subject~description~userName~eMail~phone => Posting(id, subject, description, userName, eMail, phone)
    }
  }

  def create(data: (String, String, String, String, String)) {
    DB.withConnection { implicit c =>
      SQL("insert into posting (subject, description, userName, eMail, phone) values ({subject}, {description}, {userName}, {eMail}, {phone})").on(
        'subject -> data._1,
        'description -> data._2,
        'userName -> data._3,
        'eMail -> data._4,
        'phone -> data._5
      ).executeUpdate()
    }
  }

  def list() : List[Posting] = DB.withConnection { implicit c =>
    SQL("select * from posting").as(posting *)
  }

}
