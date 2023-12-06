import kotlin.math.*

fun calculate(a: Long, b: Long): Long {
    val delta: Double = (a * a - 4 * b).toDouble()
    if (delta <= 0) return 0
    val x1 = max(ceil((a - sqrt(delta)) / 2).toLong(), 0)
    val x2 = min(floor((a + sqrt(delta)) / 2).toLong(), b)
    if (sqrt(delta) == floor(sqrt(delta))) return x2 - x1 - 1
    return x2 - x1 + 1
}

fun main() {
    val times = readln().split(":\\s+".toRegex())[1]
    val distances = readln().split(":\\s+".toRegex())[1]
    val timesSeparate = times.split("\\s+".toRegex()).map(String::toLong)
    val distancesSeparate = distances.split("\\s+".toRegex()).map(String::toLong)
    val timeMerged = times.replace(" ", "").toLong()
    val distanceMerged = distances.replace(" ", "").toLong()
    println(
        "Part 1: " + timesSeparate
            .zip(distancesSeparate)
            .map { calculate(it.first, it.second) }
            .reduce { acc, l -> acc * l }
    )
    println("Part 2: " + calculate(timeMerged, distanceMerged))
}