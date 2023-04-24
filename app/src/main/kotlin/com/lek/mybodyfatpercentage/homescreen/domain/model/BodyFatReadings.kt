package com.lek.mybodyfatpercentage.homescreen.domain.model

import com.lek.mybodyfatpercentage.onboarding.domain.Gender

data class BodyFatReadings(
    val firstReading: Double = 0.0,
    val secondReading: Double = 0.0,
    val thirdRecording: Double = 0.0
)

data class BodyFatCalculationData(
    val firstReading: Double = 0.0,
    val secondReading: Double = 0.0,
    val thirdRecording: Double = 0.0,
    val weight: Double = 0.0,
    val age: Int = 0,
    val gender: Gender
)

data class BodyFatReadingsState(
    val data: List<BodyFatData>
)

sealed interface BodyFatDataListEvent

object CreateReadingClicked : BodyFatDataListEvent

data class DeleteReadingClicked(val data: BodyFatData) : BodyFatDataListEvent

object DeleteAllClicked: BodyFatDataListEvent


sealed interface CreateBodyFatReadingEvent

data class OnFirstReadingChanged(val reading: Double): CreateBodyFatReadingEvent
data class OnSecondReadingChanged(val reading: Double): CreateBodyFatReadingEvent
data class OnThirdReadingChanged(val reading: Double): CreateBodyFatReadingEvent