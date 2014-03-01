package <%= packageName %>

import org.scalatra._
import scalate.ScalateSupport
import <%= packageName %>.data.DatabaseInit
import <%= packageName %>.data.DatabaseSessionSupport
import <%= packageName %>.json.DateSerializer
import <%= packageName %>.models.<%= _.capitalize(name) %>
import <%= packageName %>.models.<%= _.capitalize(name) %>Db
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import org.squeryl.PrimitiveTypeMode._
import java.text.SimpleDateFormat
import java.util.Random
import java.util.Collections

class <%= _.capitalize(pluralize(name)) %>Controller extends ScalatraServlet 
  with SessionSupport
	with DatabaseSessionSupport 
	with ScalateSupport
	with MethodOverride
	with FlashMapSupport
        with JacksonJsonSupport {

  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit val jsonFormats: Formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
  } + new DateSerializer

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    transaction {
      from(<%= _.capitalize(name) %>Db.<%= pluralize(name) %>)(select(_)).toList
    }
  }

  get("/:id") {
    transaction {
      val entities = <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.lookup(params("id").toLong)
      entities.nonEmpty || halt(404)
      entities
    }
  }

  post("/") {
    transaction {
      val entity = parsedBody.extract[<%= _.capitalize(name) %>]
      <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.insert(entity)
      status(201)
      entity
    }
  }

  put("/:id") {
    transaction {
      <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.lookup(params("id").toLong).nonEmpty || halt(404)
      val entity = parsedBody.extract[<%= _.capitalize(name) %>]
      entity.id = params("id").toLong
      <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.update(entity)
      entity
    }
  }

  delete("/:id") {
    transaction {
      <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.lookup(params("id").toLong).nonEmpty || halt(404)
      <%= _.capitalize(name) %>Db.<%= pluralize(name) %>.delete(params("id").toLong)
      status(204)
      ""
    }
  }
}
