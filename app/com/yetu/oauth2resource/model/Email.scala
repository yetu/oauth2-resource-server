package com.yetu.oauth2resource.model

sealed trait Email extends Product with Serializable

object Email {

  implicit def String2Email(s: String): Email = Email(s)

  def apply(email: String) = {
    //dummy validation for now will add more sophisticated later
    email.contains("@") match {
      case true  => CorrectEmail(email)
      case false => WrongEmail(email)
    }
  }

  override def toString: String = this.toString

  implicit def email2str(email: Email) = {
    email match {
      case CorrectEmail(s) => s
      case WrongEmail(s) => s
    }
  }
}

case class CorrectEmail(email: String) extends Email {
  override def toString: String = {
    email
  }
}

case class WrongEmail(email: String) extends Email {
  override def toString: String = {
    email
  }
}