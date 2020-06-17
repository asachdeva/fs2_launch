//import fs2._
//import io.circe.generic.auto._
//import io.circe.syntax._
import java.io.File
import cats.effect._

object Boot extends IOApp {
  val dataset = new File("data.csv")
  val output = new File("output.json")

  case class Row(
    policyID: String,
    statecode: String,
    county: String,
    eq_site_limit: String,
    hu_site_limit: String,
    fl_site_limit: String,
    fr_site_limit: String,
    tiv_2011: String,
    tiv_2012: String,
    eq_site_deductible: String,
    hu_site_deductible: String,
    fl_site_deductible: String,
    fr_site_deductible: String,
    point_latitude: String,
    point_longitude: String,
    line: String,
    construction: String,
    point_granularity: String
  )

  override def run(args: List[String]): IO[ExitCode] = ???
  //Blocker[IO].use { blocker =>
  //fs2.io.file
  //.readAll[IO](dataset.toPath, blocker, 4096)
  //.through(text.utf8Decode)
  //.through(text.lines)
  //}
}
