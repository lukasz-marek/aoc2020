package day25

fun main(){
    val publicKey1 = 8987316L
    val publicKey2 = 14681524L
//    val loopSize1 = calculateLoopNumber(publicKey1, 7)
    val loopSize2 = calculateLoopNumber(publicKey2, 7)
    val encryptionKey = calculateEncryptionKey(publicKey1, loopSize2)
    println(encryptionKey)
}