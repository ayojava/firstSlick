
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object CreateSchema extends App{

  private val postgreDbConfig: PostgresProfile.backend.Database = Database.forConfig("postgresDB")

  lazy val suppliersTblQuery: TableQuery[SuppliersTable] = TableQuery[SuppliersTable]

  lazy val coffeesTblQuery: TableQuery[CoffeesTable] = TableQuery[CoffeesTable]

  private val studentTblQuery: TableQuery[StudentTable] = TableQuery[StudentTable]

  private val suppliersTblString: String = suppliersTblQuery.schema.createStatements.mkString

  private val coffeesTblString: String = coffeesTblQuery.schema.createStatements.mkString

  private val studentTblString: String = studentTblQuery.schema.createStatements.mkString

  println(s"Suppliers Table Query String ::: $suppliersTblString")

  println(s"Coffee Table Query String ::: $coffeesTblString")

  println(s"Student Table Query String ::: $studentTblString")

  /*
  Suppliers Table Query String :::
  create table "Suppliers" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL,"street" VARCHAR NOT NULL,
    "city" VARCHAR NOT NULL,"state" VARCHAR NOT NULL,"zip" VARCHAR,"createDate" TIMESTAMP NOT NULL)

  Coffee Table Query String :::
  create table "Coffees" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL,"price" DOUBLE PRECISION NOT NULL,
  "qty" BIGINT NOT NULL,"supplierId" BIGINT NOT NULL,"createDate" TIMESTAMP NOT NULL)
  alter table "Coffees" add constraint "supplier_fk" foreign key("supplierId") references "Suppliers"("id") on
    update NO ACTION on delete CASCADE

  */

  private val createSchemaAction: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(
    (suppliersTblQuery.schema ++ coffeesTblQuery.schema ++ studentTblQuery.schema).create
  )

  println(" \n \n Creating Db Table ")
  private val futureResponse: Future[Unit] = postgreDbConfig.run(createSchemaAction)
  private val unitResponse:  Unit = Await.result(futureResponse, 2.seconds)

}
