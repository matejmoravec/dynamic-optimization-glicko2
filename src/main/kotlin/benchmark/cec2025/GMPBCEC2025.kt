package benchmark.cec2025

import org.um.feri.ears.algorithms.NumberAlgorithm
import org.um.feri.ears.benchmark.SOBenchmark
import org.um.feri.ears.examples.dynopt.utils.Scenario
import org.um.feri.ears.problems.*
import util.ConfigGroup

class GMPBCEC2025(val config: ConfigGroup, val maximization: Boolean = true, val drawLimit: Double = 1e-7) :
    SOBenchmark<NumberSolution<Double>, NumberSolution<Double>, DoubleProblem, NumberAlgorithm>() {

    init {
        name = "Generalized Moving Peaks Benchmark"
        shortName = "GMPB"
        maxEvaluations = 3000   // the value doesn't matter
        maxIterations = 0       // the value doesn't matter
    }

    override fun initAllProblems() {
        when (config) {
            ConfigGroup.All -> initAll()
            ConfigGroup.Dimension -> initDimension()
            ConfigGroup.ChangeFrequency -> initChangeFrequency()
            ConfigGroup.Peak -> initPeak()
            ConfigGroup.Severity -> initSeverity()
        }
    }

    private fun initAll() {
        val settings1 = Scenario.F1.settings  // dummy - just to get settings.FEs
        for (f in listOf(1, 2, 3, 4, 5, 9, 10, 11, 12)) {
            for (k in 0 until settings1.FEs.size) {
                if (settings1.FEs[k] % settings1.changeFrequency == 0) {
                    val problemName = "${shortName.lowercase()}-f${f}k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                    val problem = DummyProblem(problemName, maximization)
                    addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
                }
            }
        }
        val settings2 = Scenario.F6.settings  // dummy - just to get settings.FEs
        for (k in 0 until settings2.FEs.size) {
            if (settings2.FEs[k] % settings2.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f6k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
        val settings3 = Scenario.F7.settings  // dummy - just to get settings.FEs
        for (k in 0 until settings3.FEs.size) {
            if (settings3.FEs[k] % settings3.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f7k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
        val settings4 = Scenario.F8.settings  // dummy - just to get settings.FEs
        for (k in 0 until settings4.FEs.size) {
            if (settings4.FEs[k] % settings4.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f8k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
    }

    private fun initDimension() {
        val settings = Scenario.F2.settings  // dummy - just to get settings.FEs
        for (f in listOf(2, 9, 10)) {
            for (k in 0 until settings.FEs.size) {
                if (settings.FEs[k] % settings.changeFrequency == 0) {
                    val problemName = "${shortName.lowercase()}-f${f}k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                    val problem = DummyProblem(problemName, maximization)
                    addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
                }
            }
        }
    }

    private fun initChangeFrequency() {
        val settingsF2 = Scenario.F2.settings  // dummy - just to get settings.FEs
        for (k in 0 until settingsF2.FEs.size) {
            if (settingsF2.FEs[k] % settingsF2.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f2k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
        val settingsF6 = Scenario.F6.settings  // dummy - just to get settings.FEs
        for (k in 0 until settingsF6.FEs.size) {
            if (settingsF6.FEs[k] % settingsF6.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f6k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
        val settingsF7 = Scenario.F7.settings  // dummy - just to get settings.FEs
        for (k in 0 until settingsF7.FEs.size) {
            if (settingsF7.FEs[k] % settingsF7.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f7k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
        val settingsF8 = Scenario.F8.settings  // dummy - just to get settings.FEs
        for (k in 0 until settingsF8.FEs.size) {
            if (settingsF8.FEs[k] % settingsF8.changeFrequency == 0) {
                val problemName = "${shortName.lowercase()}-f8k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                val problem = DummyProblem(problemName, maximization)
                addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
            }
        }
    }

    private fun initPeak() {
        val settings = Scenario.F1.settings  // dummy - just to get settings.FEs
        for (f in listOf(1, 2, 3, 4, 5)) {
            for (k in 0 until settings.FEs.size) {
                if (settings.FEs[k] % settings.changeFrequency == 0) {
                    val problemName = "${shortName.lowercase()}-f${f}k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                    val problem = DummyProblem(problemName, maximization)
                    addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
                }
            }
        }
    }

    private fun initSeverity() {
        val settings = Scenario.F2.settings  // dummy - just to get settings.FEs
        for (f in listOf(2, 11, 12)) {
            for (k in 0 until settings.FEs.size) {
                if (settings.FEs[k] % settings.changeFrequency == 0) {
                    val problemName = "${shortName.lowercase()}-f${f}k${k}" // Must be the same as in the DummyAlgorithm results key (HashMap)
                    val problem = DummyProblem(problemName, maximization)
                    addTask(problem, stopCriterion, maxEvaluations, 0, maxIterations)
                }
            }
        }
    }

    override fun addTask(
        problem: DoubleProblem?,
        stopCriterion: StopCriterion?,
        maxEvaluations: Int,
        time: Long,
        maxIterations: Int
    ) {
        tasks.add(
            Task<NumberSolution<Double>, DoubleProblem>(
                problem,
                stopCriterion,
                maxEvaluations,
                time, maxIterations
            )
        )
    }
}