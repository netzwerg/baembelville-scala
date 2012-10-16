package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import java.util.UUID

case class Posting(id: String, verified: Boolean, category: Category.Value, subject: String, description: String, userName: String, eMail: String, phone: String)


object Posting {

  val postingForm = Form(
    tuple(
      "category" -> text,
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
      get[String]("category") ~
      get[String]("subject") ~
      get[String]("description") ~
      get[String]("userName") ~
      get[String]("eMail") ~
      get[String]("phone") map {
      case id ~ verified ~ category ~ subject ~ description ~ userName ~ eMail ~ phone => Posting(id, verified, Category.withName(category), subject, description, userName, eMail, phone)
    }
  }

  def create(data: (String, String, String, String, String, String)): Posting =
    DB.withConnection {
      implicit c =>

        val category = Category.withName(data._1)
        val posting = Posting(UUID.randomUUID().toString, verified = false, category = category, subject = data._2, description = data._3, userName = data._4, eMail = data._5, phone = data._6)

        SQL("insert into posting values ({id}, {verified}, {category}, {subject}, {description}, {userName}, {eMail}, {phone})").on(
          'id -> posting.id,
          'verified -> posting.verified,
          'category -> posting.category.toString,
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

  def list(category: Category.Value): List[Posting] = DB.withConnection {
    implicit c =>
      SQL("select * from posting where posting.verified = true and category = {category}").on(
        'category -> category.toString
      ).as(posting *)
  }

  def delete(id: String) {
    DB.withConnection(implicit c =>
      SQL("delete posting where id = {id}").on(
        'id -> id
      ).executeUpdate()
    )
  }

}
