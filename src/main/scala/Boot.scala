import fs2._

import java.io.File
import cats.effect._
import _root_.io.circe.generic.auto._
import _root_.io.circe.syntax._
import fs2.io.file.{Files, Path}

object Boot extends IOApp {
  val dataset = new File("data.csv")
  val output = new File("output.json")

  val parseCsv: Pipe[IO, Byte, Row] = _.through(text.utf8.decode)
    .through(text.lines)
    .drop(1) // header
    .map(_.split(';'))
    .collect { case Array(name, bank_identifier) =>
      Row(name, bank_identifier)
    }

  val rowToJson: Pipe[IO, Row, Byte] =
    _.map(_.asJson.noSpaces)
      .intersperse("\n")
      .through(text.utf8.encode)

  case class Row(
    name: String,
    bank_identifier: String
  )

  override def run(args: List[String]): IO[ExitCode] =
    Files[IO]
      .readAll(Path(dataset.getPath))
      .through(parseCsv)
      .through(rowToJson)
      .through(Files[IO].writeAll(Path(output.getPath)))
      .compile
      .drain >> IO(println("Done!"))
      .as(ExitCode.Success)
}
