package com.lek.mybodyfatpercentage.onboarding.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import com.lek.mybodyfatpercentage.onboarding.ui.UserInfoViewModel
import com.lek.mybodyfatpercentage.homescreen.ui.MainActivity
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.ActionClicked
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.AgeChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.GenderChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Loading
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.NameChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.WeightChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OnboardingScreen(viewModel)
            viewModel.initialize()
        }
    }

    @Composable
    private fun OnboardingScreen(viewModel: UserInfoViewModel) {
        MyBodyFatPercentageTheme() {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val state = viewModel.userInfoState.collectAsState(initial = Loading).value
                val context = LocalContext.current
                EnterUserInfoScreen(
                    state = state,
                    onNameChanged = { viewModel.handleEvent(NameChanged(it)) },
                    onAgeChanged = { viewModel.handleEvent(AgeChanged(it)) },
                    onWeightChanged = { viewModel.handleEvent(WeightChanged(it)) },
                    onActionButtonClicked = { viewModel.handleEvent(ActionClicked) },
                    onGenderChanged = { viewModel.handleEvent(GenderChanged(it)) },
                    onNavigateToNext = {
                        context.startActivity(
                            Intent(
                                this@OnboardingActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                )
            }
        }
    }
}