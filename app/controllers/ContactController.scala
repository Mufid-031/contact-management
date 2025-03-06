package controllers

import javax.inject._
import play.api.mvc._
import models.{Contact, ContactRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ContactController @Inject()(val controllerComponents: ControllerComponents, repo: ContactRepository)(implicit ec: ExecutionContext) extends BaseController {

  def list(): Action[AnyContent] = Action.async {
    repo.list().map { contacts =>
      Ok(views.html.contacts(contacts))
    }
  }

  def addForm(): Action[AnyContent] = Action { implicit request =>
  Ok(views.html.addContact(request))
  }

  def create(): Action[AnyContent] = Action.async { implicit request =>
    val formData = request.body.asFormUrlEncoded
    val name = formData.get("name").head
    val email = formData.get("email").head
    val phone = formData.get("phone").head

    repo.insert(Contact(None, name, email, phone)).map { _ =>
      Redirect(routes.ContactController.list())
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    repo.delete(id).map { _ =>
      Redirect(routes.ContactController.list())
    }
  }
}
