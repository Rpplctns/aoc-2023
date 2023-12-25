import java.util.PriorityQueue
import kotlin.math.abs

class Node(val dist: Int, val coords: Pair<Int, Int>, val dir: Int, val steps: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int = dist.compareTo(other.dist)

    fun next(data: List<List<Int>>, dataSize: Int, criteria: Pair<Int, Int>): List<Node> {
        fun nextCoors(dir: Int, coords: Pair<Int, Int>) = when (dir) {
            0 -> Pair(coords.first + 1, coords.second)
            1 -> Pair(coords.first, coords.second + 1)
            2 -> Pair(coords.first - 1, coords.second)
            3 -> Pair(coords.first, coords.second - 1)
            else -> Pair(-1, -1)
        }

        fun weight(coords: Pair<Int, Int>) = data[coords.second][coords.first]
        fun coordsInMap(coords: Pair<Int, Int>) =
            coords.first >= 0 && coords.second >= 0 &&
                    coords.first < dataSize && coords.second < dataSize

        val res = mutableListOf<Node>()
        for (i in 0..3) {
            if (abs(i - this.dir) == 2) continue
            if (steps < criteria.first && dir != i) continue
            if (abs(i - this.dir) % 2 == 0 && this.steps == criteria.second) continue
            if (!coordsInMap(nextCoors(i, this.coords))) continue
            res.add(Node(this.dist + weight(nextCoors(i, this.coords)), nextCoors(i, this.coords), i, if (i == dir) this.steps + 1 else 1))
        }
        return res
    }
}

fun solve (data: List<List<Int>>, dataSize: Int, criteria: Pair<Int, Int>): Int {
    val res: Int
    val queue: PriorityQueue<Node> = PriorityQueue()
    queue.add(Node(0, Pair(0, 0), 0, 0))
    val visited = mutableSetOf<List<Int>>()
    while (true) {
        val node = queue.poll()
        if (visited.contains(listOf(node.coords.first, node.coords.second, node.dir, node.steps))) continue
        visited.add(listOf(node.coords.first, node.coords.second, node.dir, node.steps))
        if (node.coords.equals(Pair(dataSize - 1, dataSize - 1))) {
            res = node.dist
            break
        }
        queue.addAll(node.next(data, dataSize, criteria))
    }
    return res
}

fun main() {
    val inputSize = 13//141
    val data = List(inputSize) { readln().map { it.digitToInt() } }
    println("Part 1: ${solve(data, inputSize, Pair(0, 3))}")
    println("Part 2: ${solve(data, inputSize, Pair(3, 10))}")
}