fun solve(data: List<Long>): Long {
    var x = 0L
    var y = 0L
    val horizontalEdges = mutableListOf<Pair<Long, Pair<Long, Long>>>()
    for (i in data.indices step 2) {
        val edge = if (data[i] > 0) Pair(x, x + data[i]) else Pair(x + data[i], x)
        horizontalEdges.add(Pair(y, edge))
        x += data[i]
        y += data[i + 1]
        horizontalEdges.sortBy { it.first }
    }
    val edgeSet = mutableSetOf<Long>()
    var res = 0L
    y = horizontalEdges.first().first
    for (edge in horizontalEdges) {
        val width = edgeSet.sorted().chunked(2).sumOf { it[1] - it[0] + 1 }
        if (edgeSet.sorted().chunked(2).any { (it[0] == edge.second.first) xor (it[1] == edge.second.second) })
            res += edge.second.second - edge.second.first
        if (edgeSet.sorted().chunked(2).any { (it[0] == edge.second.first) && (it[1] == edge.second.second) })
            res += edge.second.second - edge.second.first + 1
        if (edgeSet.sorted().chunked(2).any { it[0] < edge.second.first && edge.second.second < it[1] })
            res += edge.second.second - edge.second.first - 1
        if (edge.first != y) res += width * (edge.first - y)
        y = edge.first
        if (edgeSet.contains(edge.second.first)) edgeSet.remove(edge.second.first) else edgeSet.add(edge.second.first)
        if (edgeSet.contains(edge.second.second)) edgeSet.remove(edge.second.second) else edgeSet.add(edge.second.second)
    }
    return res
}

fun main() {
    val inputSize = 794
    val rawData = List(inputSize) { readln() }
    val data1 = rawData.map {
        it.split(' ').let { a ->
            a[1].toLong() * (if (a[0][0] == 'L' || a[0][0] == 'D') -1 else 1)
        }
    }
    val data2 = rawData.map {
        it.split(' ')[2].substring(2, 8).let { a ->
            a.substring(0, 5).toLong(radix = 16) * (if (a.last().digitToInt() >= 2) -1 else 1)
        }
    }

    println("Part 1: ${solve(data1)}")
    println("Part 2: ${solve(data2)}")
}