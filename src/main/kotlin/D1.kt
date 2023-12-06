val numbers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun calculate(s: String) = s.filter { it.isDigit() }.let { (it.first().code - '0'.code) * 10 + it.last().code - '0'.code }

fun calculateSpelled(s: String): Int {
    val firstSpelled = numbers.map { Pair(s.indexOf(it.key), it.value) }.filter { it.first != -1 }.minByOrNull { it.first }
    val lastSpelled = numbers.map { Pair(s.lastIndexOf(it.key), it.value) }.filter { it.first != -1 }.maxByOrNull { it.first }
    val firstDigit = "123456789".map { Pair(s.indexOf(it), it.code - '0'.code) }.filter { it.first != -1 }.minByOrNull { it.first }
    val lastDigit = "123456789".map { Pair(s.lastIndexOf(it), it.code - '0'.code) }.filter { it.first != -1 }.maxByOrNull { it.first }
    return listOf(firstSpelled, firstDigit).filterNotNull().minBy { it.first }.second * 10 +
            listOf(lastSpelled, lastDigit).filterNotNull().maxBy { it.first }.second
}

fun main() {
    val inputSize = 1000
    var res1 = 0
    var res2 = 0
    repeat(inputSize) {
        val inputLine = readln()
        res1 += calculate(inputLine)
        res2 += calculateSpelled(inputLine)
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}