fun main() {
    val inputSize = 140
    val labyrinth = Array(inputSize) { readln().toCharArray().toTypedArray() }
    val path = Array(inputSize) {Array(inputSize) { false } }
    var i = 0
    var j = 0
    while (labyrinth[i][j] != 'S') {
        i++
        j += if (i == inputSize) 1 else 0
        i%=inputSize
    }
    path[i][j] = true
    var dir = 0
    i--
    var steps = 1
    while (labyrinth[i][j] != 'S') {
        if (labyrinth[i][j] == 'J') {
            dir = if (dir == 1) 0 else 3
        }
        if (labyrinth[i][j] == '7') {
            dir = if (dir == 1) 2 else 3
        }
        if (labyrinth[i][j] == 'L') {
            dir = if (dir == 2) 1 else 0
        }
        if (labyrinth[i][j] == 'F') {
            dir = if (dir == 0) 1 else 2
        }
        path[i][j] = true
        if (dir == 0) i--
        if (dir == 1) j++
        if (dir == 2) i++
        if (dir == 3) j--
        steps++
    }

    println("Part 1: " + steps / 2)

    var inside: Boolean
    var lastChar = '.'
    var res2 = 0

    for (i in 0..<140) {
        inside = false
        for (j in 0..<140) {
            if (path[i][j]) {
                if (labyrinth[i][j] == '|') inside = !inside
                if ("FL".contains(labyrinth[i][j])) {
                    lastChar = labyrinth[i][j]
                    inside = !inside
                }
                if ((labyrinth[i][j] == '7' && lastChar == 'F') || (labyrinth[i][j] == 'J' && lastChar == 'L'))
                    inside = !inside
            }
            else if (inside) res2++
        }
    }

    println("Part 2: $res2")
}