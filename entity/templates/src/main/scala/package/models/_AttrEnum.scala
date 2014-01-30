package <%= packageName %>.models


object <%= _.capitalize(attr.attrName) %>Enum extends Enumeration {
  type <%= _.capitalize(attr.attrName) %>Enum = Value

  <% var index = 1; _.each(attr.enumValues, function (value) { %>
  val <%= value %> = Value(<%= index++ %>, "<%= value %>")<% }); %>
}
