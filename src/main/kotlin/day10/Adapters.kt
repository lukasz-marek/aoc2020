package day10

data class Adapter(val output: Int)

interface AdaptersConnector {
    fun connect(adapters: List<Adapter>): Map<Int, Int>
}

class AdaptersConnectorImpl : AdaptersConnector {
    override fun connect(adapters: List<Adapter>): Map<Int, Int> {
        val sortedAdapterValues = adapters.map { it.output }.sorted().toMutableList()
        val lastAdapterValue = sortedAdapterValues.last() + 3
        val firstAdapterValue = 0
        sortedAdapterValues.add(0, firstAdapterValue)
        sortedAdapterValues.add(sortedAdapterValues.size, lastAdapterValue)
        return sortedAdapterValues
            .zipWithNext()
            .fold(emptyMap()) { acc, pair ->
                val diff = pair.second - pair.first
                val previousValue = acc[diff] ?: 0
                acc + (diff to (previousValue + 1))
            }
    }
}

interface ArrangementsCounter {
    fun count(adapters: List<Adapter>): Long
}