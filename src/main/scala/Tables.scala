import java.time.LocalDateTime

import slick.lifted.{ProvenShape, Rep, Tag}
import slick.jdbc.PostgresProfile.api._

//Building applications with scala

case class Suppliers (id:Long , name : String , street : String , city : String , state : String , zip : Option[String] = None , createDate : LocalDateTime)

final class SuppliersTable(tag: Tag) extends Table[Suppliers](tag,"Suppliers"){

  def id : Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def street: Rep[String] = column[String]("street")

  def city: Rep[String] = column[String]("city")

  def state: Rep[String] = column[String]("state")

  def zip: Rep[Option[String]] = column[Option[String]]("zip")

  def createDate : Rep[LocalDateTime] = column[LocalDateTime]("createDate")

  def * = (id, name , street,city,state, zip , createDate).mapTo[Suppliers]

}



case class Coffees ( id : Long , name : String , price : Double , qty : Long ,supplierId : Long, createDate : LocalDateTime)

final class CoffeesTable(tag: Tag) extends Table[Coffees](tag ,"Coffees"){

  def id : Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name", O.Length(512))

  def price: Rep[Double] = column[Double]("price")

  def qty : Rep[Long] = column[Long]("qty")

  def supplierId : Rep[Long] = column[Long]("supplierId")

  def createDate : Rep[LocalDateTime] = column[LocalDateTime]("createDate")

  override def * = (id , name , price , qty , supplierId,createDate).mapTo[Coffees]

  def supplierFK =
    foreignKey("supplier_fk" , supplierId , TableQuery[SuppliersTable])(_.id , onDelete = ForeignKeyAction.Cascade)

}






