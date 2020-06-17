import fs2._

val stream1: Stream[Pure, Int] = Stream(1, 2, 3, 4)
stream1.compile.toList

val streamRepeat = Stream(1, 2, 3).repeat
val constantStream = Stream.constant("foo")
constantStream.take(5).compile.toList
streamRepeat.take(20).compile.toList

// Create pure stream using Stream.applyM
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

val foo = stream.evalMap(getAge)

foo.compile.toList.unsafeRunSync()

val temp = stream.evalFilter(name => IO(name.contains("o")))

temp.compile.toList.unsafeRunSync()

stream.evalTap(name => IO(println(s"received: $name"))).compile.drain.unsafeRunSync()

// Infinite Streams

val stream2 = Stream.constant("ping")
stream2.take(5).compile.toList

val stream3 = Stream(1, 2, 3).repeat

// val stream4 = Stream.awakeEvery[IO](5.seconds)
// stream4.take(2).compile.toList.unsafeRunSync()
