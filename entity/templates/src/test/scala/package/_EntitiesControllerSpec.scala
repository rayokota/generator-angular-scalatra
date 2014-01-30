package <%= packageName %>

import <%= packageName %>.data.DatabaseInit
import org.scalatra.test.scalatest._
import org.scalatest.{ FunSuite, BeforeAndAfter }
import org.squeryl.PrimitiveTypeMode._
import <%= packageName %>.models.<%= _.capitalize(name) %>Db

class <%= _.capitalize(pluralize(name)) %>ControllerTest extends ScalatraSuite with DatabaseInit with FunSuite with BeforeAndAfter {
  addServlet(classOf[<%= _.capitalize(pluralize(name)) %>Controller], "/*")
  
  before {
    configureDb()
  }
  
  after {
    closeDbConnection()
  }
  
  test("simple get") {
    transaction {
      <%= _.capitalize(name) %>Db.create
    }

    get("/") {
      status should equal (200)
    }
  }
}

