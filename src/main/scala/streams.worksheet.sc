import scala.concurrent.ExecutionContext
import fs2._

// Create pure stream using Stream.apply
val pureStream = Stream(1, 2, 3, 4)

// Streams can be compiled to another data structure
pureStream.compile.toList

// Streams can have list-like operations
val a = Stream("foo", "bar")
val b = Stream("run", "covid")

(a ++ b).compile.toVector

// Talk about effects
import cats.effect.IO

def getAge(name: String): IO[Int] = IO(name.length)

val stream = Stream("John", "Paul", "George", "Ringo")

stream.evalMap(getAge)

val temp = stream.evalFilter(name => IO(name.contains("o")))

temp.compile.toList.unsafeRunSync()

stream.evalTap(name => IO(println(s"received: $name"))).compile.drain.unsafeRunSync()

// Infinite Streams

val stream2 = Stream.constant("ping")
stream2.take(5).compile.toList

val stream3 = Stream(1, 2, 3).repeat

import cats.effect.Timer
import scala.concurrent.duration._

implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

val stream4 = Stream.awakeEvery[IO](5.seconds)
stream4.take(2).compile.toList.unsafeRunSync()
