package models

case class Category()

object Category extends Enumeration {

  val OFFERED = Value("OFFERED")
  val WANTED = Value("WANTED")

}
