package com.lek.mybodyfatpercentage.homescreen.ui.model

import com.lek.mybodyfatpercentage.core.UNDEFINED_LONG
import com.lek.mybodyfatpercentage.onboarding.domain.Gender

data class CreateReadingState(
    val firstReading: String = "",
    val secondReading: String = "",
    val thirdReading: String = "",
    val date: Long = UNDEFINED_LONG,
    val age: String = "",
    val gender: Gender = Gender.UNKNOWN,
    val weight: String = "",
    val creationSuccess: CreationSuccess? = null,
    val creationFailed: Boolean = false
) {
    val isValid: Boolean =
        firstReading.isNotBlank() && secondReading.isNotBlank() && age.isNotBlank() && thirdReading.isNotBlank()
                && gender != Gender.UNKNOWN && weight.isNotBlank()
}

data class CreationSuccess(val percentage: Double)

sealed interface CreateReadingEvent

data class OnFirstReadingChanged(val reading: String) : CreateReadingEvent
data class OnSecondReadingChanged(val reading: String) : CreateReadingEvent
data class OnThirdReadingChanged(val reading: String) : CreateReadingEvent
data class OnWeightChanged(val newWeight: String) : CreateReadingEvent
data class OnAgeChanged(val newAge: String) : CreateReadingEvent
data class OnGenderChanged(val newGender: Gender) : CreateReadingEvent
object OnCalculateClicked : CreateReadingEvent
object DismissDialogClicked : CreateReadingEvent
object NewReadingClicked : CreateReadingEvent
object SaveReadingClicked : CreateReadingEvent
