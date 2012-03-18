package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import java.util.UUID

case class Posting(id: String, verified: Boolean, subject: String, description: String, userName: String, eMail: String, phone: String)

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
      get[String]("id") ~
      get[Boolean]("verified") ~
      get[String]("subject") ~
      get[String]("description") ~
      get[String]("userName") ~
      get[String]("eMail") ~
      get[String]("phone") map {
      case id ~ verified ~ subject ~ description ~ userName ~ eMail ~ phone => Posting(id, verified, subject, description, userName, eMail, phone)
    }
  }

  def create(data: (String, String, String, String, String)): Posting =
    DB.withConnection {
      implicit c =>

        val posting = Posting(UUID.randomUUID().toString, false, data._1, data._2, data._3, data._4, data._5)

        SQL("insert into posting values ({id}, {verified}, {subject}, {description}, {userName}, {eMail}, {phone})").on(
          'id -> posting.id,
          'verified -> posting.verified,
          'subject -> posting.subject,
          'description -> posting.description,
          'userName -> posting.userName,
          'eMail -> posting.eMail,
          'phone -> posting.phone
        ).executeUpdate()

        return posting
    }

  def verify(id: String) {
    DB.withConnection(implicit c =>
      SQL("update posting set verified = true where id = {id}").on(
        'id -> id
      ).executeUpdate()
    )
  }

  def list(): List[Posting] = DB.withConnection {
    implicit c =>
      SQL("select * from posting where posting.verified = true").as(posting *)
  }

}
