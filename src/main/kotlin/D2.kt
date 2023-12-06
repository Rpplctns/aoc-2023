import kotlin.math.max

fun main(args: Array<String>) {
    val inputSize = 100
    val data = List(inputSize) { readln() }
        .map {
            Pair(it.split("\\s*:\\s*".toRegex())[0].substring(5).toInt(),
                it.split("\\s*:\\s*".toRegex())[1].split("\\s*;\\s*".toRegex())
                    .map { a ->
                        a.split("\\s*,\\s*".toRegex()).map { b -> b.split(' ') }.map { b -> Pair(b[0].toInt(), b[1]) }
                    }
                    .map { a ->
                        listOf(
                            a.filter { b -> b.second == "red" }.getOrElse(0){Pair(0, "red")}.first,
                            a.filter { b -> b.second == "green" }.getOrElse(0){Pair(0, "green")}.first,
                            a.filter { b -> b.second == "blue" }.getOrElse(0){Pair(0, "blue")}.first
                        )
                    }
            )
        }

    val res1 = data.filter { it.second.all { a -> a[0] <= 12 && a[1] <= 13 && a[2] <= 14 } }.sumOf { it.first }
    val res2 = data.map { it.second.reduce { b, c -> b.zip(c) { e1, e2 -> max(e1, e2) } } }.sumOf { it.reduce{ a, b -> a * b } }
    println("Part 1: $res1")
    println("Part 2: $res2")
}

