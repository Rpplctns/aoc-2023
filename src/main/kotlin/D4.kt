import kotlin.math.pow

fun main(args: Array<String>) {
    val inputSize = 212
    val wins: List<Int> = List(inputSize) { readln() }
        .map { a -> a.split(":\\s+".toRegex())[1] }
        .map { a -> a.split("\\s+\\|\\s+".toRegex()).map { b -> b.split("\\s+".toRegex()) } }
        .map { a -> a[1].count { b -> a[0].contains(b) } }

    val num: Array<Int> = Array(inputSize) { 1 }
    var res1 = 0
    var res2 = 0

    for(i in 0..<inputSize) {
        for(j in 1..wins[i]) {
            num[i + j] += num[i]
        }
        res1 += if (wins[i] > 0) 2.0.pow(wins[i] - 1).toInt() else 0
        res2 += num[i]
    }

    println("Part 1: $res1")
    println("Part 2: $res2")
}