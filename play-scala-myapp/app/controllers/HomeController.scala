package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import PersonForm._
import anorm._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db: Database, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request =>
    db.withConnection { implicit conn =>
      val result:List[PersonData] = SQL("Select * from people").as(personparser.*)
      Ok(views.html.index(
        "People Data.", result
      ))
    }
  }

  def show(id:Int) = Action { implicit request =>
    db.withConnection { implicit conn =>
      val result:PersonData = SQL("Select * from people where id = {id}").on("id" -> id).as(personparser.single)
      Ok(views.html.show(
        "People Data.", result
      ))
    }
  }

  def add() = Action { implicit request =>
    Ok(views.html.add(
      "フォームを記入してください。", form
    ))
  }

  def create() = Action { implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    db.withConnection { implicit conn =>
      SQL("insert into people values (default, {name}, {mail}, {tel})")
        .on(
          "name" -> data.name,
          "mail" -> data.mail,
          "tel" -> data.tel
        ).executeInsert()
      Redirect(routes.HomeController.index)
    }
  }

  def edit(id:Int) = Action { implicit request =>
    val formdata = personform.bindFromRequest
    db.withConnection { implicit conn =>
      val pdata:PersonData = SQL("Select * from people where id = {id}").on("id" -> id).as(personparser.single)
      formdata = personform.fill(pdata)
      Ok(views.html.edit(
        "フォームを編集してください。", formdata, id
      ))
    }
  }

  def update(id:Int) = Action { implicit request =>
    val formdata = personform.bindFromRequest
    val data = formdata.get
    db.withConnection { implicit conn =>
      val result = SQL("update people set name={name}, mail={mail}, tel={tel} where id={id}")
        .on(
          "name" -> data.name,
          "mail" -> data.mail,
          "tel" -> data.tel,
          "id" -> data.id
         ).executeUpdate
      Redirect(routes.HomeController.index)
    }
  }

  /**
  def form() = Action { implicit request =>
    val form = myform.bindFromRequest
    val data = form.get
    Ok(views.html.index(
      "name:" + data.name + ",pass:" + data.pass,
      form
    ))
  }
  
  def index() = TODO
  def index() = Action {
    Ok("Welcome to Play Framework !")
  }
   */
 }
