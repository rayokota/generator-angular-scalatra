package <%= packageName %>.json

import java.text.SimpleDateFormat
import java.util.Date
import org.json4s._

class DateSerializer extends CustomSerializer[Date](format => (
  {
    case JString(s) =>
      val df = new SimpleDateFormat("yyyy-MM-dd")
      new Date(df.parse(s).getTime())
  },
  {
    case x: Date =>
      val df = new SimpleDateFormat("yyyy-MM-dd")
      JString(df.format(x))
  }
))
