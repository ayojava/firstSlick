
import slick.jdbc
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object CreateSchema extends App{

  private val mysqlDBConfig: MySQLProfile.backend.Database = Database.forConfig("mysqlDB")

  lazy val suppliersTblQuery: TableQuery[SuppliersTable] = TableQuery[SuppliersTable]

  lazy val coffeesTblQuery: TableQuery[CoffeesTable] = TableQuery[CoffeesTable]

  private val studentTblQuery: TableQuery[StudentTable] = TableQuery[StudentTable]

  private val suppliersTblString: String = suppliersTblQuery.schema.createStatements.mkString

  private val coffeesTblString: String = coffeesTblQuery.schema.createStatements.mkString

  private val studentTblString: String = studentTblQuery.schema.createStatements.mkString

  println(s"Suppliers Table Query String ::: $suppliersTblString")

  println(s"Coffee Table Query String ::: $coffeesTblString")

  println(s"Student Table Query String ::: $studentTblString")



  private val schemaActions =
    DBIO.seq(
        coffeesTblQuery.schema.dropIfExists ,
        suppliersTblQuery.schema.dropIfExists ,
        studentTblQuery.schema.dropIfExists,
        suppliersTblQuery.schema.create,
        coffeesTblQuery.schema.create,
        studentTblQuery.schema.create
  )

  println(" \n \n Schema  Actions ")
  private val futureResponse: Future[Unit] = mysqlDBConfig.run(schemaActions)
  private val unitResponse:  Unit = Await.result(futureResponse, 2.seconds)

}
