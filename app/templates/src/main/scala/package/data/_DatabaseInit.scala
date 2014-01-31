package <%= packageName %>.data

import com.mchange.v2.c3p0.ComboPooledDataSource
import java.sql._
import java.util.Calendar
import java.util.TimeZone
import org.squeryl.adapters.{DerbyAdapter, PostgreSqlAdapter}
import org.squeryl.internals._
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.slf4j.LoggerFactory

trait DatabaseInit {
  val logger = LoggerFactory.getLogger(getClass)

  val databaseUsername = "root"
  val databasePassword = ""
  val databaseConnection = "jdbc:derby:/tmp/mydb;create=true"

  var cpds = new ComboPooledDataSource

  def configureDb() {
    cpds.setDriverClass("org.apache.derby.jdbc.EmbeddedDriver")
    cpds.setJdbcUrl(databaseConnection)
    cpds.setUser(databaseUsername)
    cpds.setPassword(databasePassword)

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(50)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      logger.info("Creating connection with c3po connection pool")
      Session.create(cpds.getConnection, new DerbyAdapter() {

        override def setParamInto(s: PreparedStatement, p: StatementParam, i: Int) =
          p match {
            case ConstantStatementParam(constantTypedExpression) =>
              val obj = convertToJdbcValue(constantTypedExpression.nativeJdbcValue)
              if (obj.isInstanceOf[java.sql.Date]) {
                s.setDate(i, obj.asInstanceOf[java.sql.Date], Calendar.getInstance(TimeZone.getTimeZone("UTC")))
              } else {
                s.setObject(i, obj)
              }

            case FieldStatementParam(o, fieldMetaData) =>
              val obj = convertToJdbcValue(fieldMetaData.getNativeJdbcValue(o))
              if (obj.isInstanceOf[java.sql.Date]) {
                s.setDate(i, obj.asInstanceOf[java.sql.Date], Calendar.getInstance(TimeZone.getTimeZone("UTC")))
              } else {
                s.setObject(i, obj);
              }

            case ConstantExpressionNodeListParam(v, constantExpressionNodeList) =>
              val obj = convertToJdbcValue(v)
              if (obj.isInstanceOf[java.sql.Date]) {
                s.setDate(i, obj.asInstanceOf[java.sql.Date], Calendar.getInstance(TimeZone.getTimeZone("UTC")))
              } else {
                s.setObject(i, obj)
              }
            }
      })
    }
  }

  def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }
}



