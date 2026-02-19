package run

import benchmark.cec2025.GMPBCutpoint
import org.um.feri.ears.algorithms.DummyAlgorithm
import org.um.feri.ears.algorithms.NumberAlgorithm
import util.AlgorithmPerformance
import util.EvalMetrics
import util.GMPBInstance
import util.generatePlot
import java.io.File

fun main() {
    val config = GMPBInstance.F10.config

    val resultsDir = File(System.getProperty("user.dir")).resolve("results").resolve("cec2025")
        .resolve(
            "GMPB_Peaks${config.peaks}_" +
                    "ChangeFrequency${config.changeFrequency}_" +
                    "ShiftSeverity${config.shiftSeverity}_" +
                    "Environments${config.environments}_" +
                    "Dim${config.dimensions}"
        )

    require(resultsDir.exists() && resultsDir.isDirectory) {
        "Results directory is missing: ${resultsDir.absolutePath}"
    }

    val algorithms = listOf("ACFPSO", "AMPPSO_BC", "AMPPSO_GI", "DPPSO", "DUS", "PSPSO", "SPSO_AP_AD")
    val algorithmsPerformance = algorithms.map { AlgorithmPerformance(name = it) }
    val players = algorithms
        .map { DummyAlgorithm(it, resultsDir.absolutePath, DummyAlgorithm.FileFormat.CEC_RESULTS_FORMAT) }
        .toCollection(ArrayList<NumberAlgorithm>())

    for (k in 0 until config.FEs.size) {
        val benchmark = GMPBCutpoint(k, maximization = false).apply {
            isDisplayRatingCharts = false
            setDisplayAdvancedStats(false)
            addAlgorithms(players)
            run(config.numRuns)
        }
        val tournamentResults = benchmark.tournamentResults
        tournamentResults.players.forEach { player ->
            algorithmsPerformance.firstOrNull { it.name == player.id }?.apply {
                evalMetrics.add(EvalMetrics(player.glicko2Rating.rating, player.glicko2Rating.ratingDeviation))
            }
        }
    }

    val fromEnvironment = 1
    val toEnvironment = 10

    generatePlot(
        config = config,
        algorithms = algorithmsPerformance,
        minX = if (fromEnvironment == 1) 0 else config.envIndexes[fromEnvironment - 2],
        maxX = config.envIndexes[toEnvironment - 1],
        filename = "ConfidenceGraph_CutpointInterval${config.cutpointInterval}_Environment${fromEnvironment}-${toEnvironment}.png"
    )
}