package run

import benchmark.cec2025.GMPBSingle
import org.um.feri.ears.algorithms.DummyAlgorithm
import org.um.feri.ears.algorithms.NumberAlgorithm
import util.GMPBInstance
import java.io.File

fun main() {
    val config = GMPBInstance.F10.config

    val resultsDir = File(System.getProperty("user.dir")).resolve("results").resolve("edolab")
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

    val algorithms = listOf("AMPPSO_BC", "AMPPSO_GI", "SPSO_AP_AD", "AMPPSO", "DynPopDE", "DSPSO", "mjDE", "mQSO")
    val players = algorithms
        .map { DummyAlgorithm(it, resultsDir.absolutePath, DummyAlgorithm.FileFormat.CEC_RESULTS_FORMAT) }
        .toCollection(ArrayList<NumberAlgorithm>())

    GMPBSingle(config).apply {
        isDisplayRatingCharts = true
        setDisplayAdvancedStats(true)
        addAlgorithms(players)
        run(config.numRuns)
    }
}