package com.lek.mybodyfatpercentage.homescreen.ui.model

import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData

data class ReadingListState(
    val readings: List<BodyFatData> = listOf(),
    val isDeleteDialogShowing: Boolean = false,
    val selected: BodyFatData? = null
)

sealed interface ReadingListEvent

object DeleteAllReadingsClicked : ReadingListEvent
data class DeleteOneReadingClicked(val timeAdded: Long) : ReadingListEvent
object DeleteReadingConfirmed : ReadingListEvent
object DialogDismissed : ReadingListEvent