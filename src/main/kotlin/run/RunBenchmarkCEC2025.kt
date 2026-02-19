package run

import benchmark.cec2025.GMPBCEC2025
import org.um.feri.ears.algorithms.DummyAlgorithm
import org.um.feri.ears.algorithms.NumberAlgorithm
import util.ConfigGroup
import java.io.File

fun main() {
    val configGroup = ConfigGroup.All

    val baseDir = File(System.getProperty("user.dir")).resolve("results").resolve("cec2025")
    val resultsDir = if (configGroup == ConfigGroup.All) baseDir.resolve("all") else baseDir

    require(resultsDir.exists() && resultsDir.isDirectory) {
        "Results directory is missing: ${resultsDir.absolutePath}"
    }

    val algorithms = listOf("ACFPSO", "AMPPSO_BC", "AMPPSO_GI", "DPPSO", "DUS", "PSPSO", "SPSO_AP_AD")
    val players = algorithms
        .map { DummyAlgorithm(it, resultsDir.absolutePath, DummyAlgorithm.FileFormat.CEC_RESULTS_FORMAT) }
        .toCollection(ArrayList<NumberAlgorithm>())

    GMPBCEC2025(ConfigGroup.All, maximization = false).apply {
        isDisplayRatingCharts = true
        setDisplayAdvancedStats(true)
        addAlgorithms(players)
        run(31)
    }
}