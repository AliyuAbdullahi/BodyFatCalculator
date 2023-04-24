package com.lek.mybodyfatpercentage.onboarding.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.DataSet
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Info
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Loading
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.UserInfoState
import com.lek.mybodyfatpercentage.theme.sharedcomponents.ActionButton

@Composable
fun EnterUserInfoScreen(
    state: UserInfoState,
    onNameChanged: (String) -> Unit = {},
    onAgeChanged: (String) -> Unit = {},
    onWeightChanged: (String) -> Unit = {},
    onActionButtonClicked: () -> Unit = {},
    onGenderChanged: (Gender) -> Unit = {},
    onNavigateToNext: () -> Unit = {}
) {
    when (state) {
        Loading -> LoadingScreen()
        is Info -> InfoComponent(
            state,
            onNameChanged,
            onAgeChanged,
            onWeightChanged,
            onGenderChanged,
            onActionButtonClicked
        )
        DataSet -> {
            LogoAnimation(onNavigateToNext)
        }
    }
}

@Composable
fun InfoComponent(
    userInfo: Info,
    onNameChanged: (String) -> Unit = {},
    onAgeChanged: (String) -> Unit = {},
    onWeightChanged: (String) -> Unit = {},
    onGenderChanged: (Gender) -> Unit = {},
    onActionButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val age = userInfo.age ?: 0
        val theAge = if (age > 0) age.toString() else ""
        val weight = userInfo.bodyWeight ?: 0.0
        val theWeight = if (weight > 0) weight.toString() else ""
        val context = LocalContext.current

        OutlinedTextField(
            value = userInfo.name,
            label = { Text(text = context.getString(R.string.name)) },
            onValueChange = { onNameChanged(it) }
        )

        OutlinedTextField(
            value = theAge,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = context.getString(R.string.age)) },
            onValueChange = { onAgeChanged(it) })

        OutlinedTextField(
            value = theWeight,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = context.getString(R.string.weight)) },
            onValueChange = { onWeightChanged(it) })

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = stringResource(id = R.string.select_gender_at_birth))
        Spacer(modifier = Modifier.size(8.dp))

        GenderSelectionComponent(
            gender = userInfo.gender,
            onGenderSelected = { onGenderChanged(it) }
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = context.getString(R.string.measure_info),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1F))

        if (userInfo.isValid) {
            ActionButton(label = R.string.save) { onActionButtonClicked() }
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
private fun UserInfoComponentInvalidInfoPreview() {
    MyBodyFatPercentageTheme() {
        val info = Info(name = "", age = null, bodyWeight = null)
        InfoComponent(info)
    }
}

@Composable
@Preview
private fun UserInfoComponentValidInfoPreview() {
    MyBodyFatPercentageTheme() {
        val info = Info(name = "Test", age = 30, bodyWeight = 72.0)
        InfoComponent(info)
    }
}

@Composable
@Preview
private fun EnterUserInfoScreenLoadingPreview() {
    MyBodyFatPercentageTheme() {
        EnterUserInfoScreen(state = Loading)
    }
}

@Composable
@Preview
private fun EnterUserInfoScreenDataPreview() {
    MyBodyFatPercentageTheme() {
        val info = Info(name = "Test", age = 30, bodyWeight = 72.0)
        EnterUserInfoScreen(state = info)
    }
}
