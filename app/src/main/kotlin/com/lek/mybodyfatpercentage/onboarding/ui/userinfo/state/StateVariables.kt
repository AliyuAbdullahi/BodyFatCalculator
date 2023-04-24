package com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state

import androidx.compose.runtime.Stable
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo

@Stable
sealed interface UserInfoState

object Loading : UserInfoState
data class Info(
    val name: String,
    val age: Int?,
    val bodyWeight: Double?,
    val gender: Gender = Gender.UNKNOWN
) : UserInfoState {

    val isValid = gender != Gender.UNKNOWN &&
            name.isNotBlank() &&
            age != null &&
            age > 0 &&
            bodyWeight != null &&
            bodyWeight > 10.0
}

object DataSet : UserInfoState

sealed interface UserInfoEvent

data class NameChanged(val newName: String) : UserInfoEvent
data class AgeChanged(val newAge: String) : UserInfoEvent
data class WeightChanged(val newWeight: String) : UserInfoEvent
data class GenderChanged(val newGender: Gender) : UserInfoEvent
object ActionClicked : UserInfoEvent

fun Info.toUserInfo(): UserInfo = UserInfo(
    name = name,
    age = age,
    bodyWeight = bodyWeight,
    gender = gender
)