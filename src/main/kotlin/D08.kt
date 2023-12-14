fun lcm(a: List<Long>): Long {
    if (a.isEmpty()) return 0
    var lcm = a[0]

    for (i in 1..<a.size) {
        val currentNumber = a[i]
        lcm = lcm(lcm, currentNumber)
    }

    return lcm
}

fun lcm(a: Long, b: Long): Long {
    return (a * b) / gcd(a, b)
}

fun gcd(a: Long, b: Long): Long {
    var x = a
    var y = b
    while (y != 0L) {
        val temp = y
        y = x % y
        x = temp
    }
    return x
}

fun main() {
    val inputSize = 714
    val dirs = readln()
    readln()
    val data = List(inputSize) { readln() }.associate { it.substring(0, 3) to Pair(it.substring(7, 10), it.substring(12, 15)) }
    var res1 = 0
    var node = "AAA"
    while (node != "ZZZ") node = if (dirs[res1++ % dirs.length] == 'L') data[node]!!.first else data[node]!!.second

    val graph = mutableMapOf<String, Pair<String, Set<Int>>>()
    for (n in data) {
        node = n.key
        val ends = mutableSetOf<Int>()
        for (i in dirs.indices) {
            if (node.endsWith('Z') && i > 0) ends.add(i)
            node = if (dirs[i] == 'L') data[node]!!.first else data[node]!!.second
        }
        graph[n.key] = Pair(node, ends)
    }
    val starts = data.map { it.key }.filter { it[2] == 'A' || it[2] == 'Z' }
    val finishes = data.map { it.key }.filter { it[2] == 'Z' }
    val distancesToEachZ = Array(starts.size) { Array(finishes.size) { -1L } }
    for (i in starts.indices) {
        for (j in finishes.indices) {
            val visited = mutableSetOf<String>()
            node = starts[i]
            while (!visited.contains(node)) {
                if (node == finishes[j] && visited.isNotEmpty()) {
                    distancesToEachZ[i][j] = ((visited.size.toLong()) * dirs.length.toLong())
                    break
                }
                visited.add(node)
                node = graph[node]!!.first
            }
            if (node == finishes[j] && distancesToEachZ[i][j] == -1L) {
                distancesToEachZ[i][j] = ((visited.size.toLong()) * dirs.length.toLong())
            }
        }
    }
    val distsList = mutableListOf<Long>()
    for (i in starts.indices) {
        for (j in finishes.indices) {
            if (distancesToEachZ[i][j] != -1L && starts[i].endsWith('A')) {
                distsList.add(distancesToEachZ[i][j])
            }
        }
    }
    val res2 = lcm(distsList)

    println("Part 1: $res1")
    println("Part 2: $res2")
}