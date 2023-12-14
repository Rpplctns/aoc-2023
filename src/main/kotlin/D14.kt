class Stone(val x: Int, val y: Int, var pressure: Int) {
    override fun toString() = "Stone: ($x, $y), $pressure"
}

fun main() {
    val inputSize = 10
    val data = Array(inputSize) { Array(inputSize) { 0 } }
    for (i in 0..<inputSize) {
        val ln = readln()
        for (j in 0..<inputSize) {
            data[i][j] = if (ln[j] == '.') 0 else if (ln[j] == '#') 1 else 2
        }
    }

    val stones = mutableListOf<Stone>()
    var res1 = 0
    for (i in 0..<inputSize) {
        var prevHash = -1
        var osNumber = 0
        for (j in 0..<inputSize) {
            if (data[j][i] == 1) {
                stones.add(Stone(i, prevHash, osNumber))
                prevHash = j
                osNumber = 0
            }
            else if (data[j][i] == 2) {
                osNumber++
                res1 += inputSize - prevHash - osNumber
            }
        }
        stones.add(Stone(i, prevHash, osNumber))
    }

    println("Part 1: $res1")
}