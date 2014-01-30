package <%= packageName %>.models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import org.squeryl.Query
import java.util.Date


case class <%= _.capitalize(name) %>(
    var id: Long = 0<% _.each(attrs, function (attr) { %>, var <%= attr.attrName %>: <% if (attr.attrType == 'Enum') { %>String<% } else { %><%= attr.attrType %><% } %> = <%= attr.attrDefault %><% }); %>
  ) extends ScalatraRecord

object <%= _.capitalize(name) %>Db extends Schema {

  val <%= pluralize(name) %> = table[<%= _.capitalize(name) %>]("<%= pluralize(name) %>")

  on(<%= pluralize(name) %>)(a => declare(
    a.id is(autoIncremented)))

}
