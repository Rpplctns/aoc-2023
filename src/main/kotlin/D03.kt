class Map(val size: Int, val data: Array<String>) {
    fun charAt(x: Int, y: Int) = if (x < 0 || x >= size || y < 0 || y >= size) '.' else data[x][y]

    private fun isSpecial(x: Int, y: Int) = charAt(x, y).let { !it.isDigit() && it != '.' }

    fun hasSpecialNeighbour(x: Int, y: Int) =
        (-1..1).flatMap { a -> (-1..1).map { Pair(it, a) } }.any { isSpecial(x + it.first, y + it.second) }

    fun gearNeighbours(x: Int, y: Int) =
        (x-1..x+1).flatMap { a -> (y-1..y+1).map { Pair(a, it) } }.filter { charAt(it.first, it.second) == '*' }
            .toSet()
}

fun main() {
    val inputSize = 140
    val data = Map(inputSize, Array(inputSize) { readln() })
    var res1 = 0
    var res2 = 0
    var number = 0
    var attached = false
    val gearNumbers = Array(inputSize) { Array(inputSize) { mutableListOf<Int>() } }
    var gears = mutableSetOf<Pair<Int, Int>>()
    for (i in 0..<inputSize) {
        for (j in 0..<inputSize) {
            if (data.charAt(i, j).isDigit()) {
                attached = attached || data.hasSpecialNeighbour(i, j)
                gears.addAll(data.gearNeighbours(i, j))
                number = number * 10 + data.charAt(i, j).code - '0'.code
            }
            if (j == inputSize - 1 || !data.charAt(i, j).isDigit()) {
                if (attached) res1 += number
                for (gear in gears) gearNumbers[gear.first][gear.second].add(number)
                number = 0
                attached = false
                gears = mutableSetOf()
            }
        }
    }
    for (i in 0..<inputSize) {
        for (gearList in gearNumbers[i]) {
            if (gearList.size == 2) res2 += gearList[0] * gearList[1]
        }
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}