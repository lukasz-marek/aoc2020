package day25

fun calculateLoopNumber(publicKey: Long): Long {
    val subjectNumber = 7L
    val divider = 20201227L
    var value = 1L
    var loopNumber = 0L

    while (value != publicKey) {
        loopNumber += 1
        value = (value * subjectNumber) % divider
    }

    return loopNumber
}