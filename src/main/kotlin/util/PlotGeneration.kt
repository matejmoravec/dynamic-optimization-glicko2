package util

import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.geom.geomRibbon
import org.jetbrains.letsPlot.geom.geomVLine
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.label.labs
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleXContinuous
import org.jetbrains.letsPlot.scale.scaleYContinuous
import org.jetbrains.letsPlot.themes.elementText
import org.jetbrains.letsPlot.themes.theme
import java.io.File


fun generatePlot(
    config: GMPBConfig,
    algorithms: List<AlgorithmPerformance>,
    width: Int = 1500,
    height: Int = 750,
    minX: Int? = null,
    maxX: Int? = null,
    minY: Double? = null,
    maxY: Double? = null,
    showVerticalLines: Boolean = true,
    savePath: String? = null,
    filename: String = "plot.svg"
): Plot {
    require(algorithms.isNotEmpty()) {
        "At least one algorithm must be provided."
    }
    require(algorithms.all { it.evalMetrics.size == algorithms.first().evalMetrics.size }) {
        "All algorithms must have the same number of evaluations."
    }

    var plot = letsPlot()

    val xValues = algorithms.first().evalMetrics.indices

    plot = addAlgorithmLayers(plot, algorithms, xValues)
    plot = applyAxes(plot, config, xValues, minX, maxX, minY, maxY, showVerticalLines, algorithms)
    plot = applyThemeAndLabels(plot, width, height)

    savePlot(plot, savePath, filename)

    return plot
}

private fun addAlgorithmLayers(
    plot: Plot,
    algorithms: List<AlgorithmPerformance>,
    xValues: IntRange
): Plot {
    var p = plot

    algorithms.forEach { algorithm ->
        val ratings = algorithm.evalMetrics.map { it.rating }
        val lower = algorithm.evalMetrics.map { it.rating - 2 * it.deviation }
        val upper = algorithm.evalMetrics.map { it.rating + 2 * it.deviation }

        val data = mapOf(
            "cutpoint" to xValues,
            "rating" to ratings,
            "lower" to lower,
            "upper" to upper,
            "Algorithm" to List(ratings.size) { algorithm.name }
        )

        p += geomLine(data) {
            x = "cutpoint"
            y = "rating"
            color = "Algorithm"
            linetype = "Algorithm"
        }

        p += geomRibbon(
            data = data,
            alpha = 0.1,
            color = "rgba(0,0,0,0)",
            showLegend = false
        ) {
            x = "cutpoint"
            ymin = "lower"
            ymax = "upper"
            fill = "Algorithm"
        }

        p += geomPoint(data) {
            x = "cutpoint"
            y = "rating"
            color = "Algorithm"
            shape = "Algorithm"
        }
    }

    return p
}

private fun applyAxes(
    plot: Plot,
    config: GMPBConfig,
    xValues: IntRange,
    minX: Int?,
    maxX: Int?,
    minY: Double?,
    maxY: Double?,
    showVerticalLines: Boolean,
    algorithms: List<AlgorithmPerformance>
): Plot {
    var p = plot

    val xMin = minX ?: 0
    val xMax = maxX ?: xValues.last

    // TODO: enable dynamic Y-axis limits once Lets-Plot bug is fixed
    // val allRatings = algorithms.flatMap { it.evalMetrics.map { it.rating } }
    // val yMin = minY ?: (allRatings.minOrNull()?.let { it - 2 * 50 } ?: 800.0)
    // val yMax = maxY ?: (allRatings.maxOrNull()?.let { it + 2 * 50 } ?: 2200.0)
    val yMin = minY ?: 800.0
    val yMax = maxY ?: 2200.0

    if (showVerticalLines) {
        (listOf(0) + config.envIndexes).forEach {
            p += geomVLine (
                xintercept = it,
                color = "lightgrey",
                linetype = "dashed",
            )
        }
    }

    p += scaleXContinuous(
        breaks = listOf(0) + config.envIndexes,
        labels = listOf("1") + config.envIndexes.map { config.FEs[it].toString() },
        limits = xMin to xMax
    )

    p += scaleYContinuous(
        limits = yMin to yMax,
        format = "{d}"
    )

    return p
}

private fun applyThemeAndLabels(
    plot: Plot,
    width: Int,
    height: Int
): Plot {
    return plot +
            ggsize(width, height) +
            labs(
                x = "Number of Fitness Evaluations",
                y = "Rating",
                color = "Algorithm"
            ) +
            theme(
                title = elementText(face = "bold"), // 'title' applies to plot's title, subtitle, and caption
                axisTitleX = elementText(size = 28),
                axisTextX = elementText(size = 24),
                axisTitleY = elementText(size = 28),
                axisTextY = elementText(size = 24),
                legendTitle = elementText(size = 28),
                legendText = elementText(size = 24)
            ).legendPositionTop()
}

private fun savePlot(
    plot: Plot,
    savePath: String?,
    filename: String
) {
    val dir = savePath?.let { File(it) } ?: File(System.getProperty("user.dir")).resolve("plots")
    if (!dir.exists()) dir.mkdirs()

    ggsave(plot, path = dir.absolutePath, filename = filename)
}