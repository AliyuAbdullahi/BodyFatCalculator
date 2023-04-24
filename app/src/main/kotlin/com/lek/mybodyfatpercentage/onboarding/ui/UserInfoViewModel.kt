package com.lek.mybodyfatpercentage.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.onboarding.domain.isInvalid
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.GetUserInfoUseCase
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.SetUserInfoUseCase
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.ActionClicked
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.AgeChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.DataSet
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.GenderChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Info
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Loading
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.NameChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.UserInfoEvent
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.UserInfoState
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.WeightChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.toUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val setUserInfoUseCase: SetUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private var currentState: UserInfoState = Loading

    fun initialize() {
        viewModelScope.launch {
            val userInfo = getUserInfoUseCase()
            if (userInfo.isInvalid()) {
                // get what we have
                val age = userInfo?.age ?: 0
                val name = userInfo?.name.orEmpty()
                val weight = userInfo?.bodyWeight ?: 0.0
                val info = Info(name = name, age = age, bodyWeight = weight)
                updateState(info)
            } else {
                updateState(DataSet)
            }
        }
    }

    private val mutableSharedFlowState: MutableSharedFlow<UserInfoState> =
        MutableSharedFlow(replay = 1)
    val userInfoState: Flow<UserInfoState> = mutableSharedFlowState

    private fun updateState(reducer: UserInfoState.() -> UserInfoState) {
        val updatedState = currentState.reducer()
        currentState = updatedState
        mutableSharedFlowState.tryEmit(currentState)
    }

    private fun updateState(newState: UserInfoState) {
        currentState = newState
        mutableSharedFlowState.tryEmit(currentState)
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            val info = (currentState as Info).toUserInfo()
            setUserInfoUseCase(info)
            updateState(DataSet)
        }
    }

    private fun getAge(age: String): Int =
        if (age.isEmpty()) {
            0
        } else {
            age.replace(".", "")
            age.toInt()
        }

    private fun getWeight(weight: String): Double =
        if (weight.isEmpty()) {
            0.0
        } else {
            weight.replace("..", ".").toDouble()
        }

    fun handleEvent(event: UserInfoEvent) {
        when (event) {
            ActionClicked -> setUserInfo()
            is AgeChanged -> updateState { (currentState as Info).copy(age = getAge(event.newAge)) }
            is NameChanged -> updateState { (currentState as Info).copy(name = event.newName) }
            is GenderChanged -> updateState( (currentState as Info).copy(gender = event.newGender) )
            is WeightChanged -> updateState {
                (currentState as Info).copy(
                    bodyWeight = getWeight(
                        event.newWeight
                    )
                )
            }
        }
    }
}