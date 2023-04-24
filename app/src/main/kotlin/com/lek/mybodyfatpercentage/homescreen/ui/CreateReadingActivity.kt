package com.lek.mybodyfatpercentage.homescreen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.lek.mybodyfatpercentage.homescreen.ui.componoent.CreateReadingScreen
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreateReadingState
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
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReadingActivity : ComponentActivity() {

    private val viewModel: CreateBodyFatReadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val currentState =
                viewModel.stateFlow.collectAsState(initial = CreateReadingState()).value
            MyBodyFatPercentageTheme {
                CreateReadingScreen(
                    state = currentState,
                    onFirstReadingChanged = { viewModel.handleEvent(OnFirstReadingChanged(it)) },
                    onSecondReadingChanged = { viewModel.handleEvent(OnSecondReadingChanged(it)) },
                    onThirdReadingChanged = { viewModel.handleEvent(OnThirdReadingChanged(it)) },
                    onAgeChanged = { viewModel.handleEvent(OnAgeChanged(it)) },
                    onWeightChanged = { viewModel.handleEvent(OnWeightChanged(it)) },
                    onGenderChanged = { viewModel.handleEvent(OnGenderChanged(it)) },
                    onBackPressed = { this@CreateReadingActivity.finish() },
                    onCalculateClicked = { viewModel.handleEvent(OnCalculateClicked) },
                    onDialogOkayClicked = {
                        viewModel.handleEvent(SaveReadingClicked)
                        this@CreateReadingActivity.finish()
                    },
                    onDialogNewInputClicked = { viewModel.handleEvent(NewReadingClicked) },
                    onDialogCancelClicked = { viewModel.handleEvent(DismissDialogClicked) },
                    onDialogBackgroundClicked = { viewModel.handleEvent(DismissDialogClicked) }
                )
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }
}