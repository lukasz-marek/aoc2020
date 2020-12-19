package day18

val firstPrecedenceTable = mapOf('+' to 0, '*' to 0)
val secondPrecedenceTable = mapOf('+' to 1, '*' to 0)
fun convertToReversePolishNotation(expression: String, precedenceTable: Map<Char, Int>): List<Char> {
    val outputQueue = ArrayDeque<Char>()
    val stack = mutableListOf<Char>()
    for (symbol in expression.asSequence().filterNot { it.isWhitespace() }) {
        when {
            symbol.isDigit() -> outputQueue.add(symbol)
            symbol.isOperator() -> {
                while (stack.isNotEmpty() && stack.first().isOperator()
                    && precedenceTable[stack.first()]!! >= precedenceTable[symbol]!!
                ) {
                    outputQueue.add(stack.pop())
                }
                stack.push(symbol)
            }
            symbol == '(' -> stack.push(symbol)
            symbol == ')' -> {
                while (stack.isNotEmpty() && stack.first() != '(') {
                    outputQueue.add(stack.pop())
                }
                check(stack.isNotEmpty() && stack.first() == '(')
                stack.pop()
            }
        }
    }
    for (operator in stack) {
        check(operator.isOperator())
        outputQueue.add(operator)
    }
    return outputQueue.toList()
}

private fun Char.isOperator() = this == '+' || this == '*'
private fun <T> MutableList<T>.push(item: T) = add(0, item)
private fun <T> MutableList<T>.pop() = removeAt(0)

fun reduceExpression(expression: List<Char>): Long {
    val operands = mutableListOf<Long>()
    for (symbol in expression) {
        when (symbol) {
            '*' -> {
                val product = operands.pop() * operands.pop()
                operands.push(product)
            }
            '+' -> {
                val sum = operands.pop() + operands.pop()
                operands.push(sum)
            }
            else -> {
                operands.push(Character.getNumericValue(symbol).toLong())
            }
        }
    }
    check(operands.size == 1)
    return operands.last()
}