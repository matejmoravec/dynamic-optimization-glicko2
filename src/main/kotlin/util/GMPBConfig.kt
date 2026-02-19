package util

data class GMPBConfig(
    val numRuns: Int,
    val cutpointInterval: Int,
    val peaks: Int,
    val dimensions: Int,
    val changeFrequency: Int,
    val shiftSeverity: Int,
    val environments: Int
) {
    val FEs: List<Int>
        get() = buildList {
            add(1)
            addAll((cutpointInterval..(changeFrequency * environments) step cutpointInterval).flatMap { i ->
                if (i % changeFrequency == 0 && i < changeFrequency * environments) listOf(i, i + 1)
                else listOf(i)
            })
        }
    val envIndexes: List<Int>   // TODO: works only for 10, 100, 1000?
        get() = buildList {
            val envIndex = (changeFrequency / cutpointInterval) + 1
            addAll(((envIndex - 1)..(envIndex * environments) step envIndex))
        }
}