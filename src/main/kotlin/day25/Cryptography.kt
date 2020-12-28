package day25

fun calculateLoopNumber(publicKey: Long, subjectNumber: Long): Long {
    val divider = 20201227L
    var value = 1L
    var loopNumber = 0L

    while (value != publicKey) {
        loopNumber += 1
        value = (value * subjectNumber) % divider
    }

    return loopNumber
}

fun calculateEncryptionKey(publicKey: Long, loopNumber: Long): Long =
    (1 ..loopNumber).fold(1){acc, _ -> (acc * publicKey) % 20201227L}
