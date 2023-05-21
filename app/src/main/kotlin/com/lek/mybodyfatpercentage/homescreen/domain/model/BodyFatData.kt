package com.lek.mybodyfatpercentage.homescreen.domain.model

const val INVALID_DATE = -1L
const val INVALID_READING = -0.0

data class BodyFatData(
    val date: Long,
    val reading: Double,
    val bodyWeightUsed: Double
) {
    override fun toString(): String {
        return "($date; $reading; $bodyWeightUsed)"
    }

    var isFirst: Boolean = false

    val isValid = date > 0 && reading > 0.0 && bodyWeightUsed > 0
}

fun bodyFatFromString(string: String): BodyFatData {
    val split = string.replace("(", "")
        .replace(")", "").split(";")

    val date = split.getOrNull(0)?.trim()?.toLong() ?: INVALID_DATE
    val reading = split.getOrNull(1)?.trim()?.toDouble() ?: INVALID_READING
    val bodyWeight = split.getOrNull(2)?.trim()?.toDouble() ?: INVALID_READING
    return BodyFatData(date, reading, bodyWeight)
}

fun bodyFatDataFromStringList(list: String): List<BodyFatData> {
    return when {
        list.isEmpty() -> {
            emptyList()
        }
        else -> {
            val cleanString = list.replace("[", "").replace("]", "")
            if (cleanString.isEmpty()) emptyList()
            else
                list.replace("[", "").replace("]", "")
                    .split(",").map { bodyFatFromString(it) }.filter { it.isValid }
        }
    }
}


fun bodyFatListToString(list: List<BodyFatData>): String = list.toString()