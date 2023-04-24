package com.lek.mybodyfatpercentage.homescreen.ui

import androidx.lifecycle.viewModelScope
import com.lek.mybodyfatpercentage.core.BaseViewModel
import com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.CreateBodyFatReadingUseCase
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.SaveBodyFatDataUseCase
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreateReadingEvent
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreateReadingState
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreationSuccess
import com.lek.mybodyfatpercentage.homescreen.ui.model.DismissDialogClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.NewReadingClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnAgeChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnCalculateClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnFirstReadingChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnGenderChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnSecondReadingChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnThirdReadingChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.OnWeightChanged
import com.lek.mybodyfatpercentage.homescreen.ui.model.SaveReadingClicked
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CreateBodyFatReadingViewModel @Inject constructor(
    private val createBodyFatReadingUseCase: CreateBodyFatReadingUseCase,
    private val saveBodyFatDataUseCase: SaveBodyFatDataUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<CreateReadingState, CreateReadingEvent>() {

    init {
        viewModelScope.launch {
            getUserInfoUseCase()?.let { userInfo ->
                updateState {
                    val age = if (userInfo.age != null) userInfo.age.toString() else ""
                    val weight =
                        if (userInfo.bodyWeight != null) userInfo.bodyWeight.toString() else ""
                    copy(gender = userInfo.gender, age = age, weight = weight)
                }
            }
        }
    }

    private fun calculateResult() {
        val createBodyFatData = BodyFatCalculationData(
            firstReading = state.firstReading.toDouble(),
            secondReading = state.secondReading.toDouble(),
            thirdRecording = state.thirdReading.toDouble(),
            weight = state.weight.toDouble(),
            age = state.age.toInt(),
            gender = state.gender
        )

        viewModelScope.launch {
            try {
                val result = createBodyFatReadingUseCase(createBodyFatData)
                updateState { copy(creationSuccess = CreationSuccess(result.reading)) }
            } catch (error: Throwable) {
                updateState { copy(creationFailed = true) }
            }
        }
    }

    private fun saveCurrentReading() {
        state.creationSuccess?.let { successData ->
            val data = BodyFatData(
                date = System.currentTimeMillis(),
                reading = successData.percentage,
                bodyWeightUsed = state.weight.toDouble()
            )
            viewModelScope.launch {
                saveBodyFatDataUseCase(data)
            }
        }
    }

    override fun initialState(): CreateReadingState = CreateReadingState()

    override fun handleEvent(event: CreateReadingEvent) {
        when (event) {
            OnCalculateClicked -> calculateResult()
            is OnFirstReadingChanged -> updateState { copy(firstReading = event.reading) }
            is OnSecondReadingChanged -> updateState { copy(secondReading = event.reading) }
            is OnThirdReadingChanged -> updateState { copy(thirdReading = event.reading) }
            is OnAgeChanged -> updateState { copy(age = event.newAge) }
            is OnGenderChanged -> updateState { copy(gender = event.newGender) }
            is OnWeightChanged -> updateState { copy(weight = event.newWeight) }
            DismissDialogClicked -> updateState { copy(creationSuccess = null) }
            NewReadingClicked -> clearState()
            SaveReadingClicked -> saveCurrentReading()
        }
    }
}