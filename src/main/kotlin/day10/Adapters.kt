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

class ArrangementsCounterImpl : ArrangementsCounter {
    override fun count(adapters: List<Adapter>): Long {
        val frontier = mapOf(Adapter(0) to 1L)
        val sortedAdapters = adapters.sortedBy { it.output }
        val lastAdapter = Adapter(sortedAdapters.last().output + 3)
        return count(frontier, sortedAdapters + lastAdapter, 0L)
    }

    private tailrec fun count(frontier: Map<Adapter, Long>, adapters: List<Adapter>, acc: Long): Long {
        val newFrontier = frontier.asSequence().map { entry ->
            entry to adapters.filter { adapter ->
                val diff = adapter.output - entry.key.output
                diff in 1..3
            }
        }.map { pair ->
            val multiplier = pair.first.value
            pair.second.map { it to multiplier }
        }
            .fold(mutableMapOf<Adapter, Long>()) { frontierInProgress, list ->
                list.forEach { frontierElement ->
                    val oldValue = frontierInProgress[frontierElement.first] ?: 0
                    val newValue = oldValue + frontierElement.second
                    frontierInProgress[frontierElement.first] = newValue
                }
                frontierInProgress
            }
        val newAcc = acc + frontier.filter { it.key == adapters.last() }.map { it.value }.sum()
        return if (newFrontier.isEmpty()) newAcc else count(newFrontier, adapters, newAcc)
    }

}