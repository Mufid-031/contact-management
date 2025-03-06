package models

import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.{Inject, Singleton}

// Definisi Tabel
case class Contact(id: Option[Long], name: String, email: String, phone: String)

class ContactsTable(tag: Tag) extends Table[Contact](tag, "contacts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")
  def phone = column[String]("phone")

  def * = (id.?, name, email, phone) <> (Contact.tupled, Contact.unapply)
}

// Repository
@Singleton
class ContactRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[SQLiteProfile] {

  import profile.api._

  private val contacts = TableQuery[ContactsTable]

  def list(): Future[Seq[Contact]] = db.run(contacts.result)

  def insert(contact: Contact): Future[Int] = db.run(contacts += contact)

  def update(id: Long, contact: Contact): Future[Int] =
    db.run(contacts.filter(_.id === id).update(contact))

  def getById(id: Long): Future[Option[Contact]] =
    db.run(contacts.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(contacts.filter(_.id === id).delete)
}
