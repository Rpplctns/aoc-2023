fun main() {
    val inputSize = 200
    var res1 = 0
    var res2 = 0
    repeat(inputSize) {
        val data = readln().split(' ').map { it.toInt() }
        val last = mutableListOf<Int>()
        val first = mutableListOf<Int>()
        var diff = data
        last.add(diff.last())
        first.add(diff.first())
        while (diff.any { it != 0 }) {
            diff = (1..<diff.size).map { diff[it] - diff[it - 1] }
            last.add(diff.last())
            first.add(diff.first())
        }
        res1 += last.sum()
        res2 += first.mapIndexed{ index, i -> if (index % 2 == 0) i else -i }.sum()
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}