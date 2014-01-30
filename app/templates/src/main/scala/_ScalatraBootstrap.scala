import org.scalatra.LifeCycle
import org.squeryl.PrimitiveTypeMode._
import javax.servlet.ServletContext
<% _.each(entities, function (entity) { %>
import <%= packageName %>.<%= _.capitalize(pluralize(entity.name)) %>Controller<% }); %>
<% _.each(entities, function (entity) { %>
import <%= packageName %>.models.<%= _.capitalize(entity.name) %>Db<% }); %>
import <%= packageName %>.data.DatabaseInit

class ScalatraBootstrap extends LifeCycle with DatabaseInit {

  override def init(context: ServletContext) {
    configureDb()
    <% _.each(entities, function (entity) { %>
    context mount( new <%= _.capitalize(pluralize(entity.name)) %>Controller, "/<%= baseName %>/<%= pluralize(entity.name) %>/*")<% }); %>

    transaction {
      <% _.each(entities, function (entity) { %>
      try {
        <%= _.capitalize(entity.name) %>Db.create
      } catch { case _ : Throwable => }<% }); %>
    }
  }
  
  override def destroy(context:ServletContext) {
    closeDbConnection()
  }
}
