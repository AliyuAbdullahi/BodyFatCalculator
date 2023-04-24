package com.lek.mybodyfatpercentage.homescreen.ui.model

import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData

data class ReadingListState(
    val readings: List<BodyFatData> = listOf()
) {
    override fun equals(other: Any?): Boolean {
        if (other !is ReadingListState) return false
        if (other.readings.size != readings.size) return false
        return other.readings == readings
    }

    override fun hashCode(): Int {
        return readings.hashCode()
    }
}

sealed interface ReadingListEvent

object DeleteAllReadingsClicked: ReadingListEvent
data class DeleteOneReadingClicked(val timeAdded: Long): ReadingListEvent