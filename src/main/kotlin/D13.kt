import kotlin.math.min

fun solve(data: List<String>, limit: Int): Int {
    var list = (1..<data[0].length).map{ Pair(it, 0) }.toMutableList()
    for (s in data) {
        val newList = mutableListOf<Pair<Int, Int>>()
        for (i in list) {
            val l = min(i.first, s.length - i.first)
            val s1 = s.substring(i.first - l, i.first)
            val s2 = s.substring(i.first, i.first + l).reversed()
            val smudges = s1.zip(s2).count{ it.first != it.second }
            if (i.second + smudges <= limit) newList.add(Pair(i.first, i.second + smudges))
        }
        list = newList
    }
    return list.filter { it.second == limit }.sumOf { it.first }
}

fun main() {
    val inputSize = 100
    var res1 = 0
    var res2 = 0
    repeat(inputSize) {
        val rows = mutableListOf<String>()
        do { rows.add(readln()) } while (rows.last() != "")
        rows.removeLast()
        val columns = List(rows[0].length) { index -> rows.map { it[index] }.joinToString("") }
        res1 += solve(rows, 0) + 100 * solve(columns, 0)
        res2 += solve(rows, 1) + 100 * solve(columns, 1)
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}