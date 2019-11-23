import slick.dbio.Effect
import slick.jdbc
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import slick.sql.FixedSqlAction

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object InsertRecord  extends App {

  lazy val suppliersTblQuery: TableQuery[SuppliersTable] = TableQuery[SuppliersTable]

  lazy val coffeesTblQuery: TableQuery[CoffeesTable] = TableQuery[CoffeesTable]

  lazy val studentTblQuery: TableQuery[StudentTable] = TableQuery[StudentTable]

  private val mysqlDBConfig: MySQLProfile.backend.Database = Database.forConfig("mysqlDB")


  // Helper method for running a query in this example file:
  def exec[T](dbOperation: DBIO[T]): T =
    Await.result(mysqlDBConfig.run(dbOperation), 5000.milliseconds)


  // Utility to print out what is in the database:
  def printStudentDatabaseState() = {
    println("\nState of the Student database:")
    exec(studentTblQuery.result.map(_.foreach(println)))
  }



  try{

    println("\n\n Inserting Single data for student")
    val student1 = Student("Ayodeji","Ilori",Some("Oluwaseun") ,"ayojava@gmail.com", 24)
    val insertSingleDataAction: FixedSqlAction[Int, NoStream, Effect.Write] = studentTblQuery += student1
    val insertRowsCount: Int = exec(insertSingleDataAction)
    println(s"No of Inserted Rows ::: $insertRowsCount")

    println("\n\n Inserting Single data for student and returning Id ")
    val student2 = Student("Ayodeji","samuel",Some("Igbunedion") ,"ayo@gmail.com", 34)
    val insertSingleDataReturningIdAction: _root_.slick.jdbc.MySQLProfile.ReturningInsertActionComposer[Student, Long] = studentTblQuery returning studentTblQuery.map(_.id)
    val insertSingleDataReturnId: FixedSqlAction[Long, NoStream, Effect.Write] =  insertSingleDataReturningIdAction += student2
    val databaseId: Long = exec(insertSingleDataReturnId)
    println(s"Database Id ::: $databaseId")

    println("\n\n Inserting Single data for student and returning row ")
    val student3 = Student("Demola","Ayorinde",Some("Thomas") ,"retyuu@gmail.com", 29)
    val insertSingleDataReturningAction: _root_.slick.jdbc.MySQLProfile.IntoInsertActionComposer[Student, Student] = studentTblQuery returning studentTblQuery.map(_.id) into {
      (student,id) => student.copy(id = id)
    }
    val insertSingleDataReturnRow: FixedSqlAction[Student, NoStream, Effect.Write] = insertSingleDataReturningAction += student3
    val student: Student = exec(insertSingleDataReturnRow)
    println(s"Student Data :::: $student")

    println("\n\n Inserting Multiple data for student")
    val studentSeq: Seq[Student] = Seq(
      Student("Nath","Xtina",Some("Tyrese") ,"go@gmail.com", 20),
      Student("Femi","Seoung",Some("Gibson") ,"fedrt@gmail.com", 29),
      Student("Bayo","Adeyemi",Some("tekken") ,"all@gmail.com", 29),
    )
    val insertMultipleStudentsAction: FixedSqlAction[Option[Int], NoStream, Effect.Write] = studentTblQuery ++= studentSeq
    val maybeInt: Option[Int] = exec(insertMultipleStudentsAction)
    println(s"Rows Inserted :::: $maybeInt")

    println("\n\n Inserting Multiple data for student and Returning rows")
    val studentSeq2: Seq[Student] = Seq(
      Student("aaaa","eeee",Some("gggg") ,"go@gmail.com", 20),
      Student("bbbb","dddd",Some("hhhh") ,"fedrt@gmail.com", 29),
      Student("cccc","ffff",Some("rrrr") ,"all@gmail.com", 29),
    )
    val insertMultipleStudentsReturnRowsAction: FixedSqlAction[Seq[Student], NoStream, Effect.Write] = insertSingleDataReturningAction ++= studentSeq2
    val studentSeqData: Seq[Student] = exec(insertMultipleStudentsReturnRowsAction)
    studentSeqData.foreach(aStudent => println(s"Student :::: $aStudent"))

    printStudentDatabaseState()

  }finally mysqlDBConfig.close

}
