package com.yetu.oauth2resource.model

import play.api.libs.json._


case class ContactInfo(
                        country: Option[String],
                        street: Option[String],
                        houseNumber: Option[String],
                        postalCode: Option[String],
                        city: Option[String],
                        mobile: Option[String],
                        homePhone: Option[String],
                        fax: Option[String],
                        chat: Option[String]
                        )

/**
 * Contact Information json formatter(reader and writer)
 */
object ContactInfo {

  implicit val contactInfoFormat: Format[ContactInfo] = Json.format[ContactInfo]

}
