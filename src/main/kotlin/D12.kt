fun solve (schema: String, numbers: List<Int>): Long {
    val dp = Array(numbers.size) { Array(2) { Array(schema.length) { 0L } } }
    var lastDot = -1
    var firstHash: Int? = null

    for (j in schema.indices) {
        for (k in numbers.indices) {
            if (schema[j] == '.') lastDot = j
            if (schema[j] == '#' && firstHash == null) firstHash = j

            dp[k][0][j] =
                if (j - lastDot < numbers[k] || (j - numbers[k] >= 0 && schema[j - numbers[k]] == '#')) 0
                else if (k == 0 && firstHash != null && j - firstHash > numbers[k]) 0
                else
                    if (k > 0) if (j - numbers[k] - 1 >= 0) dp[k - 1][1][j - numbers[k] - 1] else 0
                    else 1

            dp[k][1][j] =
                if (schema[j] == '?') if (j > 0) dp[k][0][j] + dp[k][1][j - 1] else dp[k][0][j]
                else if (schema[j] == '#') dp[k][0][j]
                else if (j == 0) 0 else dp[k][1][j - 1]
        }
    }
    return dp[numbers.size - 1][1][schema.length - 1]
}

fun main() {
    val inputSize = 1000
    var res1 = 0L
    var res2 = 0L
    for (i in 0..<inputSize) {
        val (s, n) = readln().split(' ').let { Pair(it[0], it[1].split(',').map { a -> a.toInt() }) }
        res1 += solve(s, n)
        res2 += solve("$s?".repeat(4).plus(s), listOf(n, n, n, n, n).flatten())
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}