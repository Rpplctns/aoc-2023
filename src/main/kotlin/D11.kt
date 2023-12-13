fun main() {
    val inputSize = 140
    val x = Array(inputSize) { 0 }
    val y = Array(inputSize) { 0 }
    for (i in 0..<inputSize) {
        val data = readln().map { it == '#' }
        y[i] = data.count { it }
        data.forEachIndexed { index, b -> if (b) x[index]++ }
    }

    var enc = 0L
    var l = 0L
    var res1 = 0L
    for (i in 0..<inputSize) {
        if (x[i] > 0) {
            enc += x[i]
            res1 += l * x[i]
            l += enc
        }
        else {
            l += 2 * enc
        }
    }
    enc = 0L
    l = 0L
    for (i in 0..<inputSize) {
        if (y[i] > 0) {
            enc += y[i]
            res1 += l * y[i]
            l += enc
        }
        else {
            l += 2 * enc
        }
    }

    enc = 0L
    l = 0L
    var res2 = 0L
    for (i in 0..<inputSize) {
        if (x[i] > 0) {
            enc += x[i]
            res2 += l * x[i]
            l += enc
        }
        else {
            l += 1000000L * enc
        }
    }
    enc = 0L
    l = 0L
    for (i in 0..<inputSize) {
        if (y[i] > 0) {
            enc += y[i]
            res2 += l * y[i]
            l += enc
        }
        else {
            l += 1000000L * enc
        }
    }
    println("Part 1: $res1")
    println("Part 2: $res2")
}