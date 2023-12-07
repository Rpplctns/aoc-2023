fun strength(card: String): Int {
    val c = card.groupBy { it }.map { it.value.size }
    if (c.size == 1) return 6
    if (c.size == 2 && c.contains(4)) return 5
    if (c.size == 2) return 4
    if (c.size == 3 && c.contains(3)) return 3
    return 5 - c.size
}

fun strengthJokers(card: String): Int {
    val c = card.filter { it != 'J' }.groupBy { it }.map { it.value.size }
    val j = card.count { it == 'J' }
    if (c.size <= 1) return 6
    if (c.size == 2 && c.contains(1)) return 5
    if (c.size == 2) return 4
    if (c.max() + j == 3) return 3
    if (c.contains(2) && j != 0) return 2
    if (c.size == 3) return 2
    if (c.contains(2) || j != 0) return 1
    return 0
}

fun main() {
    val inputSize = 1000
    val data = List(inputSize) { readln().split(' ') }

    val res1 = data
        .map {
            it
                .let { a ->
                    Triple(
                        a[0].toCharArray()
                            .map {
                                when (it) {
                                    'A' -> 14
                                    'K' -> 13
                                    'Q' -> 12
                                    'J' -> 11
                                    'T' -> 10
                                    else -> it.code - '0'.code
                                }
                            }, strength(a[0]), a[1].toInt()
                    )
                }
        }
        .sortedWith(compareBy(Triple<List<Int>, Int, Int>::second).thenBy { it.first[0] }.thenBy { it.first[1] }
            .thenBy { it.first[2] }.thenBy { it.first[3] }.thenBy { it.first[4] })
        .mapIndexed { index, triple -> (index + 1) * triple.third }
        .sum()
    val res2 = data
        .map {
            it
                .let { a ->
                    Triple(
                        a[0].toCharArray()
                            .map {
                                when (it) {
                                    'A' -> 14
                                    'K' -> 13
                                    'Q' -> 12
                                    'J' -> 1
                                    'T' -> 10
                                    else -> it.code - '0'.code
                                }
                            }, strengthJokers(a[0]), a[1].toInt()
                    )
                }
        }
        .sortedWith(compareBy(Triple<List<Int>, Int, Int>::second).thenBy { it.first[0] }.thenBy { it.first[1] }
            .thenBy { it.first[2] }.thenBy { it.first[3] }.thenBy { it.first[4] })
        .mapIndexed { index, triple -> (index + 1) * triple.third }
        .sum()
    println("Part 1: $res1")
    println("Part 2: $res2")
}