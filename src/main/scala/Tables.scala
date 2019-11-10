import java.time.LocalDateTime

import slick.lifted.{ProvenShape, Rep, Tag}
import slick.jdbc.PostgresProfile.api._

//Building applications with scala

case class Suppliers (name : String , street : String , city : String , state : String , zip : Option[String] = None , createDate : LocalDateTime , id:Long =0L)

final class SuppliersTable(tag: Tag) extends Table[Suppliers](tag,"Suppliers"){

  def id : Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def street: Rep[String] = column[String]("street")

  def city: Rep[String] = column[String]("city")

  def state: Rep[String] = column[String]("state")

  def zip: Rep[Option[String]] = column[Option[String]]("zip")

  def createDate : Rep[LocalDateTime] = column[LocalDateTime]("createDate")

  def * = ( name , street,city,state, zip , createDate , id).mapTo[Suppliers]

}



case class Coffees (  name : String , price : Double , qty : Long ,supplierId : Long, createDate : LocalDateTime , id : Long = 0L)

final class CoffeesTable(tag: Tag) extends Table[Coffees](tag ,"Coffees"){

  def id : Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name", O.Length(512))

  def price: Rep[Double] = column[Double]("price")

  def qty : Rep[Long] = column[Long]("qty")

  def supplierId : Rep[Long] = column[Long]("supplierId")

  def createDate : Rep[LocalDateTime] = column[LocalDateTime]("createDate")

  override def * = (name , price , qty , supplierId,createDate , id).mapTo[Coffees]

  def supplierFK =
    foreignKey("supplier_fk" , supplierId , TableQuery[SuppliersTable])(_.id , onDelete = ForeignKeyAction.Cascade)

}

case class Student(firstName : String , lastName : String , middleName : Option[String] , emailAddress : String , age : Int,id : Long =0L)

final class StudentTable(tag: Tag) extends Table[Student](tag , "Student"){

  def id : Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def firstName: Rep[String] = column[String]("firstName", O.Length(512))

  def lastName: Rep[String] = column[String]("lastName", O.Length(512))

  def middleName: Rep[Option[String]] = column[Option[String]]("middleName")

  def emailAddress : Rep[String] = column[String]("emailAddress")

  def age : Rep[Int] = column[Int]("age")

  override def * = ( firstName , lastName , middleName , emailAddress , age , id).mapTo[Student]
}




