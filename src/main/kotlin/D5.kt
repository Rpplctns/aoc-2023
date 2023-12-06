import kotlin.math.*

class MappingRange(source: Long, destination: Long, range: Long) {
    val begin: Long = source
    val end = source + range
    val offset = destination - source
}

class RangeMap(ranges: List<MappingRange>) {
    private val ranges = ranges.sortedBy { it.begin }

    private fun map(key: Pair<Long, Long>): Set<Pair<Long, Long>> {
        val res = mutableSetOf<Pair<Long, Long>>()
        var beg = 0L
        for (r in ranges) {
            val r1 = Pair(max(key.first, beg), min(key.second, r.begin))
            if (r1.first < r1.second) res.add(r1)
            val r2 = Pair(max(key.first, r.begin) + r.offset, min(key.second, r.end) + r.offset)
            if (r2.first < r2.second) res.add(r2)
            beg = r.end
        }
        val r1 = Pair(max(key.first, beg), key.second)
        if (r1.first < r1.second) res.add(r1)
        return processRangeSet(res)
    }

    fun map(key: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> = processRangeSet(
        key.map { map(it) }.fold(setOf()) { acc, ranges -> acc + ranges }
    )

    fun map(key: Long): Long {
        for (r in ranges) if (key > r.begin && key < r.end) return key + r.offset
        return key
    }

    fun map(key: List<Long>): List<Long> = key.map { map(it) }
}

fun processRangeSet(ranges: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> = ranges
    .sortedBy { it.first }
    .fold(listOf<Pair<Long, Long>>()) { acc, range ->
        if (acc.isNotEmpty() && acc.last().second >= range.first)
            acc.dropLast(1) + Pair(acc.last().first, range.second)
        else acc + range
    }
    .toSet()

fun readRangeMapChunk(): RangeMap {
    var inputLine: String
    val ranges = mutableListOf<MappingRange>()
    while (true) {
        inputLine = readln()
        if (inputLine == "") break
        val data = inputLine.split(' ').map { it.toLong() }
        ranges.add(MappingRange(data[1], data[0], data[2]))
    }
    return RangeMap(ranges)
}

fun main(args: Array<String>) {
    val data = readln()
        .split(":\\s+".toRegex())[1]
        .split(' ')
        .map(String::toLong)
    var data1 = data
    var data2 = data.chunked(2).map { Pair(it[0], it[0] + it[1]) }.toSet()
    for (i in 0..6) {
        while (!readln().contains("map:")) continue
        val map = readRangeMapChunk()
        data1 = map.map(data1)
        data2 = map.map(data2)
    }
    println("Part 1: " + data1.min())
    println("Part 2: " + data2.minOfOrNull { it.first })
}