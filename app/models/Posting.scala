package models

case class Posting(id: Long, subject: String)

object Posting {

  def listWanted() : List[Posting] = List(Posting(42, "Wanted 1"), Posting(99, "Wanted 2"))
  def listOffered() : List[Posting] = List(Posting(42, "Offered 1"), Posting(99, "Offered 2"))

}
